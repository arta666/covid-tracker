package com.arman.covidtracker.ui.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arman.covidtracker.R;
import com.arman.covidtracker.contract.NewsContract;
import com.arman.covidtracker.databinding.FragmentNewsBinding;
import com.arman.covidtracker.db.AppDb;
import com.arman.covidtracker.di.component.FragmentComponent;
import com.arman.covidtracker.model.Country;
import com.arman.covidtracker.model.News;
import com.arman.covidtracker.presenter.NewsPresenter;
import com.arman.covidtracker.repository.NewsRemoteRepository;
import com.arman.covidtracker.ui.adapter.CountryStatusAdapter;
import com.arman.covidtracker.ui.adapter.NewsAdapter;
import com.arman.covidtracker.ui.base.BaseFragment;
import com.arman.covidtracker.utils.RecyclerTouchListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends BaseFragment<NewsPresenter> implements NewsContract.View {

    private static final String TAG = NewsFragment.class.getSimpleName();

    private static final int DEFAULT_LIMIT_NEWS_ITEM = 20;

    FragmentNewsBinding mBinding;

    @Inject
    AppDb db;

    @Inject
    NewsRemoteRepository repository;

    NewsAdapter mAdapter;

    public NewsFragment() {
        // Required empty public constructor
    }

    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @NonNull
    @Override
    protected NewsPresenter createPresenter() {
        return new NewsPresenter(this,repository, AndroidSchedulers.mainThread());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentNewsBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();
        initView();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            presenter.fetchTrendingNews(DEFAULT_LIMIT_NEWS_ITEM);

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

    private void initView() {
        mAdapter = new NewsAdapter(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.recycler.setLayoutManager(layoutManager);
        mBinding.recycler.setHasFixedSize(true);
        mBinding.recycler.setAdapter(mAdapter);

        mBinding.recycler.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mBinding.recycler, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                News news = mAdapter.getNewsList().get(position);
                if (news !=null){
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(DetailFragment.ARG_NEWS,news);
                    bundle.putInt(DetailFragment.ARG_POSITION,position);
                    Navigation.findNavController(requireActivity(),R.id.main_container)
                            .navigate(R.id.action_newsScreen_to_detailScreen,bundle);
                }

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void setupViewModel() {

        db.newsDao().getNewsLiveData().observe(getViewLifecycleOwner(), new Observer<List<News>>() {
            @Override
            public void onChanged(List<News> news) {
                mAdapter.setNewsList(news);
            }
        });
    }

    @Override
    public void onDisplayResult(List<News> news) {
       db.newsDao().insertAll(news);
    }

    @Override
    public void onDisplayEmptyScreen() {
        Log.d(TAG, "onDisplayEmptyScreen: ");

    }

    @Override
    public void onDisplayFailed(String message) {
        Log.d(TAG, "onDisplayFailed: ");

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