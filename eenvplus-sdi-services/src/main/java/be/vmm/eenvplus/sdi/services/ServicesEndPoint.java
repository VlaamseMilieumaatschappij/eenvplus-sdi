package be.vmm.eenvplus.sdi.services;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import org.geotools.referencing.CRS;

import be.vmm.eenvplus.sdi.api.FeatureResult;
import be.vmm.eenvplus.sdi.api.IdentifyResults;
import be.vmm.eenvplus.sdi.api.ModificationAction;
import be.vmm.eenvplus.sdi.api.ModificationReport;
import be.vmm.eenvplus.sdi.api.ModificationResult;
import be.vmm.eenvplus.sdi.api.ModifiedFeature;
import be.vmm.eenvplus.sdi.api.SearchResult;
import be.vmm.eenvplus.sdi.api.SearchResults;
import be.vmm.eenvplus.sdi.api.ValidationLevel;
import be.vmm.eenvplus.sdi.api.ValidationMessage;
import be.vmm.eenvplus.sdi.api.ValidationReport;
import be.vmm.eenvplus.sdi.api.ValidationResult;
import be.vmm.eenvplus.sdi.api.feature.Feature;
import be.vmm.eenvplus.sdi.api.feature.FeatureInfo;
import be.vmm.eenvplus.sdi.api.feature.FeatureList;
import be.vmm.eenvplus.sdi.api.param.ExtentParam;
import be.vmm.eenvplus.sdi.api.param.GeometryParam;
import be.vmm.eenvplus.sdi.api.param.ViewPortParam;
import be.vmm.eenvplus.sdi.freemarker.FreemarkerTemplateHandler;
import be.vmm.eenvplus.sdi.model.Riool;
import be.vmm.eenvplus.sdi.model.RioolObject;
import be.vmm.eenvplus.sdi.model.code.Code;
import be.vmm.eenvplus.sdi.model.code.Namespace;
import be.vmm.eenvplus.sdi.model.code.RioolAppurtenanceType;
import be.vmm.eenvplus.sdi.model.code.RioolLinkType;
import be.vmm.eenvplus.sdi.model.code.SewerWaterType;
import be.vmm.eenvplus.sdi.model.code.Status;
import be.vmm.eenvplus.sdi.model.constraint.group.PostPersist;
import be.vmm.eenvplus.sdi.model.constraint.group.PrePersist;
import be.vmm.eenvplus.sdi.model.store.RioolStore;
import be.vmm.eenvplus.sdi.model.type.Reference;
import be.vmm.eenvplus.sdi.model.type.Reference.ReferenceType;
import be.vmm.eenvplus.sdi.model.type.ReferenceReplacer;
import be.vmm.eenvplus.sdi.services.geolocator.CrabGeoLocator;
import be.vmm.eenvplus.sdi.services.gml.GML2Model;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;

@Stateless
@Path("/services")
public class ServicesEndPoint {

	public static int MAX_RESULTS_SEARCH = 10;
	public static int MAX_RESULTS_IDENTIFY = 100;
	public static int MAX_RESULTS_PULL = 1000;

	@SuppressWarnings("unchecked")
	public static Class<Code>[] CODE_TYPES = new Class[] { Namespace.class,
			RioolAppurtenanceType.class, RioolLinkType.class,
			SewerWaterType.class, Status.class };

	@Resource
	protected SessionContext sessionContext;

	@Inject
	protected RioolStore rioolStore;

	@Inject
	protected ValidatorFactory validatorFactory;

	@Inject
	protected FreemarkerTemplateHandler templateHandler;

	@Inject
	protected CrabGeoLocator geoLocator;

	/**
	 * This service provides configuration data for all the available map
	 * topics.
	 * 
	 * @param lang
	 *            The language.
	 */
	@GET
	@Path("/")
	@Produces("application/json")
	public Response getMapsConfig(@QueryParam("lang") String lang) {
		return Response.ok(
				getClass().getResourceAsStream("/settings/info.json")).build();
	}

