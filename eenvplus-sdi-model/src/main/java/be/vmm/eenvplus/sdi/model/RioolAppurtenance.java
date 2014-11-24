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
public class RioolAppurtenance {

	@Id
	protected Long id;

	@NotNull
	protected Date beginLifeSpanVersion;
	protected String alternatieveId;

	@NotNull
	@Column(name = "appurtenancetypeid")
	protected int appurtenanceType;
	@NotNull
	protected int koppelpuntId;
	protected String label;
	protected String omschrijving;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "rioolappurtenanceid")
	protected List<RioolAppurtenanceStatus> statussen;

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

	public int getAppurtenanceType() {
		return appurtenanceType;
	}

	public void setAppurtenanceType(int appurtenanceType) {
		this.appurtenanceType = appurtenanceType;
	}

	public int getKoppelpuntId() {
		return koppelpuntId;
	}

	public void setKoppelpuntId(int koppelpuntId) {
		this.koppelpuntId = koppelpuntId;
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

	public List<RioolAppurtenanceStatus> getStatussen() {
		return statussen;
	}

	public void setStatussen(List<RioolAppurtenanceStatus> statussen) {
		this.statussen = statussen;
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
