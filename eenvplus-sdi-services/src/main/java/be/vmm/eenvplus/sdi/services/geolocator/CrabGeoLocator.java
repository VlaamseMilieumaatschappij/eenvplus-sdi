package be.vmm.eenvplus.sdi.services.geolocator;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.geotools.referencing.CRS;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import be.vmm.eenvplus.sdi.api.SearchResult;
import be.vmm.eenvplus.sdi.api.SearchResults;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Locator using the Agiv GeoLocation services.
 * 
 * @author stefaan
 * 
 * @see http://loc.geopunt.be/Help/Api/GET-Geolocation-
 *      Location_q_latlon_xy_capakey_poi_c
 */
public class CrabGeoLocator {

	public static final String CRAB_LOCATION_URL = "http://loc.geopunt.be/geolocation/location";
	public static final CoordinateReferenceSystem LAMBERT_72;
	public static final CoordinateReferenceSystem WGS_84;

	static {
		try {
			LAMBERT_72 = CRS.decode("EPSG:31370", true);
			WGS_84 = CRS.decode("EPSG:4326", true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected static String encodeParameter(String value) {

		if (value == null)
			return null;

		value = value.replaceAll("%", "%25");
		value = value.replaceAll("\\+", "%2B");
		value = value.replaceAll("&", "%26");
		value = value.replaceAll("#", "%23");
		value = value.replaceAll("\\s", "+");

		return value;
	}

	public SearchResults search(String term, CoordinateReferenceSystem crs)
			throws Exception {

		if (!CRS.equalsIgnoreMetadata(crs, LAMBERT_72)) {
			throw new UnsupportedOperationException();
		}

		StringBuilder urlBuilder = new StringBuilder(CRAB_LOCATION_URL);

		urlBuilder.append("?q=").append(encodeParameter(term));
		urlBuilder.append("&c=").append(5);

		JsonNode node;

		InputStream in = new URL(urlBuilder.toString()).openStream();
		try {
			node = new ObjectMapper().readTree(in);
		} finally {
			in.close();
		}

		JsonNode locationResult = node.get("LocationResult");
		if (locationResult != null) {
			List<SearchResult> results = new ArrayList<SearchResult>(
					locationResult.size());

			for (JsonNode locationItem : locationResult) {
				SearchResult result = new SearchResult();
				result.setWeight(10);

				result.setAttr("origin", "crab");
				result.setAttr("label", locationItem.get("FormattedAddress")
						.asText());

				JsonNode boundingBox = locationItem.get("BoundingBox");
				if (boundingBox != null) {
					JsonNode lowerLeft = boundingBox.get("LowerLeft");
					JsonNode upperRight = boundingBox.get("UpperRight");

					double minX = lowerLeft.get("X_Lambert72").asDouble();
					double minY = lowerLeft.get("Y_Lambert72").asDouble();
					double maxX = upperRight.get("X_Lambert72").asDouble();
					double maxY = upperRight.get("Y_Lambert72").asDouble();

					result.setAttr("geom_st_box2d", "BOX(" + minX + " " + minY
							+ "," + maxX + " " + maxY + ")");
				}

				JsonNode location = locationItem.get("Location");
				if (location != null) {
					// x and y where switched in GeoAdmin. Fixed in
					// /src/components/search/SearchDirective.js#getLocationTemplate
					result.setAttr("x", location.get("X_Lambert72").asDouble());
					result.setAttr("y", location.get("Y_Lambert72").asDouble());
				}

				results.add(result);
			}

			return new SearchResults(results);
		} else {
			return new SearchResults();
		}
	}
}
