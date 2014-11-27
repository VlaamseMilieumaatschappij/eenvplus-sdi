package be.vmm.eenvplus.sdi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "rioollink_status", schema = "gengis")
public class RioolLinkStatus {

	@Id
	protected Long id;

	@NotNull
	@Column(name = "statusid")
	protected Long statusId;

	protected String geldigVanaf;
	protected String geldigTot;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatus(Long statusId) {
		this.statusId = statusId;
	}

	public String getGeldigVanaf() {
		return geldigVanaf;
	}

	public void setGeldigVanaf(String geldigVanaf) {
		this.geldigVanaf = geldigVanaf;
	}

	public String getGeldigTot() {
		return geldigTot;
	}

	public void setGeldigTot(String geldigTot) {
		this.geldigTot = geldigTot;
	}

	@Override
	public String toString() {
		return statusId + " (" + geldigVanaf
				+ (geldigTot != null ? "/" + geldigTot : "") + ")";
	}
}
