//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.12.28 at 03:10:19 pm IST 
//


package com.demandware.xml.impex.customer._2006_10_31;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for simpleType.ImportMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="simpleType.ImportMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="delete"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "simpleType.ImportMode")
@XmlEnum
public enum SimpleTypeImportMode {

    @XmlEnumValue("delete")
    DELETE("delete");
    private final String value;

    SimpleTypeImportMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SimpleTypeImportMode fromValue(String v) {
        for (SimpleTypeImportMode c: SimpleTypeImportMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
