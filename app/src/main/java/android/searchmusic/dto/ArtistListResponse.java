package android.searchmusic.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ArtistListResponse {

    @SerializedName("artist")
    private ArrayList<ArtistSearchResponse> searchResponse;

    public ArrayList<ArtistSearchResponse> getSearchResponse() {
        return searchResponse;
    }
}
