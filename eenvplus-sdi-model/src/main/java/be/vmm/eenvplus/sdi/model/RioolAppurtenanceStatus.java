package be.vmm.eenvplus.sdi.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import be.vmm.eenvplus.sdi.model.code.Status;
import be.vmm.eenvplus.sdi.model.constraint.Refers;

@Entity
@Table(name = "rioolappurtenance_status", schema = "gengis")
public class RioolAppurtenanceStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RioolAppurtenance_status_id_seq")
	@SequenceGenerator(name = "RioolAppurtenance_status_id_seq", sequenceName = "gengis.RioolAppurtenance_status_id_seq", allocationSize = 1)
	protected Long id;

	@NotNull
	@Refers(entityType = Status.class)
	protected Long statusId;

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
