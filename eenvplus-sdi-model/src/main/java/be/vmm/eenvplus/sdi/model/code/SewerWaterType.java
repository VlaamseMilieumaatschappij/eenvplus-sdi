package be.vmm.eenvplus.sdi.model.code;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "gengis")
public class SewerWaterType {

	@Id
	protected Long id;

	protected String sewerWaterType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSewerWaterType() {
		return sewerWaterType;
	}

	public void setSewerWaterType(String sewerWaterType) {
		this.sewerWaterType = sewerWaterType;
	}
}
