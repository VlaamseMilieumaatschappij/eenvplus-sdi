package be.vmm.eenvplus.sdi.model.jaxb;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class AbstractElementAdapter<T> extends
		AbstractElementDecorator {

	protected static final Transformer TRANSFORMER;
	static {
		try {
			TRANSFORMER = TransformerFactory.newInstance().newTransformer();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected T value;
	protected Element delegate;

	public AbstractElementAdapter(T value) {
		this.value = value;
	}

	public abstract Source getSource();

	public T getValue() {
		return value;
	}

	@Override
	protected Element getDelegate() {

		if (delegate == null) {
			try {
				DOMResult result = new DOMResult();
				TRANSFORMER.transform(getSource(), result);
				delegate = ((Document) result.getNode()).getDocumentElement();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return delegate;
	}
}
