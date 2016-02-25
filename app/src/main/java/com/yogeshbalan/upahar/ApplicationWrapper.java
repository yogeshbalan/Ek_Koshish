package com.yogeshbalan.upahar;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

/**
 * Created by yogesh on 11/2/16.
 */
public class ApplicationWrapper extends Application {
    private static ApplicationWrapper applicationWrapper;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationWrapper = this;

        // [Optional] Power your app with Local Datastore. For more info, go to
        // https://parse.com/docs/android/guide#local-datastore
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(this, Constants.PARSE_APPLICATION_ID, Constants.PARSE_CLIENT_ID);

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);

    }

    public static ApplicationWrapper getApplicationWrapper() {
        return applicationWrapper;
    }

    public static Context getAppContext() {
        return applicationWrapper.getApplicationContext();
    }
}

