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
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for sharedType.SiteSpecificSiteMapChangeFrequency complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sharedType.SiteSpecificSiteMapChangeFrequency">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.demandware.com/xml/impex/catalog/2006-10-31>simpleType.SiteMapChangeFrequency">
 *       &lt;attribute name="site-id" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}simpleType.Generic.NonEmptyString.32" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sharedType.SiteSpecificSiteMapChangeFrequency", propOrder = {
    "value"
})
public class SharedTypeSiteSpecificSiteMapChangeFrequency {

    @XmlValue
    protected SimpleTypeSiteMapChangeFrequency value;
    @XmlAttribute(name = "site-id")
    protected String siteId;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link SimpleTypeSiteMapChangeFrequency }
     *     
     */
    public SimpleTypeSiteMapChangeFrequency getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimpleTypeSiteMapChangeFrequency }
     *     
     */
    public void setValue(SimpleTypeSiteMapChangeFrequency value) {
        this.value = value;
    }

    /**
     * Gets the value of the siteId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteId() {
        return siteId;
    }

    /**
     * Sets the value of the siteId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteId(String value) {
        this.siteId = value;
    }

}
