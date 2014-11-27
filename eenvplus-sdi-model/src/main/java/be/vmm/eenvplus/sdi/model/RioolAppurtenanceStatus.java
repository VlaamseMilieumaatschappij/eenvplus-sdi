package be.vmm.eenvplus.sdi.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "rioolappurtenance_status", schema = "gengis")
public class RioolAppurtenanceStatus {

	@Id
	protected Long id;

	@NotNull
	protected int statusId;

	protected String geldigVanaf;
	protected String geldigTot;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
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
