package example.m1.tv_program_viewer.view;

import java.util.List;

/**
 * Created by M1 on 09.11.2016.
 */

public interface TvViewerView<T> {
    void showData(List<T> dataList);

    void showError(String error);

    void clearData();
}
