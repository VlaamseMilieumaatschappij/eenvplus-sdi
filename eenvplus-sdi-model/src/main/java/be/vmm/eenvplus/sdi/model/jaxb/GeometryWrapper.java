package be.vmm.eenvplus.sdi.model.jaxb;

import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.DomHandler;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;

import org.geotools.gml3.v3_2.GML;
import org.geotools.gml3.v3_2.GMLConfiguration;
import org.geotools.xml.Encoder;
import org.geotools.xml.impl.ParserHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.vividsolutions.jts.geom.Geometry;

@XmlType(namespace = GML.NAMESPACE, name = "GeometryPropertyType")
public class GeometryWrapper {

	public static Source createSource(final GeometryElementAdapter element) {

		return new SAXSource(new AbstractXMLReader() {

			@Override
			public void parse() throws SAXException {
				try {
					Encoder encoder = new Encoder(new GMLConfiguration());
					encoder.getNamespaces().declarePrefix("", GML.NAMESPACE);
					encoder.encode(element.getValue(), GML.AbstractGeometry,
							new SafeContentHandlerDecorator(contentHandler));
				} catch (Exception e) {
					throw new SAXException(e);
				}
			}
		}, new InputSource());
	}

	public static class GeometryElementAdapter extends
			AbstractElementAdapter<Geometry> {

		public GeometryElementAdapter(Geometry value) {
			super(value);
		}

		@Override
		public Source getSource() {
			return createSource(this);
		}
	}

	public static class GeometryDomHandler implements
			DomHandler<GeometryElementAdapter, SAXResult> {

		@Override
		public SAXResult createUnmarshaller(ValidationEventHandler errorHandler) {
			return new SAXResult(new ParserHandler(new GMLConfiguration()));
		}

		@Override
		public GeometryElementAdapter getElement(SAXResult result) {
			return new GeometryElementAdapter(
					(Geometry) ((ParserHandler) result.getHandler()).getValue());
		}

		@Override
		public Source marshal(final GeometryElementAdapter element,
				ValidationEventHandler errorHandler) {
			return createSource(element);
		}
	}

	protected Geometry value;

	public GeometryWrapper() {
	}

	public GeometryWrapper(Geometry value) {
		this.value = value;
	}

	@XmlAnyElement(GeometryDomHandler.class)
	public GeometryElementAdapter getElement() {
		return value != null ? new GeometryElementAdapter(value) : null;
	}

	public void setElement(GeometryElementAdapter element) {
		this.value = element.getValue();
	}

	public Geometry getValue() {
		return value;
	}
}
