package be.vmm.eenvplus.sdi.model.jaxb;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class SafeContentHandlerDecorator extends ContentHandlerDecorator {

	public SafeContentHandlerDecorator(ContentHandler delegate) {
		super(delegate);
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, new SafeAttributesDecorator(
				attributes));
	}
}
