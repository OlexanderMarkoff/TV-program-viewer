package example.m1.tv_program_viewer.view;

import android.database.Cursor;

/**
 * Created by M1 on 09.11.2016.
 */

public interface TvViewerView<T> {
    void showData(Cursor dataList);

    void showError(String error);

    void clearData();
}
