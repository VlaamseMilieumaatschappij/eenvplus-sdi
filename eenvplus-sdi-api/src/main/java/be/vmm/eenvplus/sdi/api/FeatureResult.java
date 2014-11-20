package be.vmm.eenvplus.sdi.api;

import be.vmm.eenvplus.sdi.api.json.Feature;

public class FeatureResult<T> {

	protected Feature<T> feature;

	public FeatureResult() {
	}

	public FeatureResult(Feature<T> feature) {
		this.feature = feature;
	}

	public Feature<T> getFeature() {
		return feature;
	}

	public void setFeature(Feature<T> feature) {
		this.feature = feature;
	}
}
