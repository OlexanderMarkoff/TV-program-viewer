package example.m1.tv_program_viewer.presenter;

import android.database.Cursor;

import example.m1.tv_program_viewer.model.ChannelModel;
import example.m1.tv_program_viewer.model.TvViewerModel;
import example.m1.tv_program_viewer.view.TvViewerView;

/**
 * Created by M1 on 09.11.2016.
 */

public class ChannelsPresenter implements TvViewerPresenter, DataReadyListener {


    private TvViewerModel model = new ChannelModel(this);

    private TvViewerView view;

    public ChannelsPresenter(TvViewerView view) {
        this.view = view;
    }

    @Override
    public void loadData() {
        model.getData();
    }

    @Override
    public void dataLoaded(Cursor data) {
        view.showData(data);
    }

    @Override
    public void loadingError(String error) {
        view.showError(error);
    }
}
