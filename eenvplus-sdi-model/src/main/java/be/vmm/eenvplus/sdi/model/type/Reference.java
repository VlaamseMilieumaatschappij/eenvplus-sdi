package be.vmm.eenvplus.sdi.model.type;

import java.io.Serializable;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@XmlJavaTypeAdapter(ReferenceXmlAdapter.class)
@JsonSerialize(using = ReferenceSerializer.class)
@JsonDeserialize(using = ReferenceDeserializer.class)
public class Reference<T> implements Serializable {
	private static final long serialVersionUID = -8847169804570800012L;

	public enum ReferenceType {
		key("#"), id("@");

		protected String token;

		private ReferenceType(String token) {
			this.token = token;
		}
	}

	public static <T> Reference<T> fromString(String string) {

		if (string == null || string.isEmpty())
			return null;

		ReferenceType type = ReferenceType.id;
		for (ReferenceType v : ReferenceType.values()) {
			if (string.startsWith(v.token)) {
				type = v;
				string = string.substring(v.token.length());
				break;
			}
		}

		try {
			return new Reference<T>(type,
					type == ReferenceType.id ? Long.parseLong(string) : string);
		} catch (Exception e) {
			return null;
		}
	}

	protected ReferenceType type;
	protected Object value;

	public Reference(long id) {
		this.type = ReferenceType.id;
		this.value = id;
	}

	public Reference(ReferenceType type, Object value) {
		this.type = type;
		this.value = value;
	}

	public ReferenceType getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {

		if (!(o instanceof Reference))
			return false;

		@SuppressWarnings("unchecked")
		Reference<T> r = (Reference<T>) o;

		return type == r.type && value.equals(r.value);
	}

	@Override
	public int hashCode() {
		return type.hashCode() ^ value.hashCode();
	}

	@Override
	public String toString() {
		return type.token + value;
	}
}
