package be.vmm.eenvplus.sdi.api;

import java.util.HashMap;
import java.util.Map;

public class SearchResult {

	protected int weight;

	protected Map<String, Object> attrs = new HashMap<String, Object>();

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void setAttr(String name, Object value) {
		attrs.put(name, value);
	}

	public Object getAttr(String name) {
		return attrs.get(name);
	}

	public Map<String, Object> getAttrs() {
		return attrs;
	}

	public void setAttrs(Map<String, Object> attrs) {
		this.attrs = attrs;
	}
}
