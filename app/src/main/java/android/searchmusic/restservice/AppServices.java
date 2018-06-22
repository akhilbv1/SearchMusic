package android.searchmusic.restservice;


import android.searchmusic.dto.DetailsMainResponse;
import android.searchmusic.dto.MainSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AppServices {

    // Get Artists by Search key
    // http://ws.audioscrobbler.com/2.0/?method=artist.search&artist=SEARCH_PARAM&api
    //_key=API _KEY&format=json
    @GET("2.0/")
    Call<MainSearchResponse> getArtistSearchList(@Query("method") String artistSearch, @Query("artist") String searchKey,
                                                 @Query("api_key") String apiKey, @Query("format") String jsonFormat);

    //http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=Pink+Floyd&api_key=fa2e6
    //2987b 8c372e16daa60331164d12&format=json
    @GET("2.0/")
    Call<DetailsMainResponse> getArtistDetails(@Query("method") String artistInfo, @Query("artist") String artistName,
                                               @Query("api_key") String apiKey, @Query("format") String jsonFormat);
}
