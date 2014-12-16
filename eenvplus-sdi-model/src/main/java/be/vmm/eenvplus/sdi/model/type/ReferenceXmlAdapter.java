package be.vmm.eenvplus.sdi.model.type;

import javax.xml.bind.annotation.adapters.XmlAdapter;

@SuppressWarnings("rawtypes")
public class ReferenceXmlAdapter extends XmlAdapter<String, Reference> {

	@Override
	public Reference unmarshal(String v) throws Exception {
		return v != null && !v.isEmpty() ? Reference.fromString(v) : null;
	}

	@Override
	public String marshal(Reference b) throws Exception {
		return b != null ? b.toString() : null;
	}

}
