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
 * <p>Java class for simpleType.Refinement.ValueSet.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="simpleType.Refinement.ValueSet">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="search-result"/>
 *     &lt;enumeration value="refinement-category"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "simpleType.Refinement.ValueSet")
@XmlEnum
public enum SimpleTypeRefinementValueSet {

    @XmlEnumValue("search-result")
    SEARCH_RESULT("search-result"),
    @XmlEnumValue("refinement-category")
    REFINEMENT_CATEGORY("refinement-category");
    private final String value;

    SimpleTypeRefinementValueSet(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SimpleTypeRefinementValueSet fromValue(String v) {
        for (SimpleTypeRefinementValueSet c: SimpleTypeRefinementValueSet.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}