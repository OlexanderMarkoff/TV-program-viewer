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
import example.m1.tv_program_viewer.model.data.Program;
import example.m1.tv_program_viewer.model.db.ContractClass;
import example.m1.tv_program_viewer.presenter.ProgramsPresenter;
import example.m1.tv_program_viewer.presenter.TvViewerPresenter;
import example.m1.tv_program_viewer.view.TvViewerView;
import example.m1.tv_program_viewer.view.adapters.ProgramsCursorAdapter;

import static example.m1.tv_program_viewer.Constants.FRAGMENT_CHANNEL_ID_KEY;
import static example.m1.tv_program_viewer.Constants.NOW_DATE;
import static example.m1.tv_program_viewer.model.db.ContractClass.ProgramsContract.PATH_PROGRAMS_ID;
import static example.m1.tv_program_viewer.model.db.ContractClass.ProgramsContract.PROGRAMS_DEFAULT_PROJECTION;
import static example.m1.tv_program_viewer.model.db.ContractClass.ProgramsContract.PROGRAMS_SCHEME;

/**
 * Created by M1 on 09.11.2016.
 */

public class TabFragment extends Fragment implements TvViewerView<Program>, LoaderManager.LoaderCallbacks<Cursor> {

    private String channelId;

    private ListView listView;

    private CursorAdapter adapter;

    private TvViewerPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_programs_list, container, false);
        listView = (ListView) rootView.findViewById(R.id.list_program);
        presenter = new ProgramsPresenter(this);
        channelId = getArguments().getString(FRAGMENT_CHANNEL_ID_KEY);
        //first agr channelId
        presenter.loadData(channelId);
        return rootView;
    }

    @Override
    public void showData(Cursor dataList) {
        adapter = new ProgramsCursorAdapter(getActivity(), dataList, 0);
        listView.setAdapter(adapter);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void clearData() {

    }

    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
                getActivity(),
                Uri.parse(PROGRAMS_SCHEME + ContractClass.AUTHORITY + PATH_PROGRAMS_ID + channelId + "/" + NOW_DATE),
                PROGRAMS_DEFAULT_PROJECTION,
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
}
