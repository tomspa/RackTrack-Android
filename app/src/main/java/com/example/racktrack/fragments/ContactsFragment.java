package com.example.racktrack.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


public class ContactsFragment extends Fragment {
    private FragmentActivity mainActivity;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mainActivity = getActivity();
        this.context = getContext();
    }
}
