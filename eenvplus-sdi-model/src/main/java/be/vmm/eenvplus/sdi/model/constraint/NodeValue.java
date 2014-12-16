package be.vmm.eenvplus.sdi.model.constraint;

import be.vmm.eenvplus.sdi.model.type.Reference;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;

public class NodeValue<T> {

	protected Reference<T> reference;
	protected Geometry geometry;

	public NodeValue(Reference<T> reference, Geometry geometry) {
		this.reference = reference;
		this.geometry = geometry;
	}

	public Reference<T> getReference() {
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