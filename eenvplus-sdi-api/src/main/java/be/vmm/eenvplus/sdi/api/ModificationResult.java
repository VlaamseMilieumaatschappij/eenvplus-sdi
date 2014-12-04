package be.vmm.eenvplus.sdi.api;

import be.vmm.eenvplus.sdi.api.json.Feature;

public class ModificationResult {

	protected Long key;
	protected ModificationAction action;

	protected Feature<Object> feature;

	public ModificationResult(Long key, ModificationAction action,
			Feature<Object> feature) {
		this.key = key;
		this.action = action;
		this.feature = feature;
	}

	public ModificationResult(Long key, ModificationAction action) {
		this.key = key;
		this.action = action;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public ModificationAction getAction() {
		return action;
	}

	public void setAction(ModificationAction action) {
		this.action = action;
	}

	public Feature<Object> getFeature() {
		return feature;
	}

	public void setFeature(Feature<Object> feature) {
		this.feature = feature;
	}

}
