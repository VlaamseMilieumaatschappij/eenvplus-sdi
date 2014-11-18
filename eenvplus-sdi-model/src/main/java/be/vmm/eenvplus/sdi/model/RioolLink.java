package be.vmm.eenvplus.sdi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import be.vmm.eenvplus.sdi.plugins.providers.jackson.GeometryDeserializer;
import be.vmm.eenvplus.sdi.plugins.providers.jackson.GeometrySerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Geometry;

@Entity
public class RioolLink {

	@Id
	protected Long id;

	@NotNull
	protected Date beginLifeSpanVersion;
	protected String alternatieveId;

	@NotNull
	@Column(name = "stengtypeid")
	protected int stengType;
	@NotNull
	protected int startKoppelPuntId;
	@NotNull
	protected int endKoppelPuntId;
	@NotNull
	protected Double diameter;
	protected Double pressure;
	protected String label;
	protected String omschrijving;
	@NotNull
	@Column(name = "sewerwatertypeid")
	protected int sewerWaterType;

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

	public int getStengType() {
		return stengType;
	}

	public void setStengType(int stengType) {
		this.stengType = stengType;
	}

	public int getStartKoppelPuntId() {
		return startKoppelPuntId;
	}

	public void setStartKoppelPuntId(int startKoppelPuntId) {
		this.startKoppelPuntId = startKoppelPuntId;
	}

	public int getEndKoppelPuntId() {
		return endKoppelPuntId;
	}

	public void setEndKoppelPuntId(int endKoppelPuntId) {
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

	public int getSewerWaterType() {
		return sewerWaterType;
	}

	public void setSewerWaterType(int sewerWaterType) {
		this.sewerWaterType = sewerWaterType;
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
