//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.12.16 at 12:14:50 pm IST 
//


package com.datamigration.javaclass;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for complexType.Category complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="complexType.Category">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="display-name" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}sharedType.LocalizedString" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}sharedType.LocalizedString" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="online-flag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="online-from" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="online-to" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="variation-groups-display-mode" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}simpleType.VariationGroupsDisplayMode" minOccurs="0"/>
 *         &lt;element name="parent" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}simpleType.Generic.NonEmptyString.256" minOccurs="0"/>
 *         &lt;element name="position" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="thumbnail" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}simpleType.Generic.String.256" minOccurs="0"/>
 *         &lt;element name="image" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}simpleType.Generic.String.256" minOccurs="0"/>
 *         &lt;element name="template" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}simpleType.Generic.String.256" minOccurs="0"/>
 *         &lt;element name="search-placement" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="search-rank" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="sitemap-included-flag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="sitemap-changefrequency" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}simpleType.SiteMapChangeFrequency" minOccurs="0"/>
 *         &lt;element name="sitemap-priority" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}simpleType.SiteMapPriority" minOccurs="0"/>
 *         &lt;element name="page-attributes" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}complexType.PageAttributes" minOccurs="0"/>
 *         &lt;element name="custom-attributes" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}sharedType.CustomAttributes" minOccurs="0"/>
 *         &lt;element name="category-links" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}complexType.CategoryLinks" minOccurs="0"/>
 *         &lt;element name="attribute-groups" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}complexType.AttributeGroups" minOccurs="0"/>
 *         &lt;element name="refinement-definitions" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}complexType.RefinementDefinitions" minOccurs="0"/>
 *         &lt;element name="product-detail-page-meta-tag-rules" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}complexType.PageMetaTagRules" minOccurs="0"/>
 *         &lt;element name="product-listing-page-meta-tag-rules" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}complexType.PageMetaTagRules" minOccurs="0"/>
 *         &lt;element name="product-specification-rule" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}complexType.ProductSpecificationRule" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="mode" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}simpleType.ImportMode" />
 *       &lt;attribute name="category-id" type="{http://www.demandware.com/xml/impex/catalog/2006-10-31}simpleType.Generic.NonEmptyString.256" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "complexType.Category", propOrder = {
    "displayName",
    "description",
    "onlineFlag",
    "onlineFrom",
    "onlineTo",
    "variationGroupsDisplayMode",
    "parent",
    "position",
    "thumbnail",
    "image",
    "template",
    "searchPlacement",
    "searchRank",
    "sitemapIncludedFlag",
    "sitemapChangefrequency",
    "sitemapPriority",
    "pageAttributes",
    "customAttributes",
    "categoryLinks",
    "attributeGroups",
    "refinementDefinitions",
    "productDetailPageMetaTagRules",
    "productListingPageMetaTagRules",
    "productSpecificationRule"
})
public class ComplexTypeCategory {

