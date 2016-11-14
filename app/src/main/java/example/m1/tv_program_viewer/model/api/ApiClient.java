package example.m1.tv_program_viewer.model.api;

import java.util.List;

import example.m1.tv_program_viewer.model.data.Category;
import example.m1.tv_program_viewer.model.data.Channel;
import example.m1.tv_program_viewer.model.data.Program;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static example.m1.tv_program_viewer.Constants.ROOT_URL;

/**
 * Created by M1 on 09.11.2016.
 */

public class ApiClient {

    private static final Retrofit mRestAdapter;
    private static ApiInterface restService;


    static {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        mRestAdapter = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okClient)
                .build();
        restService = mRestAdapter.create(ApiInterface.class);
    }

    public static void loadChannels(Callback<List<Channel>> callback) {
        restService.getChannels().enqueue(callback);
    }

    public static void loadCategories(Callback<List<Category>> callback) {
        restService.getCategories().enqueue(callback);
    }

    public static void loadPrograms(Callback<List<Program>> callback, String channelId, String timestamp) {
        restService.getPrograms(channelId, timestamp).enqueue(callback);
    }
}
