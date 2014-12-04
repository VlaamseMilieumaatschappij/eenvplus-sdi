package be.vmm.eenvplus.sdi.model.constraint;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InValidator implements ConstraintValidator<In, Object> {

	@PersistenceContext(unitName = "eenvplus")
	protected EntityManager entityManager;

	protected Class<?> entityType;

	@Override
	public void initialize(In annotation) {
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