    @XmlElement(name = "display-name")
    protected List<SharedTypeLocalizedString> displayName;
    protected List<SharedTypeLocalizedString> description;
    @XmlElement(name = "online-flag")
    protected Boolean onlineFlag;
    @XmlElementRef(name = "online-from", namespace = "http://www.demandware.com/xml/impex/catalog/2006-10-31", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> onlineFrom;
    @XmlElementRef(name = "online-to", namespace = "http://www.demandware.com/xml/impex/catalog/2006-10-31", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> onlineTo;
    @XmlElement(name = "variation-groups-display-mode")
    protected SimpleTypeVariationGroupsDisplayMode variationGroupsDisplayMode;
    protected String parent;
    protected Double position;
    protected String thumbnail;
    protected String image;
    protected String template;
    @XmlElement(name = "search-placement")
    protected Integer searchPlacement;
    @XmlElement(name = "search-rank")
    protected Integer searchRank;
    @XmlElement(name = "sitemap-included-flag")
    protected Boolean sitemapIncludedFlag;
    @XmlElement(name = "sitemap-changefrequency")
    protected SimpleTypeSiteMapChangeFrequency sitemapChangefrequency;
    @XmlElement(name = "sitemap-priority")
    protected Double sitemapPriority;
    @XmlElement(name = "page-attributes")
    protected ComplexTypePageAttributes pageAttributes;
    @XmlElement(name = "custom-attributes")
    protected SharedTypeCustomAttributes customAttributes;
    @XmlElement(name = "category-links")
    protected ComplexTypeCategoryLinks categoryLinks;
    @XmlElement(name = "attribute-groups")
    protected ComplexTypeAttributeGroups attributeGroups;
    @XmlElement(name = "refinement-definitions")
    protected ComplexTypeRefinementDefinitions refinementDefinitions;
    @XmlElement(name = "product-detail-page-meta-tag-rules")
    protected ComplexTypePageMetaTagRules productDetailPageMetaTagRules;
    @XmlElement(name = "product-listing-page-meta-tag-rules")
    protected ComplexTypePageMetaTagRules productListingPageMetaTagRules;
    @XmlElement(name = "product-specification-rule")
    protected ComplexTypeProductSpecificationRule productSpecificationRule;
    @XmlAttribute(name = "mode")
    protected SimpleTypeImportMode mode;
    @XmlAttribute(name = "category-id")
    protected String categoryId;

    /**
     * Gets the value of the displayName property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the displayName property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDisplayName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SharedTypeLocalizedString }
     * 
     * 
     */
    public List<SharedTypeLocalizedString> getDisplayName() {
        if (displayName == null) {
            displayName = new ArrayList<SharedTypeLocalizedString>();
        }
        return this.displayName;
    }

    /**
     * Gets the value of the description property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the description property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDescription().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SharedTypeLocalizedString }
     * 
     * 
     */
    public List<SharedTypeLocalizedString> getDescription() {
        if (description == null) {
            description = new ArrayList<SharedTypeLocalizedString>();
        }
        return this.description;
    }

    /**
     * Gets the value of the onlineFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOnlineFlag() {
        return onlineFlag;
    }

    /**
     * Sets the value of the onlineFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOnlineFlag(Boolean value) {
        this.onlineFlag = value;
    }

    /**
     * Gets the value of the onlineFrom property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getOnlineFrom() {
        return onlineFrom;
    }

    /**
     * Sets the value of the onlineFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setOnlineFrom(JAXBElement<XMLGregorianCalendar> value) {
        this.onlineFrom = ((JAXBElement<XMLGregorianCalendar> ) value);
    }

    /**
     * Gets the value of the onlineTo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getOnlineTo() {
        return onlineTo;
    }

    /**
     * Sets the value of the onlineTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setOnlineTo(JAXBElement<XMLGregorianCalendar> value) {
        this.onlineTo = ((JAXBElement<XMLGregorianCalendar> ) value);
    }

    /**
     * Gets the value of the variationGroupsDisplayMode property.
     * 
     * @return
     *     possible object is
     *     {@link SimpleTypeVariationGroupsDisplayMode }
     *     
     */
    public SimpleTypeVariationGroupsDisplayMode getVariationGroupsDisplayMode() {
        return variationGroupsDisplayMode;
    }

    /**
     * Sets the value of the variationGroupsDisplayMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimpleTypeVariationGroupsDisplayMode }
     *     
     */
    public void setVariationGroupsDisplayMode(SimpleTypeVariationGroupsDisplayMode value) {
        this.variationGroupsDisplayMode = value;
    }

    /**
     * Gets the value of the parent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParent() {
        return parent;
    }

    /**
     * Sets the value of the parent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParent(String value) {
        this.parent = value;
    }

    /**
     * Gets the value of the position property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPosition() {
        return position;
    }

    /**
     * Sets the value of the position property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPosition(Double value) {
        this.position = value;
    }

    /**
     * Gets the value of the thumbnail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * Sets the value of the thumbnail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThumbnail(String value) {
        this.thumbnail = value;
    }

    /**
     * Gets the value of the image property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the value of the image property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImage(String value) {
        this.image = value;
    }

    /**
     * Gets the value of the template property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemplate() {
        return template;
    }

    /**
     * Sets the value of the template property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemplate(String value) {
        this.template = value;
    }

    /**
     * Gets the value of the searchPlacement property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSearchPlacement() {
        return searchPlacement;
    }

    /**
     * Sets the value of the searchPlacement property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSearchPlacement(Integer value) {
        this.searchPlacement = value;
    }

    /**
     * Gets the value of the searchRank property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSearchRank() {
        return searchRank;
    }

    /**
     * Sets the value of the searchRank property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSearchRank(Integer value) {
        this.searchRank = value;
    }

    /**
     * Gets the value of the sitemapIncludedFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSitemapIncludedFlag() {
        return sitemapIncludedFlag;
    }

    /**
     * Sets the value of the sitemapIncludedFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSitemapIncludedFlag(Boolean value) {
        this.sitemapIncludedFlag = value;
    }

    /**
     * Gets the value of the sitemapChangefrequency property.
     * 
     * @return
     *     possible object is
     *     {@link SimpleTypeSiteMapChangeFrequency }
     *     
     */
    public SimpleTypeSiteMapChangeFrequency getSitemapChangefrequency() {
        return sitemapChangefrequency;
    }

    /**
     * Sets the value of the sitemapChangefrequency property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimpleTypeSiteMapChangeFrequency }
     *     
     */
    public void setSitemapChangefrequency(SimpleTypeSiteMapChangeFrequency value) {
        this.sitemapChangefrequency = value;
    }

    /**
     * Gets the value of the sitemapPriority property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSitemapPriority() {
        return sitemapPriority;
    }

    /**
     * Sets the value of the sitemapPriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSitemapPriority(Double value) {
        this.sitemapPriority = value;
    }

    /**
     * Gets the value of the pageAttributes property.
     * 
     * @return
     *     possible object is
     *     {@link ComplexTypePageAttributes }
     *     
     */
    public ComplexTypePageAttributes getPageAttributes() {
        return pageAttributes;
    }

    /**
     * Sets the value of the pageAttributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComplexTypePageAttributes }
     *     
     */
    public void setPageAttributes(ComplexTypePageAttributes value) {
        this.pageAttributes = value;
    }

    /**
     * Gets the value of the customAttributes property.
     * 
     * @return
     *     possible object is
     *     {@link SharedTypeCustomAttributes }
     *     
     */
    public SharedTypeCustomAttributes getCustomAttributes() {
        return customAttributes;
    }

    /**
     * Sets the value of the customAttributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link SharedTypeCustomAttributes }
     *     
     */
    public void setCustomAttributes(SharedTypeCustomAttributes value) {
        this.customAttributes = value;
    }

    /**
     * Gets the value of the categoryLinks property.
     * 
     * @return
     *     possible object is
     *     {@link ComplexTypeCategoryLinks }
     *     
     */
    public ComplexTypeCategoryLinks getCategoryLinks() {
        return categoryLinks;
    }

    /**
     * Sets the value of the categoryLinks property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComplexTypeCategoryLinks }
     *     
     */
    public void setCategoryLinks(ComplexTypeCategoryLinks value) {
        this.categoryLinks = value;
    }

    /**
     * Gets the value of the attributeGroups property.
     * 
     * @return
     *     possible object is
     *     {@link ComplexTypeAttributeGroups }
     *     
     */
    public ComplexTypeAttributeGroups getAttributeGroups() {
        return attributeGroups;
    }

    /**
     * Sets the value of the attributeGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComplexTypeAttributeGroups }
     *     
     */
    public void setAttributeGroups(ComplexTypeAttributeGroups value) {
        this.attributeGroups = value;
    }

    /**
     * Gets the value of the refinementDefinitions property.
     * 
     * @return
     *     possible object is
     *     {@link ComplexTypeRefinementDefinitions }
     *     
     */
    public ComplexTypeRefinementDefinitions getRefinementDefinitions() {
        return refinementDefinitions;
    }

    /**
     * Sets the value of the refinementDefinitions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComplexTypeRefinementDefinitions }
     *     
     */
    public void setRefinementDefinitions(ComplexTypeRefinementDefinitions value) {
        this.refinementDefinitions = value;
    }

    /**
     * Gets the value of the productDetailPageMetaTagRules property.
     * 
     * @return
     *     possible object is
     *     {@link ComplexTypePageMetaTagRules }
     *     
     */
    public ComplexTypePageMetaTagRules getProductDetailPageMetaTagRules() {
        return productDetailPageMetaTagRules;
    }

    /**
     * Sets the value of the productDetailPageMetaTagRules property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComplexTypePageMetaTagRules }
     *     
     */
    public void setProductDetailPageMetaTagRules(ComplexTypePageMetaTagRules value) {
        this.productDetailPageMetaTagRules = value;
    }

    /**
     * Gets the value of the productListingPageMetaTagRules property.
     * 
     * @return
     *     possible object is
     *     {@link ComplexTypePageMetaTagRules }
     *     
     */
    public ComplexTypePageMetaTagRules getProductListingPageMetaTagRules() {
        return productListingPageMetaTagRules;
    }

    /**
     * Sets the value of the productListingPageMetaTagRules property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComplexTypePageMetaTagRules }
     *     
     */
    public void setProductListingPageMetaTagRules(ComplexTypePageMetaTagRules value) {
        this.productListingPageMetaTagRules = value;
    }

    /**
     * Gets the value of the productSpecificationRule property.
     * 
     * @return
     *     possible object is
     *     {@link ComplexTypeProductSpecificationRule }
     *     
     */
    public ComplexTypeProductSpecificationRule getProductSpecificationRule() {
        return productSpecificationRule;
    }

    /**
     * Sets the value of the productSpecificationRule property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComplexTypeProductSpecificationRule }
     *     
     */
    public void setProductSpecificationRule(ComplexTypeProductSpecificationRule value) {
        this.productSpecificationRule = value;
    }

    /**
     * Gets the value of the mode property.
     * 
     * @return
     *     possible object is
     *     {@link SimpleTypeImportMode }
     *     
     */
    public SimpleTypeImportMode getMode() {
        return mode;
    }

    /**
     * Sets the value of the mode property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimpleTypeImportMode }
     *     
     */
    public void setMode(SimpleTypeImportMode value) {
        this.mode = value;
    }

    /**
     * Gets the value of the categoryId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * Sets the value of the categoryId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategoryId(String value) {
        this.categoryId = value;
    }

}
