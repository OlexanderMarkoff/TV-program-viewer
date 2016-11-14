package example.m1.tv_program_viewer.presenter;

/**
 * Created by M1 on 14.11.2016.
 */

public interface TvViewerPresenterFavoritable extends TvViewerPresenter {

    void onFavoriteClicked(int position);
    void onTabChanged(int position);
}
