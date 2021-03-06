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
 * <p>Java class for complexType.VariationAttribute complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="complexType.VariationAttribute">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="display-name" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}sharedType.LocalizedString" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="variation-attribute-values" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}complexType.VariationAttribute.Values" maxOccurs="2" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="mode" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}simpleType.ImportMode" />
 *       &lt;attribute name="attribute-id" use="required" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}simpleType.Generic.NonEmptyString.256" />
 *       &lt;attribute name="variation-attribute-id" use="required" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}simpleType.Generic.NonEmptyString.256" />
 *       &lt;attribute name="slicing-attribute" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "complexType.VariationAttribute", propOrder = {
    "displayName",
    "variationAttributeValues"
})
public class ComplexTypeVariationAttribute {

    @XmlElement(name = "display-name")
    protected List<SharedTypeLocalizedString> displayName;
    @XmlElement(name = "variation-attribute-values")
    protected List<ComplexTypeVariationAttributeValues> variationAttributeValues;
    @XmlAttribute(name = "mode")
    protected SimpleTypeImportMode mode;
    @XmlAttribute(name = "attribute-id", required = true)
    protected String attributeId;
    @XmlAttribute(name = "variation-attribute-id", required = true)
    protected String variationAttributeId;
    @XmlAttribute(name = "slicing-attribute")
    protected Boolean slicingAttribute;

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
     * Gets the value of the variationAttributeValues property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the variationAttributeValues property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVariationAttributeValues().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ComplexTypeVariationAttributeValues }
     * 
     * 
     */
    public List<ComplexTypeVariationAttributeValues> getVariationAttributeValues() {
        if (variationAttributeValues == null) {
            variationAttributeValues = new ArrayList<ComplexTypeVariationAttributeValues>();
        }
        return this.variationAttributeValues;
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
     * Gets the value of the variationAttributeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVariationAttributeId() {
        return variationAttributeId;
    }

    /**
     * Sets the value of the variationAttributeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVariationAttributeId(String value) {
        this.variationAttributeId = value;
    }

    /**
     * Gets the value of the slicingAttribute property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSlicingAttribute() {
        return slicingAttribute;
    }

    /**
     * Sets the value of the slicingAttribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSlicingAttribute(Boolean value) {
        this.slicingAttribute = value;
    }

}
