package be.vmm.eenvplus.sdi.api;

import java.util.Collections;
import java.util.List;

import be.vmm.eenvplus.sdi.api.json.JsonFeature;

public class IdentifyResults<T> {

	protected List<JsonFeature<T>> results;

	public IdentifyResults() {
		this.results = Collections.emptyList();
	}

	public IdentifyResults(List<JsonFeature<T>> results) {
		this.results = results;
	}

	public List<JsonFeature<T>> getResults() {
		return results;
	}

	public void setResults(List<JsonFeature<T>> results) {
		this.results = results;
	}
}
