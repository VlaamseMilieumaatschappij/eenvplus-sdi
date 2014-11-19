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

public class JsonFeature<T> {

	protected T object;

	public JsonFeature(T object) {
		this.object = object;
	}

	@JsonCreator
	@SuppressWarnings("unchecked")
	public JsonFeature(@JsonProperty("type") String type)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		this.object = (T) Class.forName(type).newInstance();
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
		return object.getClass().getName();
	}

	public String getLayerBodId() {
		return "all:" + getType();
	}

	public Map<String, Object> getProperties() throws IntrospectionException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		return new JsonFeatureProperties<T>(object);
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

		return (Geometry) JsonBeanInfo.getBeanInfo(object.getClass())
				.getGeometryDescriptor().getReadMethod().invoke(object);
	}

	public void setGeometry(Geometry value) throws IntrospectionException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		JsonBeanInfo.getBeanInfo(object.getClass()).getGeometryDescriptor()
				.getWriteMethod().invoke(object, value);
	}

	@Override
	public int hashCode() {
		return object.hashCode();
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object o) {

		if (!(o instanceof JsonFeature<?>))
			return false;

		return object.equals(((JsonFeature<T>) o).object);
	}

	public T unwrap() {
		return object;
	}

	protected JsonBeanInfo getBeanInfo() {
		return JsonBeanInfo.getBeanInfo(object.getClass());
	}
}
