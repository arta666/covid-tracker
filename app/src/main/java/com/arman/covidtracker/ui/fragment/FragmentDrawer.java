package com.arman.covidtracker.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.arman.covidtracker.R;
import com.arman.covidtracker.databinding.FragmentNavigationDrawerBinding;
import com.arman.covidtracker.model.NavDrawerItem;
import com.arman.covidtracker.presenter.BasePresenter;
import com.arman.covidtracker.ui.adapter.NavigationDrawerAdapter;
import com.arman.covidtracker.ui.base.BaseFragment;
import com.arman.covidtracker.utils.RecyclerTouchListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FragmentDrawer extends BaseFragment {

    private static String TAG = FragmentDrawer.class.getSimpleName();

    FragmentNavigationDrawerBinding mBinding;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    private static String[] titles = null;
    private FragmentDrawerListener drawerListener;

    public FragmentDrawer() {

    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    public static List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();

        // preparing navigation drawer items
        for (String title : titles) {
            NavDrawerItem navItem = new NavDrawerItem();
            navItem.setTitle(title);
            data.add(navItem);
        }
        return data;
    }

    @NonNull
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // drawer labels
        titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentNavigationDrawerBinding.inflate(inflater,container, false);

        View layout = mBinding.getRoot();

        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(getActivity(), getData());
        mBinding.drawerList.setAdapter(adapter);
        mBinding.drawerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.drawerList.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                mBinding.drawerList,
                new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                drawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(containerView);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        return layout;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }
}