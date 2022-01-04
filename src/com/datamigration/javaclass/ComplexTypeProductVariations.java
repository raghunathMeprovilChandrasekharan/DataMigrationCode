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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for complexType.Product.Variations complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="complexType.Product.Variations">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attributes" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}complexType.Product.Variations.Attributes" minOccurs="0"/>
 *         &lt;element name="variants" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}complexType.Product.Variations.Variants" maxOccurs="2" minOccurs="0"/>
 *         &lt;element name="variation-groups" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}complexType.Product.Variations.VariationGroups" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "complexType.Product.Variations", propOrder = {
    "attributes",
    "variants",
    "variationGroups"
})
public class ComplexTypeProductVariations {

    protected ComplexTypeProductVariationsAttributes attributes;
    protected List<ComplexTypeProductVariationsVariants> variants;
    @XmlElement(name = "variation-groups")
    protected ComplexTypeProductVariationsVariationGroups variationGroups;

    /**
     * Gets the value of the attributes property.
     * 
     * @return
     *     possible object is
     *     {@link ComplexTypeProductVariationsAttributes }
     *     
     */
    public ComplexTypeProductVariationsAttributes getAttributes() {
        return attributes;
    }

    /**
     * Sets the value of the attributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComplexTypeProductVariationsAttributes }
     *     
     */
    public void setAttributes(ComplexTypeProductVariationsAttributes value) {
        this.attributes = value;
    }

    /**
     * Gets the value of the variants property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the variants property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVariants().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ComplexTypeProductVariationsVariants }
     * 
     * 
     */
    public List<ComplexTypeProductVariationsVariants> getVariants() {
        if (variants == null) {
            variants = new ArrayList<ComplexTypeProductVariationsVariants>();
        }
        return this.variants;
    }

    /**
     * Gets the value of the variationGroups property.
     * 
     * @return
     *     possible object is
     *     {@link ComplexTypeProductVariationsVariationGroups }
     *     
     */
    public ComplexTypeProductVariationsVariationGroups getVariationGroups() {
        return variationGroups;
    }

    /**
     * Sets the value of the variationGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComplexTypeProductVariationsVariationGroups }
     *     
     */
    public void setVariationGroups(ComplexTypeProductVariationsVariationGroups value) {
        this.variationGroups = value;
    }

}
