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
 * <p>Java class for simpleType.SiteMapChangeFrequency.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="simpleType.SiteMapChangeFrequency">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="always"/>
 *     &lt;enumeration value="hourly"/>
 *     &lt;enumeration value="daily"/>
 *     &lt;enumeration value="weekly"/>
 *     &lt;enumeration value="monthly"/>
 *     &lt;enumeration value="yearly"/>
 *     &lt;enumeration value="never"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "simpleType.SiteMapChangeFrequency")
@XmlEnum
public enum SimpleTypeSiteMapChangeFrequency {

    @XmlEnumValue("always")
    ALWAYS("always"),
    @XmlEnumValue("hourly")
    HOURLY("hourly"),
    @XmlEnumValue("daily")
    DAILY("daily"),
    @XmlEnumValue("weekly")
    WEEKLY("weekly"),
    @XmlEnumValue("monthly")
    MONTHLY("monthly"),
    @XmlEnumValue("yearly")
    YEARLY("yearly"),
    @XmlEnumValue("never")
    NEVER("never");
    private final String value;

    SimpleTypeSiteMapChangeFrequency(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SimpleTypeSiteMapChangeFrequency fromValue(String v) {
        for (SimpleTypeSiteMapChangeFrequency c: SimpleTypeSiteMapChangeFrequency.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}