package be.vmm.eenvplus.sdi.model.constraint;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.SynchronizationType;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RefersValidator implements ConstraintValidator<Refers, Object> {

	@PersistenceContext(unitName = "eenvplus", synchronization = SynchronizationType.UNSYNCHRONIZED)
	protected EntityManager entityManager;

	protected Class<?> entityType;

	@Override
	public void initialize(Refers annotation) {
		entityType = annotation.entityType();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {

		if (value == null)
			return true;

		try {
			return entityManager.find(entityType, value) != null;
		} catch (Exception e) {
			return false;
		}
	}
}
