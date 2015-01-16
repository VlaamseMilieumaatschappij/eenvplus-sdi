package be.vmm.eenvplus.sdi.model;

import java.util.ArrayList;
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
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import be.vmm.eenvplus.sdi.model.code.Namespace;
import be.vmm.eenvplus.sdi.model.code.RioolLinkType;
import be.vmm.eenvplus.sdi.model.code.SewerWaterType;
import be.vmm.eenvplus.sdi.model.constraint.AssertQuery;
import be.vmm.eenvplus.sdi.model.constraint.AssertQuery.AssertQueryCondition;
import be.vmm.eenvplus.sdi.model.constraint.GeometrySimple;
import be.vmm.eenvplus.sdi.model.constraint.GeometrySingle;
import be.vmm.eenvplus.sdi.model.constraint.GeometryType;
import be.vmm.eenvplus.sdi.model.constraint.NodePosition;
import be.vmm.eenvplus.sdi.model.constraint.NodeValue;
import be.vmm.eenvplus.sdi.model.constraint.Refers;
import be.vmm.eenvplus.sdi.model.constraint.RefersNode;
import be.vmm.eenvplus.sdi.model.constraint.Unique;
import be.vmm.eenvplus.sdi.model.constraint.group.PostPersist;
import be.vmm.eenvplus.sdi.model.constraint.group.PrePersist;
import be.vmm.eenvplus.sdi.model.jaxb.GeometryXmlAdapter;
import be.vmm.eenvplus.sdi.model.type.Reference;
import be.vmm.eenvplus.sdi.plugins.providers.jackson.GeometryDeserializer;
import be.vmm.eenvplus.sdi.plugins.providers.jackson.GeometrySerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Geometry;

@Entity
@Table(schema = "gengis")
@Where(clause = "endLifespanVersion IS NULL")
@SQLDelete(sql = "UPDATE gengis.RioolLink SET endLifespanVersion = now() WHERE id = ?")
@Unique(value = { "namespaceId", "alternatieveId" }, groups = PostPersist.class)
@XmlRootElement(name = "RioolLink")
@XmlType(propOrder = { "id", "creationDate", "beginLifespanVersion",
		"endLifespanVersion", "userId", "alternatieveId", "namespaceId",
		"rioolLinkTypeId", "sewerWaterTypeId", "diameter", "pressure", "label",
		"omschrijving", "straatId", "statussen", "startKoppelPuntId",
		"endKoppelPuntId", "geom" })
@XmlAccessorType(XmlAccessType.PROPERTY)
public class RioolLink implements RioolObject {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RioolLink_id_seq")
	@SequenceGenerator(name = "RioolLink_id_seq", sequenceName = "gengis.RioolLink_id_seq", allocationSize = 1)
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
	@Refers(entityType = RioolLinkType.class, groups = PrePersist.class)
	protected Reference<RioolLinkType> rioolLinkTypeId;
	@NotNull
	@Refers(entityType = SewerWaterType.class, groups = PrePersist.class)
	protected Reference<SewerWaterType> sewerWaterTypeId;

	@NotNull
	@DecimalMin("250")
	@DecimalMax("3200")
	@Digits(integer = 4, fraction = 0)
	protected Double diameter;
	@DecimalMin("0.1")
	@DecimalMax("15.0")
	@Digits(integer = 2, fraction = 2)
	protected Double pressure;

	protected String label;
	protected String omschrijving;

	protected Long straatId;

