package be.vmm.eenvplus.sdi.plugins.providers.jackson;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.Map;

import be.vmm.eenvplus.sdi.api.feature.FeatureInfo;
import be.vmm.eenvplus.sdi.api.feature.FeatureProperties;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@SuppressWarnings("rawtypes")
public class FeaturePropertiesSerializer extends
		JsonSerializer<FeatureProperties> {

	@Override
	public void serialize(FeatureProperties properties,
			JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {

		Object object = properties.unwrap();
		Map<String, PropertyDescriptor> descriptors = FeatureInfo
				.getFeatureInfo(object.getClass()).getPropertyDescriptors();

		generator.writeStartObject();

		try {
			for (PropertyDescriptor descriptor : descriptors.values()) {
				Object value = descriptor.getReadMethod().invoke(object);
				provider.defaultSerializeField(descriptor.getName(), value,
						generator);
			}
		} catch (Exception e) {
			throw new RuntimeException("Can not serialize properties.", e);
		}

		generator.writeEndObject();
	}

	@Override
	public Class<FeatureProperties> handledType() {
		return FeatureProperties.class;
	}
}