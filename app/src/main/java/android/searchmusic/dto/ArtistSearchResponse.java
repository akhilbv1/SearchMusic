package android.searchmusic.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ArtistSearchResponse {

       /* "name":"Cher",
                "listeners":"1051550",
                "mbid":"bfcc6d75-a6a5-4bc6-8282-47aec8531818",
                "url":"https://www.last.fm/music/Cher",
                "streamable":"0",
                "image":[
    {
        "#text":"https://lastfm-img2.akamaized.net/i/u/34s/ec7bf9d519606432d52e33a27d3d183d.png",
            "size":"small"
    },
    {
        "#text":"https://lastfm-img2.akamaized.net/i/u/64s/ec7bf9d519606432d52e33a27d3d183d.png",
            "size":"medium"
    },
    {
        "#text":"https://lastfm-img2.akamaized.net/i/u/174s/ec7bf9d519606432d52e33a27d3d183d.png",
            "size":"large"
    },
    {
        "#text":"https://lastfm-img2.akamaized.net/i/u/300x300/ec7bf9d519606432d52e33a27d3d183d.png",
            "size":"extralarge"
    },
    {
        "#text":"https://lastfm-img2.akamaized.net/i/u/300x300/ec7bf9d519606432d52e33a27d3d183d.png",
            "size":"mega"
    }
    ]

},*/

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

}
