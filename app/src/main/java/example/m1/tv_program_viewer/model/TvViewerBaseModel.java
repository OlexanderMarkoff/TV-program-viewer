package example.m1.tv_program_viewer.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.List;

import example.m1.tv_program_viewer.TvViewerApp;
import example.m1.tv_program_viewer.presenter.DataReadyListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by M1 on 13.11.2016.
 */

public abstract class TvViewerBaseModel<T> implements Callback<List<T>>, TvViewerModel {

    protected DataReadyListener dataReadyListener;

    public TvViewerBaseModel(DataReadyListener dataReadyListener) {
        this.dataReadyListener = dataReadyListener;
    }

    @Override
    public void getData(String... params) {

        if (TvViewerApp.isNetworkAvailable()) {
            getFromNet(params);
        } else {

            Cursor cursor = TvViewerApp
                    .getAppContext()
                    .getContentResolver()
                    .query(getUri(), null, null, null, null);

            dataReadyListener.dataLoaded(cursor);
        }
    }

    protected void saveDataToDB(List<T> data) {
        for (T object : data) {
            TvViewerApp.getAppContext().getContentResolver().insert(getUri(), objectToContentValues(object));
        }

        Cursor cursor = TvViewerApp
                .getAppContext()
                .getContentResolver()
                .query(getUri(), null, null, null, null);

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
