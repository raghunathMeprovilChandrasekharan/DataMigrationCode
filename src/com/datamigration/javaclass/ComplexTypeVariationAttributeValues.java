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
 * <p>Java class for complexType.VariationAttribute.Values complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="complexType.VariationAttribute.Values">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="variation-attribute-value" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}complexType.VariationAttribute.Value" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="merge-mode" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}simpleType.MergeMode" default="replace" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "complexType.VariationAttribute.Values", propOrder = {
    "variationAttributeValue"
})
public class ComplexTypeVariationAttributeValues {

    @XmlElement(name = "variation-attribute-value")
    protected List<ComplexTypeVariationAttributeValue> variationAttributeValue;
    @XmlAttribute(name = "merge-mode")
    protected SimpleTypeMergeMode mergeMode;

    /**
     * Gets the value of the variationAttributeValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the variationAttributeValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVariationAttributeValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ComplexTypeVariationAttributeValue }
     * 
     * 
     */
    public List<ComplexTypeVariationAttributeValue> getVariationAttributeValue() {
        if (variationAttributeValue == null) {
            variationAttributeValue = new ArrayList<ComplexTypeVariationAttributeValue>();
        }
        return this.variationAttributeValue;
    }

    /**
     * Gets the value of the mergeMode property.
     * 
     * @return
     *     possible object is
     *     {@link SimpleTypeMergeMode }
     *     
     */
    public SimpleTypeMergeMode getMergeMode() {
        if (mergeMode == null) {
            return SimpleTypeMergeMode.REPLACE;
        } else {
            return mergeMode;
        }
    }

    /**
     * Sets the value of the mergeMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimpleTypeMergeMode }
     *     
     */
    public void setMergeMode(SimpleTypeMergeMode value) {
        this.mergeMode = value;
    }

}
