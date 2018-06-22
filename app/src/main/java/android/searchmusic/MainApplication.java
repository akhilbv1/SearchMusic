package android.searchmusic;

import android.app.Application;
import android.searchmusic.restservice.RestClient;

public class MainApplication extends Application {

    private static RestClient restClient;

    @Override
    public void onCreate() {
        super.onCreate();

        restClient = new RestClient();

    }

    public static RestClient getRestClient() {
        return restClient;
    }
}
