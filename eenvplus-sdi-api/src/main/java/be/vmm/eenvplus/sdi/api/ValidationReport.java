package be.vmm.eenvplus.sdi.api;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement(name = "ValidationReport")
@XmlType
@XmlAccessorType(XmlAccessType.PROPERTY)
@JsonInclude(Include.NON_NULL)
public class ValidationReport {

	protected boolean valid;

	protected List<ValidationResult> results;

	public ValidationReport() {
		this.results = new ArrayList<ValidationResult>();
	}

	public ValidationReport(boolean valid, List<ValidationResult> results) {
		this.valid = valid;
		this.results = results;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	@XmlElement(name = "result")
	public List<ValidationResult> getResults() {
		return results;
	}

	public void setResults(List<ValidationResult> results) {
		this.results = results;
	}
}
