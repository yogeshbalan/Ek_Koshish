package com.yogeshbalan.upahar.model;

import java.io.Serializable;

/**
 * Created by yogesh on 13/2/16.
 */
public class UserLB implements Serializable {
    String logo_url;
    String ngo;

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getNgo() {
        return ngo;
    }

    public void setNgo(String ngo) {
        this.ngo = ngo;
    }
}
