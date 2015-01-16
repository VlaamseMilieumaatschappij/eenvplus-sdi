package be.vmm.eenvplus.sdi.model;

import java.util.Date;

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
import javax.xml.bind.annotation.XmlType;

import be.vmm.eenvplus.sdi.model.code.Status;
import be.vmm.eenvplus.sdi.model.constraint.Refers;
import be.vmm.eenvplus.sdi.model.constraint.group.PrePersist;
import be.vmm.eenvplus.sdi.model.type.Reference;

@Entity
@Table(name = "rioollink_status", schema = "gengis")
@XmlRootElement
@XmlType(propOrder = { "id", "statusId", "geldigVanaf", "geldigTot" })
@XmlAccessorType(XmlAccessType.PROPERTY)
public class RioolLinkStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RioolLink_status_id_seq")
	@SequenceGenerator(name = "RioolLink_status_id_seq", sequenceName = "gengis.RioolLink_status_id_seq", allocationSize = 1)
	protected Long id;

	@NotNull
	@Refers(entityType = Status.class, groups = PrePersist.class)
	protected Reference<Status> statusId;

	protected Date geldigVanaf;
	protected Date geldigTot;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Reference<Status> getStatusId() {
		return statusId;
	}

	public void setStatusId(Reference<Status> statusId) {
		this.statusId = statusId;
	}

	public Date getGeldigVanaf() {
		return geldigVanaf;
	}

	public void setGeldigVanaf(Date geldigVanaf) {
		this.geldigVanaf = geldigVanaf;
	}

	public Date getGeldigTot() {
		return geldigTot;
	}

	public void setGeldigTot(Date geldigTot) {
		this.geldigTot = geldigTot;
	}

	@Override
	public String toString() {
		return statusId + " (" + geldigVanaf
				+ (geldigTot != null ? "/" + geldigTot : "") + ")";
	}
}
