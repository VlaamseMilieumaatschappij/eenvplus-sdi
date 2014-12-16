package be.vmm.eenvplus.sdi.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ValidationMessage {

	protected ValidationLevel level;
	protected String path;
	protected String description;

	public ValidationMessage() {
		level = ValidationLevel.info;
	}

	public ValidationMessage(ValidationLevel level, String path,
			String description) {
		this.level = level;
		this.path = path;
		this.description = description;
	}

	public ValidationLevel getLevel() {
		return level;
	}

	public void setLevel(ValidationLevel level) {
		this.level = level;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
