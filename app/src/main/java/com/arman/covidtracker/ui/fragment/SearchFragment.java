package com.arman.covidtracker.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arman.covidtracker.R;
import com.arman.covidtracker.contract.SearchContract;
import com.arman.covidtracker.databinding.FragmentSearchBinding;
import com.arman.covidtracker.di.component.FragmentComponent;
import com.arman.covidtracker.model.Country;
import com.arman.covidtracker.presenter.SearchPresenter;
import com.arman.covidtracker.repository.SearchRepository;
import com.arman.covidtracker.ui.adapter.CountryStatusAdapter;
import com.arman.covidtracker.ui.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends BaseFragment<SearchPresenter> implements SearchContract.View {

    private static final String TAG = SearchFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Inject
    SearchRepository repository;

    FragmentSearchBinding mBinding;

    CountryStatusAdapter mAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter(this, repository, AndroidSchedulers.mainThread());
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
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentSearchBinding.inflate(inflater, container, false);
        initView();
        return mBinding.getRoot();
    }

    private void initView() {
        mAdapter = new CountryStatusAdapter(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.recycler.setLayoutManager(layoutManager);
        mBinding.recycler.setHasFixedSize(true);
        mBinding.recycler.setAdapter(mAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.fetchResult();
    }
    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (presenter == null) {
            injectPresenter();
        }
    }

    private void injectPresenter() {
        getComponent(FragmentComponent.class).inject(this);
        Log.d(TAG, "injectPresenter: ");

    }

    @Override
    public void onDestroyView() {
        presenter.unsubscribe();
        super.onDestroyView();

    }




    @Override
    public void onDisplayResult(List<Country> country) {
        Log.d(TAG, "onDisplayResult: " + country);
        mAdapter.setCountries(country);

    }

    @Override
    public void onDisplayEmptyScreen() {
        Log.d(TAG, "onDisplayEmptyScreen: ");

    }

    @Override
    public void onDisplayFailed(String message) {
        Log.d(TAG, "onDisplayFailed: " + message);
    }

    @Override
    public void onDisplayCountryStatus(Country country) {
        int position = mAdapter.getCountries().indexOf(country);

        if (position == -1) {
            return;
        }
        mAdapter.updateCountry(position,country);
    }

    @Override
    public void showProgress() {
        Log.d(TAG, "showProgress: ");
        if (mBinding.shimmerViewContainer.isShimmerVisible()) {
            mBinding.shimmerViewContainer.startShimmer();
        }
    }

    @Override
    public void hideProgress() {
        Log.d(TAG, "hideProgress: ");
        if(mBinding.shimmerViewContainer.isShimmerStarted()){
            mBinding.shimmerViewContainer.stopShimmer();
            mBinding.shimmerViewContainer.setVisibility(View.GONE);
        }
    }
}