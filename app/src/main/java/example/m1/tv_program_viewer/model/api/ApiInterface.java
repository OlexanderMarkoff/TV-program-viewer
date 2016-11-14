package example.m1.tv_program_viewer.model.api;

import java.util.List;

import example.m1.tv_program_viewer.model.data.Category;
import example.m1.tv_program_viewer.model.data.Channel;
import example.m1.tv_program_viewer.model.data.Program;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by M1 on 09.11.2016.
 */

public interface ApiInterface {

    @GET("chanels")
    Call<List<Channel>> getChannels();

    @GET("categories ")
    Call<List<Category>> getCategories();

    @GET("programs/{channel_id}/{timestamp}")
    Call<List<Program>> getPrograms(@Path("channel_id") String channel_id, @Path("timestamp") String timestamp);

}
