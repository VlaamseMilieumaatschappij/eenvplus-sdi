package be.vmm.eenvplus.sdi.model;

import java.util.Date;

import be.vmm.eenvplus.sdi.model.code.Namespace;
import be.vmm.eenvplus.sdi.model.type.Reference;

import com.vividsolutions.jts.geom.Geometry;

public interface RioolObject {

	public Long getId();

	public void setId(Long id);

	public Date getCreationDate();

	public void setCreationDate(Date creationDate);

	public Date getBeginLifespanVersion();

	public void setBeginLifespanVersion(Date beginLifespanVersion);

	public Date getEndLifespanVersion();

	public void setEndLifespanVersion(Date endLifespanVersion);

	public String getUserId();

	public void setUserId(String userId);

	public String getAlternatieveId();

	public void setAlternatieveId(String alternatieveId);

	public Reference<Namespace> getNamespaceId();

	public void setNamespaceId(Reference<Namespace> namespaceId);

	public Geometry getGeom();

	public void setGeom(Geometry geom);
}
