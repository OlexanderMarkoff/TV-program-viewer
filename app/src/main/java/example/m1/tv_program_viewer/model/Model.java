package example.m1.tv_program_viewer.model;


import java.util.List;

import example.m1.tv_program_viewer.model.api.ApiClient;
import example.m1.tv_program_viewer.model.api.ApiInterface;
import example.m1.tv_program_viewer.model.data.Channel;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by M1 on 09.11.2016.
 */

public class Model implements TvViewerModel {

    ApiInterface apiInterface = ApiClient.getApiInterface();

    @Override
    public Observable<List<Channel>> getChannels() {
        return apiInterface.getChanels()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
