package be.vmm.eenvplus.sdi.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ModificationReport {

	protected boolean completed;
	protected ValidationReport validation;
	protected List<ModificationResult> results;

	public ModificationReport(boolean completed, ValidationReport validation) {
		this.completed = completed;
		this.validation = validation;
	}

	public ModificationReport(boolean completed, ValidationReport validation,
			List<ModificationResult> results) {
		this.completed = completed;
		this.validation = validation;
		this.results = results;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public ValidationReport getValidation() {
		return validation;
	}

	public void setValidation(ValidationReport validation) {
		this.validation = validation;
	}

	public List<ModificationResult> getResults() {
		return results;
	}

	public void setResults(List<ModificationResult> results) {
		this.results = results;
	}
}
