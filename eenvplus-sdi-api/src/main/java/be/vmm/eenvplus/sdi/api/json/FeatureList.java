package be.vmm.eenvplus.sdi.api.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FeatureList<T> implements List<Feature<T>> {

	public static class JsonFeatureIterator<T> implements
			Iterator<Feature<T>> {

		protected Iterator<T> iterator;

		public JsonFeatureIterator(Iterator<T> iterator) {
			this.iterator = iterator;
		}

		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public Feature<T> next() {
			return new Feature<T>(iterator.next());
		}

		@Override
		public void remove() {
			iterator.remove();
		}
	}

	public static class JsonFeatureListIterator<T> extends
			JsonFeatureIterator<T> implements ListIterator<Feature<T>> {

		public JsonFeatureListIterator(ListIterator<T> iterator) {
			super(iterator);
		}

		@Override
		public boolean hasPrevious() {
			return ((ListIterator<T>) iterator).hasPrevious();
		}

		@Override
		public Feature<T> previous() {
			return new Feature<T>(((ListIterator<T>) iterator).previous());
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
		public void set(Feature<T> e) {
			((ListIterator<T>) iterator).set(e.unwrap());
		}

		@Override
		public void add(Feature<T> e) {
			((ListIterator<T>) iterator).add(e.unwrap());
		}
	}

	protected List<T> delegate;

	public FeatureList() {
		this.delegate = new ArrayList<T>();
	}

	public FeatureList(List<T> delegate) {
		this.delegate = delegate;
	}

	public boolean add(Feature<T> e) {
		return delegate.add(e.unwrap());
	}

	public boolean addAll(Collection<? extends Feature<T>> c) {
		throw new UnsupportedOperationException();
	}

	public void clear() {
		delegate.clear();
	}

	public boolean contains(Object o) {

		for (T e : delegate) {
			if (o.equals(new Feature<T>(e)))
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

		Iterator<Feature<T>> i1 = iterator();
		Iterator<?> i2 = ((Collection<?>) o).iterator();
		while (i1.hasNext() && i2.hasNext()) {
			Feature<T> o1 = i1.next();
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

	public Iterator<Feature<T>> iterator() {
		return new JsonFeatureIterator<T>(delegate.iterator());
	}

	public boolean remove(Object e) {

		boolean result = false;
		Iterator<T> i = delegate.iterator();
		while (i.hasNext()) {
			if (e.equals(new Feature<T>(i.next()))) {
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
			if (c.contains(new Feature<T>(i.next()))) {
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
			if (!c.contains(new Feature<T>(i.next()))) {
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
		Iterator<Feature<T>> i = iterator();
		if (!i.hasNext())
			return "[]";

		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (;;) {
			Feature<T> e = i.next();
			sb.append(e);
			if (!i.hasNext())
				return sb.append(']').toString();
			sb.append(", ");
		}
	}

	@Override
	public boolean addAll(int index, Collection<? extends Feature<T>> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Feature<T> get(int index) {
		return new Feature<T>(delegate.get(index));
	}

	@Override
	public Feature<T> set(int index, Feature<T> e) {
		return new Feature<T>(delegate.set(index, e.unwrap()));
	}

	@Override
	public void add(int index, Feature<T> e) {
		delegate.add(index, e.unwrap());
	}

	@Override
	public Feature<T> remove(int index) {
		return new Feature<T>(delegate.remove(index));
	}

	@Override
	public int indexOf(Object e) {

		int size = delegate.size();

		for (int i = 0; i < size; i++) {
			if (new Feature<T>(delegate.get(i)).equals(e))
				return i;
		}

		return -1;
	}

	@Override
	public int lastIndexOf(Object e) {

		int size = delegate.size();

		for (int i = size - 1; i >= 0; i--) {
			if (new Feature<T>(delegate.get(i)).equals(e))
				return i;
		}

		return -1;
	}

	@Override
	public ListIterator<Feature<T>> listIterator() {
		return new JsonFeatureListIterator<T>(delegate.listIterator());
	}

	@Override
	public ListIterator<Feature<T>> listIterator(int index) {
		return new JsonFeatureListIterator<T>(delegate.listIterator(index));
	}

	@Override
	public List<Feature<T>> subList(int fromIndex, int toIndex) {
		return new FeatureList<T>(delegate.subList(fromIndex, toIndex));
	}
}
