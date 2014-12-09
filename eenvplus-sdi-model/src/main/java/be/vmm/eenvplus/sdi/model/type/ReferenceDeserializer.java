package be.vmm.eenvplus.sdi.model.type;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;

@SuppressWarnings("rawtypes")
public class ReferenceDeserializer extends JsonDeserializer<Reference> {

	@Override
	public Reference deserialize(JsonParser parser,
			DeserializationContext context) throws IOException,
			JsonProcessingException {

		JsonToken t = parser.getCurrentToken();

		Reference result = null;
		if (t == JsonToken.VALUE_NUMBER_INT)
			result = new Reference(parser.getLongValue());
		else if (t == JsonToken.VALUE_STRING)
			result = Reference.fromString(parser.getText());
		else
			throw new JsonMappingException("Can not parse reference.");

		return result;
	}
}