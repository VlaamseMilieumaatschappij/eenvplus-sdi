package be.vmm.eenvplus.sdi.model.constraint;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.SynchronizationType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StaticValidator implements ConstraintValidator<Static, Object> {

	@PersistenceContext(unitName = "eenvplus", synchronization = SynchronizationType.UNSYNCHRONIZED)
	protected EntityManager entityManager;

	protected String[] propertyNames;

	@Override
	public void initialize(Static annotation) {
		propertyNames = annotation.value();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {

		if (value == null)
			return true;

		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();

			@SuppressWarnings("unchecked")
			Class<Object> clazz = (Class<Object>) value.getClass();
			EntityType<Object> model = entityManager.getMetamodel().entity(
					clazz);

			Object id = EntityUtils.getIdValue(model, value);
			if (id == null)
				return true;

			for (String propertyName : propertyNames) {
				CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
				Root<Object> root = criteria.from(clazz);

				criteria.select(builder.count(root));

				Object propertyValue = EntityUtils.getPropertyValue(model,
						propertyName, value);

				criteria.where(
						builder.equal(root.get(propertyName), propertyValue),
						builder.equal(root.get(model.getId(Object.class)), id));

				TypedQuery<Long> query = entityManager.createQuery(criteria);

				if (query.getSingleResult() == 0) {
					return false;
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
