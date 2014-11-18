package be.vmm.eenvplus.sdi.plugins.providers.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class ESRIGeometryDeserializer extends JsonDeserializer<Geometry> {

	private static final GeometryFactory FACTORY = new GeometryFactory();

	@Override
	public Geometry deserialize(JsonParser parser,
			DeserializationContext context) throws IOException,
			JsonProcessingException {

		ObjectCodec codec = parser.getCodec();
		return handleGeometry((JsonNode) codec.readTree(parser));
	}

	private Geometry handleGeometry(JsonNode node) {

		String typeName = node.get("geometryType").asText();
		if (typeName.equals("esriGeometryPoint")) {
			return handlePoint(node);
		} else if (typeName.equals("esriGeometryMultipoint")) {
			return handleMultiPoint(node);
		} else if (typeName.equals("esriPolyline")) {
			return handleMultiLineString(node);
		} else if (typeName.equals("esriGeometryPolygon")) {
			return handleMultiPolygon(node);
		} else if (typeName.equals("esriGeometryCollection")) {
			return FACTORY.createGeometryCollection(handleGeometries(node
					.get("geometries")));
		} else {
			throw new UnsupportedOperationException();
		}
	}

	private Point handlePoint(JsonNode node) {

		Point value = FACTORY.createPoint(handleCoordinate((ArrayNode) node
				.get("coordinates")));
		value.setSRID(handleCRS(node.get("spatialReference")));
		return value;
	}

	private MultiPoint handleMultiPoint(JsonNode node) {

		MultiPoint value = FACTORY.createMultiPoint(handleLineStringCoords(node
				.get("coordinates")));
		value.setSRID(handleCRS(node.get("spatialReference")));
		return value;
	}

	private MultiLineString handleMultiLineString(JsonNode node) {

		MultiLineString value = FACTORY
				.createMultiLineString(handleLineStrings(node
						.get("coordinates")));
		value.setSRID(handleCRS(node.get("spatialReference")));
		return value;
	}

	private MultiPolygon handleMultiPolygon(JsonNode node) {

		MultiPolygon value = FACTORY.createMultiPolygon(handlePolygons(node
				.get("rings")));
		value.setSRID(handleCRS(node.get("spatialReference")));
		return value;
	}

	private Geometry[] handleGeometries(JsonNode array) {
		Geometry[] geometries = new Geometry[array.size()];

		for (int i = 0; i != array.size(); ++i) {
			geometries[i] = handleGeometry(array.get(i));
		}

		return geometries;
	}

	private Polygon handlePolygonRings(JsonNode array) {
		return FACTORY.createPolygon(handleExteriorRing(array),
				handleInteriorRings(array));
	}

	private Polygon[] handlePolygons(JsonNode array) {
		Polygon[] polygons = new Polygon[array.size()];

		for (int i = 0; i != array.size(); ++i) {
			polygons[i] = handlePolygonRings(array.get(i));
		}

		return polygons;
	}

	private LinearRing handleExteriorRing(JsonNode array) {
		return FACTORY.createLinearRing(handleLineStringCoords(array.get(0)));
	}

	private LinearRing[] handleInteriorRings(JsonNode array) {
		LinearRing rings[] = new LinearRing[array.size() - 1];

		for (int i = 1; i < array.size(); ++i) {
			rings[i - 1] = FACTORY
					.createLinearRing(handleLineStringCoords(array.get(i)));
		}

		return rings;
	}

	private Coordinate handleCoordinate(JsonNode array) {
		return new Coordinate(array.get(0).asDouble(), array.get(1).asDouble());
	}

	private Coordinate[] handleLineStringCoords(JsonNode array) {
		Coordinate[] results = new Coordinate[array.size()];

		for (int i = 0; i != array.size(); ++i) {
			results[i] = handleCoordinate(array.get(i));
		}

		return results;
	}

	private LineString[] handleLineStrings(JsonNode array) {
		LineString[] strings = new LineString[array.size()];

		for (int i = 0; i != array.size(); ++i) {
			strings[i] = FACTORY.createLineString(handleLineStringCoords(array
					.get(i)));
		}

		return strings;
	}

	private int handleCRS(JsonNode node) {

		if (node != null) {
			JsonNode wkidNode = node.get("wkid");

			if (wkidNode != null)
				return wkidNode.asInt();
		}

		return 0;
	}
}