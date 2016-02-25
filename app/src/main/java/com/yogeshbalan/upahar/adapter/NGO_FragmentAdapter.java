package com.yogeshbalan.upahar.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.github.nitrico.mapviewpager.MapViewPager;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.yogeshbalan.upahar.fragment.NGO_BaseFragment;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by yogesh on 11/2/16.
 */
public class NGO_FragmentAdapter extends MapViewPager.MultiAdapter {

    public static final String[] PAGE_TITLES = { "Delhi", "Maharashtra", "Tamil Nadu" };

    public static final String[] DELHI_TITLES = { "Anwar Education Society", "Atmashakti", "Ayushi Jan Sewa Welfare Foundation" };
    public static final String[] MAHARSHTRA_TITLES = { "AASTHA FOUNDATION", "AID FOR SOCIAL CHANGE AND WELFARE ASSOCIATION", "Apne Aap Women's Collective", "Child Help Foundation" };
    public static final String[] TAMIL_NADU_TITLES = { "All India Movement For Seva"};

    public static final CameraPosition ANWAR
            = CameraPosition.fromLatLngZoom(new LatLng(28.6761864,77.3159348), 6f);
    public static final CameraPosition ATMA
            = CameraPosition.fromLatLngZoom(new LatLng(28.5668488,77.266387999), 6f);
    public static final CameraPosition AYUSHI
            = CameraPosition.fromLatLngZoom(new LatLng(28.6841206,77.0632942), 6f);
    public static final CameraPosition AASTHA
            = CameraPosition.fromLatLngZoom(new LatLng(19.3006638,72.863008), 6f);
    public static final CameraPosition AID
            = CameraPosition.fromLatLngZoom(new LatLng(18.4828904,73.9016832), 6f);
    public static final CameraPosition APNE
            = CameraPosition.fromLatLngZoom(new LatLng(18.9581018,72.823314799), 6f);
    public static final CameraPosition CHILD
            = CameraPosition.fromLatLngZoom(new LatLng(19.3006638,72.863008), 6f);
    public static final CameraPosition ALLINDIA
            = CameraPosition.fromLatLngZoom(new LatLng(13.0374103,80.2581988), 6f);

    private LinkedList<CameraPosition> delhi;
    private LinkedList<CameraPosition> maharashtra;
    private LinkedList<CameraPosition> tamil_nadu;


    public NGO_FragmentAdapter(FragmentManager fm) {
        super(fm);

        // camera positions
        delhi = new LinkedList<>();
        maharashtra = new LinkedList<>();
        tamil_nadu = new LinkedList<>();

        delhi.add(ANWAR);
        delhi.add(ATMA);
        delhi.add(AYUSHI);
        maharashtra.add(AASTHA);
        maharashtra.add(AID);
        maharashtra.add(APNE);
        maharashtra.add(CHILD);
        tamil_nadu.add(ALLINDIA);

    }

    @Override
    public int getCount() {
        return PAGE_TITLES.length;
    }

    @Override
    public Fragment getItem(int position) {
        return NGO_BaseFragment.newInstance(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return PAGE_TITLES[position];
    }

    @Override
    public String getMarkerTitle(int page, int position) {
        switch (page) {
            case 0: return DELHI_TITLES[position];
            case 1: return MAHARSHTRA_TITLES[position];
            case 2: return TAMIL_NADU_TITLES[position];

        }
        return null;
    }

    @Override
    public List<CameraPosition> getCameraPositions(int page) {
        switch (page) {
            case 0: return delhi;
            case 1: return maharashtra;
            case 2: return tamil_nadu;

        }
        return null;
    }
}
