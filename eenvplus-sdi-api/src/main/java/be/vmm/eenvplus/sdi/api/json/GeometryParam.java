package be.vmm.eenvplus.sdi.api.json;

import javax.ws.rs.QueryParam;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

public class GeometryParam {

	protected static final GeometryFactory GEOMETRY_FACTORY = new GeometryFactory();

	@QueryParam("geometry")
	protected String coordinates;
	@QueryParam("geometryType")
	protected String geometryType;

	public Geometry getGeometry() {

		if ("esriGeometryPoint".equals(geometryType)) {

			String[] p = coordinates.split(",");
			if (p.length != 2)
				throw new IllegalArgumentException();

			return GEOMETRY_FACTORY.createPoint(new Coordinate(Double
					.parseDouble(p[0]), Double.parseDouble(p[1])));
		}

		return null;
	}
}
