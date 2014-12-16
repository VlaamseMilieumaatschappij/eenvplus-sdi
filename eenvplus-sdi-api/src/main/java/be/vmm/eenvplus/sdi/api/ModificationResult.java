package be.vmm.eenvplus.sdi.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import be.vmm.eenvplus.sdi.api.feature.Feature;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlType
@XmlAccessorType(XmlAccessType.PROPERTY)
@JsonInclude(Include.NON_NULL)
public class ModificationResult {

	protected String layerBodId;
	protected Object key;
	protected ModificationAction action;

	protected Feature<Object> feature;

	public ModificationResult(String layerBodId, Object key,
			ModificationAction action, Feature<Object> feature) {
		this.layerBodId = layerBodId;
		this.key = key;
		this.action = action;
		this.feature = feature;
	}

	public ModificationResult(String layerBodId, Object key,
			ModificationAction action) {
		this.layerBodId = layerBodId;
		this.key = key;
		this.action = action;
	}

	public String getLayerBodId() {
		return layerBodId;
	}

	public void setLayerBodId(String layerBodId) {
		this.layerBodId = layerBodId;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
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
