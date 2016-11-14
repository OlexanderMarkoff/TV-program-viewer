package example.m1.tv_program_viewer.presenter;

import android.database.Cursor;

import example.m1.tv_program_viewer.model.ProgramsModel;
import example.m1.tv_program_viewer.model.TvViewerModel;
import example.m1.tv_program_viewer.view.TvViewerView;

/**
 * Created by M1 on 13.11.2016.
 */

public class ProgramsPresenter extends BaseTvViewerPresenter<TvViewerView, TvViewerModel> {


    public ProgramsPresenter(TvViewerView view) {
        super(view);
    }

    @Override
    protected void initModel() {
        model = new ProgramsModel(this);
    }

    @Override
    public void dataLoaded(Cursor data) {
        view.showData(data);
    }

    @Override
    public void loadingError(String error) {
        view.showError(error);
    }

    //first agr channelId
    @Override
    public void loadData(String... params) {
        model.getData(params);
    }

    @Override
    public void onStop() {

    }
}
