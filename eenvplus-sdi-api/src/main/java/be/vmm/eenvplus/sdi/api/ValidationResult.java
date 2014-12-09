package be.vmm.eenvplus.sdi.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ValidationResult {

	protected String layerBodId;
	protected Long key;
	protected boolean valid;
	protected List<ValidationMessage> messages;

	public ValidationResult(String layerBodId, Long key, boolean valid,
			List<ValidationMessage> messages) {
		this.layerBodId = layerBodId;
		this.valid = valid;
		this.key = key;
		this.messages = messages;
	}

	public ValidationResult(String layerBodId, Long key, boolean valid) {
		this.layerBodId = layerBodId;
		this.valid = valid;
		this.key = key;
	}

	public String getLayerBodId() {
		return layerBodId;
	}

	public void setLayerBodId(String layerBodId) {
		this.layerBodId = layerBodId;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public List<ValidationMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<ValidationMessage> messages) {
		this.messages = messages;
	}
}
