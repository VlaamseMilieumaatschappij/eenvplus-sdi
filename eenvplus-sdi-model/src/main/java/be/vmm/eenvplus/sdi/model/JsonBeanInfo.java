package be.vmm.eenvplus.sdi.model;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.WeakHashMap;

import com.vividsolutions.jts.geom.Geometry;

public class JsonBeanInfo {

	protected static Map<Class<?>, JsonBeanInfo> cache = new WeakHashMap<Class<?>, JsonBeanInfo>();

	public static JsonBeanInfo getBeanInfo(Class<?> clazz) {

		JsonBeanInfo info = (JsonBeanInfo) cache.get(clazz);

		if (info == null) {
			try {
				Map<String, PropertyDescriptor> attributeDescriptors = new LinkedHashMap<String, PropertyDescriptor>();
				PropertyDescriptor geometryDescriptor = null;

				BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
				for (PropertyDescriptor descriptor : beanInfo
						.getPropertyDescriptors()) {
					if (Geometry.class.isAssignableFrom(descriptor
							.getPropertyType())) {
						geometryDescriptor = descriptor;
					} else if (!"class".equals(descriptor.getName())) {
						attributeDescriptors.put(descriptor.getName(),
								descriptor);
					}
				}

				info = new JsonBeanInfo(attributeDescriptors,
						geometryDescriptor);
				cache.put(clazz, info);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return info;
	}

	protected Map<String, PropertyDescriptor> attributeDescriptors;
	protected PropertyDescriptor geometryDescriptor;

	public JsonBeanInfo(Map<String, PropertyDescriptor> attributeDescriptors,
			PropertyDescriptor geometryDescriptor) {
		this.attributeDescriptors = attributeDescriptors;
		this.geometryDescriptor = geometryDescriptor;
	}

	public Map<String, PropertyDescriptor> getAttributeDescriptors() {
		return attributeDescriptors;
	}

	public PropertyDescriptor getGeometryDescriptor() {
		return geometryDescriptor;
	}
}
