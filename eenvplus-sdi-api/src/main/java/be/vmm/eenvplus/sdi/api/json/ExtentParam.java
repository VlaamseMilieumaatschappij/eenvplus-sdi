package be.vmm.eenvplus.sdi.api.json;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

public class ExtentParam {

	protected static final GeometryFactory GEOMETRY_FACTORY = new GeometryFactory();

	protected Envelope envelope;

	public static ExtentParam valueOf(String s) {

		String[] p = s.split(",");
		if (p.length != 4)
			throw new IllegalArgumentException();
		return new ExtentParam(new Envelope(Double.parseDouble(p[0]),
				Double.parseDouble(p[2]), Double.parseDouble(p[1]),
				Double.parseDouble(p[3])));
	}

	public ExtentParam(Envelope envelope) {
		this.envelope = envelope;
	}

	public ExtentParam(Geometry geometry) {
		this.envelope = geometry.getEnvelopeInternal();
	}

	public Envelope getEnvelope() {
		return envelope;
	}

	public Geometry getGeometry() {
		return GEOMETRY_FACTORY.toGeometry(envelope);
	}

	@Override
	public String toString() {
		return envelope.getMinX() + "," + envelope.getMinY() + ","
				+ envelope.getMaxX() + "," + envelope.getMaxY();
	}

	@Override
	public int hashCode() {
		return envelope.hashCode();
	}

	@Override
	public boolean equals(Object o) {

		if (!(o instanceof ExtentParam)) {
			return false;
		}

		ExtentParam e = (ExtentParam) o;

		return envelope.equals(e.envelope);
	}
}
