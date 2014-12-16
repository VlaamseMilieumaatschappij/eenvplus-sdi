package be.vmm.eenvplus.sdi.model.type;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReferenceReplacer {

	protected Map<Class<?>, Map<Reference<?>, Reference<?>>> replacementsByClass = new HashMap<Class<?>, Map<Reference<?>, Reference<?>>>(
			5);

	public Reference<?> get(Class<?> referenceType, Reference<?> reference) {

		Map<Reference<?>, Reference<?>> replacements = replacementsByClass
				.get(referenceType);
		if (replacements != null) {
			return replacements.get(reference);
		}

		return null;
	}

	public void put(Class<?> referenceType, Reference<?> reference,
			Reference<?> replacement) {

		Map<Reference<?>, Reference<?>> replacements = replacementsByClass
				.get(referenceType);
		if (replacements == null) {
			replacements = new HashMap<Reference<?>, Reference<?>>();
			replacementsByClass.put(referenceType, replacements);
		}
		replacements.put(reference, replacement);
	}

	public Reference<?> remove(Class<?> referenceType, Reference<?> reference) {

		Map<Reference<?>, Reference<?>> replacements = replacementsByClass
				.get(referenceType);
		if (replacements != null) {
			return replacements.remove(reference);
		}

		return null;
	}

	public void replace(Object object) {

		ReferenceInfo info = ReferenceInfo.getReferenceInfo(object.getClass());

		for (Map.Entry<Class<?>, Map<Reference<?>, Reference<?>>> entry : replacementsByClass
				.entrySet()) {
			List<PropertyDescriptor> descriptors = info
					.getReferenceDescriptors(entry.getKey());
			Map<Reference<?>, Reference<?>> replacements = entry.getValue();
			if (replacements != null) {
				for (PropertyDescriptor descriptor : descriptors) {
					try {
						Object value = descriptor.getReadMethod()
								.invoke(object);
						Object replacement = replacements.get(value);
						if (replacement != null)
							descriptor.getWriteMethod().invoke(object,
									replacement);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
	}

	public void replace(List<Object> objects) {

		for (Object object : objects) {
			replace(object);
		}
	}
}
