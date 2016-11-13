package example.m1.tv_program_viewer.view.activities;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import example.m1.tv_program_viewer.R;
import example.m1.tv_program_viewer.model.data.Channel;
import example.m1.tv_program_viewer.presenter.ChannelsPresenter;
import example.m1.tv_program_viewer.presenter.TvViewerPresenter;
import example.m1.tv_program_viewer.view.TvViewerView;
import example.m1.tv_program_viewer.view.adapters.TvViewerPagerAdapter;
import example.m1.tv_program_viewer.view.fragments.TabFragment;

import static android.provider.BaseColumns._ID;
import static example.m1.tv_program_viewer.Constants.FRAGMENT_CHANNEL_ID_KEY;
import static example.m1.tv_program_viewer.Constants.FRAGMENT_TITLE_KEY;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.COLUMN_NAME_NAME;

/**
 * Created by M1 on 09.11.2016.
 */

public class MainActivity extends AppCompatActivity implements TvViewerView<Channel> {

    private ViewPager pager;

    private TabLayout tabs;

    private TvViewerPagerAdapter pagerAdapter;

    private TvViewerPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        setUI();
        presenter = new ChannelsPresenter(this);
        presenter.loadData();
    }

    private void initUI() {
        pager = (ViewPager) findViewById(R.id.pager);
        tabs = (TabLayout) findViewById(R.id.sliding_tabs);
    }

    private void setUI() {
    }


    private void setViewPager(Cursor dataList) {
        final List<Fragment> pages = new ArrayList<>();

        Fragment fragment;

        if (dataList.moveToFirst()) {
            while (!dataList.isAfterLast()) {
                Bundle args = new Bundle();
                args.putString(FRAGMENT_TITLE_KEY, dataList.getString(dataList.getColumnIndex(COLUMN_NAME_NAME)));
                args.putString(FRAGMENT_CHANNEL_ID_KEY, dataList.getString(dataList.getColumnIndex(_ID)));
                fragment = new TabFragment();
                fragment.setArguments(args);
                pages.add(fragment);
                dataList.moveToNext();
            }
        }
        dataList.close();

        tabs.setupWithViewPager(pager);
        if (pagerAdapter == null) {
            pagerAdapter = new TvViewerPagerAdapter(getFragmentManager(), pages);
        } else {
            pagerAdapter.updatePages(pages);
        }
        pager.setOffscreenPageLimit(2);
        pager.setAdapter(pagerAdapter);

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tabs.getSelectedTabPosition(), true);

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
    public void showData(Cursor dataList) {
        setViewPager(dataList);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void clearData() {

    }
}