	/**
	 * This service provides metadata for all the available layers in the a map
	 * topic.
	 * 
	 * @param mapId
	 *            The map ID.
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

	/**
	 * This service provides configuration data for all the available layers in
	 * the a map topic.
	 * 
	 * @param mapId
	 *            The map ID.
	 */
	@GET
	@Path("/{mapId}/MapServer/layersConfig")
	@Produces("application/json")
	public Response getLayersConfig(@PathParam("mapId") String mapId) {
		return Response.ok(
				getClass().getResourceAsStream("/settings/layersConfig.json"))
				.build();
	}

	/**
	 * This service is used to expose the attributes names that are specific to
	 * a layer. This service is especially useful when combined wit h the find
	 * service.
	 * 
	 * @param mapId
	 *            The map ID.
	 * @param layerBodId
	 *            The layer ID (or technical name).
	 */
	@GET
	@Path("/{mapId}/MapServer/{layerBodId}")
	@Produces("application/json")
	public List<String> describeLayer(@PathParam("mapId") String mapId,
			@PathParam("layerBodId") String layerBodId)
			throws ClassNotFoundException {

		Collection<PropertyDescriptor> descriptors = FeatureInfo
				.getFeatureInfo(FeatureInfo.getFeatureClass(layerBodId))
				.getPropertyDescriptors().values();

		List<String> description = new ArrayList<String>(descriptors.size());
		for (PropertyDescriptor descriptor : descriptors) {
			description.add(descriptor.getName());
		}
		return description;
	}

	/**
	 * This service can be used to retrieve a legend for a layer.
	 * 
	 * @param mapId
	 *            The map ID.
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

	/**
	 * With an ID and a layer ID (technical name), this service can be used to
	 * retrieve a feature resource.
	 * 
	 * @param mapId
	 *            The map ID.
	 * @param layerBodId
	 *            The layer ID (or technical name).
	 * @param featureId
	 *            The feature ID.
	 */
	@GET
	@Path("/{mapId}/MapServer/{layerBodId}/{featureId}")
	@Produces("application/json")
	public FeatureResult<RioolObject> getFeature(
			@PathParam("mapId") String mapId,
			@PathParam("layerBodId") String layerBodId,
			@PathParam("featureId") Long featureId)
			throws ClassNotFoundException {

		RioolObject object = rioolStore.getObject(
				FeatureInfo.<RioolObject> getFeatureClass(layerBodId),
				featureId);

		return new FeatureResult<RioolObject>(
				object != null ? new Feature<RioolObject>(object) : null);
	}

	/**
	 * With an ID and a layer ID (technical name), this service can be used to
	 * retrieve an html popup. An html popup is an html formatted representation
	 * of the textual information about the feature.
	 * 
	 * @param mapId
	 *            The map ID.
	 * @param layerBodId
	 *            The layer ID (or technical name).
	 * @param featureId
	 *            The feature ID.
	 */
	@GET
	@Path("/{mapId}/MapServer/{layerBodId}/{featureId}/htmlPopup")
	@Produces("text/html")
	public String getHTMLPopup(@PathParam("mapId") String mapId,
			@PathParam("layerBodId") String layerBodId,
			@PathParam("featureId") Long featureId) throws IOException,
			ClassNotFoundException {

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("mapId", mapId);
		params.put("layerBodId", layerBodId);
		params.put("featureId", featureId);

		RioolObject object = rioolStore.getObject(
				FeatureInfo.<RioolObject> getFeatureClass(layerBodId),
				featureId);

		params.put("feature", new Feature<RioolObject>(object));

		return templateHandler.evaluate("/templates/htmlPopup.fmt", null,
				params);
	}

