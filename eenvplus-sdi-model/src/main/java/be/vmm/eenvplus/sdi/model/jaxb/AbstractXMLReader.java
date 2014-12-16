package be.vmm.eenvplus.sdi.model.jaxb;

import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public abstract class AbstractXMLReader implements XMLReader {

	protected ContentHandler contentHandler;
	protected DTDHandler dtdHandler;
	protected EntityResolver entityResolver;
	protected ErrorHandler errorHandler;

	public ContentHandler getContentHandler() {
		return contentHandler;
	}

	public DTDHandler getDTDHandler() {
		return dtdHandler;
	}

	public EntityResolver getEntityResolver() {
		return entityResolver;
	}

	public ErrorHandler getErrorHandler() {
		return errorHandler;
	}

	public boolean getFeature(String name) {
		return false;
	}

	public Object getProperty(String name) {
		return null;
	}

	public void setContentHandler(ContentHandler handler) {
		contentHandler = handler;
	}

	public void setDTDHandler(DTDHandler handler) {
		dtdHandler = handler;
	}

	public void setEntityResolver(EntityResolver resolver) {
		entityResolver = resolver;
	}

	public void setErrorHandler(ErrorHandler handler) {
		errorHandler = handler;
	}

	public void setFeature(String name, boolean value) {
	}

	public void setProperty(String name, Object value) {
	}

	public void parse(InputSource input) throws SAXException {
		parse();
	}

	public void parse(String systemId) throws SAXException {
		parse();
	}

	public abstract void parse() throws SAXException;
}