package be.vmm.eenvplus.sdi.api.json;

import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class FeatureProperties<T> implements Map<String, Object> {

	public class JsonFeaturePropertiesEntrySet implements
			Set<Map.Entry<String, Object>> {

		public boolean add(Map.Entry<String, Object> e) {
			throw new UnsupportedOperationException();
		}

		public boolean addAll(Collection<? extends Map.Entry<String, Object>> c) {
			throw new UnsupportedOperationException();
		}

		public void clear() {
			throw new UnsupportedOperationException();
		}

		public boolean contains(Object o) {
			throw new UnsupportedOperationException();
		}

		public boolean containsAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@SuppressWarnings("unchecked")
		public boolean equals(Object o) {

			if (!(o instanceof FeatureProperties))
				return false;

			FeatureProperties<T> p = (FeatureProperties<T>) o;

			return object.equals(p.object);
		}

		public int hashCode() {
			return object.hashCode();
		}

		public boolean isEmpty() {
			return FeatureProperties.this.isEmpty();
		}

		public Iterator<Map.Entry<String, Object>> iterator() {
			return new Iterator<Map.Entry<String, Object>>() {

				Iterator<PropertyDescriptor> delegate = FeatureProperties.this
						.getBeanInfo().getAttributeDescriptors().values()
						.iterator();

				@Override
				public boolean hasNext() {
					return delegate.hasNext();
				}

				@Override
				public Map.Entry<String, Object> next() {
					return new Map.Entry<String, Object>() {

						PropertyDescriptor descriptor = delegate.next();

						@Override
						public String getKey() {
							return descriptor.getName();
						}

						@Override
						public Object getValue() {

							try {
								return descriptor.getReadMethod()
										.invoke(object);
							} catch (Exception e) {
								throw new RuntimeException(e);
							}
						}

						@Override
						public Object setValue(Object value) {
							try {
								Object old = descriptor.getReadMethod().invoke(
										object);
								descriptor.getWriteMethod().invoke(object,
										value);
								return old;
							} catch (Exception e) {
								throw new RuntimeException(e);
							}
						}
					};
				}

				@Override
				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		}

		public boolean remove(Object e) {
			throw new UnsupportedOperationException();
		}

		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		public int size() {
			return FeatureProperties.this.size();
		}

		public Object[] toArray() {
			return toArray(new Object[FeatureProperties.this.size()]);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public <S> S[] toArray(S[] array) {

			int size = FeatureProperties.this.size();

			if (array.length < size)
				array = (S[]) new Object[size];

			Iterator i = iterator();
			int c = 0;
			while (i.hasNext())
				array[c++] = (S) i.next();

			return array;
		}

		public String toString() {
			Iterator<Map.Entry<String, Object>> i = iterator();
			if (!i.hasNext())
				return "[]";

			StringBuilder sb = new StringBuilder();
			sb.append('[');
			for (;;) {
				Map.Entry<String, Object> e = i.next();
				sb.append(e);
				if (!i.hasNext())
					return sb.append(']').toString();
				sb.append(", ");
			}
		}
	}

	protected T object;

	public FeatureProperties(T object) {
		this.object = object;
	}

	@Override
	public int size() {
		return getBeanInfo().getAttributeDescriptors().size();
	}

	@Override
	public boolean isEmpty() {
		return getBeanInfo().getAttributeDescriptors().isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return getBeanInfo().getAttributeDescriptors().containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object get(Object key) {

		try {
			return getBeanInfo().getAttributeDescriptors().get(key)
					.getReadMethod().invoke(object);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Object put(String key, Object value) {

		try {
			PropertyDescriptor descriptor = getBeanInfo()
					.getAttributeDescriptors().get(key);
			Object old = descriptor.getReadMethod().invoke(object);
			descriptor.getWriteMethod().invoke(object, value);
			return old;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object remove(Object key) {

		try {
			PropertyDescriptor descriptor = getBeanInfo()
					.getAttributeDescriptors().get(key);
			Object old = descriptor.getReadMethod().invoke(object);
			descriptor.getWriteMethod().invoke(object, new Object[] { null });
			return old;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {

		for (Map.Entry<? extends String, ? extends Object> e : m.entrySet()) {
			put(e.getKey(), e.getValue());
		}
	}

	@Override
	public void clear() {

		try {
			for (PropertyDescriptor descriptor : getBeanInfo()
					.getAttributeDescriptors().values()) {
				descriptor.getWriteMethod().invoke(object,
						new Object[] { null });
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Set<String> keySet() {
		return getBeanInfo().getAttributeDescriptors().keySet();
	}

	@Override
	public Collection<Object> values() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Map.Entry<String, Object>> entrySet() {
		return new JsonFeaturePropertiesEntrySet();
	}

	protected FeatureInfo getBeanInfo() {
		return FeatureInfo.getFeatureInfo(object.getClass());
	}
}
