package be.vmm.eenvplus.sdi.model.type;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

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

		ReferenceType type = ReferenceType.id;
		for (ReferenceType v : ReferenceType.values()) {
			if (string.startsWith(v.token)) {
				type = v;
				string = string.substring(v.token.length());
				break;
			}
		}

		return new Reference<T>(type, Long.parseLong(string));
	}

	protected ReferenceType type;
	protected long value;

	public Reference(long id) {
		this.type = ReferenceType.id;
		this.value = id;
	}

	public Reference(ReferenceType type, long value) {
		this.type = type;
		this.value = value;
	}

	public ReferenceType getType() {
		return type;
	}

	public long getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {

		if (!(o instanceof Reference))
			return false;

		@SuppressWarnings("unchecked")
		Reference<T> r = (Reference<T>) o;

		return type == r.type && value == r.value;
	}

	@Override
	public int hashCode() {
		return type.hashCode() ^ new Long(value).hashCode();
	}

	@Override
	public String toString() {
		return type.token + value;
	}
}
