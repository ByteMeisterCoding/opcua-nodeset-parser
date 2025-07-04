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
 * <p>Java class for UAMethod complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="UAMethod">
 *   &lt;complexContent>
 *     &lt;extension base="{http://opcfoundation.org/UA/2011/03/UANodeSet.xsd}UAInstance">
 *       &lt;sequence>
 *         &lt;element name="ArgumentDescription" type="{http://opcfoundation.org/UA/2011/03/UANodeSet.xsd}UAMethodArgument" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Executable" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;attribute name="UserExecutable" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;attribute name="MethodDeclarationId" type="{http://opcfoundation.org/UA/2011/03/UANodeSet.xsd}NodeId" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UAMethod", propOrder = {
        "argumentDescription"
})
public class UAMethod
        extends UAInstance {

    @XmlElement(name = "ArgumentDescription")
    protected List<UAMethodArgument> argumentDescription;
    @XmlAttribute(name = "Executable")
    protected Boolean executable;
    @XmlAttribute(name = "UserExecutable")
    protected Boolean userExecutable;
    @XmlAttribute(name = "MethodDeclarationId")
    protected String methodDeclarationId;

    /**
     * Gets the value of the argumentDescription property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the argumentDescription property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getArgumentDescription().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UAMethodArgument }
     */
    public List<UAMethodArgument> getArgumentDescription() {
        if (argumentDescription == null) {
            argumentDescription = new ArrayList<UAMethodArgument>();
        }
        return this.argumentDescription;
    }

    /**
     * Gets the value of the executable property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public boolean isExecutable() {
        if (executable == null) {
            return true;
        } else {
            return executable;
        }
    }

    /**
     * Sets the value of the executable property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setExecutable(Boolean value) {
        this.executable = value;
    }

    /**
     * Gets the value of the userExecutable property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public boolean isUserExecutable() {
        if (userExecutable == null) {
            return true;
        } else {
            return userExecutable;
        }
    }

    /**
     * Sets the value of the userExecutable property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setUserExecutable(Boolean value) {
        this.userExecutable = value;
    }

    /**
     * Gets the value of the methodDeclarationId property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getMethodDeclarationId() {
        return methodDeclarationId;
    }

    /**
     * Sets the value of the methodDeclarationId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setMethodDeclarationId(String value) {
        this.methodDeclarationId = value;
    }

}