	@Valid
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH, CascadeType.DETACH }, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "rioollinkid", nullable = false)
	protected List<RioolLinkStatus> statussen;

	@NotNull
	protected Reference<KoppelPunt> startKoppelPuntId;
	@NotNull
	protected Reference<KoppelPunt> endKoppelPuntId;

	@NotNull
	@GeometryType("LineString")
	@GeometrySingle
	@GeometrySimple
	@Type(type = "org.hibernate.spatial.GeometryType")
	@XmlTransient
	@JsonSerialize(using = GeometrySerializer.class)
	@JsonDeserialize(using = GeometryDeserializer.class)
	protected Geometry geom;

	public RioolLink() {
		this.statussen = new ArrayList<RioolLinkStatus>(2);
	}

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

	public Reference<RioolLinkType> getRioolLinkTypeId() {
		return rioolLinkTypeId;
	}

	public void setRioolLinkTypeId(Reference<RioolLinkType> rioolLinkTypeId) {
		this.rioolLinkTypeId = rioolLinkTypeId;
	}

	public Reference<SewerWaterType> getSewerWaterTypeId() {
		return sewerWaterTypeId;
	}

	public void setSewerWaterTypeId(Reference<SewerWaterType> sewerWaterTypeId) {
		this.sewerWaterTypeId = sewerWaterTypeId;
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

	public Long getStraatId() {
		return straatId;
	}

	public void setStraatId(Long straatId) {
		this.straatId = straatId;
	}

	@XmlElementWrapper(name = "statussen")
	@XmlElement(name = "status")
	public List<RioolLinkStatus> getStatussen() {
		return statussen;
	}

	public void setStatussen(List<RioolLinkStatus> statussen) {
		this.statussen = statussen;
	}

	public Reference<KoppelPunt> getStartKoppelPuntId() {
		return startKoppelPuntId;
	}

	public void setStartKoppelPuntId(Reference<KoppelPunt> startKoppelPuntId) {
		this.startKoppelPuntId = startKoppelPuntId;
	}

	public Reference<KoppelPunt> getEndKoppelPuntId() {
		return endKoppelPuntId;
	}

	public void setEndKoppelPuntId(Reference<KoppelPunt> endKoppelPuntId) {
		this.endKoppelPuntId = endKoppelPuntId;
	}

	@XmlJavaTypeAdapter(value = GeometryXmlAdapter.class)
	public Geometry getGeom() {
		return geom;
	}

	public void setGeom(Geometry geom) {
		this.geom = geom;
	}

	@RefersNode(nodePosition = NodePosition.start, nodeType = KoppelPunt.class, nodeGeometryName = "geom", maxDistance = 0.01, groups = PostPersist.class)
	protected NodeValue<KoppelPunt> getStartKoppelPuntReference() {
		return new NodeValue<KoppelPunt>(startKoppelPuntId, geom);
	}

	@RefersNode(nodePosition = NodePosition.end, nodeType = KoppelPunt.class, nodeGeometryName = "geom", maxDistance = 0.01, groups = PostPersist.class)
	protected NodeValue<KoppelPunt> getEndKoppelPuntReference() {
		return new NodeValue<KoppelPunt>(endKoppelPuntId, geom);
	}

	@AssertQuery(value = {
			"SELECT count(l) > 0 FROM RioolLink l WHERE rioolLinkTypeId = 2 AND (l.startKoppelPuntId = :koppelpuntId OR l.endKoppelPuntId = :koppelpuntId)",
			"SELECT count(a) > 0 FROM RioolAppurtenance a WHERE rioolAppurtenanceTypeId = 7 AND (a.koppelpuntId = :koppelpuntId)" }, condition = AssertQueryCondition.any, groups = PostPersist.class)
	protected Map<String, Object> getCheckStartPersleidingParams() {

		if (Long.valueOf(2L/* persleiding */).equals(
				rioolLinkTypeId.getValue())) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("koppelpuntId", startKoppelPuntId.getValue());
			return params;
		}

		return null;
	}

	@AssertQuery(value = {
			"SELECT count(l) = 0 FROM RioolLink l WHERE rioolLinkTypeId = 2 AND (l.startKoppelPuntId = :koppelpuntId OR l.endKoppelPuntId = :koppelpuntId)",
			"SELECT count(a) = 0 FROM RioolAppurtenance a WHERE rioolAppurtenanceTypeId = 7 AND (a.koppelpuntId = :koppelpuntId)" }, condition = AssertQueryCondition.any, groups = PostPersist.class)
	protected Map<String, Object> getCheckEndPersleidingParams() {

		if (Long.valueOf(2L/* persleiding */).equals(
				rioolLinkTypeId.getValue())) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("koppelpuntId", endKoppelPuntId.getValue());
			return params;
		}

		return null;
	}
}
