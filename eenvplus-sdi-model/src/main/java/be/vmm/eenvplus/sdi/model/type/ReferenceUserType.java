package be.vmm.eenvplus.sdi.model.type;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import be.vmm.eenvplus.sdi.model.type.Reference.ReferenceType;

/**
 * User type to store references.
 */
public class ReferenceUserType implements UserType, Serializable {
	private static final long serialVersionUID = 1907254758736729201L;

	public static final int[] SQL_TYPES = new int[] { Types.BIGINT };

	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	public Class<?> returnedClass() {
		return Reference.class;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		return x != null ? x.equals(y) : y == null;
	}

	public int hashCode(Object x) throws HibernateException {
		return x != null ? x.hashCode() : 0;
	}

	public Object nullSafeGet(ResultSet rs, String[] names,
			SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		Long value = rs.getLong(names[0]);
		return value != null ? new Reference<Object>(value) : null;
	}

	@SuppressWarnings("unchecked")
	public void nullSafeSet(PreparedStatement st, Object value, int index,
			SessionImplementor session) throws HibernateException, SQLException {
		if (value instanceof Reference) {
			if (((Reference<Object>) value).getType() == ReferenceType.id)
				st.setLong(index, (Long) ((Reference<Object>) value).getValue());
			else
				st.setLong(index, -1L);
		} else if (value instanceof Number) {
			st.setLong(index, ((Number) value).longValue());
		}
	}

	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	public boolean isMutable() {
		return false;
	}

	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return cached;
	}

	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return original;
	}
}
