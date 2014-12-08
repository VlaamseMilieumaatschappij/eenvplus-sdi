package be.vmm.eenvplus.sdi.model.constraint;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;

public class NodeValue {

	protected Object reference;
	protected Geometry geometry;

	public NodeValue(Object reference, Geometry geometry) {
		this.reference = reference;
		this.geometry = geometry;
	}

	public Object getReference() {
		return reference;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public Geometry getGeometry(NodePosition nodePosition) {

		switch (nodePosition) {
		case start:
			return ((LineString) geometry).getPointN(0);
		case end:
			return ((LineString) geometry)
					.getPointN(geometry.getNumPoints() - 1);
		default:
			return geometry;
		}
	}
}