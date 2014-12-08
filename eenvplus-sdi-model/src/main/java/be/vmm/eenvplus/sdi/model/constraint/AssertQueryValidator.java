package be.vmm.eenvplus.sdi.model.constraint;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.SynchronizationType;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import be.vmm.eenvplus.sdi.model.constraint.AssertQuery.AssertQueryCondition;

public class AssertQueryValidator implements
		ConstraintValidator<AssertQuery, Map<String, Object>> {

	@PersistenceContext(unitName = "eenvplus", synchronization = SynchronizationType.UNSYNCHRONIZED)
	protected EntityManager entityManager;

	protected String[] sqls;
	protected AssertQueryCondition condition;

	@Override
	public void initialize(AssertQuery annotation) {
		sqls = annotation.value();
		condition = annotation.condition();

	}

	@Override
	public boolean isValid(Map<String, Object> value,
			ConstraintValidatorContext context) {

		if (value == null)
			return true;

		try {
			for (String sql : sqls) {
				Query query = entityManager.createQuery(sql, Boolean.class);

				for (Map.Entry<String, Object> e : value.entrySet()) {
					query.setParameter((String) e.getKey(), e.getValue());
				}

				if (Boolean.TRUE.equals(query.getSingleResult())) {
					if (condition == AssertQueryCondition.any)
						break;
				} else {
					return false;
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
