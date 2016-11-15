package example.m1.tv_program_viewer.model;

import android.net.Uri;

import example.m1.tv_program_viewer.TvViewerApp;
import example.m1.tv_program_viewer.model.db.ContractClass;
import example.m1.tv_program_viewer.presenter.DataReadyListener;

import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.CHANNEL_SCHEME;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.PATH_CHANNEL_IS_FAVORITE;

/**
 * Created by M1 on 15.11.2016.
 */

public class FavoritesModel implements TvViewerModel {

    protected DataReadyListener dataReadyListener;

    public FavoritesModel(DataReadyListener dataReadyListener) {
        this.dataReadyListener = dataReadyListener;
    }

    @Override
    public void getData(String... params) {
        dataReadyListener.dataLoaded(
                TvViewerApp.getAppContext()
                        .getContentResolver()
                        .query(Uri.parse(CHANNEL_SCHEME + ContractClass.AUTHORITY + PATH_CHANNEL_IS_FAVORITE + "1"),
                                null, null, null, null));

    }

    @Override
    public void updateData(String... params) {

    }
}
