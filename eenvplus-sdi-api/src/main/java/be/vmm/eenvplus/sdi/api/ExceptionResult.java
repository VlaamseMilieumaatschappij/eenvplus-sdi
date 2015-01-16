package be.vmm.eenvplus.sdi.api;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ExceptionResult {

	protected String type;
	protected String message;

	public ExceptionResult(String type, String message) {
		this.type = type;
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
