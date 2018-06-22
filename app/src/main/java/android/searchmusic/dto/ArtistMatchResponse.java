package android.searchmusic.dto;

import com.google.gson.annotations.SerializedName;

public class ArtistMatchResponse {

    @SerializedName("artistmatches")
    private ArtistListResponse artistListResponse;

    public ArtistListResponse getArtistListResponse() {
        return artistListResponse;
    }
}
