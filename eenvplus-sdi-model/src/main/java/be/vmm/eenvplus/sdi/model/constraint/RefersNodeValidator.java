package be.vmm.eenvplus.sdi.model.constraint;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.SynchronizationType;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.vividsolutions.jts.geom.Geometry;

public class RefersNodeValidator implements
		ConstraintValidator<RefersNode, NodeValue> {

	@PersistenceContext(unitName = "eenvplus" , synchronization = SynchronizationType.UNSYNCHRONIZED)
	protected EntityManager entityManager;

	protected NodePosition nodePosition;
	protected Class<?> nodeType;
	protected String nodeGeometryName;
	protected double maxDistance;

	@Override
	public void initialize(RefersNode annotation) {
		nodePosition = annotation.nodePosition();
		nodeType = annotation.nodeType();
		nodeGeometryName = annotation.nodeGeometryName();
		maxDistance = annotation.maxDistance();
	}

	@Override
	public boolean isValid(NodeValue value, ConstraintValidatorContext context) {

		if (value == null || value.getReference() == null
				|| value.getGeometry() == null)
			return true;

		try {
			Object node = entityManager.find(nodeType, value.getReference());
			if (node == null) {
				return false;
			}

			Geometry geometry = value.getGeometry(nodePosition);

			Geometry nodeGeometry = (Geometry) EntityUtils.getPropertyValue(
					entityManager.getMetamodel().entity(nodeType),
					nodeGeometryName, node);
			return nodeGeometry.distance(geometry) < maxDistance;
		} catch (Exception e) {
			return false;
		}
	}
}
