package be.vmm.eenvplus.sdi.model.constraint;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StaticValidator implements ConstraintValidator<Static, Object> {

	@PersistenceContext(unitName = "eenvplus")
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

			CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
			Root<Object> root = criteria.from(clazz);
			EntityType<Object> model = root.getModel();

			Object id = getIdValue(model, value);
			if (id == null)
				return true;

			criteria.select(builder.count(root));

			List<Predicate> predicates = new ArrayList<Predicate>(
					propertyNames.length + 1);

			for (String propertyName : propertyNames) {
				Object propertyValue = getPropertyValue(model, propertyName,
						value);
				predicates.add(builder.equal(root.get(propertyName),
						propertyValue));
			}

			predicates.add(builder.equal(root.get(model.getId(Object.class)),
					id));

			criteria.where(predicates.toArray(new Predicate[predicates.size()]));

			TypedQuery<Long> query = entityManager.createQuery(criteria);

			return query.getSingleResult() != 0;
		} catch (Exception e) {
			return false;
		}
	}

	protected Object getIdValue(EntityType<Object> entityType, Object object)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		return getValue(entityType, entityType.getId(Object.class)
				.getJavaMember(), object);
	}

	protected Object getPropertyValue(EntityType<Object> entityType,
			String propertyName, Object object)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		return getValue(entityType, entityType.getAttribute(propertyName)
				.getJavaMember(), object);
	}

	protected Object getValue(EntityType<Object> entityType, Member member,
			Object object) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {

		if (member instanceof Field) {
			((Field) member).setAccessible(true);
			return ((Field) member).get(object);
		}
		if (member instanceof Method) {
			return ((Method) member).invoke(object);
		}

		throw new IllegalArgumentException("member");
	}
}
