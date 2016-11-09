package example.m1.tv_program_viewer.model;

import java.util.List;

import example.m1.tv_program_viewer.model.data.Channel;
import rx.Observable;

/**
 * Created by M1 on 09.11.2016.
 */

public interface TvViewerModel {
    Observable<List<Channel>> getChannels();
}
