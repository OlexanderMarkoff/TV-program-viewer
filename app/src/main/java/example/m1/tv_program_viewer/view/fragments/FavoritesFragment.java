package example.m1.tv_program_viewer.view.fragments;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;

import example.m1.tv_program_viewer.R;
import example.m1.tv_program_viewer.model.data.Channel;
import example.m1.tv_program_viewer.model.db.ContractClass;
import example.m1.tv_program_viewer.presenter.FavoritesPresenter;
import example.m1.tv_program_viewer.presenter.TvViewerPresenter;
import example.m1.tv_program_viewer.view.TvViewerView;
import example.m1.tv_program_viewer.view.adapters.FavoritesCursorAdapter;

import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.CHANNEL_DEFAULT_PROJECTION;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.CHANNEL_SCHEME;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.PATH_CHANNEL_IS_FAVORITE;

/**
 * Created by M1 on 15.11.2016.
 */

public class FavoritesFragment extends Fragment implements TvViewerView<Channel>, LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = TabsMainFragment.class.getName();

    private String channelId;

    private ListView listView;

    private CursorAdapter adapter;

    private TvViewerPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        listView = (ListView) rootView.findViewById(R.id.list_program);
        presenter = new FavoritesPresenter(this);
        presenter.loadData();
        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                Uri.parse(CHANNEL_SCHEME + ContractClass.AUTHORITY + PATH_CHANNEL_IS_FAVORITE + "1"),
                CHANNEL_DEFAULT_PROJECTION,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        adapter.swapCursor(null);
    }


    @Override
    public void showData(Cursor dataList) {
        adapter = new FavoritesCursorAdapter(getActivity(), dataList, 0);
        listView.setAdapter(adapter);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void clearData() {

    }
}
