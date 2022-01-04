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
 * <p>Java class for complexType.ProductSpecification.ProductCategoryFilter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="complexType.ProductSpecification.ProductCategoryFilter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="category-id" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}simpleType.Generic.String.256" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="catalog-id" use="required" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}simpleType.Generic.NonEmptyString.256" />
 *       &lt;attribute name="operator" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}simpleType.CategoryIDComparator" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "complexType.ProductSpecification.ProductCategoryFilter", propOrder = {
    "categoryId"
})
public class ComplexTypeProductSpecificationProductCategoryFilter {

    @XmlElement(name = "category-id", required = true)
    protected List<String> categoryId;
    @XmlAttribute(name = "catalog-id", required = true)
    protected String catalogId;
    @XmlAttribute(name = "operator")
    protected SimpleTypeCategoryIDComparator operator;

    /**
     * Gets the value of the categoryId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the categoryId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCategoryId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getCategoryId() {
        if (categoryId == null) {
            categoryId = new ArrayList<String>();
        }
        return this.categoryId;
    }

    /**
     * Gets the value of the catalogId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCatalogId() {
        return catalogId;
    }

    /**
     * Sets the value of the catalogId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCatalogId(String value) {
        this.catalogId = value;
    }

    /**
     * Gets the value of the operator property.
     * 
     * @return
     *     possible object is
     *     {@link SimpleTypeCategoryIDComparator }
     *     
     */
    public SimpleTypeCategoryIDComparator getOperator() {
        return operator;
    }

    /**
     * Sets the value of the operator property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimpleTypeCategoryIDComparator }
     *     
     */
    public void setOperator(SimpleTypeCategoryIDComparator value) {
        this.operator = value;
    }

}