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
 * <p>Java class for complexType.Option.Value complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="complexType.Option.Value">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="display-value" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}sharedType.LocalizedString" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="product-id-modifier" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}simpleType.Generic.String.256" minOccurs="0"/>
 *         &lt;element name="option-value-prices" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}complexType.Option.Value.Prices" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="value-id" use="required" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}simpleType.Generic.NonEmptyString.256" />
 *       &lt;attribute name="default" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "complexType.Option.Value", propOrder = {
    "displayValue",
    "productIdModifier",
    "optionValuePrices"
})
public class ComplexTypeOptionValue {

    @XmlElement(name = "display-value")
    protected List<SharedTypeLocalizedString> displayValue;
    @XmlElement(name = "product-id-modifier")
    protected String productIdModifier;
    @XmlElement(name = "option-value-prices")
    protected ComplexTypeOptionValuePrices optionValuePrices;
    @XmlAttribute(name = "value-id", required = true)
    protected String valueId;
    @XmlAttribute(name = "default")
    protected Boolean _default;

    /**
     * Gets the value of the displayValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the displayValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDisplayValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SharedTypeLocalizedString }
     * 
     * 
     */
    public List<SharedTypeLocalizedString> getDisplayValue() {
        if (displayValue == null) {
            displayValue = new ArrayList<SharedTypeLocalizedString>();
        }
        return this.displayValue;
    }

    /**
     * Gets the value of the productIdModifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductIdModifier() {
        return productIdModifier;
    }

    /**
     * Sets the value of the productIdModifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductIdModifier(String value) {
        this.productIdModifier = value;
    }

    /**
     * Gets the value of the optionValuePrices property.
     * 
     * @return
     *     possible object is
     *     {@link ComplexTypeOptionValuePrices }
     *     
     */
    public ComplexTypeOptionValuePrices getOptionValuePrices() {
        return optionValuePrices;
    }

    /**
     * Sets the value of the optionValuePrices property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComplexTypeOptionValuePrices }
     *     
     */
    public void setOptionValuePrices(ComplexTypeOptionValuePrices value) {
        this.optionValuePrices = value;
    }

    /**
     * Gets the value of the valueId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValueId() {
        return valueId;
    }

    /**
     * Sets the value of the valueId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValueId(String value) {
        this.valueId = value;
    }

    /**
     * Gets the value of the default property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDefault() {
        return _default;
    }

    /**
     * Sets the value of the default property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDefault(Boolean value) {
        this._default = value;
    }

}
