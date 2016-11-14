package example.m1.tv_program_viewer.view.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import example.m1.tv_program_viewer.R;
import example.m1.tv_program_viewer.model.data.Channel;
import example.m1.tv_program_viewer.presenter.ChannelsPresenter;
import example.m1.tv_program_viewer.presenter.TvViewerPresenterFavoritable;
import example.m1.tv_program_viewer.view.TvViewerViewFavoritable;
import example.m1.tv_program_viewer.view.adapters.TvViewerPagerAdapter;
import example.m1.tv_program_viewer.view.fragments.TabFragment;

import static android.provider.BaseColumns._ID;
import static example.m1.tv_program_viewer.Constants.FRAGMENT_CHANNEL_ID_KEY;
import static example.m1.tv_program_viewer.Constants.FRAGMENT_TITLE_KEY;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.CHANNEL_COLUMN_NAME_NAME;

/**
 * Created by M1 on 09.11.2016.
 */

public class MainActivity extends AppCompatActivity implements TvViewerViewFavoritable<Channel>, View.OnClickListener,  NavigationView.OnNavigationItemSelectedListener {

    private Button btnFavorite;

    private ViewPager pager;

    private TabLayout tabs;

    private TvViewerPagerAdapter pagerAdapter;

    private TvViewerPresenterFavoritable presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDrawer();
        btnFavorite = (Button)findViewById(R.id.add_remove_favorites);
        btnFavorite.setOnClickListener(this);

        pager = (ViewPager) findViewById(R.id.pager);
        tabs = (TabLayout) findViewById(R.id.sliding_tabs);

        presenter = new ChannelsPresenter(this);
        presenter.loadData();

    }

    private void initDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawer,         /* DrawerLayout object */
                toolbar,  /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open, R.string.navigation_drawer_close /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                drawerView.bringToFront();
                drawerView.requestLayout();
                invalidateOptionsMenu();
            }
        };

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_category) {
            // Handle the camera action
        } else if (id == R.id.nav_channels_list) {

        } else if (id == R.id.nav_favorites) {

        } else if (id == R.id.nav_programs_list) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





    private void setViewPager(Cursor dataList) {
        final List<Fragment> pages = new ArrayList<>();

        Fragment fragment;

        if (dataList.moveToFirst()) {
            while (!dataList.isAfterLast()) {
                Bundle args = new Bundle();
                args.putString(FRAGMENT_TITLE_KEY, dataList.getString(dataList.getColumnIndex(CHANNEL_COLUMN_NAME_NAME)));
                args.putString(FRAGMENT_CHANNEL_ID_KEY, dataList.getString(dataList.getColumnIndex(_ID)));
                fragment = new TabFragment();
                fragment.setArguments(args);
                pages.add(fragment);
                dataList.moveToNext();
            }
        }

        tabs.setupWithViewPager(pager);
        if (pagerAdapter == null) {
            pagerAdapter = new TvViewerPagerAdapter(getSupportFragmentManager(), pages);
        } else {
            pagerAdapter.updatePages(pages);
        }
        pager.setOffscreenPageLimit(2);
        pager.setAdapter(pagerAdapter);
        presenter.onTabChanged(tabs.getSelectedTabPosition());
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tabs.getSelectedTabPosition(), true);
                presenter.onTabChanged(tabs.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onStop();
    }

    @Override
    public void showData(Cursor dataList) {
        setViewPager(dataList);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void clearData() {
    }

    @Override
    public void setFavoriteButtonText(String text) {
        btnFavorite.setText(text);
    }

    @Override
    public void onClick(View view) {
        presenter.onFavoriteClicked(pager.getCurrentItem());
    }
}
