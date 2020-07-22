package com.arman.covidtracker.utils;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.arman.covidtracker.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.Locale;

public class CustomNavigation extends BottomNavigationView {

    private static final String TAG = CustomNavigation.class.getSimpleName();

    private FragmentManager mFragmentManager;
    private String selectedItemTag;
    private String firstFragmentTag;
    private boolean isOnFirstFragment;
    int firstFragmentGraphId;
    private SparseArray<String> graphIdToTagMap;
    private MutableLiveData<NavController> selectedNavController;

    public CustomNavigation(@NonNull Context context) {
        super(context);
        init();
    }

    public CustomNavigation(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomNavigation(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    public LiveData<NavController> setupWithNavController(List<Integer> navGraphIds, FragmentManager fragmentManager, final int containerId, Intent intent) {

        Log.d(TAG, "setupWithNavController: ");

        this.mFragmentManager = fragmentManager;
        this.firstFragmentGraphId = 0;
        this.graphIdToTagMap = new SparseArray<>();
        this.selectedNavController = new MutableLiveData<>();

        for (int index = 0; index < navGraphIds.size(); index++) {

            String fragmentTag = getFragmentTag(index);

            NavHostFragment navHostFragment = obtainNavHostFragment(fragmentManager, fragmentTag, navGraphIds.get(index), containerId);

            int graphId = navHostFragment.getNavController().getGraph().getId();

            if (index == 0) {
                firstFragmentGraphId = graphId;
            }

            graphIdToTagMap.put(graphId, fragmentTag);

            if (this.getSelectedItemId() == graphId) {
                selectedNavController.setValue(navHostFragment.getNavController());
                attachNavHostFragment(fragmentManager, navHostFragment, index == 0);
            } else {
                detachNavHostFragment(fragmentManager, navHostFragment);
            }
        }

        selectedItemTag = graphIdToTagMap.get(this.getSelectedItemId());
        firstFragmentTag = graphIdToTagMap.get(firstFragmentGraphId);
        isOnFirstFragment = selectedItemTag.equals(firstFragmentTag);

        this.setOnNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (mFragmentManager.isStateSaved()) {
                    return false;

                } else {
                    String newlySelectedItemTag = graphIdToTagMap.get(item.getItemId());
                    Log.d(TAG, "onNavigationItemSelected: " + newlySelectedItemTag);
                    if (!selectedItemTag.equals(newlySelectedItemTag)) {
                        mFragmentManager.popBackStack(firstFragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        NavHostFragment selectedFragment = (NavHostFragment) mFragmentManager.findFragmentByTag(newlySelectedItemTag);

                        if (!firstFragmentTag.equals(newlySelectedItemTag)) {
                            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction()
                                    .setCustomAnimations(
                                            R.anim.nav_default_enter_anim,
                                            R.anim.nav_default_exit_anim,
                                            R.anim.nav_default_pop_enter_anim,
                                            R.anim.nav_default_pop_exit_anim)
                                    .attach(selectedFragment)
                                    .setPrimaryNavigationFragment(selectedFragment);
                            fragmentTransaction.addToBackStack(firstFragmentTag)
                                    .setReorderingAllowed(true).commit();

//                            detachAllOther(fragmentTransaction, firstFragmentTag, newlySelectedItemTag);
                        }
                        selectedItemTag = newlySelectedItemTag;
                        isOnFirstFragment = selectedItemTag.equals(firstFragmentTag);
                        selectedNavController.setValue(selectedFragment.getNavController());
                        return true;

                    } else {
                        return false;
                    }
                }
            }
        });
//        setupItemReselected(graphIdToTagMap, fragmentManager);

        setupDeepLinks(navGraphIds, fragmentManager, containerId, intent);

        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (!isOnFirstFragment && isOnBackStack(firstFragmentTag)) {
                    setSelectedItemId(firstFragmentGraphId);
                }

                NavController controller = selectedNavController.getValue();

                if (controller != null && controller.getCurrentDestination() == null) {
                    controller.navigate(controller.getGraph().getId());

                }

            }
        });


        return selectedNavController;
    }

    private void detachAllOther(FragmentTransaction fragmentTransaction, String firstFragmentTag, String newlySelectedItemTag) {
        for (int i = 0; i < graphIdToTagMap.size(); i++) {
            String fragmentTag = graphIdToTagMap.valueAt(i);
            if (!fragmentTag.equals(newlySelectedItemTag)) {
                Fragment fragment = mFragmentManager.findFragmentByTag(firstFragmentTag);
                if (fragment != null) {
                    Log.d(TAG, "detachAllOther: " + fragmentTag);
                    fragmentTransaction.detach(fragment);

                }
            }
        }

        fragmentTransaction.addToBackStack(firstFragmentTag)
                .setReorderingAllowed(true).commit();
    }


    private void setupDeepLinks(List<Integer> navGraphIds, FragmentManager manager, int containerId, Intent intent) {
        for (int index = 0; index < navGraphIds.size(); index++) {
            String fragmentTag = getFragmentTag(index);
            NavHostFragment navHostFragment = obtainNavHostFragment(manager, fragmentTag, navGraphIds.get(index), containerId);

            if (navHostFragment.getNavController().handleDeepLink(intent)
                    && getSelectedItemId() != navHostFragment.getNavController().getGraph().getId()) {
                setSelectedItemId(navHostFragment.getNavController().getGraph().getId());
            }

        }
    }

    private void setupItemReselected(final SparseArray<String> graphIdTagMap, final FragmentManager manager) {
        setOnNavigationItemReselectedListener(new OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                String newlySelectedItemTag = graphIdTagMap.get(item.getItemId());
                NavHostFragment selectedFragment = (NavHostFragment) manager.findFragmentByTag(newlySelectedItemTag);
                NavController navController;
                if (selectedFragment != null) {
                    navController = selectedFragment.getNavController();
                    navController.popBackStack(navController.getGraph().getStartDestination(), false);
                }
            }
        });
    }


    private void attachNavHostFragment(FragmentManager fragmentManager, NavHostFragment navHostFragment, boolean isPrimaryNavFragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.attach(navHostFragment);
        if (isPrimaryNavFragment) {
            transaction.setPrimaryNavigationFragment(navHostFragment);
        }
        transaction.commitNow();

    }


    private void detachNavHostFragment(FragmentManager fragmentManager, NavHostFragment navHostFragment) {
        fragmentManager.beginTransaction()
                .detach(navHostFragment)
                .commitNow();
    }


    private static NavHostFragment obtainNavHostFragment(FragmentManager manager, String fragmentTag, int navGraphId, int containerId) {

        NavHostFragment existFragment = (NavHostFragment) manager.findFragmentByTag(fragmentTag);
        if (existFragment != null) {
            return existFragment;
        }

        NavHostFragment navHostFragment = NavHostFragment.create(navGraphId);

        manager.beginTransaction()
                .add(containerId, navHostFragment, fragmentTag)
                .commitNow();
        return navHostFragment;
    }


    private boolean isOnBackStack(String backStackName) {
        int backStackCount = mFragmentManager.getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            if (mFragmentManager.getBackStackEntryAt(i).getName().equals(backStackName)) {
                return true;
            }
        }
        return false;
    }

    private String getFragmentTag(int index) {
        return String.format(Locale.getDefault(), "bottomNavigation#%d", index);
    }
}
