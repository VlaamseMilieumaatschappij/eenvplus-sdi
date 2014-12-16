package be.vmm.eenvplus.sdi.model;

import java.util.Date;

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

	public Long getNamespaceId();

	public void setNamespaceId(Long namespaceId);

	public Geometry getGeom();

	public void setGeom(Geometry geom);
}
