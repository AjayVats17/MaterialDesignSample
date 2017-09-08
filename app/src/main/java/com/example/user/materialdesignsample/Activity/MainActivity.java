package com.example.user.materialdesignsample.Activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.materialdesignsample.Fragment.CheeseListFragment;
import com.example.user.materialdesignsample.Fragment.CheeseListNewFragment;
import com.example.user.materialdesignsample.Fragment.CheeseViewFragment;
import com.example.user.materialdesignsample.Fragment.ModelBottomsheetFragment;
import com.example.user.materialdesignsample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1><font color="orange">MainActivity</font></h1>
 * Activity class for loading fragment with viewPager and tabLayout (CheeseListFragment,CheeseViewFragment,CheeseListNewFragment).
 *
 * @author Shubham Chauhan
 */
public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private CoordinatorLayout mCoordinatorLayout;
    private String TAG=MainActivity.class.getSimpleName();
    private BottomSheetBehavior mBottomSheetBehavior;
    private int[] myBtmDataset = {R.drawable.cheese_1, R.drawable.cheese_2, R.drawable.cheese_3, R.drawable.cheese_4,R.drawable.cheese_5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    /**
     * Method is used to initializing Views
     */
    private void initUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_content);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        View bottomSheet = findViewById(R.id.bottom_sheet1);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomSnackbar();
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * Method is used to setting up View Pager
     */
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new CheeseListFragment(), "Category 1");
        adapter.addFragment(new CheeseViewFragment(), "Category 2");
        adapter.addFragment(new CheeseListNewFragment(), "Category 3");
        viewPager.setAdapter(adapter);
    }

    /**
     * Method is used to setting up Drawer Content
     */
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    menuItem.setChecked(false);
                // Handle navigation view item clicks here.
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        menuItem.setChecked(true);
                        break;
                    case R.id.nav_messages:
                        menuItem.setChecked(true);
                        break;
                    case R.id.nav_friends:
                        menuItem.setChecked(true);
                        break;
                    case R.id.nav_discussion:
                        menuItem.setChecked(true);
                        break;
                    case R.id.persistent_bottomsheet: {
                        if (mBottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        } else {
                            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        }
                        break;
                    }
                    case R.id.model_bottomsheet: {
                        ModelBottomsheetFragment modelBottomsheetFragment = ModelBottomsheetFragment.newInstance(myBtmDataset);
                        modelBottomsheetFragment.show(getSupportFragmentManager(), "Model BottomSheet");
                        break;
                    }
                    default:
                        Log.e(TAG, getString(R.string.wrong_case_selection));
                        break;
                }
                //close navigation drawer
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    /**
     * this method simply Shows a Custom Snackbar
     */
    private void showCustomSnackbar() {
        Snackbar snackbar = Snackbar.make(mCoordinatorLayout, "", Snackbar.LENGTH_LONG);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        TextView textView = layout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setVisibility(View.INVISIBLE);

        View snackView = View.inflate(this, R.layout.my_custom_snackbar, null);
        ImageView imageView = snackView.findViewById(R.id.iv_background);
        Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        imageView.setImageBitmap(image);

        TextView textViewTop = snackView.findViewById(R.id.tv_snackbar);
        textViewTop.setText(getString(R.string.have_a_bite));
        textViewTop.setTextColor(Color.BLACK);

        layout.addView(snackView, 0);
    /*    // Change the position of snackbar
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)layout.getLayoutParams();
        params.gravity = Gravity.TOP;
        layout.setLayoutParams(params);*/
        snackbar.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                menu.findItem(R.id.menu_night_mode_system).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_AUTO:
                menu.findItem(R.id.menu_night_mode_auto).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                menu.findItem(R.id.menu_night_mode_night).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_NO:
                menu.findItem(R.id.menu_night_mode_day).setChecked(true);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_night_mode_system:
                item.setChecked(true);
                setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case R.id.menu_night_mode_day:
                item.setChecked(true);
                setNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case R.id.menu_night_mode_night:
                item.setChecked(true);
                setNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case R.id.menu_night_mode_auto:
                item.setChecked(true);
                setNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * this method is used for set mode (day,night etc)
     */
    private void setNightMode(@AppCompatDelegate.NightMode int nightMode) {
        AppCompatDelegate.setDefaultNightMode(nightMode);
        if (Build.VERSION.SDK_INT >= 15) {
            recreate();
        }
    }

    private static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        Adapter(FragmentManager fm) {
            super(fm);
        }

        void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

}
