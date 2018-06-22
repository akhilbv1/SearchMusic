package android.searchmusic.dto;

import com.google.gson.annotations.SerializedName;

public class ResultResponse {

    @SerializedName("results")
    private ArtistMatchResponse matchResponse;

    public ArtistMatchResponse getMatchResponse() {
        return matchResponse;
    }
}
