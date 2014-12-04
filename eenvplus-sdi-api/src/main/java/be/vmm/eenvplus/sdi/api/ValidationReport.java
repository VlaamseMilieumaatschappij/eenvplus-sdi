package be.vmm.eenvplus.sdi.api;

import java.util.List;

public class ValidationReport {

	protected boolean valid;

	protected List<ValidationResult> results;

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

	public List<ValidationResult> getResults() {
		return results;
	}

	public void setResults(List<ValidationResult> results) {
		this.results = results;
	}
}
