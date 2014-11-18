package be.vmm.eenvplus.sdi.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class LayerMeta {

	protected String name;
	protected String fullName;
	protected String idGeoCat;
	protected String layerBodId;

	protected String wmsResource;
	protected String scaleLimit;
	protected String inspireUpperAbstract;
	protected String insprireName;
	protected String urlDetails;
	protected String bundCollectionNumber;
	protected String dataOwner;
	protected String inspireAbstract;
	protected String absctract;
	protected String wmsContactAbbreviation;
	protected String downloadUrl;
	protected String maps;
	protected String wmsContactName;
	protected String dataStatus;
	protected String bundCollectionName;
	protected String inspireUpperName;
	protected String urlApplication;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getIdGeoCat() {
		return idGeoCat;
	}

	public void setIdGeoCat(String idGeoCat) {
		this.idGeoCat = idGeoCat;
	}

	public String getLayerBodId() {
		return layerBodId;
	}

	public void setLayerBodId(String layerBodId) {
		this.layerBodId = layerBodId;
	}

	public String getWmsResource() {
		return wmsResource;
	}

	public void setWmsResource(String wmsResource) {
		this.wmsResource = wmsResource;
	}

	public String getScaleLimit() {
		return scaleLimit;
	}

	public void setScaleLimit(String scaleLimit) {
		this.scaleLimit = scaleLimit;
	}

	public String getInspireUpperAbstract() {
		return inspireUpperAbstract;
	}

	public void setInspireUpperAbstract(String inspireUpperAbstract) {
		this.inspireUpperAbstract = inspireUpperAbstract;
	}

	public String getInsprireName() {
		return insprireName;
	}

	public void setInsprireName(String insprireName) {
		this.insprireName = insprireName;
	}

	public String getUrlDetails() {
		return urlDetails;
	}

	public void setUrlDetails(String urlDetails) {
		this.urlDetails = urlDetails;
	}

	public String getBundCollectionNumber() {
		return bundCollectionNumber;
	}

	public void setBundCollectionNumber(String bundCollectionNumber) {
		this.bundCollectionNumber = bundCollectionNumber;
	}

	public String getDataOwner() {
		return dataOwner;
	}

	public void setDataOwner(String dataOwner) {
		this.dataOwner = dataOwner;
	}

	public String getInspireAbstract() {
		return inspireAbstract;
	}

	public void setInspireAbstract(String inspireAbstract) {
		this.inspireAbstract = inspireAbstract;
	}

	public String getAbsctract() {
		return absctract;
	}

	public void setAbsctract(String absctract) {
		this.absctract = absctract;
	}

	public String getWmsContactAbbreviation() {
		return wmsContactAbbreviation;
	}

	public void setWmsContactAbbreviation(String wmsContactAbbreviation) {
		this.wmsContactAbbreviation = wmsContactAbbreviation;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getMaps() {
		return maps;
	}

	public void setMaps(String maps) {
		this.maps = maps;
	}

	public String getWmsContactName() {
		return wmsContactName;
	}

	public void setWmsContactName(String wmsContactName) {
		this.wmsContactName = wmsContactName;
	}

	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}

	public String getBundCollectionName() {
		return bundCollectionName;
	}

	public void setBundCollectionName(String bundCollectionName) {
		this.bundCollectionName = bundCollectionName;
	}

	public String getInspireUpperName() {
		return inspireUpperName;
	}

	public void setInspireUpperName(String inspireUpperName) {
		this.inspireUpperName = inspireUpperName;
	}

	public String getUrlApplication() {
		return urlApplication;
	}

	public void setUrlApplication(String urlApplication) {
		this.urlApplication = urlApplication;
	}
}
