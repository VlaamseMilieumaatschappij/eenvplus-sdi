package be.vmm.eenvplus.sdi.plugins.providers.jackson;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
public class JacksonConfig implements ContextResolver<ObjectMapper> {

	private final ObjectMapper objectMapper;

	public JacksonConfig() {

		objectMapper = new ObjectMapper();
		// objectMapper.setDateFormat(new ISO8601DateFormat());
	}

	@Override
	public ObjectMapper getContext(Class<?> clazz) {
		return objectMapper;
	}
}