package be.vmm.eenvplus.sdi.model.code;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "gengis")
public class RioolLinkType {

	@Id
	protected Long id;

	protected String rioolLinkType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRioolLinkType() {
		return rioolLinkType;
	}

	public void setRioolLinkType(String rioolLinkType) {
		this.rioolLinkType = rioolLinkType;
	}
}
