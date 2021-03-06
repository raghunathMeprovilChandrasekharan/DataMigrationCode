//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.12.28 at 03:10:19 pm IST 
//


package com.demandware.xml.impex.customer._2006_10_31;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="customer" type="{http://www.demandware.com/xml/impex/customer/2006-10-31}complexType.Customer" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="group-assignment" type="{http://www.demandware.com/xml/impex/customer/2006-10-31}complexType.CustomerGroupAssignment" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "customer",
    "groupAssignment"
})
@XmlRootElement(name = "customers")
public class Customers {

    protected List<ComplexTypeCustomer> customer;
    @XmlElement(name = "group-assignment")
    protected List<ComplexTypeCustomerGroupAssignment> groupAssignment;

    /**
     * Gets the value of the customer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the customer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustomer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ComplexTypeCustomer }
     * 
     * 
     */
    public List<ComplexTypeCustomer> getCustomer() {
        if (customer == null) {
            customer = new ArrayList<ComplexTypeCustomer>();
        }
        return this.customer;
    }

    /**
     * Gets the value of the groupAssignment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the groupAssignment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGroupAssignment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ComplexTypeCustomerGroupAssignment }
     * 
     * 
     */
    public List<ComplexTypeCustomerGroupAssignment> getGroupAssignment() {
        if (groupAssignment == null) {
            groupAssignment = new ArrayList<ComplexTypeCustomerGroupAssignment>();
        }
        return this.groupAssignment;
    }

}
