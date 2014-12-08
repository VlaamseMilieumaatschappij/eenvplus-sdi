package be.vmm.eenvplus.sdi.model;

import java.util.Date;

import com.vividsolutions.jts.geom.Geometry;

public interface RioolObject {

	public Long getId();

	public void setId(Long id);

	public Date getCreationDate();

	public void setCreationDate(Date creationDate);

	public Date getBeginLifeSpanVersion();

	public void setBeginLifeSpanVersion(Date beginLifeSpanVersion);

	public Date getEndLifeSpanVersion();

	public void setEndLifeSpanVersion(Date endLifeSpanVersion);

	public String getAlternatieveId();

	public void setAlternatieveId(String alternatieveId);

	public Long getNamespaceId();

	public void setNamespaceId(Long namespaceId);

	public Geometry getGeom();

	public void setGeom(Geometry geom);

	public String getUserId();

	public void setUserId(String userId);
}
