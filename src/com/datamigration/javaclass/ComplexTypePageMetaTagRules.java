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
 * <p>Java class for complexType.PageMetaTagRules complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="complexType.PageMetaTagRules">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="page-meta-tag-rule" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}complexType.PageMetaTagRule" maxOccurs="50" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "complexType.PageMetaTagRules", propOrder = {
    "pageMetaTagRule"
})
public class ComplexTypePageMetaTagRules {

    @XmlElement(name = "page-meta-tag-rule")
    protected List<ComplexTypePageMetaTagRule> pageMetaTagRule;

    /**
     * Gets the value of the pageMetaTagRule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pageMetaTagRule property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPageMetaTagRule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ComplexTypePageMetaTagRule }
     * 
     * 
     */
    public List<ComplexTypePageMetaTagRule> getPageMetaTagRule() {
        if (pageMetaTagRule == null) {
            pageMetaTagRule = new ArrayList<ComplexTypePageMetaTagRule>();
        }
        return this.pageMetaTagRule;
    }

}