package be.vmm.eenvplus.sdi.api.json;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Coercion {

	protected final static Long ZERO = Long.valueOf(0L);

	protected final static Boolean coerceToBoolean(final Object obj)
			throws IllegalArgumentException {

		if (obj == null || "".equals(obj)) {
			return Boolean.FALSE;
		}
		if (obj instanceof Boolean) {
			return (Boolean) obj;
		}
		if (obj instanceof String) {
			return Boolean.valueOf((String) obj);
		}

		throw new IllegalArgumentException();
	}

	protected final static Enum coerceToEnum(final Object obj, Class type) {

		if (obj == null || "".equals(obj)) {
			return null;
		}
		if (obj.getClass().isEnum()) {
			return (Enum) obj;
		}
		return Enum.valueOf(type, obj.toString());
	}

	@SuppressWarnings("deprecation")
	protected final static Date coerceToDate(final Object obj, Class type) {

		if (obj == null || "".equals(obj)) {
			return null;
		}
		if (obj instanceof Date) {
			return (Date) obj;
		}
		if (obj instanceof Number) {
			return new Date(((Number) obj).longValue());
		}
		return new Date(obj.toString());
	}

	protected final static Character coerceToCharacter(final Object obj)
			throws IllegalArgumentException {

		if (obj == null || "".equals(obj)) {
			return Character.valueOf((char) 0);
		}
		if (obj instanceof String) {
			return Character.valueOf(((String) obj).charAt(0));
		}
		if (Number.class.isAssignableFrom(obj.getClass())) {
			return Character.valueOf((char) ((Number) obj).shortValue());
		}
		if (obj instanceof Character) {
			return (Character) obj;
		}

		throw new IllegalArgumentException();
	}

	protected final static Number coerceToNumber(final Object obj) {

		if (obj == null) {
			return ZERO;
		} else if (obj instanceof Number) {
			return (Number) obj;
		} else {
			String str = coerceToString(obj);
			if (isStringFloat(str)) {
				return toFloat(str);
			} else {
				return toNumber(str);
			}
		}
	}

	protected final static Number coerceToNumber(final Number number,
			final Class type) throws IllegalArgumentException {

		if (Long.TYPE == type || Long.class.equals(type)) {
			return Long.valueOf(number.longValue());
		}
		if (Double.TYPE == type || Double.class.equals(type)) {
			return Double.valueOf(number.doubleValue());
		}
		if (Integer.TYPE == type || Integer.class.equals(type)) {
			return Integer.valueOf(number.intValue());
		}
		if (BigInteger.class.equals(type)) {
			if (number instanceof BigDecimal) {
				return ((BigDecimal) number).toBigInteger();
			}
			if (number instanceof BigInteger) {
				return number;
			}
			return BigInteger.valueOf(number.longValue());
		}
		if (BigDecimal.class.equals(type)) {
			if (number instanceof BigDecimal) {
				return number;
			}
			if (number instanceof BigInteger) {
				return new BigDecimal((BigInteger) number);
			}
			if (number instanceof Long) {
				return new BigDecimal((Long) number);
			}
			return new BigDecimal(number.doubleValue());
		}
		if (Byte.TYPE == type || Byte.class.equals(type)) {
			return Byte.valueOf(number.byteValue());
		}
		if (Short.TYPE == type || Short.class.equals(type)) {
			return Short.valueOf(number.shortValue());
		}
		if (Float.TYPE == type || Float.class.equals(type)) {
			return Float.valueOf(number.floatValue());
		}

		throw new IllegalArgumentException();
	}

	protected final static Number coerceToNumber(final Object obj,
			final Class type) throws IllegalArgumentException {

		if (obj == null || "".equals(obj)) {
			return coerceToNumber(ZERO, type);
		}
		if (obj instanceof String) {
			return coerceToNumber((String) obj, type);
		}
		if (Number.class.isAssignableFrom(obj.getClass())) {
			if (obj.getClass().equals(type)) {
				return (Number) obj;
			}
			return coerceToNumber((Number) obj, type);
		}

		if (obj instanceof Character) {
			return coerceToNumber(
					Short.valueOf((short) ((Character) obj).charValue()), type);
		}

		throw new IllegalArgumentException();
	}

	protected final static Number coerceToNumber(final String val,
			final Class type) throws IllegalArgumentException {

		if (Long.TYPE == type || Long.class.equals(type)) {
			return Long.valueOf(val);
		}
		if (Integer.TYPE == type || Integer.class.equals(type)) {
			return Integer.valueOf(val);
		}
		if (Double.TYPE == type || Double.class.equals(type)) {
			return Double.valueOf(val);
		}
		if (BigInteger.class.equals(type)) {
			return new BigInteger(val);
		}
		if (BigDecimal.class.equals(type)) {
			return new BigDecimal(val);
		}
		if (Byte.TYPE == type || Byte.class.equals(type)) {
			return Byte.valueOf(val);
		}
		if (Short.TYPE == type || Short.class.equals(type)) {
			return Short.valueOf(val);
		}
		if (Float.TYPE == type || Float.class.equals(type)) {
			return Float.valueOf(val);
		}

		throw new IllegalArgumentException();
	}

	protected final static String coerceToString(final Object obj) {

		if (obj == null) {
			return "";
		} else if (obj instanceof String) {
			return (String) obj;
		} else if (obj instanceof Enum) {
			return ((Enum) obj).name();
		} else {
			return obj.toString();
		}
	}

	public final static Object coerce(final Object obj, final Class<?> type)
			throws IllegalArgumentException {

		if (type == null || Object.class.equals(type)
				|| (obj != null && type.isAssignableFrom(obj.getClass()))) {
			return obj;
		}

		if (String.class.equals(type)) {
			return coerceToString(obj);
		}
		if (Number.class.isAssignableFrom(type) || Byte.TYPE == type
				|| Short.TYPE == type || Integer.TYPE == type
				|| Long.TYPE == type || Float.TYPE == type
				|| Double.TYPE == type) {
			return coerceToNumber(obj, type);
		}
		if (Character.class.equals(type) || Character.TYPE == type) {
			return coerceToCharacter(obj);
		}
		if (Boolean.class.equals(type) || Boolean.TYPE == type) {
			return coerceToBoolean(obj);
		}
		if (Date.class.equals(type)) {
			return coerceToDate(obj, type);
		}
		if (type.isEnum()) {
			return coerceToEnum(obj, type);
		}

		if (obj == null || "".equals(obj)) {
			return null;
		}

		try {
			Method method = type.getMethod("valueOf", String.class);
			if (Modifier.isStatic(method.getModifiers()))
				return method.invoke(null, obj.toString());
		} catch (NoSuchMethodException e) {
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}

		try {
			Constructor constructor = type.getConstructor(String.class);
			return constructor.newInstance(obj.toString());
		} catch (NoSuchMethodException e) {
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		}

		if (obj instanceof String) {
			if ("".equals(obj))
				return null;
			PropertyEditor editor = PropertyEditorManager.findEditor(type);
			if (editor != null) {
				editor.setAsText((String) obj);
				return editor.getValue();
			}
		}

		throw new IllegalArgumentException();
	}

	protected final static boolean isStringFloat(final String str) {
		int len = str.length();
		if (len > 1) {
			for (int i = 0; i < len; i++) {
				switch (str.charAt(i)) {
				case 'E':
					return true;
				case 'e':
					return true;
				case '.':
					return true;
				}
			}
		}
		return false;
	}

	protected final static Number toFloat(final String value) {
		try {
			if (Double.parseDouble(value) > Double.MAX_VALUE) {
				return new BigDecimal(value);
			} else {
				return Double.valueOf(value);
			}
		} catch (NumberFormatException e0) {
			return new BigDecimal(value);
		}
	}

	protected final static Number toNumber(final String value) {
		try {
			return Integer.valueOf(Integer.parseInt(value));
		} catch (NumberFormatException e0) {
			try {
				return Long.valueOf(Long.parseLong(value));
			} catch (NumberFormatException e1) {
				return new BigInteger(value);
			}
		}
	}
}
