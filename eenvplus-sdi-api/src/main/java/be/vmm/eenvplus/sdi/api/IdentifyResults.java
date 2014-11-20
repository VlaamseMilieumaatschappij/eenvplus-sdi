package be.vmm.eenvplus.sdi.api;

import java.util.Collections;
import java.util.List;

import be.vmm.eenvplus.sdi.api.json.Feature;

public class IdentifyResults<T> {

	protected List<Feature<T>> results;

	public IdentifyResults() {
		this.results = Collections.emptyList();
	}

	public IdentifyResults(List<Feature<T>> results) {
		this.results = results;
	}

	public List<Feature<T>> getResults() {
		return results;
	}

	public void setResults(List<Feature<T>> results) {
		this.results = results;
	}
}
