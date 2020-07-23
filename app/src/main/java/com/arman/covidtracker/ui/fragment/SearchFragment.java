package com.arman.covidtracker.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arman.covidtracker.R;
import com.arman.covidtracker.contract.SearchContract;
import com.arman.covidtracker.databinding.FragmentSearchBinding;
import com.arman.covidtracker.db.AppDb;
import com.arman.covidtracker.di.component.FragmentComponent;
import com.arman.covidtracker.model.Country;
import com.arman.covidtracker.presenter.SearchPresenter;
import com.arman.covidtracker.repository.CountryRepository;
import com.arman.covidtracker.repository.SearchRepository;
import com.arman.covidtracker.ui.adapter.CountryStatusAdapter;
import com.arman.covidtracker.ui.base.BaseFragment;
import com.arman.covidtracker.viewModel.CountryViewModel;

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

    @Inject
    AppDb db;

    FragmentSearchBinding mBinding;

    CountryStatusAdapter mAdapter;

    @Inject
    public CountryRepository countryRepository;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String param1, String param2) {
        return new SearchFragment();
    }

    @NonNull
    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter(this, repository,AndroidSchedulers.mainThread());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: " + (savedInstanceState == null));
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: " + (savedInstanceState == null));
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
        Log.d(TAG, "onViewCreated: " + (savedInstanceState == null));
        if (savedInstanceState == null) {
            presenter.fetchResult();

        }

        setupViewModel();
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


    private void setupViewModel() {

        countryRepository.getCountriesLiveData().observe(getViewLifecycleOwner(), new Observer<List<Country>>() {
            @Override
            public void onChanged(@Nullable List<Country> countries) {
                mAdapter.setCountries(countries);

            }
        });
    }

    @Override
    public void onDisplayResult(List<Country> country) {
        Log.d(TAG, "onDisplayResult: " + country);
        db.countryDao().insertAll(country);

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
        mAdapter.updateCountry(position, country);
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
        if (mBinding.shimmerViewContainer.isShimmerStarted()) {
            mBinding.shimmerViewContainer.stopShimmer();
            mBinding.shimmerViewContainer.setVisibility(View.GONE);
        }
    }
}