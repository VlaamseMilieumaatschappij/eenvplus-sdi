package be.vmm.eenvplus.sdi.model.constraint;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

import javax.persistence.metamodel.EntityType;

public class EntityUtils {

	public static Object getIdValue(EntityType<?> entityType, Object object)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		return getValue(entityType, entityType.getId(Object.class)
				.getJavaMember(), object);
	}

	public static Object getPropertyValue(EntityType<?> entityType,
			String propertyName, Object object)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		return getValue(entityType, entityType.getAttribute(propertyName)
				.getJavaMember(), object);
	}

	public static Object getValue(EntityType<?> entityType, Member member,
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
