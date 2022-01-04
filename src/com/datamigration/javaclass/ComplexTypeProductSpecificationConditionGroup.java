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
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for complexType.ProductSpecification.ConditionGroup complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="complexType.ProductSpecification.ConditionGroup">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="brand-condition" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}complexType.ProductSpecification.ProductBrandFilter"/>
 *         &lt;element name="product-id-condition" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}complexType.ProductSpecification.ProductIDFilter"/>
 *         &lt;element name="category-condition" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}complexType.ProductSpecification.ProductCategoryFilter"/>
 *         &lt;element name="attribute-condition" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}complexType.ProductSpecification.ProductAttributeFilter"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "complexType.ProductSpecification.ConditionGroup", propOrder = {
    "brandConditionOrProductIdConditionOrCategoryCondition"
})
public class ComplexTypeProductSpecificationConditionGroup {

    @XmlElements({
        @XmlElement(name = "category-condition", type = ComplexTypeProductSpecificationProductCategoryFilter.class),
        @XmlElement(name = "brand-condition", type = ComplexTypeProductSpecificationProductBrandFilter.class),
        @XmlElement(name = "product-id-condition", type = ComplexTypeProductSpecificationProductIDFilter.class),
        @XmlElement(name = "attribute-condition", type = ComplexTypeProductSpecificationProductAttributeFilter.class)
    })
    protected List<Object> brandConditionOrProductIdConditionOrCategoryCondition;

    /**
     * Gets the value of the brandConditionOrProductIdConditionOrCategoryCondition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the brandConditionOrProductIdConditionOrCategoryCondition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBrandConditionOrProductIdConditionOrCategoryCondition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ComplexTypeProductSpecificationProductCategoryFilter }
     * {@link ComplexTypeProductSpecificationProductBrandFilter }
     * {@link ComplexTypeProductSpecificationProductIDFilter }
     * {@link ComplexTypeProductSpecificationProductAttributeFilter }
     * 
     * 
     */
    public List<Object> getBrandConditionOrProductIdConditionOrCategoryCondition() {
        if (brandConditionOrProductIdConditionOrCategoryCondition == null) {
            brandConditionOrProductIdConditionOrCategoryCondition = new ArrayList<Object>();
        }
        return this.brandConditionOrProductIdConditionOrCategoryCondition;
    }

}
