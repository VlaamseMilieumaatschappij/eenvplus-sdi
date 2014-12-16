package be.vmm.eenvplus.sdi.api;

import be.vmm.eenvplus.sdi.api.feature.Feature;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ModifiedFeature<T> extends Feature<T> {

	protected Long key;
	protected ModificationAction action;

	public ModifiedFeature(T object) {
		super(object);
	}

	public ModifiedFeature(T object, Long key, ModificationAction action) {
		super(object);
		this.key = key;
		this.action = action;
	}

	@JsonCreator
	public ModifiedFeature(
			@JsonProperty(value = "layerBodId", required = true) String layerBodId)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		super(layerBodId);
	}

	@JsonProperty(required = true)
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
}
