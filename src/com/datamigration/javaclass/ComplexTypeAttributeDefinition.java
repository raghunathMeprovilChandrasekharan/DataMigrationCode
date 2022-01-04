//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.12.16 at 12:14:50 pm IST 
//


package com.datamigration.javaclass;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for complexType.AttributeDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="complexType.AttributeDefinition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="display-name" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}sharedType.LocalizedString" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}sharedType.LocalizedString" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}simpleType.AttributeType"/>
 *         &lt;element name="localizable-flag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="site-specific-flag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="mandatory-flag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="visible-flag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="externally-managed-flag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="min-length" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="field-length" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="field-height" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="unit" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}sharedType.LocalizedString" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="scale" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="min-value" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="max-value" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="regex" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}simpleType.Generic.String.256" minOccurs="0"/>
 *         &lt;element name="select-multiple-flag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="value-definitions" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}complexType.ValueDefinitions" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="attribute-id" use="required" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}simpleType.AttributeDefinition.ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "complexType.AttributeDefinition", propOrder = {
    "displayName",
    "description",
    "type",
    "localizableFlag",
    "siteSpecificFlag",
    "mandatoryFlag",
    "visibleFlag",
    "externallyManagedFlag",
    "minLength",
    "fieldLength",
    "fieldHeight",
    "unit",
    "scale",
    "minValue",
    "maxValue",
    "regex",
    "selectMultipleFlag",
    "valueDefinitions"
})
public class ComplexTypeAttributeDefinition {

    @XmlElement(name = "display-name")
    protected List<SharedTypeLocalizedString> displayName;
    protected List<SharedTypeLocalizedString> description;
    @XmlElement(required = true)
    protected SimpleTypeAttributeType type;
    @XmlElement(name = "localizable-flag")
    protected Boolean localizableFlag;
    @XmlElement(name = "site-specific-flag")
    protected Boolean siteSpecificFlag;
    @XmlElement(name = "mandatory-flag")
    protected Boolean mandatoryFlag;
    @XmlElement(name = "visible-flag")
    protected Boolean visibleFlag;
    @XmlElement(name = "externally-managed-flag")
    protected Boolean externallyManagedFlag;
    @XmlElement(name = "min-length")
    protected Integer minLength;
    @XmlElement(name = "field-length")
    protected Integer fieldLength;
    @XmlElement(name = "field-height")
    protected Integer fieldHeight;
    protected List<SharedTypeLocalizedString> unit;
    protected Integer scale;
    @XmlElement(name = "min-value")
    protected Double minValue;
    @XmlElement(name = "max-value")
    protected Double maxValue;
    protected String regex;
    @XmlElement(name = "select-multiple-flag")
    protected Boolean selectMultipleFlag;
    @XmlElement(name = "value-definitions")
    protected ComplexTypeValueDefinitions valueDefinitions;
    @XmlAttribute(name = "attribute-id", required = true)
    protected String attributeId;

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
     * {@link SharedTypeLocalizedString }
     * 
     * 
     */
    public List<SharedTypeLocalizedString> getDisplayName() {
        if (displayName == null) {
            displayName = new ArrayList<SharedTypeLocalizedString>();
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
     * {@link SharedTypeLocalizedString }
     * 
     * 
     */
    public List<SharedTypeLocalizedString> getDescription() {
        if (description == null) {
            description = new ArrayList<SharedTypeLocalizedString>();
        }
        return this.description;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link SimpleTypeAttributeType }
     *     
     */
    public SimpleTypeAttributeType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimpleTypeAttributeType }
     *     
     */
    public void setType(SimpleTypeAttributeType value) {
        this.type = value;
    }

    /**
     * Gets the value of the localizableFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLocalizableFlag() {
        return localizableFlag;
    }

    /**
     * Sets the value of the localizableFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLocalizableFlag(Boolean value) {
        this.localizableFlag = value;
    }

    /**
     * Gets the value of the siteSpecificFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSiteSpecificFlag() {
        return siteSpecificFlag;
    }

    /**
     * Sets the value of the siteSpecificFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSiteSpecificFlag(Boolean value) {
        this.siteSpecificFlag = value;
    }

    /**
     * Gets the value of the mandatoryFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMandatoryFlag() {
        return mandatoryFlag;
    }

    /**
     * Sets the value of the mandatoryFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMandatoryFlag(Boolean value) {
        this.mandatoryFlag = value;
    }

    /**
     * Gets the value of the visibleFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isVisibleFlag() {
        return visibleFlag;
    }

    /**
     * Sets the value of the visibleFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVisibleFlag(Boolean value) {
        this.visibleFlag = value;
    }

    /**
     * Gets the value of the externallyManagedFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isExternallyManagedFlag() {
        return externallyManagedFlag;
    }

    /**
     * Sets the value of the externallyManagedFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExternallyManagedFlag(Boolean value) {
        this.externallyManagedFlag = value;
    }

    /**
     * Gets the value of the minLength property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMinLength() {
        return minLength;
    }

    /**
     * Sets the value of the minLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMinLength(Integer value) {
        this.minLength = value;
    }

    /**
     * Gets the value of the fieldLength property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFieldLength() {
        return fieldLength;
    }

    /**
     * Sets the value of the fieldLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFieldLength(Integer value) {
        this.fieldLength = value;
    }

    /**
     * Gets the value of the fieldHeight property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFieldHeight() {
        return fieldHeight;
    }

    /**
     * Sets the value of the fieldHeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFieldHeight(Integer value) {
        this.fieldHeight = value;
    }

    /**
     * Gets the value of the unit property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the unit property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUnit().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SharedTypeLocalizedString }
     * 
     * 
     */
    public List<SharedTypeLocalizedString> getUnit() {
        if (unit == null) {
            unit = new ArrayList<SharedTypeLocalizedString>();
        }
        return this.unit;
    }

    /**
     * Gets the value of the scale property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getScale() {
        return scale;
    }

    /**
     * Sets the value of the scale property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setScale(Integer value) {
        this.scale = value;
    }

    /**
     * Gets the value of the minValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMinValue() {
        return minValue;
    }

    /**
     * Sets the value of the minValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMinValue(Double value) {
        this.minValue = value;
    }

    /**
     * Gets the value of the maxValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMaxValue() {
        return maxValue;
    }

    /**
     * Sets the value of the maxValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMaxValue(Double value) {
        this.maxValue = value;
    }

    /**
     * Gets the value of the regex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegex() {
        return regex;
    }

    /**
     * Sets the value of the regex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegex(String value) {
        this.regex = value;
    }

    /**
     * Gets the value of the selectMultipleFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSelectMultipleFlag() {
        return selectMultipleFlag;
    }

    /**
     * Sets the value of the selectMultipleFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSelectMultipleFlag(Boolean value) {
        this.selectMultipleFlag = value;
    }

    /**
     * Gets the value of the valueDefinitions property.
     * 
     * @return
     *     possible object is
     *     {@link ComplexTypeValueDefinitions }
     *     
     */
    public ComplexTypeValueDefinitions getValueDefinitions() {
        return valueDefinitions;
    }

    /**
     * Sets the value of the valueDefinitions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComplexTypeValueDefinitions }
     *     
     */
    public void setValueDefinitions(ComplexTypeValueDefinitions value) {
        this.valueDefinitions = value;
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

}