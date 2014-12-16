package be.vmm.eenvplus.sdi.model.jaxb;

import org.xml.sax.Attributes;

public class SafeAttributesDecorator extends AttributesDecorator {

	public SafeAttributesDecorator(Attributes delegate) {
		super(delegate);
	}

	@Override
	public String getURI(int index) {
		String uri = super.getURI(index);
		return uri != null ? uri : "";
	}
}
