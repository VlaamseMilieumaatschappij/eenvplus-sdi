package be.vmm.eenvplus.sdi.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class JsonFeatureList<T> implements List<JsonFeature<T>> {

	public static class JsonFeatureIterator<T> implements
			Iterator<JsonFeature<T>> {

		protected Iterator<T> iterator;

		public JsonFeatureIterator(Iterator<T> iterator) {
			this.iterator = iterator;
		}

		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public JsonFeature<T> next() {
			return new JsonFeature<T>(iterator.next());
		}

		@Override
		public void remove() {
			iterator.remove();
		}
	}

	public static class JsonFeatureListIterator<T> extends
			JsonFeatureIterator<T> implements ListIterator<JsonFeature<T>> {

		public JsonFeatureListIterator(ListIterator<T> iterator) {
			super(iterator);
		}

		@Override
		public boolean hasPrevious() {
			return ((ListIterator<T>) iterator).hasPrevious();
		}

		@Override
		public JsonFeature<T> previous() {
			return new JsonFeature<T>(((ListIterator<T>) iterator).previous());
		}

		@Override
		public int nextIndex() {
			return ((ListIterator<T>) iterator).nextIndex();
		}

		@Override
		public int previousIndex() {
			return ((ListIterator<T>) iterator).previousIndex();
		}

		@Override
		public void set(JsonFeature<T> e) {
			((ListIterator<T>) iterator).set(e.unwrap());
		}

		@Override
		public void add(JsonFeature<T> e) {
			((ListIterator<T>) iterator).add(e.unwrap());
		}
	}

	protected List<T> delegate;

	public JsonFeatureList() {
		this.delegate = new ArrayList<T>();
	}

	public JsonFeatureList(List<T> delegate) {
		this.delegate = delegate;
	}

	public boolean add(JsonFeature<T> e) {
		return delegate.add(e.unwrap());
	}

	public boolean addAll(Collection<? extends JsonFeature<T>> c) {
		throw new UnsupportedOperationException();
	}

	public void clear() {
		delegate.clear();
	}

	public boolean contains(Object o) {

		for (T e : delegate) {
			if (o.equals(new JsonFeature<T>(e)))
				return true;
		}
		return false;
	}

	public boolean containsAll(Collection<?> c) {
		for (Object e : c) {
			if (!contains(e))
				return false;
		}
		return true;
	}

	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Collection<?>))
			return false;

		Iterator<JsonFeature<T>> i1 = iterator();
		Iterator<?> i2 = ((Collection<?>) o).iterator();
		while (i1.hasNext() && i2.hasNext()) {
			JsonFeature<T> o1 = i1.next();
			Object o2 = i2.next();
			if (!(o1 == null ? o2 == null : o1.equals(o2)))
				return false;
		}
		return !(i1.hasNext() || i2.hasNext());
	}

	public int hashCode() {
		return delegate.hashCode();
	}

	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	public Iterator<JsonFeature<T>> iterator() {
		return new JsonFeatureIterator<T>(delegate.iterator());
	}

	public boolean remove(Object e) {

		boolean result = false;
		Iterator<T> i = delegate.iterator();
		while (i.hasNext()) {
			if (e.equals(new JsonFeature<T>(i.next()))) {
				i.remove();
				result = true;
			}
		}
		return result;
	}

	public boolean removeAll(Collection<?> c) {

		boolean result = false;
		Iterator<T> i = delegate.iterator();
		while (i.hasNext()) {
			if (c.contains(new JsonFeature<T>(i.next()))) {
				i.remove();
				result = true;
			}
		}
		return result;
	}

	public boolean retainAll(Collection<?> c) {
		boolean result = false;
		Iterator<T> i = delegate.iterator();
		while (i.hasNext()) {
			if (!c.contains(new JsonFeature<T>(i.next()))) {
				i.remove();
				result = true;
			}
		}
		return result;
	}

	public int size() {
		return delegate.size();
	}

	public Object[] toArray() {
		return toArray(new Object[delegate.size()]);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <S> S[] toArray(S[] array) {

		int size = delegate.size();

		if (array.length < size)
			array = (S[]) new Object[size];

		Iterator i = iterator();
		int c = 0;
		while (i.hasNext())
			array[c++] = (S) i.next();

		return array;
	}

	public String toString() {
		Iterator<JsonFeature<T>> i = iterator();
		if (!i.hasNext())
			return "[]";

		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (;;) {
			JsonFeature<T> e = i.next();
			sb.append(e);
			if (!i.hasNext())
				return sb.append(']').toString();
			sb.append(", ");
		}
	}

	@Override
	public boolean addAll(int index, Collection<? extends JsonFeature<T>> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JsonFeature<T> get(int index) {
		return new JsonFeature<T>(delegate.get(index));
	}

	@Override
	public JsonFeature<T> set(int index, JsonFeature<T> e) {
		return new JsonFeature<T>(delegate.set(index, e.unwrap()));
	}

	@Override
	public void add(int index, JsonFeature<T> e) {
		delegate.add(index, e.unwrap());
	}

	@Override
	public JsonFeature<T> remove(int index) {
		return new JsonFeature<T>(delegate.remove(index));
	}

	@Override
	public int indexOf(Object e) {

		int size = delegate.size();

		for (int i = 0; i < size; i++) {
			if (new JsonFeature<T>(delegate.get(i)).equals(e))
				return i;
		}

		return -1;
	}

	@Override
	public int lastIndexOf(Object e) {

		int size = delegate.size();

		for (int i = size - 1; i >= 0; i--) {
			if (new JsonFeature<T>(delegate.get(i)).equals(e))
				return i;
		}

		return -1;
	}

	@Override
	public ListIterator<JsonFeature<T>> listIterator() {
		return new JsonFeatureListIterator<T>(delegate.listIterator());
	}

	@Override
	public ListIterator<JsonFeature<T>> listIterator(int index) {
		return new JsonFeatureListIterator<T>(delegate.listIterator(index));
	}

	@Override
	public List<JsonFeature<T>> subList(int fromIndex, int toIndex) {
		return new JsonFeatureList<T>(delegate.subList(fromIndex, toIndex));
	}
}
