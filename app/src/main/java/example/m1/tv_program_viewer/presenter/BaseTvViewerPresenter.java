package example.m1.tv_program_viewer.presenter;

import example.m1.tv_program_viewer.model.TvViewerModel;
import example.m1.tv_program_viewer.view.TvViewerView;

/**
 * Created by M1 on 13.11.2016.
 */

public abstract class BaseTvViewerPresenter<T extends TvViewerView, D extends TvViewerModel> implements TvViewerPresenter, DataReadyListener {

    protected D model;

    protected T view;

    public BaseTvViewerPresenter(T view) {
        this.view = view;
        initModel();
    }

    protected abstract void initModel();
}