	/**
	 * With an ID and a layer ID (technical name), this service can be used to
	 * retrieve an extended html popup. An html popup is an html formatted
	 * representation of the textual information about the feature.
	 * 
	 * @param mapId
	 *            The map ID.
	 * @param layerBodId
	 *            The layer ID (or technical name).
	 * @param featureId
	 *            The feature ID.
	 */
	@GET
	@Path("/{mapId}/MapServer/{layerBodId}/{featureId}/exendedHtmlPopup")
	@Produces("text/html")
	public String getExtendedHTMLPopup(@PathParam("mapId") String mapId,
			@PathParam("layerBodId") String layerBodId,
			@PathParam("featureId") Long featureId) throws IOException,
			ClassNotFoundException {

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("mapId", mapId);
		params.put("layerBodId", layerBodId);
		params.put("featureId", featureId);

		RioolObject object = rioolStore.getObject(
				FeatureInfo.<RioolObject> getFeatureClass(layerBodId),
				featureId);

		params.put("feature", new Feature<RioolObject>(object));

		return templateHandler.evaluate("/templates/extendedHtmlPopup.fmt",
				null, params);
	}

	/**
	 * This service can be used to discover features at a specific location.
	 * 
	 * @param mapId
	 *            The map ID.
	 * @param geometry
	 *            The geometry to identify on. The geometry is specified by the
	 *            geometry type. This parameter is specified as a separated list
	 *            of coordinates.
	 * @param layers
	 *            The layers to perform the identify operation on. Per default
	 *            query all the layers. This is a comma separated list of
	 *            technical layer names.
	 * @param mapExtent
	 *            The extent of the map. (minx, miny, maxx, maxy).
	 * @param imageDisplay
	 *            The screen image display parameters (width, height, and dpi)
	 *            of the map. The mapExtent and the imageDisplay parameters are
	 *            used by the server to calculate the the distance on the map to
	 *            search based on the tolerance in screen pixels.
	 * @param tolerance
	 *            The tolerance in pixels around the specified geometry. This
	 *            parameter is used to create a buffer around the geometry.
	 *            Therefore, a tolerance of 0 deactivates the buffer creation.
	 * @param lang
	 *            The language (when available).
	 */
	@GET
	@Path("/{mapId}/MapServer/identify")
	@Produces("application/json")
	public IdentifyResults<RioolObject> identify(
			@PathParam("mapId") String mapId,
			@BeanParam GeometryParam geometry,
			@QueryParam("layers") String layers,
			@QueryParam("mapExtent") ExtentParam mapExtent,
			@QueryParam("imageDisplay") ViewPortParam imageDisplay,
			@QueryParam("tolerance") Integer tolerance,
			@QueryParam("lang") String lang) throws ClassNotFoundException {

		Geometry location = geometry.getGeometry();
		if (tolerance != null) {
			double radius = mapExtent.getEnvelope().getHeight()
					/ imageDisplay.getHeight() * tolerance;

			location = location.buffer(radius, 3);
		}

		List<RioolObject> results = rioolStore.query(
				FeatureInfo.<RioolObject> getFeatureClasses(layers.split(",")),
				location, MAX_RESULTS_IDENTIFY);

		return new IdentifyResults<RioolObject>(new FeatureList<RioolObject>(
				results));
	}

	/**
	 * This service is used to search the attributes of features. Each result
	 * include a feature ID, a layer ID, a layer name, a geometry (optionally)
	 * and attributes in the form of name-value pair. Here is a complete list of
	 * layers for which this service is available.
	 * 
	 * @param mapId
	 *            The map ID.
	 */
	@GET
	@Path("/{mapId}/MapServer/find")
	@Produces("application/json")
	public List<Object> find(@PathParam("mapId") String mapId) {
		// TODO implement
		return Collections.emptyList();
	}

	@GET
	@Path("/{mapId}/MapServer/pull")
	@Produces("application/json")
	public List<Feature<RioolObject>> pull(@PathParam("mapId") String mapId,
			@QueryParam("types") String types,
			@QueryParam("extent") ExtentParam extent)
			throws ClassNotFoundException {

		Geometry location = null;
		if (extent != null) {
			location = extent.getGeometry();
		}

		List<RioolObject> results = rioolStore.query(
				FeatureInfo.<RioolObject> getFeatureClasses(types.split(",")),
				location, MAX_RESULTS_PULL);

		return new FeatureList<RioolObject>(results);
	}

