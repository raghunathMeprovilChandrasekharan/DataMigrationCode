//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.02.04 at 09:29:38 am IST 
//


package com.demandware.xml.impex.productlist._2009_10_28;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for simpleType.ProductList.Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="simpleType.ProductList.Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="wish_list"/>
 *     &lt;enumeration value="gift_registry"/>
 *     &lt;enumeration value="shopping_list"/>
 *     &lt;enumeration value="custom_1"/>
 *     &lt;enumeration value="custom_2"/>
 *     &lt;enumeration value="custom_3"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "simpleType.ProductList.Type")
@XmlEnum
public enum SimpleTypeProductListType {

    @XmlEnumValue("wish_list")
    WISH_LIST("wish_list"),
    @XmlEnumValue("gift_registry")
    GIFT_REGISTRY("gift_registry"),
    @XmlEnumValue("shopping_list")
    SHOPPING_LIST("shopping_list"),
    @XmlEnumValue("custom_1")
    CUSTOM_1("custom_1"),
    @XmlEnumValue("custom_2")
    CUSTOM_2("custom_2"),
    @XmlEnumValue("custom_3")
    CUSTOM_3("custom_3");
    private final String value;

    SimpleTypeProductListType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SimpleTypeProductListType fromValue(String v) {
        for (SimpleTypeProductListType c: SimpleTypeProductListType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
