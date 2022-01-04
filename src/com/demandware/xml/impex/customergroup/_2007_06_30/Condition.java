//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.12.28 at 10:48:34 pm IST 
//


package com.demandware.xml.impex.customergroup._2007_06_30;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Condition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Condition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attribute-path" type="{http://www.demandware.com/xml/impex/customergroup/2007-06-30}AttributePath"/>
 *         &lt;element name="operator" type="{http://www.demandware.com/xml/impex/customergroup/2007-06-30}Operator"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="string" type="{http://www.demandware.com/xml/impex/customergroup/2007-06-30}Generic.NonEmptyString.256" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="int" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded"/>
 *           &lt;element name="int-range" type="{http://www.demandware.com/xml/impex/customergroup/2007-06-30}IntRange"/>
 *           &lt;element name="number" type="{http://www.w3.org/2001/XMLSchema}decimal" maxOccurs="unbounded"/>
 *           &lt;element name="number-range" type="{http://www.demandware.com/xml/impex/customergroup/2007-06-30}NumberRange"/>
 *           &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *           &lt;element name="date-range" type="{http://www.demandware.com/xml/impex/customergroup/2007-06-30}DateRange"/>
 *           &lt;element name="number-of-days" type="{http://www.demandware.com/xml/impex/customergroup/2007-06-30}NumberOfDays"/>
 *           &lt;element name="time-period" type="{http://www.demandware.com/xml/impex/customergroup/2007-06-30}TimePeriod"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Condition", propOrder = {
    "attributePath",
    "operator",
    "string",
    "_int",
    "intRange",
    "number",
    "numberRange",
    "date",
    "dateRange",
    "numberOfDays",
    "timePeriod"
})
public class Condition {

    @XmlElement(name = "attribute-path", required = true)
    protected String attributePath;
    @XmlElement(required = true)
    protected Operator operator;
    protected List<String> string;
    @XmlElement(name = "int", type = Integer.class)
    protected List<Integer> _int;
    @XmlElement(name = "int-range")
    protected IntRange intRange;
    protected List<BigDecimal> number;
    @XmlElement(name = "number-range")
    protected NumberRange numberRange;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar date;
    @XmlElement(name = "date-range")
    protected DateRange dateRange;
    @XmlElement(name = "number-of-days")
    protected Integer numberOfDays;
    @XmlElement(name = "time-period")
    protected TimePeriod timePeriod;

    /**
     * Gets the value of the attributePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributePath() {
        return attributePath;
    }

    /**
     * Sets the value of the attributePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributePath(String value) {
        this.attributePath = value;
    }

    /**
     * Gets the value of the operator property.
     * 
     * @return
     *     possible object is
     *     {@link Operator }
     *     
     */
    public Operator getOperator() {
        return operator;
    }

    /**
     * Sets the value of the operator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Operator }
     *     
     */
    public void setOperator(Operator value) {
        this.operator = value;
    }

    /**
     * Gets the value of the string property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the string property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getString().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getString() {
        if (string == null) {
            string = new ArrayList<String>();
        }
        return this.string;
    }

    /**
     * Gets the value of the int property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the int property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInt().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getInt() {
        if (_int == null) {
            _int = new ArrayList<Integer>();
        }
        return this._int;
    }

    /**
     * Gets the value of the intRange property.
     * 
     * @return
     *     possible object is
     *     {@link IntRange }
     *     
     */
    public IntRange getIntRange() {
        return intRange;
    }

    /**
     * Sets the value of the intRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntRange }
     *     
     */
    public void setIntRange(IntRange value) {
        this.intRange = value;
    }

    /**
     * Gets the value of the number property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the number property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNumber().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BigDecimal }
     * 
     * 
     */
    public List<BigDecimal> getNumber() {
        if (number == null) {
            number = new ArrayList<BigDecimal>();
        }
        return this.number;
    }

    /**
     * Gets the value of the numberRange property.
     * 
     * @return
     *     possible object is
     *     {@link NumberRange }
     *     
     */
    public NumberRange getNumberRange() {
        return numberRange;
    }

    /**
     * Sets the value of the numberRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link NumberRange }
     *     
     */
    public void setNumberRange(NumberRange value) {
        this.numberRange = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    /**
     * Gets the value of the dateRange property.
     * 
     * @return
     *     possible object is
     *     {@link DateRange }
     *     
     */
    public DateRange getDateRange() {
        return dateRange;
    }

    /**
     * Sets the value of the dateRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateRange }
     *     
     */
    public void setDateRange(DateRange value) {
        this.dateRange = value;
    }

    /**
     * Gets the value of the numberOfDays property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    /**
     * Sets the value of the numberOfDays property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumberOfDays(Integer value) {
        this.numberOfDays = value;
    }

    /**
     * Gets the value of the timePeriod property.
     * 
     * @return
     *     possible object is
     *     {@link TimePeriod }
     *     
     */
    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    /**
     * Sets the value of the timePeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimePeriod }
     *     
     */
    public void setTimePeriod(TimePeriod value) {
        this.timePeriod = value;
    }

}
