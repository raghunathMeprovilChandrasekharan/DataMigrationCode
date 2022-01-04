//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.12.16 at 12:14:50 pm IST 
//


package com.datamigration.javaclass;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for simpleType.AttributeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="simpleType.AttributeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="string"/>
 *     &lt;enumeration value="int"/>
 *     &lt;enumeration value="double"/>
 *     &lt;enumeration value="text"/>
 *     &lt;enumeration value="html"/>
 *     &lt;enumeration value="image"/>
 *     &lt;enumeration value="boolean"/>
 *     &lt;enumeration value="date"/>
 *     &lt;enumeration value="datetime"/>
 *     &lt;enumeration value="email"/>
 *     &lt;enumeration value="password"/>
 *     &lt;enumeration value="set-of-string"/>
 *     &lt;enumeration value="set-of-int"/>
 *     &lt;enumeration value="set-of-double"/>
 *     &lt;enumeration value="enum-of-string"/>
 *     &lt;enumeration value="enum-of-int"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "simpleType.AttributeType")
@XmlEnum
public enum SimpleTypeAttributeType {

    @XmlEnumValue("string")
    STRING("string"),
    @XmlEnumValue("int")
    INT("int"),
    @XmlEnumValue("double")
    DOUBLE("double"),
    @XmlEnumValue("text")
    TEXT("text"),
    @XmlEnumValue("html")
    HTML("html"),
    @XmlEnumValue("image")
    IMAGE("image"),
    @XmlEnumValue("boolean")
    BOOLEAN("boolean"),
    @XmlEnumValue("date")
    DATE("date"),
    @XmlEnumValue("datetime")
    DATETIME("datetime"),
    @XmlEnumValue("email")
    EMAIL("email"),
    @XmlEnumValue("password")
    PASSWORD("password"),
    @XmlEnumValue("set-of-string")
    SET_OF_STRING("set-of-string"),
    @XmlEnumValue("set-of-int")
    SET_OF_INT("set-of-int"),
    @XmlEnumValue("set-of-double")
    SET_OF_DOUBLE("set-of-double"),
    @XmlEnumValue("enum-of-string")
    ENUM_OF_STRING("enum-of-string"),
    @XmlEnumValue("enum-of-int")
    ENUM_OF_INT("enum-of-int");
    private final String value;

    SimpleTypeAttributeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SimpleTypeAttributeType fromValue(String v) {
        for (SimpleTypeAttributeType c: SimpleTypeAttributeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
