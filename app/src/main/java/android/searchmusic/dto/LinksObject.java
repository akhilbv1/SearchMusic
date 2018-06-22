package android.searchmusic.dto;

import com.google.gson.annotations.SerializedName;

public class LinksObject {

    @SerializedName("links")
    private LinkObject link = new LinkObject();

    @SerializedName("summary")
    private String summary;

    @SerializedName("content")
    private String content;

    public String getSummary() {
        return summary;
    }

    public String getContent() {
        return content;
    }

    public LinkObject getLink() {
        return link;
    }
}