	@POST
	@Path("/{mapId}/MapServer/push")
	@Consumes("application/json")
	@Produces("application/json")
	@Transactional
	public ModificationReport push(@PathParam("mapId") String mapId,
			List<ModifiedFeature<RioolObject>> features)
			throws IllegalStateException, SystemException,
			NotSupportedException, SecurityException, RollbackException,
			HeuristicMixedException, HeuristicRollbackException {

		ValidationReport validationReport = validate(features, Default.class,
				PrePersist.class);
		if (!validationReport.isValid())
			return new ModificationReport(false, validationReport);

		boolean completed = true;
		ReferenceReplacer replacer = new ReferenceReplacer();
		List<ModificationResult> results = new ArrayList<ModificationResult>(
				features.size());

		for (ModifiedFeature<RioolObject> feature : features) {
			RioolObject object = feature.unwrap();
			String layerBodId = feature.getLayerBodId();
			Object key = feature.getKey();
			ModificationAction action = feature.getAction();

			switch (action) {
			case create:
				rioolStore.persist(object);
				results.add(new ModificationResult(layerBodId, key,
						ModificationAction.create, new Feature<Object>(object)));
				replacer.put(object.getClass(), new Reference<RioolObject>(
						ReferenceType.key, key.toString()),
						new Reference<RioolObject>(object.getId()));

				break;
			case update:
				object = rioolStore.merge(object);
				feature.wrap(object);
				results.add(new ModificationResult(layerBodId, key,
						ModificationAction.update, new Feature<Object>(object)));

				break;
			case delete:
				rioolStore.remove(object);
				results.add(new ModificationResult(layerBodId, key,
						ModificationAction.delete));
				break;
			default:
				results.add(new ModificationResult(layerBodId, key,
						ModificationAction.none));
			}
		}

		replaceReferences(features, replacer);

		rioolStore.flush();

		validationReport = validate(features);

		completed = validationReport.isValid();

		if (!completed)
			sessionContext.setRollbackOnly();

		return new ModificationReport(completed, validationReport, results);
	}

	protected ReferenceReplacer getCodeReferenceReplacer() {

		ReferenceReplacer replacer = new ReferenceReplacer();

		for (Class<Code> type : CODE_TYPES) {
			for (Code code : rioolStore.getCodes(type)) {
				replacer.put(
						type,
						new Reference<Code>(ReferenceType.key, code.getLabel()),
						new Reference<Code>(code.getId()));
			}
		}

		return replacer;
	}

	protected void replaceReferences(
			List<ModifiedFeature<RioolObject>> features,
			ReferenceReplacer replacer) {

		for (ModifiedFeature<RioolObject> feature : features) {
			if (feature.getAction() != ModificationAction.delete) {
				replaceReferences(feature, replacer);
			}
		}
	}

	protected void replaceReferences(ModifiedFeature<RioolObject> feature,
			ReferenceReplacer replacer) {

		RioolObject object = feature.unwrap();
		replacer.replace(object);
		object = rioolStore.merge(object);
		feature.wrap(object);
	}

	@POST
	@Path("/{mapId}/MapServer/test")
	@Consumes("application/json")
	@Produces("application/json")
	@Transactional
	public ValidationReport test(@PathParam("mapId") String mapId,
			List<ModifiedFeature<RioolObject>> features)
			throws IllegalStateException, SystemException,
			NotSupportedException {

		ValidationReport validationReport = validate(features, Default.class,
				PrePersist.class);
		if (!validationReport.isValid())
			return validationReport;

		try {
			ReferenceReplacer replacer = new ReferenceReplacer();

			for (ModifiedFeature<RioolObject> feature : features) {
				RioolObject object = feature.unwrap();
				Object key = feature.getKey();
				ModificationAction action = feature.getAction();

				switch (action) {
				case create:
					rioolStore.persist(object);
					replacer.put(object.getClass(), new Reference<RioolObject>(
							ReferenceType.key, key.toString()),
							new Reference<RioolObject>(object.getId()));

					break;
				case update:
					object = rioolStore.merge(object);
					feature.wrap(object);

					break;
				case delete:
					// Does a soft delete by setting endLifespanVersion
					rioolStore.remove(object);
					break;
				default:
				}
			}

			replaceReferences(features, replacer);

			rioolStore.flush();

			validationReport = validate(features, PostPersist.class);

			return validationReport;
		} finally {
			sessionContext.setRollbackOnly();
		}
	}

