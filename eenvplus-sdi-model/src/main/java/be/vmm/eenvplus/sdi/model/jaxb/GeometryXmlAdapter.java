package be.vmm.eenvplus.sdi.model.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.vividsolutions.jts.geom.Geometry;

public class GeometryXmlAdapter extends XmlAdapter<GeometryWrapper, Geometry> {

	@Override
	public Geometry unmarshal(GeometryWrapper v) throws Exception {
		return (Geometry) v.getValue();
	}

	@Override
	public GeometryWrapper marshal(Geometry b) throws Exception {
		return b != null ? new GeometryWrapper(b) : null;
	}
}
