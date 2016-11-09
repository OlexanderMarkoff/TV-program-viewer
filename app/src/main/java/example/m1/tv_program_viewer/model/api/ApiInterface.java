package example.m1.tv_program_viewer.model.api;

import java.util.List;

import example.m1.tv_program_viewer.model.data.Category;
import example.m1.tv_program_viewer.model.data.Channel;
import example.m1.tv_program_viewer.model.data.Program;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by M1 on 09.11.2016.
 */

public interface ApiInterface {

    @GET("chanels")
    Observable<List<Channel>> getChanels();

    @GET("categories ")
    Observable<List<Category>> getCategories();

    @GET("programs/{timestamp}")
    Observable<List<Program>> getPrograms(@Path("timestamp") String timestamp);
}
