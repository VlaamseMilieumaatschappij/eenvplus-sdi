package be.vmm.eenvplus.sdi.api;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlType
@XmlAccessorType(XmlAccessType.PROPERTY)
@JsonInclude(Include.NON_NULL)
public class ValidationResult {

	protected String layerBodId;
	protected Object key;
	protected boolean valid;
	protected List<ValidationMessage> messages;

	public ValidationResult() {
		this.messages = new ArrayList<ValidationMessage>();
	}

	public ValidationResult(String layerBodId, Object key, boolean valid,
			List<ValidationMessage> messages) {
		this.layerBodId = layerBodId;
		this.valid = valid;
		this.key = key;
		this.messages = messages;
	}

	public ValidationResult(String layerBodId, Object key, boolean valid) {
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

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	@XmlElement(name = "message")
	public List<ValidationMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<ValidationMessage> messages) {
		this.messages = messages;
	}
}
