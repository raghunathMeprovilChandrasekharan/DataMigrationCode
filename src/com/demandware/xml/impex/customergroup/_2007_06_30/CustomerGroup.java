//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.12.28 at 10:48:34 pm IST 
//


package com.demandware.xml.impex.customergroup._2007_06_30;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustomerGroup complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerGroup">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.demandware.com/xml/impex/customergroup/2007-06-30}Generic.String.4000" minOccurs="0"/>
 *         &lt;element name="membership-rule" type="{http://www.demandware.com/xml/impex/customergroup/2007-06-30}MembershipRule" minOccurs="0"/>
 *         &lt;element name="custom-attributes" type="{http://www.demandware.com/xml/impex/customergroup/2007-06-30}CustomAttributes" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="group-id" use="required" type="{http://www.demandware.com/xml/impex/customergroup/2007-06-30}Generic.NonEmptyString.256" />
 *       &lt;attribute name="mode" type="{http://www.demandware.com/xml/impex/customergroup/2007-06-30}simpleType.ImportMode" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerGroup", propOrder = {
    "description",
    "membershipRule",
    "customAttributes"
})
public class CustomerGroup {

    protected String description;
    @XmlElement(name = "membership-rule")
    protected MembershipRule membershipRule;
    @XmlElement(name = "custom-attributes")
    protected CustomAttributes customAttributes;
    @XmlAttribute(name = "group-id", required = true)
    protected String groupId;
    @XmlAttribute(name = "mode")
    protected SimpleTypeImportMode mode;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the membershipRule property.
     * 
     * @return
     *     possible object is
     *     {@link MembershipRule }
     *     
     */
    public MembershipRule getMembershipRule() {
        return membershipRule;
    }

    /**
     * Sets the value of the membershipRule property.
     * 
     * @param value
     *     allowed object is
     *     {@link MembershipRule }
     *     
     */
    public void setMembershipRule(MembershipRule value) {
        this.membershipRule = value;
    }

    /**
     * Gets the value of the customAttributes property.
     * 
     * @return
     *     possible object is
     *     {@link CustomAttributes }
     *     
     */
    public CustomAttributes getCustomAttributes() {
        return customAttributes;
    }

    /**
     * Sets the value of the customAttributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomAttributes }
     *     
     */
    public void setCustomAttributes(CustomAttributes value) {
        this.customAttributes = value;
    }

    /**
     * Gets the value of the groupId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Sets the value of the groupId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupId(String value) {
        this.groupId = value;
    }

    /**
     * Gets the value of the mode property.
     * 
     * @return
     *     possible object is
     *     {@link SimpleTypeImportMode }
     *     
     */
    public SimpleTypeImportMode getMode() {
        return mode;
    }

    /**
     * Sets the value of the mode property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimpleTypeImportMode }
     *     
     */
    public void setMode(SimpleTypeImportMode value) {
        this.mode = value;
    }

}