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
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.geotools.referencing.CRS;

import be.vmm.eenvplus.sdi.api.SearchResult;
import be.vmm.eenvplus.sdi.freemarker.FreemarkerTemplateHandler;
import be.vmm.eenvplus.sdi.model.JsonBeanInfo;
import be.vmm.eenvplus.sdi.model.JsonFeature;
import be.vmm.eenvplus.sdi.services.geolocator.CrabGeoLocator;

@Stateless
@Path("/")
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
		return null;
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

		Collection<PropertyDescriptor> descriptors = JsonBeanInfo
				.getBeanInfo(getFeatureClass(layerBodId))
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
	public Object getLegend(@PathParam("mapId") String mapId,
			@PathParam("layerBodId") String layerBodId,
			@QueryParam("lang") String lang) {
		return null;
	}

	@GET
	@Path("/{mapId}/MapServer/{layerBodId}/{featureId}")
	@Produces("application/json")
	public JsonFeature<?> getFeature(@PathParam("mapId") String mapId,
			@PathParam("layerBodId") String layerBodId,
			@PathParam("featureId") String featureId)
			throws ClassNotFoundException {
		return new JsonFeature<Object>(entityManager.find(
				getFeatureClass(layerBodId), new Long(featureId)));
	}

	@GET
	@Path("/{mapId}/MapServer/{layerBodId}/{featureId}/htmlPopup")
	@Produces("application/xhtml+xml")
	public String getHTMLPopup(@PathParam("mapId") String mapId,
			@PathParam("layerBodId") String layerBodId,
			@PathParam("featureId") String featureId) throws IOException,
			ClassNotFoundException {

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("feature", getFeature(mapId, layerBodId, featureId));

		return templateHandler.evaluate("/templates/htmlPopup.fmt", null,
				params);
	}

	@GET
	@Path("/{mapId}/MapServer/{layerBodId}/{featureId}/exendedHtmlPopup")
	@Produces("application/xhtml+xml")
	public String getExtendedHTMLPopup(@PathParam("mapId") String mapId,
			@PathParam("layerBodId") String layerBodId,
			@PathParam("featureId") String featureId) throws IOException,
			ClassNotFoundException {

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("feature", getFeature(mapId, layerBodId, featureId));

		return templateHandler.evaluate("/templates/extendedHtmlPopup.fmt",
				null, params);
	}

	@GET
	@Path("/{mapId}/MapServer/identify")
	@Produces("application/json")
	public List<Object> identify(@PathParam("mapId") String mapId) {
		return Collections.emptyList();
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
	public List<SearchResult> search(@PathParam("mapId") String mapId,
			@QueryParam("searchText") String searchText,
			@QueryParam("type") String type, @QueryParam("lang") String lang)
			throws Exception {

		if ("location".equals(type)) {
			return geoLocator
					.search(searchText, CRS.decode("EPSG:31370", true));
		} else {
			return Collections.emptyList();
		}
	}

	protected Class<?> getFeatureClass(String layerBodId)
			throws ClassNotFoundException {
		return Class.forName(layerBodId);
	}
}
