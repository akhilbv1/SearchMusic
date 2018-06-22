package android.searchmusic.dto;

import com.google.gson.annotations.SerializedName;

public class LinkDetailObject {

     /*#text":"",
            "rel":"original",
            "href":"https://last.fm/music/Pink+Floyd/+wiki"*/

    @SerializedName("#text")
    private String imageUrl;

    @SerializedName("rel")
    private String rel;

    @SerializedName("href")
    private String href;

    public String getImageUrl() {
        return imageUrl;
    }

    public String getRel() {
        return rel;
    }

    public String getHref() {
        return href;
    }
}
