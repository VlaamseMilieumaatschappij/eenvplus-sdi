package be.vmm.eenvplus.sdi.model.type;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class ReferenceInfo {

	protected static Map<Class<?>, ReferenceInfo> cache = new WeakHashMap<Class<?>, ReferenceInfo>();

	public static ReferenceInfo getReferenceInfo(Class<?> clazz) {

		ReferenceInfo info = (ReferenceInfo) cache.get(clazz);

		if (info == null) {
			try {
				Map<Class<?>, List<PropertyDescriptor>> referenceDescriptorMap = new HashMap<Class<?>, List<PropertyDescriptor>>(
						4);

				BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
				for (PropertyDescriptor descriptor : beanInfo
						.getPropertyDescriptors()) {

					if (Reference.class.isAssignableFrom(descriptor
							.getPropertyType())) {
						Class<?> referenceType = getTypeArgument(descriptor
								.getReadMethod().getGenericReturnType());
						List<PropertyDescriptor> referenceDescriptors = referenceDescriptorMap
								.get(referenceType);
						if (referenceDescriptors == null) {
							referenceDescriptors = new ArrayList<PropertyDescriptor>(
									2);
							referenceDescriptorMap.put(referenceType,
									referenceDescriptors);
						}
						referenceDescriptors.add(descriptor);
					}
				}

				info = new ReferenceInfo(referenceDescriptorMap);
				cache.put(clazz, info);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return info;
	}

	protected static Class<?> getTypeArgument(Type type) {

		if (type instanceof ParameterizedType
				&& ((ParameterizedType) type).getActualTypeArguments().length == 1) {
			Type typeArgument = ((ParameterizedType) type)
					.getActualTypeArguments()[0];
			if (typeArgument instanceof Class)
				return (Class<?>) typeArgument;
		}

		return Object.class;
	}

	protected Map<Class<?>, List<PropertyDescriptor>> referenceDescriptorMap;

	public ReferenceInfo(
			Map<Class<?>, List<PropertyDescriptor>> referenceDescriptorMap) {
		this.referenceDescriptorMap = referenceDescriptorMap;
	}

	@SuppressWarnings("unchecked")
	public List<PropertyDescriptor> getReferenceDescriptors(
			Class<?> referenceType) {
		List<PropertyDescriptor> referenceDescriptors = referenceDescriptorMap
				.get(referenceType);
		return referenceDescriptors != null ? referenceDescriptors
				: Collections.EMPTY_LIST;
	}
}
