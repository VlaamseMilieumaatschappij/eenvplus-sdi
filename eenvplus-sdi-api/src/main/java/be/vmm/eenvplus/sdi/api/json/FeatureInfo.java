package be.vmm.eenvplus.sdi.api.json;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.WeakHashMap;

import com.vividsolutions.jts.geom.Geometry;

public class FeatureInfo {

	protected static Map<Class<?>, FeatureInfo> cache = new WeakHashMap<Class<?>, FeatureInfo>();

	public static FeatureInfo getFeatureInfo(Class<?> clazz) {

		FeatureInfo info = (FeatureInfo) cache.get(clazz);

		if (info == null) {
			try {
				PropertyDescriptor idDescriptor = null;
				Map<String, PropertyDescriptor> attributeDescriptors = new LinkedHashMap<String, PropertyDescriptor>();
				PropertyDescriptor geometryDescriptor = null;

				BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
				for (PropertyDescriptor descriptor : beanInfo
						.getPropertyDescriptors()) {
					if ("id".equals(descriptor.getName())) {
						idDescriptor = descriptor;
					} else if (Geometry.class.isAssignableFrom(descriptor
							.getPropertyType()) && geometryDescriptor == null) {
						geometryDescriptor = descriptor;
					} else if (!"class".equals(descriptor.getName())) {
						attributeDescriptors.put(descriptor.getName(),
								descriptor);
					}
				}

				info = new FeatureInfo(idDescriptor, attributeDescriptors,
						geometryDescriptor);
				cache.put(clazz, info);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return info;
	}

	@SuppressWarnings("unchecked")
	public static Class<Object> getFeatureClass(String layerBodId)
			throws ClassNotFoundException {

		int index = layerBodId.indexOf(':');
		if (index > 0)
			layerBodId = layerBodId.substring(index + 1);

		return (Class<Object>) Class.forName(layerBodId);
	}

	public static String getLayerBodId(Class<?> clazz) {

		return "all:" + clazz.getName();
	}

	protected PropertyDescriptor idDescriptor;
	protected Map<String, PropertyDescriptor> attributeDescriptors;
	protected PropertyDescriptor geometryDescriptor;

	public FeatureInfo(PropertyDescriptor idDescriptor,
			Map<String, PropertyDescriptor> attributeDescriptors,
			PropertyDescriptor geometryDescriptor) {
		this.idDescriptor = idDescriptor;
		this.attributeDescriptors = attributeDescriptors;
		this.geometryDescriptor = geometryDescriptor;
	}

	public PropertyDescriptor getIdDescriptor() {
		return idDescriptor;
	}

	public Map<String, PropertyDescriptor> getAttributeDescriptors() {
		return attributeDescriptors;
	}

	public PropertyDescriptor getGeometryDescriptor() {
		return geometryDescriptor;
	}
}
