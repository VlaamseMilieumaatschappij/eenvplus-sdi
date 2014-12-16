package be.vmm.eenvplus.sdi.api.feature;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import be.vmm.eenvplus.sdi.plugins.providers.jackson.GeometryDeserializer;
import be.vmm.eenvplus.sdi.plugins.providers.jackson.GeometrySerializer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Geometry;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Feature<T> {

	protected T object;

	public Feature() {
	}

	public Feature(T object) {
		this.object = object;
	}

	@JsonCreator
	@SuppressWarnings("unchecked")
	public Feature(
			@JsonProperty(value = "layerBodId", required = true) String layerBodId)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {

		if (layerBodId == null)
			throw new NullPointerException("layerBodId");

		this.object = (T) FeatureInfo.getFeatureClass(layerBodId).newInstance();
	}

	@XmlElement
	public Long getId() {
		try {
			return (Long) getBeanInfo().getIdDescriptor().getReadMethod()
					.invoke(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setId(Long id) {
		try {
			getBeanInfo().getIdDescriptor().getWriteMethod().invoke(object, id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@XmlElement
	public Long getFeatureId() {
		return getId();
	}

	@XmlElement
	public String getType() {
		return "Feature";
	}

	@XmlElement
	public String getLayerBodId() {
		return FeatureInfo.getLayerBodId(object.getClass());
	}

	@XmlElement
	public FeatureProperties<T> getProperties() throws IntrospectionException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		return new FeatureProperties<T>(object);
	}

	@XmlTransient
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

		FeatureInfo.getFeatureInfo(object.getClass()).getGeometryDescriptor()
				.getWriteMethod().invoke(object, value);
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

	public void wrap(T object) {
		this.object = object;
	}

	@SuppressWarnings("unchecked")
	protected Class<T> getObjectClass() {
		return (Class<T>) object.getClass();
	}

	protected FeatureInfo getBeanInfo() {
		return FeatureInfo.getFeatureInfo(getObjectClass());
	}
}
