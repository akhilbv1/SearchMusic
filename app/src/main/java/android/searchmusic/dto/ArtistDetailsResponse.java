package android.searchmusic.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ArtistDetailsResponse {

    @SerializedName("name")
    private String artistName;

    @SerializedName("listeners")
    private int listeners;

    @SerializedName("url")
    private String artistAboutUrl;

    @SerializedName("streamable")
    private String streamable;

    @SerializedName("image")
    private ArrayList<ImageResponse> imageArray;

    @SerializedName("bio")
    private LinksObject boiData = new LinksObject();

    public String getArtistName() {
        return artistName;
    }

    public int getListeners() {
        return listeners;
    }

    public String getArtistAboutUrl() {
        return artistAboutUrl;
    }

    public String getStreamable() {
        return streamable;
    }

    public ArrayList<ImageResponse> getImageArray() {
        return imageArray;
    }

    public LinksObject getBoiData() {
        return boiData;
    }
}
