package android.searchmusic.dto;

import com.google.gson.annotations.SerializedName;

public class MainSearchResponse {

    @SerializedName("results")
    private ArtistMatchResponse matchResponse;

    public ArtistMatchResponse getMatchResponse() {
        return matchResponse;
    }
}
