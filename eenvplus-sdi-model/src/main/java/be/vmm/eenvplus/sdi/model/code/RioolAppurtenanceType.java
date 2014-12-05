package be.vmm.eenvplus.sdi.model.code;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "gengis")
public class RioolAppurtenanceType implements Code {

	@Id
	protected Long id;

	@Column(name = "rioolAppurtenanceType")
	protected String label;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String rioolAppurtenanceType) {
		this.label = rioolAppurtenanceType;
	}
}
