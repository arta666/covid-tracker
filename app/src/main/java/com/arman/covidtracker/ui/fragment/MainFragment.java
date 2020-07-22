package com.arman.covidtracker.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arman.covidtracker.R;
import com.arman.covidtracker.contract.MainContract;
import com.arman.covidtracker.databinding.FragmentMainBinding;
import com.arman.covidtracker.di.component.FragmentComponent;
import com.arman.covidtracker.model.Global;
import com.arman.covidtracker.model.Summary;
import com.arman.covidtracker.presenter.MainPresenter;
import com.arman.covidtracker.repository.MainRepository;
import com.arman.covidtracker.ui.base.BaseFragment;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.MPPointF;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends BaseFragment<MainPresenter> implements MainContract.View {

    private static final String TAG = MainFragment.class.getSimpleName();

    FragmentMainBinding mBinding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @Inject
    MainRepository repository;


    MutableLiveData<Global> mutableLiveData;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this, repository, AndroidSchedulers.mainThread());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentMainBinding.inflate(inflater, container, false);
        if (savedInstanceState == null) {
            setUpChart();

        }
        return mBinding.getRoot();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void setUpChart() {
        mBinding.chartPie.getDescription().setEnabled(false);
        mBinding.chartPie.setHoleRadius(45f);
        mBinding.chartPie.setTransparentCircleRadius(50f);
        mBinding.chartPie.animateY(140, Easing.EaseInOutQuad);
        mBinding.chartPie.animateXY(2000, 2000);

        mBinding.chartPie.getDescription().setEnabled(false);

        mBinding.chartPie.setEntryLabelColor(Color.WHITE);
        mBinding.chartPie.setEntryLabelTextSize(12f);


        Legend legend = mBinding.chartPie.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);

    }

    private void setUpHorizontalChart(Global global) {
        BarData barData = new BarData(getData(global));
        barData.setValueTextSize(14f);
        mBinding.chartLine.setData(barData);
        Description description = new Description();
        description.setText("");
        mBinding.chartLine.setDescription(description);
        mBinding.chartLine.animateXY(2000, 2000);
        mBinding.chartLine.invalidate();
    }


    private ArrayList<IBarDataSet> getData(Global global) {

        ArrayList<BarEntry> confirmedSet = new ArrayList<>();
        BarEntry confirmedEntry = new BarEntry(1f, (float) global.getNewConfirmed());
        confirmedSet.add(confirmedEntry);

        ArrayList<BarEntry> deathSet = new ArrayList<>();
        BarEntry deathEntry = new BarEntry(2f, (float) global.getNewDeaths());
        deathSet.add(deathEntry);


        BarDataSet confirmedDataSet = new BarDataSet(confirmedSet, "New Confirmed");
        confirmedDataSet.setColor(ContextCompat.getColor(getContext(), R.color.chart_yellow));

        BarDataSet deathsDataSet = new BarDataSet(deathSet, "New Deaths");
        deathsDataSet.setColor(ContextCompat.getColor(getContext(), R.color.chart_red));

        ArrayList<IBarDataSet> barDataSets = new ArrayList<>();

        barDataSets.add(confirmedDataSet);
        barDataSets.add(deathsDataSet);

        return barDataSets;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            presenter.fetchGlobal();
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


    private void createPieChartOfTotal(Global global) {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((float) global.getTotalRecovered(), "Total Recovered"));
        entries.add(new PieEntry((float) global.getTotalConfirmed(), "Total Confirmed"));
        entries.add(new PieEntry((float) global.getTotalDeaths(), "Total Deaths"));
        PieDataSet dataSet = new PieDataSet(entries, "Word Covid-19");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        int[] colors = {R.color.chart_green, R.color.chart_yellow, R.color.chart_red};

        dataSet.setColors(colors, getContext());


        PieData data = new PieData(dataSet);
        data.setValueTextSize(14);
        mBinding.chartPie.setData(data);
        mBinding.chartPie.invalidate(); // refresh
    }


    @Override
    public void onDisplayGlobal(Global global) {
        if (mutableLiveData == null){
            mutableLiveData = new MutableLiveData<>();
            mutableLiveData.setValue(global);
        }
        setupViewModel();
    }

    private void setupViewModel() {
        mutableLiveData.observe(this, new Observer<Global>() {
            @Override
            public void onChanged(Global global) {
                createPieChartOfTotal(global);
                setUpHorizontalChart(global);
            }
        });
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
    public void showProgress() {
        Log.d(TAG, "showProgress: ");
    }

    @Override
    public void hideProgress() {
        Log.d(TAG, "hideProgress: ");

    }
}