	protected ValidationReport validate(
			List<ModifiedFeature<RioolObject>> features, Class<?>... groups)
			throws IllegalStateException, SystemException {

		boolean valid = true;
		List<ValidationResult> results = new ArrayList<ValidationResult>(
				features.size());

		for (ModifiedFeature<RioolObject> feature : features) {
			ValidationResult result = validate(feature, groups);
			if (result != null) {
				valid &= result.isValid();
				results.add(result);
			}
		}

		if (!valid)
			sessionContext.setRollbackOnly();

		return new ValidationReport(valid, results);
	}

	protected ValidationResult validate(ModifiedFeature<RioolObject> feature,
			Class<?>... groups) {

		if (feature.getAction() != ModificationAction.delete) {
			return validate(feature.unwrap(), feature.getLayerBodId(),
					feature.getKey(), groups);
		}

		return null;
	}

	protected ValidationResult validate(RioolObject object, String layerBodId,
			Object key, Class<?>... groups) {

		Set<ConstraintViolation<RioolObject>> violations = rioolStore.validate(
				object, groups);

		if (violations.size() > 0) {
			List<ValidationMessage> messages = new ArrayList<ValidationMessage>();
			for (ConstraintViolation<RioolObject> violation : violations) {
				messages.add(new ValidationMessage(ValidationLevel.error,
						violation.getPropertyPath().toString(), violation
								.getMessage()));
			}

			return new ValidationResult(layerBodId, key, false, messages);
		} else {
			return new ValidationResult(layerBodId, key, true);

		}
	}

	/**
	 * This service provides summary metadata for all the available layers and
	 * layer groups in the a map topic.
	 * 
	 * @param mapId
	 *            The map ID.
	 * @param lang
	 *            The language.
	 */
	@GET
	@Path("/{mapId}/CatalogServer")
	@Produces("application/json")
	public Response catalog(@PathParam("mapId") String mapId,
			@QueryParam("lang") String lang) {
		return Response.ok(
				getClass().getResourceAsStream("/settings/catalog.json"))
				.build();
	}

	/**
	 * <p>
	 * The search service can be used to search for locations, layers or
	 * features.
	 * </p>
	 * <p>
	 * The search service is separated in 4 various categories or types:
	 * </p>
	 * 
	 * <ul>
	 * <li>The <strong>location search</strong> which is composed of the
	 * following geocoded locations:
	 * <ul>
	 * <li>Cantons, Cities and communes</li>
	 * <li>Names of communes</li>
	 * <li>ZIP codes</li>
	 * <li>Addresses</li>
	 * <li>Cadastral parcels</li>
	 * </ul>
	 * </li>
	 * <li>The <strong>layer search</strong> which enables the search of layers.
	 * </li>
	 * <li>The <strong>feature search</strong> which is used to search through
	 * features descriptions.</li>
	 * <li>The <strong>feature identify</strong> which is designed to
	 * efficiently discover the features of a layer based on a geographic
	 * extent.</li>
	 * </ul>
	 * 
	 * @param mapId
	 *            The map ID.
	 * @param searchText
	 *            The text to search for.
	 * @param type
	 *            The type of performed search.
	 * @param features
	 *            A comma separated list of technical layer names.
	 * @param bbox
	 *            A comma separated list of 4 coordinates representing the
	 *            bounding box on which features should be filtered (SRID:
	 *            21781). If this parameter is defined, the ranking of the
	 *            results is performed according to the distance between the
	 *            locations and the center of the bounding box.
	 * @param lang
	 *            The language (when available).
	 */
	@GET
	@Path("/{mapId}/SearchServer")
	@Produces("application/json")
	public SearchResults search(@PathParam("mapId") String mapId,
			@QueryParam("searchText") String searchText,
			@QueryParam("type") String type,
			@QueryParam("features") String features,
			@QueryParam("bbox") ExtentParam bbox,
			@QueryParam("lang") String lang) throws Exception {

		if ("locations".equals(type)) {
			return geoLocator
					.search(searchText, CRS.decode("EPSG:31370", true));
		} else if ("featureidentify".equals(type)) {
			Geometry location = bbox.getGeometry();

			List<SearchResult> results = new ArrayList<SearchResult>();

			List<RioolObject> objects = rioolStore.query(FeatureInfo
					.<RioolObject> getFeatureClasses(features.split(",")),
					location, MAX_RESULTS_SEARCH);

			for (RioolObject object : objects) {
				SearchResult result = new SearchResult();
				result.setWeight(10);

				result.setAttr("origin", "feature");
				result.setAttr("layer",
						FeatureInfo.getLayerBodId(object.getClass()));

				result.setAttr("featureId", object.getId());

				result.setAttr("label", object.getId());

				Envelope envelope = object.getGeom().getEnvelopeInternal();
				result.setAttr("geom_st_box2d", "BOX(" + envelope.getMinX()
						+ " " + envelope.getMinY() + "," + envelope.getMaxX()
						+ " " + envelope.getMaxY() + ")");
				results.add(result);
			}

			return new SearchResults(results);
		} else {
			return new SearchResults();
		}
	}

