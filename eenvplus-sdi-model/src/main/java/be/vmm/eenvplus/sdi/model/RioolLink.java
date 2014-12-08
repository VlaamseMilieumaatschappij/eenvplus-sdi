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
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import be.vmm.eenvplus.sdi.model.code.Namespace;
import be.vmm.eenvplus.sdi.model.code.RioolLinkType;
import be.vmm.eenvplus.sdi.model.code.SewerWaterType;
import be.vmm.eenvplus.sdi.model.constraint.GeometryType;
import be.vmm.eenvplus.sdi.model.constraint.In;
import be.vmm.eenvplus.sdi.model.constraint.Unique;
import be.vmm.eenvplus.sdi.plugins.providers.jackson.GeometryDeserializer;
import be.vmm.eenvplus.sdi.plugins.providers.jackson.GeometrySerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Geometry;

@Entity
@Table(schema = "gengis")
@Where(clause = "endLifeSpanVersion IS NULL")
@SQLDelete(sql = "UPDATE gengis.RioolLink SET endLifeSpanVersion = now() WHERE id = ?")
@Unique({ "namespaceId", "alternatieveId" })
public class RioolLink implements RioolObject {

	@Id
	protected Long id;

	@Past
	protected Date creationDate;
	@Past
	protected Date beginLifeSpanVersion;
	@Past
	protected Date endLifeSpanVersion;

	protected String alternatieveId;

	@NotNull
	@In(entityType = RioolLinkType.class)
	protected Long rioolLinkTypeId;
	@NotNull
	protected Long startKoppelPuntId;
	@NotNull
	protected Long endKoppelPuntId;
	@NotNull
	@Min(0)
	protected Double diameter;
	protected Double pressure;
	protected String label;
	protected String omschrijving;
	@NotNull
	@In(entityType = SewerWaterType.class)
	protected Long sewerWaterTypeId;

	protected Long straatId;

	@Valid
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH, CascadeType.DETACH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "rioollinkid")
	protected List<RioolLinkStatus> statussen;

	@In(entityType = Namespace.class)
	protected Long namespaceId;

	@NotNull
	@GeometryType("LineString")
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getBeginLifeSpanVersion() {
		return beginLifeSpanVersion;
	}

	public void setBeginLifeSpanVersion(Date beginLifeSpanVersion) {
		this.beginLifeSpanVersion = beginLifeSpanVersion;
	}

	public Date getEndLifeSpanVersion() {
		return endLifeSpanVersion;
	}

	public void setEndLifeSpanVersion(Date endLifeSpanVersion) {
		this.endLifeSpanVersion = endLifeSpanVersion;
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
