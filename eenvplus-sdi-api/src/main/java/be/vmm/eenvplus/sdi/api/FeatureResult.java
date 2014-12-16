package be.vmm.eenvplus.sdi.api;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import be.vmm.eenvplus.sdi.api.feature.Feature;

@XmlRootElement
public class FeatureResult<T> {

	protected Feature<T> feature;

	public FeatureResult() {
	}

	public FeatureResult(Feature<T> feature) {
		this.feature = feature;
	}

	@XmlElement
	public Feature<T> getFeature() {
		return feature;
	}

	public void setFeature(Feature<T> feature) {
		this.feature = feature;
	}
}
