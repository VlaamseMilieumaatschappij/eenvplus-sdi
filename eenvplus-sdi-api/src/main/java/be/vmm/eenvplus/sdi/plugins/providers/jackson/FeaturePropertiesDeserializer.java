package be.vmm.eenvplus.sdi.plugins.providers.jackson;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import be.vmm.eenvplus.sdi.api.feature.FeatureInfo;
import be.vmm.eenvplus.sdi.api.feature.FeatureProperties;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

@SuppressWarnings("rawtypes")
public class FeaturePropertiesDeserializer extends
		JsonDeserializer<FeatureProperties> {

	@Override
	public FeatureProperties deserialize(JsonParser parser,
			DeserializationContext context) throws IOException,
			JsonProcessingException {
		throw new UnsupportedOperationException();
	}

	@Override
	public FeatureProperties deserialize(JsonParser parser,
			DeserializationContext context, FeatureProperties properties)
			throws IOException, JsonProcessingException {

		Object object = properties.unwrap();
		Map<String, PropertyDescriptor> descriptors = FeatureInfo
				.getFeatureInfo(object.getClass()).getPropertyDescriptors();

		JsonToken t = parser.getCurrentToken();
		if (t == JsonToken.START_OBJECT) {
			t = parser.nextToken();
		}

		try {
			for (; t == JsonToken.FIELD_NAME; t = parser.nextToken()) {
				String name = parser.getCurrentName();
				Object value;
				t = parser.nextToken(); // to get to value

				PropertyDescriptor descriptor = descriptors.get(name);

				if (t == JsonToken.VALUE_NULL) {
					value = null;
				} else {
					Type type = descriptor.getReadMethod()
							.getGenericReturnType();
					JsonDeserializer<Object> deserializer = context
							.findRootValueDeserializer(context.getTypeFactory()
									.constructType(type));
					value = deserializer.deserialize(parser, context);
				}

				descriptor.getWriteMethod().invoke(object, value);
			}
		} catch (Exception e) {
			throw new RuntimeException("Can not deserialize properties.", e);
		}

		if (t != JsonToken.END_OBJECT) {
			throw new RuntimeException("Invalid end object.");
		}

		return properties;
	}
}