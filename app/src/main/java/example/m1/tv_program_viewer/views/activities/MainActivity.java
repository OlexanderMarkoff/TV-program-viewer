package example.m1.tv_program_viewer.views.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import example.m1.tv_program_viewer.R;
import example.m1.tv_program_viewer.views.adapters.TvViewerPagerAdapter;
import example.m1.tv_program_viewer.views.fragments.TabFragment;

import static example.m1.tv_program_viewer.Constants.FRAGMENT_TITLE_KEY;

/**
 * Created by M1 on 09.11.2016.
 */

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;

    private TabLayout tabs;

    private TvViewerPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        setUI();
        setViewPager();
    }

    private void initUI() {
    pager = (ViewPager) findViewById(R.id.pager);
        tabs = (TabLayout) findViewById(R.id.sliding_tabs);
    }

    private void setUI() {}
    private void setViewPager() {
        final List<Fragment> pages = new ArrayList<>();

        Fragment fragment;
        //FIXME: test data
        for (int i = 0; i < 5; i++) {
            Bundle args = new Bundle();
            args.putString(FRAGMENT_TITLE_KEY, "tab " + i);
            fragment = new TabFragment();
            fragment.setArguments(args);
            pages.add(fragment);
        }

        tabs.setupWithViewPager(pager);
        if (pagerAdapter == null) {
            pagerAdapter = new TvViewerPagerAdapter(getFragmentManager(), pages);
        } else {
            pagerAdapter.updatePages(pages);
        }
        pager.setOffscreenPageLimit(0);
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
}
