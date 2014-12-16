package be.vmm.eenvplus.sdi.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Riool")
public class Riool {

	protected List<RioolObject> objects;

	@XmlElementRefs({ @XmlElementRef(type = RioolLink.class),
			@XmlElementRef(type = RioolAppurtenance.class),
			@XmlElementRef(type = KoppelPunt.class) })
	public List<RioolObject> getObjects() {
		return objects;
	}

	public void setObjects(List<RioolObject> objects) {
		this.objects = objects;
	}
}
