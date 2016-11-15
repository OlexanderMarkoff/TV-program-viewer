package example.m1.tv_program_viewer.presenter;

import android.database.Cursor;

import example.m1.tv_program_viewer.model.FavoritesModel;
import example.m1.tv_program_viewer.view.TvViewerView;

/**
 * Created by M1 on 15.11.2016.
 */

public class FavoritesPresenter extends BaseTvViewerPresenter implements TvViewerPresenter {


    public FavoritesPresenter(TvViewerView view) {
        super(view);
    }

    @Override
    protected void initModel() {
        model = new FavoritesModel(this);
    }

    @Override
    public void dataLoaded(Cursor data) {
        view.showData(data);
    }

    @Override
    public void loadingError(String error) {
        view.showError(error);
    }

    @Override
    public void loadData(String... params) {
        model.getData();
    }

    @Override
    public void onStop() {
    }
}
