package be.vmm.eenvplus.sdi.services.gml;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBResult;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import be.vmm.eenvplus.sdi.model.Riool;

@ApplicationScoped
public class GML2Model {

	//protected Validator validator;
	protected Transformer transformer;

	@PostConstruct
	public void init() {

		try {
			//SchemaFactory schemaFactory = SchemaFactory
			//		.newInstance("http://www.w3.org/2001/XMLSchema");
			//Schema schema = schemaFactory.newSchema(new StreamSource(getClass()
			//		.getResourceAsStream("/xslts/eenv.xsd")));
			//
			//validator = schema.newValidator();

			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Source xsl = new StreamSource(getClass().getResourceAsStream(
					"/xslts/GML2Model.xsl"));

			transformer = transformerFactory.newTransformer(xsl);
		} catch (Exception e) {
			throw new RuntimeException("Can not create transformer", e);
		}
	}

	public void validate(InputStream in) throws SAXException, IOException {

		//validator.validate(new StreamSource(in));
	}

	public Riool transform(InputStream in) throws IOException,
			TransformerException, JAXBException {

		JAXBContext context = JAXBContext.newInstance(Riool.class);
		JAXBResult result = new JAXBResult(context);
		transformer.transform(new StreamSource(in), result);
		return (Riool) result.getResult();
	}
}
