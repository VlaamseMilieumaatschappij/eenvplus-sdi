package be.vmm.eenvplus.sdi.api;

import java.util.Collections;
import java.util.List;

public class SearchResults {

	protected List<SearchResult> results;

	public SearchResults() {
		this.results = Collections.emptyList();
	}

	public SearchResults(List<SearchResult> results) {
		this.results = results;
	}

	public List<SearchResult> getResults() {
		return results;
	}

	public void setResults(List<SearchResult> results) {
		this.results = results;
	}
}
