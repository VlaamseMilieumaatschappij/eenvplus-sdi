package be.vmm.eenvplus.sdi.services;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.geotools.referencing.CRS;

import be.vmm.eenvplus.sdi.api.FeatureResult;
import be.vmm.eenvplus.sdi.api.IdentifyResults;
import be.vmm.eenvplus.sdi.api.SearchResult;
import be.vmm.eenvplus.sdi.api.SearchResults;
import be.vmm.eenvplus.sdi.api.json.Feature;
import be.vmm.eenvplus.sdi.api.json.FeatureInfo;
import be.vmm.eenvplus.sdi.api.json.FeatureList;
import be.vmm.eenvplus.sdi.api.json.GeometryParam;
import be.vmm.eenvplus.sdi.api.json.MapExtentParam;
import be.vmm.eenvplus.sdi.api.json.ViewPortParam;
import be.vmm.eenvplus.sdi.freemarker.FreemarkerTemplateHandler;
import be.vmm.eenvplus.sdi.services.geolocator.CrabGeoLocator;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;

@Stateless
@Path("/services")
public class ServicesEndPoint {

	@PersistenceContext(unitName = "eenvplus")
	protected EntityManager entityManager;

	@Inject
	protected FreemarkerTemplateHandler templateHandler;

	@Inject
	protected CrabGeoLocator geoLocator;

	@GET
	@Path("/")
	@Produces("application/json")
	public Response getMapsConfig(@QueryParam("lang") String lang) {
		return Response.ok(
				getClass().getResourceAsStream("/settings/info.json")).build();
	}

	/**
	 * This service provides metadata for all the available layers in the
	 * GeoAdmin API.
	 * 
	 * @param searchText
	 *            The text to search for in the layer description.
	 * @param lang
	 *            The language.
	 */
	@GET
	@Path("/{mapId}/MapServer")
	@Produces("application/json")
	public Response getMapService(@PathParam("mapId") String mapId,
			@QueryParam("searchText") String searchText,
			@QueryParam("lang") String lang) {
		return Response.ok(
				getClass().getResourceAsStream("/settings/meta.json")).build();
	}

	@GET
	@Path("/{mapId}/MapServer/layersConfig")
	@Produces("application/json")
	public Response getLayersConfig(@PathParam("mapId") String mapId) {
		return Response.ok(
				getClass().getResourceAsStream("/settings/layersConfig.json"))
				.build();
	}

	@GET
	@Path("/{mapId}/MapServer/{layerBodId}")
	@Produces("application/json")
	public List<String> describeLayer(@PathParam("mapId") String mapId,
			@PathParam("layerBodId") String layerBodId)
			throws ClassNotFoundException {

		Collection<PropertyDescriptor> descriptors = FeatureInfo
				.getFeatureInfo(FeatureInfo.getFeatureClass(layerBodId))
				.getAttributeDescriptors().values();

		List<String> description = new ArrayList<String>(descriptors.size());
		for (PropertyDescriptor descriptor : descriptors) {
			description.add(descriptor.getName());
		}
		return description;
	}

	/**
	 * This service can be used to retrieve a legend.
	 * 
	 * @param layerBodId
	 *            The layer ID (or technical name).
	 * @param lang
	 *            The language.
	 */
	@GET
	@Path("/{mapId}/MapServer/{layerBodId}/legend")
	@Produces("application/xhtml+xml")
	public String getLegend(@PathParam("mapId") String mapId,
			@PathParam("layerBodId") String layerBodId,
			@QueryParam("lang") String lang) throws IOException {

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("mapId", mapId);
		params.put("layerBodId", layerBodId);

		return templateHandler.evaluate("/templates/legend.fmt", null, params);
	}

	@GET
	@Path("/{mapId}/MapServer/{layerBodId}/{featureId}")
	@Produces("application/json")
	public FeatureResult<Object> getFeature(@PathParam("mapId") String mapId,
			@PathParam("layerBodId") String layerBodId,
			@PathParam("featureId") Long featureId)
			throws ClassNotFoundException {
		return new FeatureResult<Object>(new Feature<Object>(
				entityManager.find(FeatureInfo.getFeatureClass(layerBodId),
						featureId)));
	}

	@GET
	@Path("/{mapId}/MapServer/{layerBodId}/{featureId}/htmlPopup")
	@Produces("application/xhtml+xml")
	public String getHTMLPopup(@PathParam("mapId") String mapId,
			@PathParam("layerBodId") String layerBodId,
			@PathParam("featureId") Long featureId) throws IOException,
			ClassNotFoundException {

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("mapId", mapId);
		params.put("layerBodId", layerBodId);
		params.put("featureId", featureId);

		params.put(
				"feature",
				new Feature<Object>(entityManager.find(
						FeatureInfo.getFeatureClass(layerBodId), featureId)));

		return templateHandler.evaluate("/templates/htmlPopup.fmt", null,
				params);
	}

