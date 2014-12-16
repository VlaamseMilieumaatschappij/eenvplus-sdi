package be.vmm.eenvplus.sdi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import be.vmm.eenvplus.sdi.model.code.Namespace;
import be.vmm.eenvplus.sdi.model.constraint.GeometryType;
import be.vmm.eenvplus.sdi.model.constraint.Refers;
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
@SQLDelete(sql = "UPDATE gengis.KoppelPunt SET endLifespanVersion = now() WHERE id = ?")
@Unique(value = { "namespaceId", "alternatieveId" }, groups = PostPersist.class)
@XmlRootElement(name = "KoppelPunt")
@XmlType(propOrder = { "id", "creationDate", "beginLifespanVersion",
		"endLifespanVersion", "userId", "alternatieveId", "namespaceId", "geom" })
@XmlAccessorType(XmlAccessType.PROPERTY)
public class KoppelPunt implements RioolObject {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KoppelPunt_id_seq")
	@SequenceGenerator(name = "KoppelPunt_id_seq", sequenceName = "gengis.KoppelPunt_id_seq", allocationSize = 1)
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
	@GeometryType("Point")
	@Type(type = "org.hibernate.spatial.GeometryType")
	@XmlTransient
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

	@XmlJavaTypeAdapter(value = GeometryXmlAdapter.class)
	public Geometry getGeom() {
		return geom;
	}

	public void setGeom(Geometry geom) {
		this.geom = geom;
	}
}