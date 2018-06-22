package android.searchmusic.dto;

import com.google.gson.annotations.SerializedName;

public class ImageResponse {

    /*{

        "#text":"https://lastfm-img2.akamaized.net/i/u/300x300/ec7bf9d519606432d52e33a27d3d183d.png",
            "size":"extralarge"

    },*/

    @SerializedName("#text")
    private String imageUrl;

    @SerializedName("size")
    private String size;

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSize() {
        return size;
    }
}
