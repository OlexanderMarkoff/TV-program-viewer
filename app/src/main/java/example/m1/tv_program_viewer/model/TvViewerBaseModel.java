package example.m1.tv_program_viewer.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.List;

import example.m1.tv_program_viewer.R;
import example.m1.tv_program_viewer.TvViewerApp;
import example.m1.tv_program_viewer.presenter.DataReadyListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static example.m1.tv_program_viewer.TvViewerApp.getAppContext;

/**
 * Created by M1 on 13.11.2016.
 */

public abstract class TvViewerBaseModel<T> implements Callback<List<T>>, TvViewerModel {

    protected DataReadyListener dataReadyListener;

    protected String[] tempParams;

    public TvViewerBaseModel(DataReadyListener dataReadyListener) {
        this.dataReadyListener = dataReadyListener;
    }

    @Override
    public void getData(String... params) {
        Cursor cursor =
                getAppContext()
                        .getContentResolver()
                        .query(getUri(params), null, null, null, null);

        if (cursor.getCount() != 0) {
            dataReadyListener.dataLoaded(cursor);
        } else {
            updateData(params);
        }
    }

    @Override
    public void updateData(String... params) {
        if (TvViewerApp.isNetworkAvailable()) {
            getFromNet(params);
        } else {
            dataReadyListener.loadingError(getAppContext().getString(R.string.title_network_error));
        }
    }

    protected void saveDataToDB(List<T> data) {
        for (T object : data) {
            Uri uri = TvViewerApp.getAppContext().getContentResolver().insert(getUri(), objectToContentValues(object));
        }

        Cursor cursor =
                getAppContext()
                        .getContentResolver()
                        .query(getUri(tempParams), null, null, null, null);

        dataReadyListener.dataLoaded(cursor);
    }


    protected abstract void getFromNet(String... params);

    protected abstract void handleError(Throwable t);

    protected abstract Uri getUri(String... params);

    protected abstract ContentValues objectToContentValues(T object);

    @Override
    public void onFailure(Call<List<T>> call, Throwable t) {
        handleError(t);
    }

    @Override
    public void onResponse(Call<List<T>> call, Response<List<T>> response) {
        saveDataToDB(response.body());
    }

}
