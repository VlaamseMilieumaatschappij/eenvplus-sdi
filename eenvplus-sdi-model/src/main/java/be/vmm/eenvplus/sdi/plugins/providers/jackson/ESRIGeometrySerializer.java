package be.vmm.eenvplus.sdi.plugins.providers.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class ESRIGeometrySerializer extends JsonSerializer<Geometry> {

	@Override
	public void serialize(Geometry value, JsonGenerator generator,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {

		generator.writeStartObject();

		writeGeometry(generator, value);

		generator.writeEndObject();
	}

	@Override
	public Class<Geometry> handledType() {
		return Geometry.class;
	}

	public static void writeGeometry(JsonGenerator generator, Geometry value)
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

	private static void writeGeometryCollection(JsonGenerator generator,
			GeometryCollection value) throws JsonGenerationException,
			IOException {

		generator.writeStringField("geometryType", "esriGeometryCollection");
		generator.writeArrayFieldStart("geometries");

		for (int i = 0; i != value.getNumGeometries(); ++i) {
			writeGeometry(generator, value.getGeometryN(i));
		}

		generator.writeEndArray();
	}

	private static void writePoint(JsonGenerator generator, Point value)
			throws JsonGenerationException, IOException {

		generator.writeStringField("geometryType", "esriGeometryPoint");

		generator.writeObjectFieldStart("geometry");
		generator.writeFieldName("coordinates");

		writePointCoords(generator, value);

		generator.writeFieldName("spatialReference");
		writeCRS(generator, value.getSRID());

		generator.writeFieldName("bbox");
		writeBBox(generator, value.getEnvelopeInternal());
	}

	private static void writeMultiPoint(JsonGenerator generator,
			MultiPoint value) throws JsonGenerationException, IOException {

		generator.writeStringField("geometryType", "esriGeometryMultipoint");

		generator.writeArrayFieldStart("points");

		for (int i = 0; i != value.getNumGeometries(); ++i) {
			writePointCoords(generator, (Point) value.getGeometryN(i));
		}

		generator.writeEndArray();

		generator.writeString("spatialReference");
		writeCRS(generator, value.getSRID());

		generator.writeFieldName("bbox");
		writeBBox(generator, value.getEnvelopeInternal());
	}

	private static void writeLineString(JsonGenerator generator,
			LineString value) throws JsonGenerationException, IOException {

		generator.writeStringField("geometryType", "esriGeometryPolyline");

		generator.writeArrayFieldStart("paths");

		writeLineStringCoords(generator, value);

		generator.writeEndArray();

		generator.writeFieldName("spatialReference");
		writeCRS(generator, value.getSRID());

		generator.writeFieldName("bbox");
		writeBBox(generator, value.getEnvelopeInternal());
	}

	private static void writeMultiLineString(JsonGenerator generator,
			MultiLineString value) throws JsonGenerationException, IOException {

		generator.writeStringField("geometryType", "esriGeometryPolyline");

		generator.writeArrayFieldStart("paths");

		for (int i = 0; i != value.getNumGeometries(); ++i) {
			writeLineStringCoords(generator, (LineString) value.getGeometryN(i));
		}

		generator.writeEndArray();

		generator.writeString("spatialReference");
		writeCRS(generator, value.getSRID());

		generator.writeFieldName("bbox");
		writeBBox(generator, value.getEnvelopeInternal());
	}

	private static void writePolygon(JsonGenerator generator, Polygon value)
			throws JsonGenerationException, IOException {

		generator.writeStringField("geometryType", "esriGeometryPolygon");

		generator.writeArrayFieldStart("rings");

		writePolygonCoords(generator, value);

		generator.writeEndArray();

		generator.writeFieldName("spatialReference");
		writeCRS(generator, value.getSRID());

		generator.writeFieldName("bbox");
		writeBBox(generator, value.getEnvelopeInternal());
	}

	private static void writeMultiPolygon(JsonGenerator generator,
			MultiPolygon value) throws JsonGenerationException, IOException {

		generator.writeStringField("geometryType", "esriGeometryPolygon");

		generator.writeArrayFieldStart("rings");

		for (int i = 0; i != value.getNumGeometries(); ++i) {
			writePolygonCoords(generator, (Polygon) value.getGeometryN(i));
		}

		generator.writeEndArray();

		generator.writeFieldName("spatialReference");
		writeCRS(generator, value.getSRID());

		generator.writeFieldName("bbox");
		writeBBox(generator, value.getEnvelopeInternal());
	}

	private static void writePointCoords(JsonGenerator generator, Point value)
			throws IOException, JsonGenerationException {

		generator.writeStartArray();

		generator.writeNumber(value.getX());
		generator.writeNumber(value.getY());

		generator.writeEndArray();
	}

	private static void writeLineStringCoords(JsonGenerator generator,
			LineString value) throws JsonGenerationException, IOException {

		generator.writeStartArray();

		for (int i = 0; i != value.getNumPoints(); ++i) {
			Point p = value.getPointN(i);
			writePointCoords(generator, p);
		}

		generator.writeEndArray();
	}

	private static void writePolygonCoords(JsonGenerator generator,
			Polygon value) throws IOException, JsonGenerationException {

		generator.writeStartArray();
		writeLineStringCoords(generator, value.getExteriorRing());

		for (int i = 0; i != value.getNumInteriorRing(); ++i) {
			writeLineStringCoords(generator, value.getInteriorRingN(i));
		}

		generator.writeEndArray();
	}

	public static void writeCRS(JsonGenerator generator, int srid)
			throws JsonGenerationException, IOException {

		generator.writeStartObject();

		if (srid > 0)
			generator.writeNumberField("wkid", srid);

		generator.writeEndObject();
	}

	public static void writeBBox(JsonGenerator generator, Envelope bbox)
			throws JsonGenerationException, IOException {

		generator.writeStartArray();

		generator.writeNumber(bbox.getMinX());
		generator.writeNumber(bbox.getMinY());
		generator.writeNumber(bbox.getMaxX());
		generator.writeNumber(bbox.getMaxY());

		generator.writeEndArray();
	}
}