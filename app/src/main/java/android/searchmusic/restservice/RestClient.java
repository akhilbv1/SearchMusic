package android.searchmusic.restservice;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private static final String BASE_URL = "http://ws.audioscrobbler.com/";

    private AppServices appServices;

    public RestClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        appServices = retrofit.create(AppServices.class);
    }

    public AppServices getRestServices() {
        return appServices;
    }

}
