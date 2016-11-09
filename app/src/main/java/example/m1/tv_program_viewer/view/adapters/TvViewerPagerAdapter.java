package example.m1.tv_program_viewer.view.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import example.m1.tv_program_viewer.model.data.Channel;

import static example.m1.tv_program_viewer.Constants.FRAGMENT_TITLE_KEY;

/**
 * Created by M1 on 09.11.2016.
 */

public class TvViewerPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> pages = new ArrayList<>();

    public TvViewerPagerAdapter(FragmentManager fm, List<Fragment> pages) {
        super(fm);
        this.pages.addAll(pages);
    }

    public void updatePages(List<Fragment> pages) {
        this.pages.clear();
        this.pages.addAll(pages);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return pages.get(position);
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Bundle bundle = pages.get(position).getArguments();

        if (bundle != null && bundle.getParcelable(FRAGMENT_TITLE_KEY) != null) {
            Channel channel = bundle.getParcelable(FRAGMENT_TITLE_KEY);
            return channel.getName();
        } else {
            //add fragment title as bundle into fragment
            return "No title";
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
