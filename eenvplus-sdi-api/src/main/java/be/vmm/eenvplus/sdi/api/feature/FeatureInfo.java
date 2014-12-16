package be.vmm.eenvplus.sdi.api.feature;

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
				Map<String, PropertyDescriptor> propertyDescriptors = new LinkedHashMap<String, PropertyDescriptor>();
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
						propertyDescriptors.put(descriptor.getName(),
								descriptor);
					}
				}

				info = new FeatureInfo(idDescriptor, propertyDescriptors,
						geometryDescriptor);
				cache.put(clazz, info);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return info;
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> getFeatureClass(String layerBodId)
			throws ClassNotFoundException {

		int index = layerBodId.indexOf(':');
		if (index > 0)
			layerBodId = layerBodId.substring(index + 1);

		return (Class<T>) Class.forName(layerBodId);
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T>[] getFeatureClasses(String[] layerBodIds)
			throws ClassNotFoundException {
		Class<T>[] result = new Class[layerBodIds.length];

		for (int i = 0; i < layerBodIds.length; i++) {
			result[i] = getFeatureClass(layerBodIds[i]);
		}

		return result;
	}

	public static String getLayerBodId(Class<?> clazz) {

		return "all:" + clazz.getName();
	}

	protected PropertyDescriptor idDescriptor;
	protected Map<String, PropertyDescriptor> propertyDescriptors;
	protected PropertyDescriptor geometryDescriptor;

	public FeatureInfo(PropertyDescriptor idDescriptor,
			Map<String, PropertyDescriptor> propertyDescriptors,
			PropertyDescriptor geometryDescriptor) {
		this.idDescriptor = idDescriptor;
		this.propertyDescriptors = propertyDescriptors;
		this.geometryDescriptor = geometryDescriptor;
	}

	public PropertyDescriptor getIdDescriptor() {
		return idDescriptor;
	}

	public Map<String, PropertyDescriptor> getPropertyDescriptors() {
		return propertyDescriptors;
	}

	public PropertyDescriptor getGeometryDescriptor() {
		return geometryDescriptor;
	}
}
