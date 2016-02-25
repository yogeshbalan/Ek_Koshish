package com.yogeshbalan.upahar.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yogeshbalan.upahar.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NGO_BaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NGO_BaseFragment extends Fragment {

    private Toolbar toolbar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public NGO_BaseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param index Parameter 1.
     * @param //ngo Parameter 2.
     * @return A new instance of fragment NGO_BaseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NGO_BaseFragment newInstance(int index) {
        NGO_BaseFragment fragment = new NGO_BaseFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, index);
        //args.putSerializable(ARG_PARAM2, ngo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ngo__base, container, false);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("NGO - Ek Koshish");

        return view;
    }

}
