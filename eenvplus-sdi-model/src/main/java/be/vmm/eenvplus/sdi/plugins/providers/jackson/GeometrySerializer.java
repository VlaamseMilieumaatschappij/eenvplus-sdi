package be.vmm.eenvplus.sdi.plugins.providers.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class GeometrySerializer extends JsonSerializer<Geometry> {

	@Override
	public void serialize(Geometry value, JsonGenerator generator,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		writeGeometry(generator, value);
	}

	@Override
	public Class<Geometry> handledType() {
		return Geometry.class;
	}

	private void writeGeometry(JsonGenerator generator, Geometry value)
			throws JsonGenerationException, IOException {

		if (value instanceof Polygon) {
			writePolygon(generator, (Polygon) value);
		} else if (value instanceof Point) {
			writePoint(generator, (Point) value);
		} else if (value instanceof MultiPoint) {
			writeMultiPoint(generator, (MultiPoint) value);
		} else if (value instanceof MultiPolygon) {
			writeMultiPolygon(generator, (MultiPolygon) value);
		} else if (value instanceof LineString) {
			writeLineString(generator, (LineString) value);
		} else if (value instanceof MultiLineString) {
			writeMultiLineString(generator, (MultiLineString) value);
		} else if (value instanceof GeometryCollection) {
			writeGeometryCollection(generator, (GeometryCollection) value);
		} else {
			throw new UnsupportedOperationException("not implemented: "
					+ value.getClass().getName());
		}
	}

	private void writeGeometryCollection(JsonGenerator generator,
			GeometryCollection value) throws JsonGenerationException,
			IOException {

		generator.writeStartObject();

		generator.writeStringField("type", "GeometryCollection");

		generator.writeArrayFieldStart("geometries");
		for (int i = 0; i != value.getNumGeometries(); ++i) {
			writeGeometry(generator, value.getGeometryN(i));
		}
		generator.writeEndArray();

		generator.writeEndObject();
	}

	private void writePoint(JsonGenerator generator, Point value)
			throws JsonGenerationException, IOException {

		generator.writeStartObject();

		generator.writeStringField("type", "Point");

		generator.writeFieldName("coordinates");
		writePointCoords(generator, value);

		generator.writeFieldName("crs");
		writeCRSString(generator, value.getSRID());

		generator.writeEndObject();
	}

	private void writeMultiPoint(JsonGenerator generator, MultiPoint value)
			throws JsonGenerationException, IOException {

		generator.writeStartObject();

		generator.writeStringField("type", "MultiPoint");

		generator.writeArrayFieldStart("coordinates");
		for (int i = 0; i != value.getNumGeometries(); ++i) {
			writePointCoords(generator, (Point) value.getGeometryN(i));
		}
		generator.writeEndArray();

		generator.writeString("crs");
		writeCRSString(generator, value.getSRID());

		generator.writeEndObject();
	}

	private void writeLineString(JsonGenerator generator, LineString value)
			throws JsonGenerationException, IOException {

		generator.writeStartObject();

		generator.writeStringField("type", "LineString");

		generator.writeFieldName("coordinates");
		writeLineStringCoords(generator, value);

		generator.writeFieldName("crs");
		writeCRSString(generator, value.getSRID());

		generator.writeEndObject();
	}

	private void writeMultiLineString(JsonGenerator generator,
			MultiLineString value) throws JsonGenerationException, IOException {

		generator.writeStartObject();

		generator.writeStringField("type", "MultiLineString");

		generator.writeArrayFieldStart("coordinates");
		for (int i = 0; i != value.getNumGeometries(); ++i) {
			writeLineStringCoords(generator, (LineString) value.getGeometryN(i));
		}
		generator.writeEndArray();

		generator.writeString("crs");
		writeCRSString(generator, value.getSRID());

		generator.writeEndObject();
	}

	private void writePolygon(JsonGenerator generator, Polygon value)
			throws JsonGenerationException, IOException {

		generator.writeStartObject();

		generator.writeStringField("type", "Polygon");

		generator.writeFieldName("coordinates");
		writePolygonCoords(generator, value);

		generator.writeFieldName("crs");
		writeCRSString(generator, value.getSRID());

		generator.writeEndObject();
	}

	private void writeMultiPolygon(JsonGenerator generator, MultiPolygon value)
			throws JsonGenerationException, IOException {

		generator.writeStartObject();

		generator.writeStringField("type", "MultiPolygon");

		generator.writeArrayFieldStart("coordinates");
		for (int i = 0; i != value.getNumGeometries(); ++i) {
			writePolygonCoords(generator, (Polygon) value.getGeometryN(i));
		}
		generator.writeEndArray();

		generator.writeFieldName("crs");
		writeCRSString(generator, value.getSRID());

		generator.writeEndObject();
	}

	private void writePointCoords(JsonGenerator generator, Point value)
			throws IOException, JsonGenerationException {

		generator.writeStartArray();

		generator.writeNumber(value.getX());
		generator.writeNumber(value.getY());

		generator.writeEndArray();
	}

	private void writeLineStringCoords(JsonGenerator generator, LineString value)
			throws JsonGenerationException, IOException {

		generator.writeStartArray();

		for (int i = 0; i != value.getNumPoints(); ++i) {
			Point p = value.getPointN(i);
			writePointCoords(generator, p);
		}

		generator.writeEndArray();
	}

	private void writePolygonCoords(JsonGenerator generator, Polygon value)
			throws IOException, JsonGenerationException {

		generator.writeStartArray();
		writeLineStringCoords(generator, value.getExteriorRing());

		for (int i = 0; i != value.getNumInteriorRing(); ++i) {
			writeLineStringCoords(generator, value.getInteriorRingN(i));
		}

		generator.writeEndArray();
	}

	private void writeCRSString(JsonGenerator generator, int srid)
			throws JsonGenerationException, IOException {

		generator.writeStartObject();

		if (srid > 0) {
			generator.writeStringField("type", "name");
			generator.writeFieldName("properties");
			generator.writeStartObject();
			generator.writeStringField("name", "urn:ogc:def:crs:EPSG:" + srid);
			generator.writeEndObject();
		}

		generator.writeEndObject();
	}
}