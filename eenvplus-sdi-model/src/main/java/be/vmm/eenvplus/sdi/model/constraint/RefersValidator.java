package be.vmm.eenvplus.sdi.model.constraint;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.SynchronizationType;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import be.vmm.eenvplus.sdi.model.type.Reference;
import be.vmm.eenvplus.sdi.model.type.Reference.ReferenceType;

@SuppressWarnings("rawtypes")
public class RefersValidator implements ConstraintValidator<Refers, Reference> {

	@PersistenceContext(unitName = "eenvplus", synchronization = SynchronizationType.UNSYNCHRONIZED)
	protected EntityManager entityManager;

	protected Class<?> entityType;

	@Override
	public void initialize(Refers annotation) {
		entityType = annotation.entityType();
	}

	@Override
	public boolean isValid(Reference value, ConstraintValidatorContext context) {

		if (value == null)
			return true;

		if (value.getType() != ReferenceType.id)
			return false;

		try {
			return entityManager.find(entityType, value.getValue()) != null;
		} catch (Exception e) {
			return false;
		}
	}
}
