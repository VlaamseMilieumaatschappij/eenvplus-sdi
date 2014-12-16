package be.vmm.eenvplus.sdi.model.jaxb;

import org.xml.sax.Attributes;

public class AttributesDecorator implements Attributes {

	protected Attributes delegate;

	public AttributesDecorator(Attributes delegate) {
		this.delegate = delegate;
	}

	public int getLength() {
		return delegate.getLength();
	}

	public String getURI(int index) {
		return delegate.getURI(index);
	}

	public String getLocalName(int index) {
		return delegate.getLocalName(index);
	}

	public String getQName(int index) {
		return delegate.getQName(index);
	}

	public String getType(int index) {
		return delegate.getType(index);
	}

	public String getValue(int index) {
		return delegate.getValue(index);
	}

	public int getIndex(String uri, String localName) {
		return delegate.getIndex(uri, localName);
	}

	public int getIndex(String qName) {
		return delegate.getIndex(qName);
	}

	public String getType(String uri, String localName) {
		return delegate.getType(uri, localName);
	}

	public String getType(String qName) {
		return delegate.getType(qName);
	}

	public String getValue(String uri, String localName) {
		return delegate.getValue(uri, localName);
	}

	public String getValue(String qName) {
		return delegate.getValue(qName);
	}
}
