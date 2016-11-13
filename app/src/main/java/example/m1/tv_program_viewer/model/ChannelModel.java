package example.m1.tv_program_viewer.model;


import android.content.ContentValues;
import android.net.Uri;

import example.m1.tv_program_viewer.model.api.ApiClient;
import example.m1.tv_program_viewer.model.data.Channel;
import example.m1.tv_program_viewer.model.db.ContractClass;
import example.m1.tv_program_viewer.presenter.DataReadyListener;

import static android.provider.BaseColumns._ID;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.COLUMN_NAME_CATEGORY_ID;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.COLUMN_NAME_NAME;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.COLUMN_NAME_PICTURE;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.COLUMN_NAME_URL;

/**
 * Created by M1 on 09.11.2016.
 */

public class ChannelModel extends TvViewerBaseModel<Channel> {

    public ChannelModel(DataReadyListener dataReadyListener) {
        super(dataReadyListener);
    }

    @Override
    protected void getFromNet(String... params) {
        ApiClient.loadChannels(this);
    }

    @Override
    protected void handleError(Throwable t) {
        dataReadyListener.loadingError(t.getMessage());
    }

    @Override
    protected Uri getUri(String... params) {
        return ContractClass.ChannelContract.CONTENT_URI;
    }

    @Override
    protected ContentValues objectToContentValues(Channel object) {
        ContentValues cv = new ContentValues();
        cv.put(_ID, object.getChannelId());
        cv.put(COLUMN_NAME_NAME, object.getName());
        cv.put(COLUMN_NAME_URL, object.getUrl());
        cv.put(COLUMN_NAME_PICTURE, object.getPicture());
        cv.put(COLUMN_NAME_CATEGORY_ID, object.getCategoryId());
        return cv;
    }

}
