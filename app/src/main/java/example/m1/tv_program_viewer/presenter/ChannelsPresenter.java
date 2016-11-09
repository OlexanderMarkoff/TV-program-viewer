package example.m1.tv_program_viewer.presenter;

import java.util.List;

import example.m1.tv_program_viewer.model.Model;
import example.m1.tv_program_viewer.model.TvViewerModel;
import example.m1.tv_program_viewer.model.data.Channel;
import example.m1.tv_program_viewer.view.TvViewerView;
import rx.Observer;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by M1 on 09.11.2016.
 */

public class ChannelsPresenter implements TvViewerPresenter {


    private TvViewerModel model = new Model();

    private TvViewerView view;
    private Subscription subscription = Subscriptions.empty();

    public ChannelsPresenter(TvViewerView view) {
        this.view = view;
    }

    @Override
    public void loadData() {

        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = model.getChannels()
                .subscribe(new Observer<List<Channel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Channel> data) {
                        if (data != null && !data.isEmpty()) {
                            view.showData(data);
                        } else {
                            view.clearData();
                        }
                    }
                });
    }

    @Override
    public void onStop() {

        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
