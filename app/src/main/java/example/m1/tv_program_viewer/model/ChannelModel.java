package example.m1.tv_program_viewer.model;


import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import example.m1.tv_program_viewer.TvViewerApp;
import example.m1.tv_program_viewer.model.api.ApiClient;
import example.m1.tv_program_viewer.model.data.Channel;
import example.m1.tv_program_viewer.model.db.ContractClass;
import example.m1.tv_program_viewer.presenter.DataReadyListener;

import static android.provider.BaseColumns._ID;
import static example.m1.tv_program_viewer.TvViewerApp.getAppContext;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.CHANNEL_COLUMN_NAME_CATEGORY_ID;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.CHANNEL_COLUMN_NAME_IS_FAVORITE;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.CHANNEL_COLUMN_NAME_NAME;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.CHANNEL_COLUMN_NAME_PICTURE;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.CHANNEL_COLUMN_NAME_URL;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.CHANNEL_SCHEME;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.PATH_CHANNEL_IS_FAVORITE;

/**
 * Created by M1 on 09.11.2016.
 */

public class ChannelModel extends TvViewerBaseModel<Channel> implements TvViewerModelFavoritable {

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
        return ContractClass.ChannelContract.CHANNEL_CONTENT_URI;
    }

    @Override
    protected ContentValues objectToContentValues(Channel object) {
        ContentValues cv = new ContentValues();
        cv.put(_ID, object.getChannelId());
        cv.put(CHANNEL_COLUMN_NAME_NAME, object.getName());
        cv.put(CHANNEL_COLUMN_NAME_URL, object.getUrl());
        cv.put(CHANNEL_COLUMN_NAME_PICTURE, object.getPicture());
        cv.put(CHANNEL_COLUMN_NAME_CATEGORY_ID, object.getCategoryId());
        return cv;
    }

    @Override
    public Cursor updateFavorites(String channelId, long isFavorite) {
        ContentValues cv = new ContentValues();
        cv.put(CHANNEL_COLUMN_NAME_IS_FAVORITE, isFavorite);
        TvViewerApp.getAppContext().getContentResolver().update(Uri.parse(CHANNEL_SCHEME + ContractClass.AUTHORITY + PATH_CHANNEL_IS_FAVORITE +channelId ),
                cv, null, null);
        return getAppContext()
                        .getContentResolver()
                        .query(getUri(), null, null, null, null);
    }
}
