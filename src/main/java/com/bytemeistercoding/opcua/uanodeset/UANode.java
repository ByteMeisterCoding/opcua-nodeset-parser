//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2025.03.15 at 12:19:21 PM CET 
//


package com.bytemeistercoding.opcua.uanodeset;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for UANode complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="UANode">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DisplayName" type="{http://opcfoundation.org/UA/2011/03/UANodeSet.xsd}UALocalizedText" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://opcfoundation.org/UA/2011/03/UANodeSet.xsd}UALocalizedText" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Category" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Documentation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="References" type="{http://opcfoundation.org/UA/2011/03/UANodeSet.xsd}ListOfReferences" minOccurs="0"/>
 *         &lt;element name="RolePermissions" type="{http://opcfoundation.org/UA/2011/03/UANodeSet.xsd}ListOfRolePermissions" minOccurs="0"/>
 *         &lt;element name="Extensions" type="{http://opcfoundation.org/UA/2011/03/UANodeSet.xsd}ListOfExtensions" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="NodeId" use="required" type="{http://opcfoundation.org/UA/2011/03/UANodeSet.xsd}NodeId" />
 *       &lt;attribute name="BrowseName" use="required" type="{http://opcfoundation.org/UA/2011/03/UANodeSet.xsd}QualifiedName" />
 *       &lt;attribute name="WriteMask" type="{http://opcfoundation.org/UA/2011/03/UANodeSet.xsd}WriteMask" default="0" />
 *       &lt;attribute name="UserWriteMask" type="{http://opcfoundation.org/UA/2011/03/UANodeSet.xsd}WriteMask" default="0" />
 *       &lt;attribute name="AccessRestrictions" type="{http://opcfoundation.org/UA/2011/03/UANodeSet.xsd}AccessRestriction" />
 *       &lt;attribute name="HasNoPermissions" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="SymbolicName" type="{http://opcfoundation.org/UA/2011/03/UANodeSet.xsd}SymbolicName" />
 *       &lt;attribute name="ReleaseStatus" type="{http://opcfoundation.org/UA/2011/03/UANodeSet.xsd}ReleaseStatus" default="Released" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UANode", propOrder = {
        "displayName",
        "description",
        "category",
        "documentation",
        "references",
        "rolePermissions",
        "extensions"
})
@XmlSeeAlso({
        UAType.class,
        UAInstance.class
})
public class UANode {

    @XmlElement(name = "DisplayName")
    protected List<UALocalizedText> displayName;
    @XmlElement(name = "Description")
    protected List<UALocalizedText> description;
    @XmlElement(name = "Category")
    protected List<String> category;
    @XmlElement(name = "Documentation")
    protected String documentation;
    @XmlElement(name = "References")
    protected ListOfReferences references;
    @XmlElement(name = "RolePermissions")
    protected ListOfRolePermissions rolePermissions;
    @XmlElement(name = "Extensions")
    protected ListOfExtensions extensions;
    @XmlAttribute(name = "NodeId", required = true)
    protected String nodeId;
    @XmlAttribute(name = "BrowseName", required = true)
    protected String browseName;
    @XmlAttribute(name = "WriteMask")
    protected Long writeMask;
    @XmlAttribute(name = "UserWriteMask")
    protected Long userWriteMask;
    @XmlAttribute(name = "AccessRestrictions")
    protected Integer accessRestrictions;
    @XmlAttribute(name = "HasNoPermissions")
    protected Boolean hasNoPermissions;
    @XmlAttribute(name = "SymbolicName")
    protected String symbolicName;
    @XmlAttribute(name = "ReleaseStatus")
    protected ReleaseStatus releaseStatus;

    /**
     * Gets the value of the displayName property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the displayName property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDisplayName().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UALocalizedText }
     */
    public List<UALocalizedText> getDisplayNames() {
        if (displayName == null) {
            displayName = new ArrayList<UALocalizedText>();
        }
        return this.displayName;
    }

    /**
     * Gets the value of the description property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the description property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDescription().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UALocalizedText }
     */
    public List<UALocalizedText> getDescriptions() {
        if (description == null) {
            description = new ArrayList<UALocalizedText>();
        }
        return this.description;
    }

    /**
     * Gets the value of the category property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the category property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCategory().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     */
    public List<String> getCategory() {
        if (category == null) {
            category = new ArrayList<String>();
        }
        return this.category;
    }

