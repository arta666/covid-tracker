package com.arman.covidtracker.ui.fragment;

import android.app.SharedElementCallback;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arman.covidtracker.R;
import com.arman.covidtracker.databinding.FragmentDetailBinding;
import com.arman.covidtracker.model.Country;
import com.arman.covidtracker.model.News;
import com.arman.covidtracker.utils.ImageLoader;

import androidx.navigation.NavArgs;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    private static final String TAG = DetailFragment.class.getSimpleName();

    public static final String ARG_NEWS = "news";

    public static final String ARG_POSITION = "position";

    private News mNews;

    private int position;

    FragmentDetailBinding mBinding;

    public DetailFragment() {
        // Required empty public constructor
    }


    public static DetailFragment newInstance(News news) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NEWS, news);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Transition Enter = TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move);
            Transition Exit = TransitionInflater.from(getContext()).inflateTransition(android.R.transition.explode);
            setEnterTransition(Enter);
            setExitTransition(Exit);
        }
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNews = getArguments().getParcelable(ARG_NEWS);
            position = getArguments().getInt(ARG_POSITION);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentDetailBinding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated: " + position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBinding.thumbnail.setTransitionName("thumb" + position);
        }
        mBinding.articleTitle.setText(mNews.getTitle());
        mBinding.articleBody.setText(mNews.getContent());

        ImageLoader.load(mBinding.thumbnail, mNews.getUrlToImage());

        mBinding.shareFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFabClick();
            }
        });
    }

    private void handleFabClick() {

        if (TextUtils.isEmpty(mNews.getTitle()) || TextUtils.isEmpty(mNews.getContent())) {
            return;
        }
        startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(Objects.requireNonNull(getActivity()))
                .setType("text/plain")
                .setText(mNews.getTitle() + "/n" + mNews.getPublishedAt() + "/n" + mNews.getContent() + "/n" + mNews.getUrl())
                .getIntent(), getString(R.string.action_share)));

    }
}