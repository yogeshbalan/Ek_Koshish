package com.yogeshbalan.upahar;

/**
 * Created by yogesh on 11/2/16.
 */
public class Constants {

    public static final String BASE_URL = "http://192.168.1.5:5000/";

    public static final String NGO_ALL = "ngolist";
    public static final String ADDRESS = "address";
    public static final String DESCRIPTION = "description";
    public static final String EMAIL = "email";
    public static final String GEOPOINT = "geopoint";
    public static final String LATTITUDE = "lat";
    public static final String LONGITUDE = "lng";
    public static final String IMAGE_URL = "image_urls";
    public static final String LOGO_URL = "logo_url";
    public static final String NAME = "name";
    public static final String OBJECT_ID = "objectId";
    public static final String PHONE = "phone_no";
    public static final String SLOGAN = "slogan";
    public static final String WEBSITE_URL = "website_url";

    public static final String PARSE_APPLICATION_ID = "HK0hfpUC18oALkUU5fOUgBDq4Sw0lb4GZ63X4VTg";
    public static final String PARSE_CLIENT_ID = "l8eh08t1rJk8E5TM3yFgeR2smx6h7nb4TxO4V4tY";

    public static final String PREFERENCE_KEY = "myPrefs";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    public static final String getRequestURL(String state){
        return BASE_URL + "ngo_info/" + state;
    }

}
