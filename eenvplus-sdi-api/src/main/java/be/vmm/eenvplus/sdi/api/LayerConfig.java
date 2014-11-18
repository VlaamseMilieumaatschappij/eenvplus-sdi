package be.vmm.eenvplus.sdi.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class LayerConfig {

	protected static double[] RESOLUTIONS = new double[] { 1024, 512, 256, 128,
			64, 32, 16, 8, 4, 2, 1, 0.5, 0.25, 0.125, 0.0625, 0.03125 };

	protected static double[] _getResolutionsFromMatrixSet(int[] matrixSet) {
		double[] result = new double[matrixSet.length];

		for (int i = 0; i < matrixSet.length; i++) {
			result[i] = RESOLUTIONS[matrixSet[i]];
		}

		return result;
	}

	protected String layerBodId;
	protected String attribution;
	protected boolean background;
	protected boolean hasLegend;
	protected String format;
	protected Integer gutter;
	protected String type;
	protected boolean highlightable;
	protected Float opacity;
	protected Double minResolution;
	protected Double maxResolution;
	protected String parentLayerId;
	protected boolean queryable;
	protected boolean searchable;
	protected boolean selectbyrectangle;
	protected String serverLayerName;
	protected boolean singleTile;
	protected String[] subLayersIds;
	protected int[] matrixSet;
	protected boolean timeEnabled;
	protected String[] timestamps;
	protected String timeBehaviour;
	protected String[] maps;
	protected boolean chargeable;
	protected String staging;
	protected String wmsLayers;
	protected String wmsUrl;

	public LayerConfig() {
	}

	public LayerConfig(String layerBodId) {
		this.layerBodId = layerBodId;
	}

	public String getLayerBodId() {
		return layerBodId;
	}

	public void setLayerBodId(String layerBodId) {
		this.layerBodId = layerBodId;
	}

	public String getAttribution() {
		return attribution;
	}

	public void setAttribution(String attribution) {
		this.attribution = attribution;
	}

	public boolean isBackground() {
		return background;
	}

	public void setBackground(boolean background) {
		this.background = background;
	}

	public boolean isHasLegend() {
		return hasLegend;
	}

	public void setHasLegend(boolean hasLegend) {
		this.hasLegend = hasLegend;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Integer getGutter() {
		return gutter;
	}

	public void setGutter(Integer gutter) {
		this.gutter = gutter;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isHighlightable() {
		return highlightable;
	}

	public void setHighlightable(boolean highlightable) {
		this.highlightable = highlightable;
	}

	public Float getOpacity() {
		return opacity;
	}

	public void setOpacity(Float opacity) {
		this.opacity = opacity;
	}

	public Double getMinResolution() {
		return minResolution;
	}

	public void setMinResolution(Double minResolution) {
		this.minResolution = minResolution;
	}

	public Double getMaxResolution() {
		return maxResolution;
	}

	public void setMaxResolution(Double maxResolution) {
		this.maxResolution = maxResolution;
	}

	public String getParentLayerId() {
		return parentLayerId;
	}

	public void setParentLayerId(String parentLayerId) {
		this.parentLayerId = parentLayerId;
	}

	public boolean isQueryable() {
		return queryable;
	}

	public void setQueryable(boolean queryable) {
		this.queryable = queryable;
	}

	public boolean isSearchable() {
		return searchable;
	}

	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}

	public boolean isSelectbyrectangle() {
		return selectbyrectangle;
	}

	public void setSelectbyrectangle(boolean selectbyrectangle) {
		this.selectbyrectangle = selectbyrectangle;
	}

	public String getServerLayerName() {
		return serverLayerName;
	}

	public void setServerLayerName(String serverLayerName) {
		this.serverLayerName = serverLayerName;
	}

	public boolean isSingleTile() {
		return singleTile;
	}

	public void setSingleTile(boolean singleTile) {
		this.singleTile = singleTile;
	}

	public String[] getSubLayersIds() {
		return subLayersIds;
	}

	public void setSubLayersIds(String[] subLayersIds) {
		this.subLayersIds = subLayersIds;
	}

	public int[] getMatrixSet() {
		return matrixSet;
	}

	public void setMatrixSet(int[] matrixSet) {
		this.matrixSet = matrixSet;
	}

	public boolean isTimeEnabled() {
		return timeEnabled;
	}

	public void setTimeEnabled(boolean timeEnabled) {
		this.timeEnabled = timeEnabled;
	}

	public String[] getTimestamps() {
		return timestamps;
	}

	public void setTimestamps(String[] timestamps) {
		this.timestamps = timestamps;
	}

	public String getTimeBehaviour() {
		return timeBehaviour;
	}

	public void setTimeBehaviour(String timeBehaviour) {
		this.timeBehaviour = timeBehaviour;
	}

	public String[] getMaps() {
		return maps;
	}

	public void setMaps(String[] maps) {
		this.maps = maps;
	}

	public boolean isChargeable() {
		return chargeable;
	}

	public void setChargeable(boolean chargeable) {
		this.chargeable = chargeable;
	}

	public String getStaging() {
		return staging;
	}

	public void setStaging(String staging) {
		this.staging = staging;
	}

	public String getWmsLayers() {
		return wmsLayers;
	}

	public void setWmsLayers(String wmsLayers) {
		this.wmsLayers = wmsLayers;
	}

	public String getWmsUrl() {
		return wmsUrl;
	}

	public void setWmsUrl(String wmsUrl) {
		this.wmsUrl = wmsUrl;
	}

	public double[] getLabel() {
		return null;
	}

	public double[] getResolutions() {
		return matrixSet != null ? _getResolutionsFromMatrixSet(matrixSet)
				: null;
	}
}
