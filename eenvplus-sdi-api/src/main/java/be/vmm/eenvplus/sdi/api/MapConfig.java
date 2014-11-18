package be.vmm.eenvplus.sdi.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class MapConfig {

	protected String id;
	protected String[] langs;
	protected String[] selectedLayers;
	protected String[] backgroundLayers;
	protected boolean showCatalog;

	public MapConfig() {
	}

	public MapConfig(String id, String[] langs, String[] selectedLayers,
			String[] backgroundLayers, boolean showCatalog) {
		this.id = id;
		this.langs = langs;
		this.selectedLayers = selectedLayers;
		this.backgroundLayers = backgroundLayers;
		this.showCatalog = showCatalog;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getLangs() {
		return langs;
	}

	public void setLangs(String[] langs) {
		this.langs = langs;
	}

	public String[] getSelectedLayers() {
		return selectedLayers;
	}

	public void setSelectedLayers(String[] selectedLayers) {
		this.selectedLayers = selectedLayers;
	}

	public String[] getBackgroundLayers() {
		return backgroundLayers;
	}

	public void setBackgroundLayers(String[] backgroundLayers) {
		this.backgroundLayers = backgroundLayers;
	}

	public boolean isShowCatalog() {
		return showCatalog;
	}

	public void setShowCatalog(boolean showCatalog) {
		this.showCatalog = showCatalog;
	}
}
