package be.vmm.eenvplus.sdi.model.code;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "gengis")
public class SewerWaterType implements Code {

	@Id
	protected Long id;

	@Column(name = "sewerWaterType_en")
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

	public void setLabel(String label) {
		this.label = label;
	}
}
