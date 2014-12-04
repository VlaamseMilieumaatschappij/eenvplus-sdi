package be.vmm.eenvplus.sdi.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import be.vmm.eenvplus.sdi.model.code.Status;
import be.vmm.eenvplus.sdi.model.constraint.In;

@Entity
@Table(name = "rioollink_status", schema = "gengis")
public class RioolLinkStatus {

	@Id
	protected Long id;

	@NotNull
	@In(entityType = Status.class)
	protected Long statusId;

	@NotNull
	protected Date geldigVanaf;
	protected Date geldigTot;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
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
