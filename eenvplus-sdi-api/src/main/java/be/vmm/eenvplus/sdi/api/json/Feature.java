package be.vmm.eenvplus.sdi.api.json;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import be.vmm.eenvplus.sdi.plugins.providers.jackson.GeometryDeserializer;
import be.vmm.eenvplus.sdi.plugins.providers.jackson.GeometrySerializer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Geometry;

public class Feature<T> {

	protected T object;

	public Feature(T object) {
		this.object = object;
	}

	@JsonCreator
	@SuppressWarnings("unchecked")
	public Feature(@JsonProperty("layerBodId") String layerBodId)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		this.object = (T) FeatureInfo.getFeatureClass(layerBodId)
				.newInstance();
	}

	public Object getId() throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		return getBeanInfo().getIdDescriptor().getReadMethod().invoke(object);
	}

	public Object getFeatureId() throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		return getId();
	}

	public String getType() {
		return "Feature";
	}

	public String getLayerBodId() {
		return FeatureInfo.getLayerBodId(object.getClass());
	}

	public Map<String, Object> getProperties() throws IntrospectionException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		return new FeatureProperties<T>(object);
	}

	public void setProperties(Map<String, Object> properties)
			throws IntrospectionException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		for (Map.Entry<String, Object> e : properties.entrySet()) {
			Map<String, PropertyDescriptor> descriptors = getBeanInfo()
					.getAttributeDescriptors();
			descriptors.get(e.getKey()).getWriteMethod()
					.invoke(object, e.getValue());
		}
	}

	@JsonSerialize(using = GeometrySerializer.class)
	@JsonDeserialize(using = GeometryDeserializer.class)
	public Geometry getGeometry() throws IntrospectionException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		return (Geometry) FeatureInfo.getFeatureInfo(object.getClass())
				.getGeometryDescriptor().getReadMethod().invoke(object);
	}

	public void setGeometry(Geometry value) throws IntrospectionException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		FeatureInfo.getFeatureInfo(object.getClass())
				.getGeometryDescriptor().getWriteMethod().invoke(object, value);
	}

	@Override
	public int hashCode() {
		return object.hashCode();
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object o) {

		if (!(o instanceof Feature<?>))
			return false;

		return object.equals(((Feature<T>) o).object);
	}

	public T unwrap() {
		return object;
	}

	protected FeatureInfo getBeanInfo() {
		return FeatureInfo.getFeatureInfo(object.getClass());
	}
}
