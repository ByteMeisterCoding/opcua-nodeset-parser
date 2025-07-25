//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2025.03.15 at 12:19:21 PM CET 
//


package com.bytemeistercoding.opcua.uanodeset;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for DataTypeField complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="DataTypeField">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DisplayName" type="{http://opcfoundation.org/UA/2011/03/UANodeSet.xsd}UALocalizedText" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://opcfoundation.org/UA/2011/03/UANodeSet.xsd}UALocalizedText" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="SymbolicName" type="{http://opcfoundation.org/UA/2011/03/UANodeSet.xsd}SymbolicName" />
 *       &lt;attribute name="DataType" type="{http://opcfoundation.org/UA/2011/03/UANodeSet.xsd}NodeId" default="i=24" />
 *       &lt;attribute name="ValueRank" type="{http://opcfoundation.org/UA/2011/03/UANodeSet.xsd}ValueRank" default="-1" />
 *       &lt;attribute name="ArrayDimensions" type="{http://opcfoundation.org/UA/2011/03/UANodeSet.xsd}ArrayDimensions" default="" />
 *       &lt;attribute name="MaxStringLength" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" default="0" />
 *       &lt;attribute name="Value" type="{http://www.w3.org/2001/XMLSchema}int" default="-1" />
 *       &lt;attribute name="IsOptional" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="AllowSubTypes" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataTypeField", propOrder = {
        "displayName",
        "description"
})
public class DataTypeField {

    @XmlElement(name = "DisplayName")
    protected List<UALocalizedText> displayName;
    @XmlElement(name = "Description")
    protected List<UALocalizedText> description;
    @XmlAttribute(name = "Name", required = true)
    protected String name;
    @XmlAttribute(name = "SymbolicName")
    protected String symbolicName;
    @XmlAttribute(name = "DataType")
    protected String dataType;
    @XmlAttribute(name = "ValueRank")
    protected Integer valueRank;
    @XmlAttribute(name = "ArrayDimensions")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String arrayDimensions;
    @XmlAttribute(name = "MaxStringLength")
    @XmlSchemaType(name = "unsignedInt")
    protected Long maxStringLength;
    @XmlAttribute(name = "Value")
    protected Integer value;
    @XmlAttribute(name = "IsOptional")
    protected Boolean isOptional;
    @XmlAttribute(name = "AllowSubTypes")
    protected Boolean allowSubTypes;

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
    public List<UALocalizedText> getDisplayName() {
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
    public List<UALocalizedText> getDescription() {
        if (description == null) {
            description = new ArrayList<UALocalizedText>();
        }
        return this.description;
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setName(String value) {
        this.name = value;
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
     * Gets the value of the dataType property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDataType() {
        if (dataType == null) {
            return "i=24";
        } else {
            return dataType;
        }
    }

    /**
     * Sets the value of the dataType property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDataType(String value) {
        this.dataType = value;
    }

    /**
     * Gets the value of the valueRank property.
     *
     * @return possible object is
     * {@link Integer }
     */
    public int getValueRank() {
        if (valueRank == null) {
            return -1;
        } else {
            return valueRank;
        }
    }

    /**
     * Sets the value of the valueRank property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setValueRank(Integer value) {
        this.valueRank = value;
    }

    /**
     * Gets the value of the arrayDimensions property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getArrayDimensions() {
        if (arrayDimensions == null) {
            return "";
        } else {
            return arrayDimensions;
        }
    }

    /**
     * Sets the value of the arrayDimensions property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setArrayDimensions(String value) {
        this.arrayDimensions = value;
    }

    /**
     * Gets the value of the maxStringLength property.
     *
     * @return possible object is
     * {@link Long }
     */
    public long getMaxStringLength() {
        if (maxStringLength == null) {
            return 0L;
        } else {
            return maxStringLength;
        }
    }

    /**
     * Sets the value of the maxStringLength property.
     *
     * @param value allowed object is
     *              {@link Long }
     */
    public void setMaxStringLength(Long value) {
        this.maxStringLength = value;
    }

    /**
     * Gets the value of the value property.
     *
     * @return possible object is
     * {@link Integer }
     */
    public int getValue() {
        if (value == null) {
            return -1;
        } else {
            return value;
        }
    }

    /**
     * Sets the value of the value property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setValue(Integer value) {
        this.value = value;
    }

    /**
     * Gets the value of the isOptional property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public boolean isIsOptional() {
        if (isOptional == null) {
            return false;
        } else {
            return isOptional;
        }
    }

    /**
     * Sets the value of the isOptional property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setIsOptional(Boolean value) {
        this.isOptional = value;
    }

    /**
     * Gets the value of the allowSubTypes property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public boolean isAllowSubTypes() {
        if (allowSubTypes == null) {
            return false;
        } else {
            return allowSubTypes;
        }
    }

    /**
     * Sets the value of the allowSubTypes property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setAllowSubTypes(Boolean value) {
        this.allowSubTypes = value;
    }

}
