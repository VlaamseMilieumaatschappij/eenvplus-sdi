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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import be.vmm.eenvplus.sdi.model.code.Namespace;
import be.vmm.eenvplus.sdi.model.code.RioolAppurtenanceType;
import be.vmm.eenvplus.sdi.model.constraint.AssertQuery;
import be.vmm.eenvplus.sdi.model.constraint.GeometrySimple;
import be.vmm.eenvplus.sdi.model.constraint.GeometryType;
import be.vmm.eenvplus.sdi.model.constraint.RefersNode;
import be.vmm.eenvplus.sdi.model.constraint.NodeValue;
import be.vmm.eenvplus.sdi.model.constraint.Refers;
import be.vmm.eenvplus.sdi.model.constraint.Static;
import be.vmm.eenvplus.sdi.model.constraint.Unique;
import be.vmm.eenvplus.sdi.plugins.providers.jackson.GeometryDeserializer;
import be.vmm.eenvplus.sdi.plugins.providers.jackson.GeometrySerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Geometry;

@Entity
@Table(schema = "gengis")
@Where(clause = "endLifeSpanVersion IS NULL")
@SQLDelete(sql = "UPDATE gengis.RioolAppurtenance SET endLifeSpanVersion = now() WHERE id = ?")
@Unique({ "namespaceId", "alternatieveId" })
@Static("rioolAppurtenanceTypeId")
public class RioolAppurtenance implements RioolObject {

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
	@Refers(entityType = RioolAppurtenanceType.class)
	protected Long rioolAppurtenanceTypeId;
	@NotNull
	protected Long koppelpuntId;
	protected String label;
	protected String omschrijving;

	protected Long vhaSegmentId;

	@Valid
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH, CascadeType.DETACH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "rioolappurtenanceid")
	protected List<RioolAppurtenanceStatus> statussen;

	@Refers(entityType = Namespace.class)
	protected Long namespaceId;

	@NotNull
	@GeometryType("Point")
	@GeometrySimple
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

	public Long getRioolAppurtenanceTypeId() {
		return rioolAppurtenanceTypeId;
	}

	public void setRioolAppurtenanceTypeId(Long rioolAppurtenanceTypeId) {
		this.rioolAppurtenanceTypeId = rioolAppurtenanceTypeId;
	}

	public Long getKoppelpuntId() {
		return koppelpuntId;
	}

	public void setKoppelpuntId(Long koppelpuntId) {
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

	public Long getVhaSegmentId() {
		return vhaSegmentId;
	}

	public void setVhaSegmentId(Long vhaSegmentId) {
		this.vhaSegmentId = vhaSegmentId;
	}

	public void setStatussen(List<RioolAppurtenanceStatus> statussen) {
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

	@RefersNode(nodeType = KoppelPunt.class, nodeGeometryName = "geom", maxDistance = 5)
	protected NodeValue getKoppelPuntReference() {
		return new NodeValue(koppelpuntId, geom);
	}

	@AssertQuery("SELECT count(l) = 0 FROM RioolLink l WHERE l.startKoppelPuntId = :koppelpuntId OR l.endKoppelPuntId = :koppelpuntId")
	protected Map<String, Object> getCheckUitlaatParams() {

		if (rioolAppurtenanceTypeId == 6L/* dischargeStructure */) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("koppelpuntId", koppelpuntId);
			return params;
		}

		return null;
	}
}
