package com.yogeshbalan.upahar.volley;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.yogeshbalan.upahar.ApplicationWrapper;

/**
 * Created by yogesh on 11/2/16.
 */
public class VolleySingleton {
    public static VolleySingleton myInstance = null;
    private RequestQueue requestQueue;

    private VolleySingleton() {
        requestQueue = Volley.newRequestQueue(ApplicationWrapper.getAppContext());
    }

    public static VolleySingleton getMyInstance() {
        if (myInstance == null) {
            myInstance = new VolleySingleton();
        }
        return myInstance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}

