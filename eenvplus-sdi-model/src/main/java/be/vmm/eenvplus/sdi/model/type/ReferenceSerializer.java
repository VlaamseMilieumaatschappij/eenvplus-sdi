package be.vmm.eenvplus.sdi.model.type;

import java.io.IOException;

import be.vmm.eenvplus.sdi.model.type.Reference.ReferenceType;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@SuppressWarnings("rawtypes")
public class ReferenceSerializer extends JsonSerializer<Reference> {

	@Override
	public void serialize(Reference reference, JsonGenerator generator,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {

		if (reference.getType() == ReferenceType.id)
			generator.writeNumber(reference.getValue());
		else
			generator.writeString(reference.toString());

	}

	@Override
	public Class<Reference> handledType() {
		return Reference.class;
	}
}