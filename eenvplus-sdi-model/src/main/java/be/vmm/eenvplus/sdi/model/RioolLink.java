package be.vmm.eenvplus.sdi.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import be.vmm.eenvplus.sdi.model.constraint.AssertQuery;
import be.vmm.eenvplus.sdi.model.constraint.AssertQuery.AssertQueryCondition;
import be.vmm.eenvplus.sdi.model.constraint.GeometryType;
import be.vmm.eenvplus.sdi.model.constraint.NodePosition;
import be.vmm.eenvplus.sdi.model.constraint.RefersNode;
import be.vmm.eenvplus.sdi.model.constraint.NodeValue;
import be.vmm.eenvplus.sdi.model.constraint.Refers;
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
	@Refers(entityType = RioolLinkType.class)
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
	@Refers(entityType = SewerWaterType.class)
	protected Long sewerWaterTypeId;

	protected Long straatId;

	@Valid
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH, CascadeType.DETACH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "rioollinkid")
	protected List<RioolLinkStatus> statussen;

	@Refers(entityType = Namespace.class)
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

	@RefersNode(nodePosition = NodePosition.start, nodeType = KoppelPunt.class, nodeGeometryName = "geom", maxDistance = 0.01)
	protected NodeValue getStartKoppelPuntReference() {
		return new NodeValue(startKoppelPuntId, geom);
	}

	@RefersNode(nodePosition = NodePosition.end, nodeType = KoppelPunt.class, nodeGeometryName = "geom", maxDistance = 0.01)
	protected NodeValue getEndKoppelPuntReference() {
		return new NodeValue(startKoppelPuntId, geom);
	}

	@AssertQuery(value = {
			"SELECT count(l) = 0 FROM RioolLink l WHERE rioolLinkTypeId = 2 AND (l.startKoppelPuntId = :koppelpuntId OR l.endKoppelPuntId = :koppelpuntId)",
			"SELECT count(a) = 0 FROM RioolAppurtenance a WHERE rioolAppurtenanceTypeId = 7 AND (a.koppelpuntId = :koppelpuntId)" }, condition = AssertQueryCondition.any)
	protected Map<String, Object> getCheckStartPersleidingParams() {

		if (rioolLinkTypeId == 2L/* persleiding */) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("koppelpuntId", startKoppelPuntId);
			return params;
		}

		return null;
	}

	@AssertQuery(value = {
			"SELECT count(l) = 0 FROM RioolLink l WHERE rioolLinkTypeId = 2 AND (l.startKoppelPuntId = :koppelpuntId OR l.endKoppelPuntId = :koppelpuntId)",
			"SELECT count(a) = 0 FROM RioolAppurtenance a WHERE rioolAppurtenanceTypeId = 7 AND (a.koppelpuntId = :koppelpuntId)" }, condition = AssertQueryCondition.any)
	protected Map<String, Object> getCheckEndPersleidingParams() {

		if (rioolLinkTypeId == 2L/* persleiding */) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("koppelpuntId", endKoppelPuntId);
			return params;
		}

		return null;
	}
}
