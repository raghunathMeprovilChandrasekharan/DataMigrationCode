//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.12.16 at 12:14:50 pm IST 
//


package com.datamigration.javaclass;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for complexType.BlockedRefinementDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="complexType.BlockedRefinementDefinition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="type" use="required" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}simpleType.Refinement.Type" />
 *       &lt;attribute name="bucket-type" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}simpleType.Bucket.Type" />
 *       &lt;attribute name="attribute-id" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}simpleType.Generic.NonEmptyString.256" />
 *       &lt;attribute name="system" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "complexType.BlockedRefinementDefinition")
public class ComplexTypeBlockedRefinementDefinition {

    @XmlAttribute(name = "type", required = true)
    protected SimpleTypeRefinementType type;
    @XmlAttribute(name = "bucket-type")
    protected SimpleTypeBucketType bucketType;
    @XmlAttribute(name = "attribute-id")
    protected String attributeId;
    @XmlAttribute(name = "system")
    protected Boolean system;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link SimpleTypeRefinementType }
     *     
     */
    public SimpleTypeRefinementType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimpleTypeRefinementType }
     *     
     */
    public void setType(SimpleTypeRefinementType value) {
        this.type = value;
    }

    /**
     * Gets the value of the bucketType property.
     * 
     * @return
     *     possible object is
     *     {@link SimpleTypeBucketType }
     *     
     */
    public SimpleTypeBucketType getBucketType() {
        return bucketType;
    }

    /**
     * Sets the value of the bucketType property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimpleTypeBucketType }
     *     
     */
    public void setBucketType(SimpleTypeBucketType value) {
        this.bucketType = value;
    }

    /**
     * Gets the value of the attributeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeId() {
        return attributeId;
    }

    /**
     * Sets the value of the attributeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeId(String value) {
        this.attributeId = value;
    }

    /**
     * Gets the value of the system property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isSystem() {
        if (system == null) {
            return false;
        } else {
            return system;
        }
    }

    /**
     * Sets the value of the system property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSystem(Boolean value) {
        this.system = value;
    }

}
