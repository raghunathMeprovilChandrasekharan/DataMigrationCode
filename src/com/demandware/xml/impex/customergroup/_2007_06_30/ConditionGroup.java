//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.12.28 at 10:48:34 pm IST 
//


package com.demandware.xml.impex.customergroup._2007_06_30;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConditionGroup complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConditionGroup">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded">
 *         &lt;element name="condition" type="{http://www.demandware.com/xml/impex/customergroup/2007-06-30}Condition"/>
 *       &lt;/sequence>
 *       &lt;attribute name="match-mode" use="required" type="{http://www.demandware.com/xml/impex/customergroup/2007-06-30}MatchMode" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConditionGroup", propOrder = {
    "condition"
})
public class ConditionGroup {

    @XmlElement(required = true)
    protected List<Condition> condition;
    @XmlAttribute(name = "match-mode", required = true)
    protected MatchMode matchMode;

    /**
     * Gets the value of the condition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the condition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCondition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Condition }
     * 
     * 
     */
    public List<Condition> getCondition() {
        if (condition == null) {
            condition = new ArrayList<Condition>();
        }
        return this.condition;
    }

    /**
     * Gets the value of the matchMode property.
     * 
     * @return
     *     possible object is
     *     {@link MatchMode }
     *     
     */
    public MatchMode getMatchMode() {
        return matchMode;
    }

    /**
     * Sets the value of the matchMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link MatchMode }
     *     
     */
    public void setMatchMode(MatchMode value) {
        this.matchMode = value;
    }

}