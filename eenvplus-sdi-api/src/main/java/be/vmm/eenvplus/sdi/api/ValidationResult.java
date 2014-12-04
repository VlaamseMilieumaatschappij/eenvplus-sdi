package be.vmm.eenvplus.sdi.api;

import java.util.List;

public class ValidationResult {

	protected Long key;
	protected boolean valid;
	protected List<ValidationMessage> messages;

	public ValidationResult(Long key, boolean valid,
			List<ValidationMessage> messages) {
		this.valid = valid;
		this.key = key;
		this.messages = messages;
	}
	
	public ValidationResult(Long key, boolean valid) {
		this.valid = valid;
		this.key = key;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public List<ValidationMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<ValidationMessage> messages) {
		this.messages = messages;
	}
}
