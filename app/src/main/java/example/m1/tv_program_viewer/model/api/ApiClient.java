package example.m1.tv_program_viewer.model.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by M1 on 09.11.2016.
 */

public class ApiClient {

    private static final String ROOT_URL = "http://194.44.253.78:8090/ChanelAPI/";

    private static final boolean ENABLE_LOG = true;

    private ApiClient() {
    }

    public static ApiInterface getApiInterface() {

        OkHttpClient httpClient = new OkHttpClient();

        if (ENABLE_LOG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.newBuilder()
                    .addInterceptor(interceptor)
                    .build();
        }

        Retrofit.Builder builder = new Retrofit.Builder().
                baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        builder.client(httpClient);

        ApiInterface apiInterface = builder.build().create(ApiInterface.class);
        return apiInterface;
    }

}
