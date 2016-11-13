package example.m1.tv_program_viewer.presenter;

import android.database.Cursor;

/**
 * Created by M1 on 13.11.2016.
 */

public interface DataReadyListener {
    void dataLoaded(Cursor data);
    void loadingError(String error);
}