    /**
     * Gets the value of the documentation property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDocumentation() {
        return documentation;
    }

    /**
     * Sets the value of the documentation property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDocumentation(String value) {
        this.documentation = value;
    }

    /**
     * Gets the value of the references property.
     *
     * @return possible object is
     * {@link ListOfReferences }
     */
    public ListOfReferences getReferences() {
        return references;
    }

    /**
     * Sets the value of the references property.
     *
     * @param value allowed object is
     *              {@link ListOfReferences }
     */
    public void setReferences(ListOfReferences value) {
        this.references = value;
    }

    /**
     * Gets the value of the rolePermissions property.
     *
     * @return possible object is
     * {@link ListOfRolePermissions }
     */
    public ListOfRolePermissions getRolePermissions() {
        return rolePermissions;
    }

    /**
     * Sets the value of the rolePermissions property.
     *
     * @param value allowed object is
     *              {@link ListOfRolePermissions }
     */
    public void setRolePermissions(ListOfRolePermissions value) {
        this.rolePermissions = value;
    }

    /**
     * Gets the value of the extensions property.
     *
     * @return possible object is
     * {@link ListOfExtensions }
     */
    public ListOfExtensions getExtensions() {
        return extensions;
    }

    /**
     * Sets the value of the extensions property.
     *
     * @param value allowed object is
     *              {@link ListOfExtensions }
     */
    public void setExtensions(ListOfExtensions value) {
        this.extensions = value;
    }

    /**
     * Gets the value of the nodeId property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getNodeId() {
        return nodeId;
    }

    /**
     * Sets the value of the nodeId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNodeId(String value) {
        this.nodeId = value;
    }

    /**
     * Gets the value of the browseName property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getBrowseName() {
        return browseName;
    }

    /**
     * Sets the value of the browseName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBrowseName(String value) {
        this.browseName = value;
    }

    /**
     * Gets the value of the writeMask property.
     *
     * @return possible object is
     * {@link Long }
     */
    public long getWriteMask() {
        if (writeMask == null) {
            return 0L;
        } else {
            return writeMask;
        }
    }

    /**
     * Sets the value of the writeMask property.
     *
     * @param value allowed object is
     *              {@link Long }
     */
    public void setWriteMask(Long value) {
        this.writeMask = value;
    }

    /**
     * Gets the value of the userWriteMask property.
     *
     * @return possible object is
     * {@link Long }
     */
    public long getUserWriteMask() {
        if (userWriteMask == null) {
            return 0L;
        } else {
            return userWriteMask;
        }
    }

    /**
     * Sets the value of the userWriteMask property.
     *
     * @param value allowed object is
     *              {@link Long }
     */
    public void setUserWriteMask(Long value) {
        this.userWriteMask = value;
    }

    /**
     * Gets the value of the accessRestrictions property.
     *
     * @return possible object is
     * {@link Integer }
     */
    public Integer getAccessRestrictions() {
        return accessRestrictions;
    }

    /**
     * Sets the value of the accessRestrictions property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setAccessRestrictions(Integer value) {
        this.accessRestrictions = value;
    }

    /**
     * Gets the value of the hasNoPermissions property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public boolean isHasNoPermissions() {
        if (hasNoPermissions == null) {
            return false;
        } else {
            return hasNoPermissions;
        }
    }

    /**
     * Sets the value of the hasNoPermissions property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setHasNoPermissions(Boolean value) {
        this.hasNoPermissions = value;
    }

    /**
     * Gets the value of the symbolicName property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSymbolicName() {
        return symbolicName;
    }

    /**
     * Sets the value of the symbolicName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSymbolicName(String value) {
        this.symbolicName = value;
    }

    /**
     * Gets the value of the releaseStatus property.
     *
     * @return possible object is
     * {@link ReleaseStatus }
     */
    public ReleaseStatus getReleaseStatus() {
        if (releaseStatus == null) {
            return ReleaseStatus.RELEASED;
        } else {
            return releaseStatus;
        }
    }

    /**
     * Sets the value of the releaseStatus property.
     *
     * @param value allowed object is
     *              {@link ReleaseStatus }
     */
    public void setReleaseStatus(ReleaseStatus value) {
        this.releaseStatus = value;
    }

}
