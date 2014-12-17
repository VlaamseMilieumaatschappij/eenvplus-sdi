package be.vmm.eenvplus.sdi.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import be.vmm.eenvplus.sdi.model.code.Namespace;
import be.vmm.eenvplus.sdi.model.code.RioolAppurtenanceType;
import be.vmm.eenvplus.sdi.model.constraint.AssertQuery;
import be.vmm.eenvplus.sdi.model.constraint.GeometrySimple;
import be.vmm.eenvplus.sdi.model.constraint.GeometryType;
import be.vmm.eenvplus.sdi.model.constraint.NodeValue;
import be.vmm.eenvplus.sdi.model.constraint.Refers;
import be.vmm.eenvplus.sdi.model.constraint.RefersNode;
import be.vmm.eenvplus.sdi.model.constraint.Static;
import be.vmm.eenvplus.sdi.model.constraint.Unique;
import be.vmm.eenvplus.sdi.model.constraint.group.PostPersist;
import be.vmm.eenvplus.sdi.model.constraint.group.PrePersist;
import be.vmm.eenvplus.sdi.model.jaxb.GeometryXmlAdapter;
import be.vmm.eenvplus.sdi.model.type.Reference;
import be.vmm.eenvplus.sdi.plugins.providers.jackson.GeometryDeserializer;
import be.vmm.eenvplus.sdi.plugins.providers.jackson.GeometrySerializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Geometry;

@Entity
@Table(schema = "gengis")
@Where(clause = "endLifespanVersion IS NULL")
@SQLDelete(sql = "UPDATE gengis.RioolAppurtenance SET endLifespanVersion = now() WHERE id = ?")
@Unique(value = { "namespaceId", "alternatieveId" }, groups = PostPersist.class)
@Static(value = "rioolAppurtenanceTypeId", groups = PrePersist.class)
@XmlRootElement(name = "RioolAppurtenance")
@XmlType(propOrder = { "id", "creationDate", "beginLifespanVersion",
		"endLifespanVersion", "userId", "alternatieveId", "namespaceId",
		"rioolAppurtenanceTypeId", "label", "omschrijving", "vhaSegmentId",
		"statussen", "koppelPuntId", "geom" })
@XmlAccessorType(XmlAccessType.PROPERTY)
public class RioolAppurtenance implements RioolObject {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RioolAppurtenance_id_seq")
	@SequenceGenerator(name = "RioolAppurtenance_id_seq", sequenceName = "gengis.RioolAppurtenance_id_seq", allocationSize = 1)
	protected Long id;

	protected Date creationDate;
	protected Date beginLifespanVersion;
	protected Date endLifespanVersion;

	@Column(name = "user_id")
	protected String userId;

	protected String alternatieveId;
	@NotNull
	@Refers(entityType = Namespace.class, groups = PrePersist.class)
	protected Reference<Namespace> namespaceId;

	@NotNull
	@Refers(entityType = RioolAppurtenanceType.class, groups = PrePersist.class)
	protected Reference<RioolAppurtenanceType> rioolAppurtenanceTypeId;

	protected String label;
	protected String omschrijving;

	protected Long vhaSegmentId;

	@Valid
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH, CascadeType.DETACH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "rioolappurtenanceid")
	protected List<RioolAppurtenanceStatus> statussen;

	@NotNull
	@JsonProperty("koppelpuntId")
	protected Reference<KoppelPunt> koppelPuntId;

	@NotNull
	@GeometryType("Point")
	@GeometrySimple
	@Type(type = "org.hibernate.spatial.GeometryType")
	@JsonSerialize(using = GeometrySerializer.class)
	@JsonDeserialize(using = GeometryDeserializer.class)
	protected Geometry geom;

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

	public Date getBeginLifespanVersion() {
		return beginLifespanVersion;
	}

	public void setBeginLifespanVersion(Date beginLifespanVersion) {
		this.beginLifespanVersion = beginLifespanVersion;
	}

	public Date getEndLifespanVersion() {
		return endLifespanVersion;
	}

	public void setEndLifespanVersion(Date endLifespanVersion) {
		this.endLifespanVersion = endLifespanVersion;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAlternatieveId() {
		return alternatieveId;
	}

	public void setAlternatieveId(String alternatieveId) {
		this.alternatieveId = alternatieveId;
	}

	public Reference<Namespace> getNamespaceId() {
		return namespaceId;
	}

	public void setNamespaceId(Reference<Namespace> namespaceId) {
		this.namespaceId = namespaceId;
	}

	public Reference<RioolAppurtenanceType> getRioolAppurtenanceTypeId() {
		return rioolAppurtenanceTypeId;
	}

	public void setRioolAppurtenanceTypeId(
			Reference<RioolAppurtenanceType> rioolAppurtenanceTypeId) {
		this.rioolAppurtenanceTypeId = rioolAppurtenanceTypeId;
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

	public Long getVhaSegmentId() {
		return vhaSegmentId;
	}

	public void setVhaSegmentId(Long vhaSegmentId) {
		this.vhaSegmentId = vhaSegmentId;
	}

	@XmlElementWrapper(name = "statussen")
	@XmlElement(name = "status")
	public List<RioolAppurtenanceStatus> getStatussen() {
		return statussen;
	}

	public void setStatussen(List<RioolAppurtenanceStatus> statussen) {
		this.statussen = statussen;
	}

	public Reference<KoppelPunt> getKoppelPuntId() {
		return koppelPuntId;
	}

	public void setKoppelPuntId(Reference<KoppelPunt> koppelpuntId) {
		this.koppelPuntId = koppelpuntId;
	}

	@XmlJavaTypeAdapter(value = GeometryXmlAdapter.class)
	public Geometry getGeom() {
		return geom;
	}

	public void setGeom(Geometry geom) {
		this.geom = geom;
	}

	@RefersNode(nodeType = KoppelPunt.class, nodeGeometryName = "geom", maxDistance = 5, groups = PostPersist.class)
	protected NodeValue<KoppelPunt> getKoppelPuntReference() {
		return new NodeValue<KoppelPunt>(koppelPuntId, geom);
	}

	@AssertQuery(value = "SELECT count(l) = 0 FROM RioolLink l WHERE l.startKoppelPuntId = :koppelPuntId", groups = PostPersist.class)
	protected Map<String, Object> getCheckUitlaatParams() {

		if (Long.valueOf(6L/* dischargeStructure */).equals(
				rioolAppurtenanceTypeId)) {
			for (RioolAppurtenanceStatus status : statussen) {
				Date now = new Date();
				if (Long.valueOf(2L/* actief */).equals(
						status.statusId.getValue())
						&& (status.geldigVanaf == null || status.geldigVanaf
								.before(now))
						&& (status.geldigTot == null || status.geldigTot
								.after(now))) {
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("koppelPuntId", koppelPuntId.getValue());
					return params;
				}
			}
		}

		return null;
	}
}
