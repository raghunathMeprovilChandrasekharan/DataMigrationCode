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
 * <p>Java class for complexType.Product.BundledProducts complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="complexType.Product.BundledProducts">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bundled-product" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}complexType.Product.BundledProduct" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "complexType.Product.BundledProducts", propOrder = {
    "bundledProduct"
})
public class ComplexTypeProductBundledProducts {

    @XmlElement(name = "bundled-product")
    protected List<ComplexTypeProductBundledProduct> bundledProduct;

    /**
     * Gets the value of the bundledProduct property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bundledProduct property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBundledProduct().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ComplexTypeProductBundledProduct }
     * 
     * 
     */
    public List<ComplexTypeProductBundledProduct> getBundledProduct() {
        if (bundledProduct == null) {
            bundledProduct = new ArrayList<ComplexTypeProductBundledProduct>();
        }
        return this.bundledProduct;
    }

}