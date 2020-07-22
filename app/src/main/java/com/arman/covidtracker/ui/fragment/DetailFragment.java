package com.arman.covidtracker.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arman.covidtracker.R;
import com.arman.covidtracker.databinding.FragmentDetailBinding;
import com.arman.covidtracker.model.Country;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

   private static final String TAG = DetailFragment.class.getSimpleName();

    private static final String ARG_COUNTRY_STATUS = "arg_country_status";

    private Country mCountry;

    FragmentDetailBinding mBinding;

    public DetailFragment() {
        // Required empty public constructor
    }


    public static DetailFragment newInstance(Country country) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_COUNTRY_STATUS, country);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCountry = getArguments().getParcelable(ARG_COUNTRY_STATUS);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentDetailBinding.inflate(inflater,container,false);

        return mBinding.getRoot();
    }
    
}