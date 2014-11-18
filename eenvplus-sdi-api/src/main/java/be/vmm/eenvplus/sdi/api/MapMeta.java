package be.vmm.eenvplus.sdi.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class MapMeta {

	protected List<LayerMeta> layers;

	protected String description;
	protected String capabilities;
	protected String copyrightText;

	public List<LayerMeta> getLayers() {
		return layers;
	}

	public void setLayers(List<LayerMeta> layers) {
		this.layers = layers;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(String capabilities) {
		this.capabilities = capabilities;
	}

	public String getCopyrightText() {
		return copyrightText;
	}

	public void setCopyrightText(String copyrightText) {
		this.copyrightText = copyrightText;
	}
}
