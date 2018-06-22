package android.searchmusic.dto;

import com.google.gson.annotations.SerializedName;

public class DetailsMainResponse {

    @SerializedName("artist")
    private ArtistDetailsResponse detailsResponse;

    public ArtistDetailsResponse getDetailsResponse() {
        return detailsResponse;
    }
}
