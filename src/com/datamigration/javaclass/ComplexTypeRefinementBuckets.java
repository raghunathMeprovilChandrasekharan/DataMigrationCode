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
 * <p>Java class for complexType.RefinementBuckets complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="complexType.RefinementBuckets">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="attribute-bucket" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}complexType.AttributeRefinementBucket" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="price-bucket" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}complexType.PriceRefinementBucket" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="threshold-bucket" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}complexType.ThresholdRefinementBucket" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="period-bucket" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}complexType.PeriodRefinementBucket" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "complexType.RefinementBuckets", propOrder = {
    "attributeBucket",
    "priceBucket",
    "thresholdBucket",
    "periodBucket"
})
public class ComplexTypeRefinementBuckets {

    @XmlElement(name = "attribute-bucket")
    protected List<ComplexTypeAttributeRefinementBucket> attributeBucket;
    @XmlElement(name = "price-bucket")
    protected List<ComplexTypePriceRefinementBucket> priceBucket;
    @XmlElement(name = "threshold-bucket")
    protected List<ComplexTypeThresholdRefinementBucket> thresholdBucket;
    @XmlElement(name = "period-bucket")
    protected List<ComplexTypePeriodRefinementBucket> periodBucket;

    /**
     * Gets the value of the attributeBucket property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributeBucket property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributeBucket().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ComplexTypeAttributeRefinementBucket }
     * 
     * 
     */
    public List<ComplexTypeAttributeRefinementBucket> getAttributeBucket() {
        if (attributeBucket == null) {
            attributeBucket = new ArrayList<ComplexTypeAttributeRefinementBucket>();
        }
        return this.attributeBucket;
    }

    /**
     * Gets the value of the priceBucket property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the priceBucket property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPriceBucket().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ComplexTypePriceRefinementBucket }
     * 
     * 
     */
    public List<ComplexTypePriceRefinementBucket> getPriceBucket() {
        if (priceBucket == null) {
            priceBucket = new ArrayList<ComplexTypePriceRefinementBucket>();
        }
        return this.priceBucket;
    }

    /**
     * Gets the value of the thresholdBucket property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the thresholdBucket property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getThresholdBucket().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ComplexTypeThresholdRefinementBucket }
     * 
     * 
     */
    public List<ComplexTypeThresholdRefinementBucket> getThresholdBucket() {
        if (thresholdBucket == null) {
            thresholdBucket = new ArrayList<ComplexTypeThresholdRefinementBucket>();
        }
        return this.thresholdBucket;
    }

    /**
     * Gets the value of the periodBucket property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the periodBucket property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPeriodBucket().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ComplexTypePeriodRefinementBucket }
     * 
     * 
     */
    public List<ComplexTypePeriodRefinementBucket> getPeriodBucket() {
        if (periodBucket == null) {
            periodBucket = new ArrayList<ComplexTypePeriodRefinementBucket>();
        }
        return this.periodBucket;
    }

}
