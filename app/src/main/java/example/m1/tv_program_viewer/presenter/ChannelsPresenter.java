package example.m1.tv_program_viewer.presenter;

import android.database.Cursor;

import example.m1.tv_program_viewer.R;
import example.m1.tv_program_viewer.TvViewerApp;
import example.m1.tv_program_viewer.model.ChannelModel;
import example.m1.tv_program_viewer.model.TvViewerModelFavoritable;
import example.m1.tv_program_viewer.view.TvViewerViewFavoritable;

import static android.provider.BaseColumns._ID;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.CHANNEL_COLUMN_NAME_IS_FAVORITE;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.CHANNEL_COLUMN_NAME_NAME;

/**
 * Created by M1 on 09.11.2016.
 */

public class ChannelsPresenter extends BaseTvViewerPresenter<TvViewerViewFavoritable, TvViewerModelFavoritable> implements TvViewerPresenterFavoritable {

    Cursor cursor;

    public ChannelsPresenter(TvViewerViewFavoritable view) {
        super(view);
    }

    @Override
    protected void initModel() {
        model = new ChannelModel(this);
    }

    @Override
    public void loadData(String... params) {
        model.getData();
    }

    @Override
    public void dataLoaded(Cursor data) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = data;
        view.showData(data);
    }

    @Override
    public void loadingError(String error) {
        view.showError(error);
    }

    @Override
    public void onStop() {
        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    public void onFavoriteClicked(int position) {
        cursor.moveToPosition(position);
        String id = cursor.getString(cursor.getColumnIndex(_ID));
        long isFavorite = cursor.getInt(cursor.getColumnIndex(CHANNEL_COLUMN_NAME_IS_FAVORITE)) == 1 ? 0 : 1;
        cursor.close();
        cursor = model.updateFavorites(id, isFavorite);
        cursor.moveToPosition(position);
        onTabChanged(position);
    }

    @Override
    public void onTabChanged(int position) {
        cursor.moveToPosition(position);
        String channelName = cursor.getString(cursor.getColumnIndex(CHANNEL_COLUMN_NAME_NAME));
        if (cursor.getInt(cursor.getColumnIndex(CHANNEL_COLUMN_NAME_IS_FAVORITE)) == 1) {
            view.setFavoriteButtonText(channelName + " " + TvViewerApp.getAppContext().getString(R.string.title_btn_favorites_remove));
        } else {
            view.setFavoriteButtonText(channelName + " " + TvViewerApp.getAppContext().getString(R.string.title_btn_favorites_add));
        }
    }
}
