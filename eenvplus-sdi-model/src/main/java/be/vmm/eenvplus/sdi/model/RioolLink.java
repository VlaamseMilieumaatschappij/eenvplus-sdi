package be.vmm.eenvplus.sdi.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import be.vmm.eenvplus.sdi.plugins.providers.jackson.GeometryDeserializer;
import be.vmm.eenvplus.sdi.plugins.providers.jackson.GeometrySerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Geometry;

@Entity
@Table(schema = "gengis")
public class RioolLink {

	@Id
	protected Long id;

	@NotNull
	protected Date beginLifeSpanVersion;
	protected String alternatieveId;

	@NotNull
	protected Long rioolLinkTypeId;
	@NotNull
	protected Long startKoppelPuntId;
	@NotNull
	protected Long endKoppelPuntId;
	@NotNull
	protected Double diameter;
	protected Double pressure;
	protected String label;
	protected String omschrijving;
	@NotNull
	protected Long sewerWaterTypeId;

	protected Long straatId;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "rioollinkid")
	protected List<RioolLinkStatus> statussen;

	@NotNull
	protected Long namespaceId;

	@Type(type = "org.hibernate.spatial.GeometryType")
	@JsonSerialize(using = GeometrySerializer.class)
	@JsonDeserialize(using = GeometryDeserializer.class)
	protected Geometry geom;

	@Column(name = "user_id")
	protected String userId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getBeginLifeSpanVersion() {
		return beginLifeSpanVersion;
	}

	public void setBeginLifeSpanVersion(Date beginLifeSpanVersion) {
		this.beginLifeSpanVersion = beginLifeSpanVersion;
	}

	public String getAlternatieveId() {
		return alternatieveId;
	}

	public void setAlternatieveId(String alternatieveId) {
		this.alternatieveId = alternatieveId;
	}

	public Long getRioolLinkTypeId() {
		return rioolLinkTypeId;
	}

	public void setRioolLinkTypeId(Long rioolLinkTypeId) {
		this.rioolLinkTypeId = rioolLinkTypeId;
	}

	public Long getStartKoppelPuntId() {
		return startKoppelPuntId;
	}

	public void setStartKoppelPuntId(Long startKoppelPuntId) {
		this.startKoppelPuntId = startKoppelPuntId;
	}

	public Long getEndKoppelPuntId() {
		return endKoppelPuntId;
	}

	public void setEndKoppelPuntId(Long endKoppelPuntId) {
		this.endKoppelPuntId = endKoppelPuntId;
	}

	public Double getDiameter() {
		return diameter;
	}

	public void setDiameter(Double diameter) {
		this.diameter = diameter;
	}

	public Double getPressure() {
		return pressure;
	}

	public void setPressure(Double pressure) {
		this.pressure = pressure;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getOmschrijving() {
		return omschrijving;
	}

	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}

	public Long getSewerWaterTypeId() {
		return sewerWaterTypeId;
	}

	public void setSewerWaterTypeId(Long sewerWaterTypeId) {
		this.sewerWaterTypeId = sewerWaterTypeId;
	}

	public Long getStraatId() {
		return straatId;
	}

	public void setStraatId(Long straatId) {
		this.straatId = straatId;
	}

	public List<RioolLinkStatus> getStatussen() {
		return statussen;
	}

	public void setStatussen(List<RioolLinkStatus> statussen) {
		this.statussen = statussen;
	}

	public Long getNamespaceId() {
		return namespaceId;
	}

	public void setNamespaceId(Long namespaceId) {
		this.namespaceId = namespaceId;
	}

	public Geometry getGeom() {
		return geom;
	}

	public void setGeom(Geometry geom) {
		this.geom = geom;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
