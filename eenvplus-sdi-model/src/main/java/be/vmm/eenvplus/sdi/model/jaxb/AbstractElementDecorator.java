package be.vmm.eenvplus.sdi.model.jaxb;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

public abstract class AbstractElementDecorator implements Element {

	protected abstract Element getDelegate();

	public String getAttribute(String name) {
		return getDelegate().getAttribute(name);
	}

	public Attr getAttributeNode(String name) {
		return getDelegate().getAttributeNode(name);
	}

	public NodeList getElementsByTagName(String name) {
		return getDelegate().getElementsByTagName(name);
	}

	public String getNodeName() {
		return getDelegate().getNodeName();
	}

	public String getNodeValue() throws DOMException {
		return getDelegate().getNodeValue();
	}

	public short getNodeType() {
		return getDelegate().getNodeType();
	}

	public Node getParentNode() {
		return getDelegate().getParentNode();
	}

	public NodeList getChildNodes() {
		return getDelegate().getChildNodes();
	}

	public Node getFirstChild() {
		return getDelegate().getFirstChild();
	}

	public Node getLastChild() {
		return getDelegate().getLastChild();
	}

	public Node getNextSibling() {
		return getDelegate().getNextSibling();
	}

	public NamedNodeMap getAttributes() {
		return getDelegate().getAttributes();
	}

	public Node appendChild(Node newChild) throws DOMException {
		return getDelegate().appendChild(newChild);
	}

	public Node cloneNode(boolean deep) {
		return getDelegate().cloneNode(deep);
	}

	public String getAttributeNS(String arg0, String arg1) {
		return getDelegate().getAttributeNS(arg0, arg1);
	}

	public Attr getAttributeNodeNS(String arg0, String arg1) {
		return getDelegate().getAttributeNodeNS(arg0, arg1);
	}

	public NodeList getElementsByTagNameNS(String arg0, String arg1) {
		return getDelegate().getElementsByTagNameNS(arg0, arg1);
	}

	public String getLocalName() {
		return getDelegate().getLocalName();
	}

	public String getNamespaceURI() {
		return getDelegate().getNamespaceURI();
	}

	public Document getOwnerDocument() {
		return getDelegate().getOwnerDocument();
	}

	public String getPrefix() {
		return getDelegate().getPrefix();
	}

	public String getTagName() {
		return getDelegate().getTagName();
	}

	public Node getPreviousSibling() {
		return getDelegate().getPreviousSibling();
	}

	public boolean hasAttribute(String arg0) {
		return getDelegate().hasAttribute(arg0);
	}

	public boolean hasAttributeNS(String arg0, String arg1) {
		return getDelegate().hasAttributeNS(arg0, arg1);
	}

	public boolean hasAttributes() {
		return getDelegate().hasAttributes();
	}

	public void removeAttributeNS(String arg0, String arg1) throws DOMException {
		getDelegate().removeAttributeNS(arg0, arg1);
	}

	public void setAttribute(String name, String value) throws DOMException {
		getDelegate().setAttribute(name, value);
	}

	public Attr setAttributeNode(Attr newAttr) throws DOMException {
		return getDelegate().setAttributeNode(newAttr);
	}

	public Attr removeAttributeNode(Attr oldAttr) throws DOMException {
		return getDelegate().removeAttributeNode(oldAttr);
	}

	public void setNodeValue(String nodeValue) throws DOMException {
		getDelegate().setNodeValue(nodeValue);
	}

	public Node insertBefore(Node newChild, Node refChild) throws DOMException {
		return getDelegate().insertBefore(newChild, refChild);
	}

	public boolean hasChildNodes() {
		return getDelegate().hasChildNodes();
	}

	public boolean isSupported(String arg0, String arg1) {
		return getDelegate().isSupported(arg0, arg1);
	}

	public void normalize() {
		getDelegate().normalize();
	}

	public void removeAttribute(String name) throws DOMException {
		getDelegate().removeAttribute(name);
	}

	public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
		return getDelegate().replaceChild(newChild, oldChild);
	}

	public Node removeChild(Node oldChild) throws DOMException {
		return getDelegate().removeChild(oldChild);
	}

	public void setAttributeNS(String arg0, String arg1, String arg2)
			throws DOMException {
		getDelegate().setAttributeNS(arg0, arg1, arg2);
	}

	public Attr setAttributeNodeNS(Attr arg0) throws DOMException {
		return getDelegate().setAttributeNodeNS(arg0);
	}

	public void setPrefix(String arg0) throws DOMException {
		getDelegate().setPrefix(arg0);
	}

	public TypeInfo getSchemaTypeInfo() {
		return getDelegate().getSchemaTypeInfo();
	}

	public void setIdAttribute(String name, boolean isId) throws DOMException {
		getDelegate().setIdAttribute(name, isId);
	}

	public void setIdAttributeNS(String namespaceURI, String localName,
			boolean isId) throws DOMException {
		getDelegate().setIdAttributeNS(namespaceURI, localName, isId);
	}

	public void setIdAttributeNode(Attr idAttr, boolean isId)
			throws DOMException {
		getDelegate().setIdAttributeNode(idAttr, isId);
	}

	public String getBaseURI() {
		return getDelegate().getBaseURI();
	}

	public short compareDocumentPosition(Node other) throws DOMException {
		return getDelegate().compareDocumentPosition(other);
	}

	public String getTextContent() throws DOMException {
		return getDelegate().getTextContent();
	}

	public void setTextContent(String textContent) throws DOMException {
		getDelegate().setTextContent(textContent);
	}

	public boolean isSameNode(Node other) {
		return getDelegate().isSameNode(other);
	}

	public String lookupPrefix(String namespaceURI) {
		return getDelegate().lookupPrefix(namespaceURI);
	}

	public boolean isDefaultNamespace(String namespaceURI) {
		return getDelegate().isDefaultNamespace(namespaceURI);
	}

	public String lookupNamespaceURI(String prefix) {
		return getDelegate().lookupNamespaceURI(prefix);
	}

	public boolean isEqualNode(Node arg) {
		return getDelegate().isEqualNode(arg);
	}

	public Object getFeature(String feature, String version) {
		return getDelegate().getFeature(feature, version);
	}

	public Object setUserData(String key, Object data, UserDataHandler handler) {
		return getDelegate().setUserData(key, data, handler);
	}

	public Object getUserData(String key) {
		return getDelegate().getUserData(key);
	}

	public boolean equals(Object obj) {
		return getDelegate().equals(obj);
	}

	public int hashCode() {
		return getDelegate().hashCode();
	}
}
