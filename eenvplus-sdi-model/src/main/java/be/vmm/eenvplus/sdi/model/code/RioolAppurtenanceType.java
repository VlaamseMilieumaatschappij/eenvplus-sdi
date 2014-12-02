package be.vmm.eenvplus.sdi.model.code;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "gengis")
public class RioolAppurtenanceType {

	@Id
	protected Long id;

	protected String rioolAppurtenanceType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRioolAppurtenanceType() {
		return rioolAppurtenanceType;
	}

	public void setRioolAppurtenanceType(String rioolAppurtenanceType) {
		this.rioolAppurtenanceType = rioolAppurtenanceType;
	}
}