	@GET
	@Path("/{mapId}/CodeServer/{type}")
	@Produces("application/json")
	@SuppressWarnings("unchecked")
	public List<Code> getCodes(@PathParam("mapId") String mapId,
			@PathParam("type") String type) throws ClassNotFoundException {
		return rioolStore.getCodes((Class<Code>) Class.forName(type));
	}

	@GET
	@Path("/{mapId}/CodeServer/{type}/{id}")
	@Produces("application/json")
	@SuppressWarnings("unchecked")
	public Object getCode(@PathParam("mapId") String mapId,
			@PathParam("type") String type, @PathParam("id") Long id)
			throws ClassNotFoundException {
		return rioolStore.getCode((Class<Code>) Class.forName(type), id);
	}

	@POST
	@Path("/{mapId}/DataServer")
	@Consumes("application/xml")
	@Produces({ "application/xml", "application/json" })
	@SuppressWarnings("unchecked")
	@Transactional
	public ValidationReport _import(@PathParam("mapId") String mapId,
			InputStream in) throws IOException, TransformerException,
			JAXBException, IllegalStateException, SystemException,
			SecurityException, NotSupportedException, RollbackException,
			HeuristicMixedException, HeuristicRollbackException {

		Riool riool = new GML2Model().transform(in);
		List<RioolObject> objects = riool.getObjects();

		ReferenceReplacer replacer = getCodeReferenceReplacer();

		List<ModifiedFeature<RioolObject>> features = new ArrayList<ModifiedFeature<RioolObject>>(
				objects.size());

		for (RioolObject object : objects) {
			String key = object.getNamespaceId().getValue() + ":"
					+ object.getAlternatieveId();

			replacer.replace(object);

			Object namespaceId = object.getNamespaceId().getValue();
			String alternatieveId = object.getAlternatieveId();

			ModificationAction action = ModificationAction.create;
			if (namespaceId instanceof Long
					&& rioolStore.exists(
							(Class<RioolObject>) object.getClass(),
							(Long) namespaceId, alternatieveId)) {
				action = ModificationAction.update;
			}

			features.add(new ModifiedFeature<RioolObject>(object, key, action));
		}

		return test(mapId, features);
	}

	@GET
	@Path("/{mapId}/DataServer/{layerBodId}/{id}")
	@Produces("application/xml")
	public Object export(@PathParam("mapId") String mapId,
			@PathParam("layerBodId") String layerBodId, @PathParam("id") Long id)
			throws ClassNotFoundException {

		return rioolStore.getObject(
				FeatureInfo.<RioolObject> getFeatureClass(layerBodId), id);
	}
}
