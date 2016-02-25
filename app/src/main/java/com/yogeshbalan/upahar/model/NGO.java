package com.yogeshbalan.upahar.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yogesh on 11/2/16.
 */
public class NGO implements Serializable {

    private String address;
    private String description;
    private String email;
    private Geopoint geopoint;
    private List<String> imageUrls = new ArrayList<String>();
    private String logoUrl;
    private String name;
    private String objectId;
    private String phoneNo;
    private String slogan;
    private String websiteUrl;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The geopoint
     */
    public Geopoint getGeopoint() {
        return geopoint;
    }

    /**
     *
     * @param geopoint
     * The geopoint
     */
    public void setGeopoint(Geopoint geopoint) {
        this.geopoint = geopoint;
    }

    /**
     *
     * @return
     * The imageUrls
     */
    public List<String> getImageUrls() {
        return imageUrls;
    }

    /**
     *
     * @param imageUrls
     * The image_urls
     */
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    /**
     *
     * @return
     * The logoUrl
     */
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     *
     * @param logoUrl
     * The logo_url
     */
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The objectId
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     *
     * @param objectId
     * The objectId
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     *
     * @return
     * The phoneNo
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     *
     * @param phoneNo
     * The phone_no
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     *
     * @return
     * The slogan
     */
    public String getSlogan() {
        return slogan;
    }

    /**
     *
     * @param slogan
     * The slogan
     */
    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    /**
     *
     * @return
     * The websiteUrl
     */
    public String getWebsiteUrl() {
        return websiteUrl;
    }

    /**
     *
     * @param websiteUrl
     * The website_url
     */
    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


}
