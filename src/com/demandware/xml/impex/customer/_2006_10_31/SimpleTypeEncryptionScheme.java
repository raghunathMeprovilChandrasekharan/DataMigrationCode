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
 * <p>Java class for simpleType.EncryptionScheme.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="simpleType.EncryptionScheme">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="md5"/>
 *     &lt;enumeration value="scrypt"/>
 *     &lt;enumeration value="s0001"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "simpleType.EncryptionScheme")
@XmlEnum
public enum SimpleTypeEncryptionScheme {


    /**
     * Support for the md5 encryption algorithm has changed. Passwords imported in md5 will be stored as an scrypt'd value.
     * 
     */
    @XmlEnumValue("md5")
    MD_5("md5"),
    @XmlEnumValue("scrypt")
    SCRYPT("scrypt"),
    @XmlEnumValue("s0001")
    S_0001("s0001");
    private final String value;

    SimpleTypeEncryptionScheme(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SimpleTypeEncryptionScheme fromValue(String v) {
        for (SimpleTypeEncryptionScheme c: SimpleTypeEncryptionScheme.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
