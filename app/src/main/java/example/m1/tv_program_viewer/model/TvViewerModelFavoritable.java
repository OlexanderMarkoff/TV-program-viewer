package example.m1.tv_program_viewer.model;

import android.database.Cursor;

/**
 * Created by M1 on 14.11.2016.
 */

public interface TvViewerModelFavoritable extends TvViewerModel {

    Cursor updateFavorites(String ChannelId, long isFavorite);
}