	@GET
	@Path("/{mapId}/MapServer/{layerBodId}/{featureId}/exendedHtmlPopup")
	@Produces("application/xhtml+xml")
	public String getExtendedHTMLPopup(@PathParam("mapId") String mapId,
			@PathParam("layerBodId") String layerBodId,
			@PathParam("featureId") Long featureId) throws IOException,
			ClassNotFoundException {

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("mapId", mapId);
		params.put("layerBodId", layerBodId);
		params.put("featureId", featureId);

		params.put(
				"feature",
				new Feature<Object>(entityManager.find(
						FeatureInfo.getFeatureClass(layerBodId), featureId)));

		return templateHandler.evaluate("/templates/extendedHtmlPopup.fmt",
				null, params);
	}

	@GET
	@Path("/{mapId}/MapServer/identify")
	@Produces("application/json")
	public IdentifyResults<Object> identify(@PathParam("mapId") String mapId,
			@BeanParam GeometryParam geometry,
			@QueryParam("layers") String layers,
			@QueryParam("mapExtent") MapExtentParam mapExtent,
			@QueryParam("imageDisplay") ViewPortParam imageDisplay,
			@QueryParam("tolerance") Integer tolerance,
			@QueryParam("lang") String lang) throws ClassNotFoundException {

		Geometry location = geometry.getGeometry();
		if (tolerance != null) {
			double radius = mapExtent.getEnvelope().getHeight()
					/ imageDisplay.getHeight() * tolerance;

			location = location.buffer(radius, 3);
		}
		location.setSRID(31370);

		List<Object> results = new ArrayList<Object>();

		for (String layerBodId : layers.split(",")) {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();

			Class<Object> clazz = FeatureInfo.getFeatureClass(layerBodId);

			CriteriaQuery<Object> criteria = builder.createQuery(clazz);
			Root<Object> root = criteria.from(clazz);
			criteria.select(root);

			criteria.where(builder.equal(
					builder.function("intersects", Boolean.class,
							root.get("geom"), builder.literal(location)),
					Boolean.TRUE));

			TypedQuery<Object> query = entityManager.createQuery(criteria);
			results.addAll(query.getResultList());
		}

		return new IdentifyResults<Object>(new FeatureList<Object>(results));
	}

	@GET
	@Path("/{mapId}/MapServer/find")
	@Produces("application/json")
	public List<Object> find(@PathParam("mapId") String mapId) {
		return Collections.emptyList();
	}

	@GET
	@Path("/{mapId}/MapServer/pull")
	@Consumes("application/json")
	public List<Object> pull() {
		return Collections.emptyList();
	}

	@POST
	@Path("/{mapId}/MapServer/push")
	@Consumes("application/json")
	public void push(List<Object> features) {
	}

	@GET
	@Path("/{mapId}/CatalogServer")
	@Produces("application/json")
	public Response catalog(@PathParam("mapId") String mapId,
			@QueryParam("lang") String lang) {
		return Response.ok(
				getClass().getResourceAsStream("/settings/catalog.json"))
				.build();
	}

	@GET
	@Path("/{mapId}/SearchServer")
	@Produces("application/json")
	public SearchResults search(@PathParam("mapId") String mapId,
			@QueryParam("searchText") String searchText,
			@QueryParam("type") String type,
			@QueryParam("features") String layers,
			@QueryParam("bbox") MapExtentParam bbox,
			@QueryParam("lang") String lang) throws Exception {

		if ("locations".equals(type)) {
			return geoLocator
					.search(searchText, CRS.decode("EPSG:31370", true));
		} else if ("featureidentify".equals(type)) {
			Geometry location = bbox.getGeometry();
			location.setSRID(31370);

			List<SearchResult> results = new ArrayList<SearchResult>();

			for (String layerBodId : layers.split(",")) {
				CriteriaBuilder builder = entityManager.getCriteriaBuilder();

				Class<Object> clazz = FeatureInfo.getFeatureClass(layerBodId);

				CriteriaQuery<Object[]> criteria = builder
						.createQuery(Object[].class);
				Root<Object> root = criteria.from(clazz);
				criteria.select(builder.array(root.get("id"), root.get("geom")));

				criteria.where(builder.equal(
						builder.function("intersects", Boolean.class,
								root.get("geom"), builder.literal(location)),
						Boolean.TRUE));

				TypedQuery<Object[]> query = entityManager
						.createQuery(criteria);

				for (Object[] item : query.getResultList()) {
					SearchResult result = new SearchResult();
					result.setWeight(10);

					// {"results":[{"id":3,"weight":1537,"attrs":{"origin":"feature","feature_id":"2","layer":"ch.bafu.fischerei-krebspest","@geodist":31630.279296875,"featureId":"2","lon":9.509796142578125,"geom_st_box2d":"BOX(757500 207800,757500 207800)","geom_quadindex":"030310013113133111313","lat":47.00254821777344,"label":"3"}}]}

					result.setAttr("origin", "feature");
					result.setAttr("layer", layerBodId);

					result.setAttr("feature_id", item[0]);
					result.setAttr("featureId", item[0]);

					result.setAttr("label", item[0]);

					Envelope envelope = ((Geometry) item[1])
							.getEnvelopeInternal();
					result.setAttr(
							"geom_st_box2d",
							"BOX(" + envelope.getMinX() + " "
									+ envelope.getMinY() + ","
									+ envelope.getMaxX() + " "
									+ envelope.getMaxY() + ")");
					results.add(result);
				}
			}

			return new SearchResults(results);
		} else {
			return new SearchResults();
		}
	}
}
