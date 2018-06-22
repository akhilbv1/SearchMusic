package android.searchmusic.dto;

import com.google.gson.annotations.SerializedName;

public class LinkObject {

    @SerializedName("link")
    private LinkDetailObject linkDetailObject = new LinkDetailObject();

    public LinkDetailObject getLinkDetailObject() {
        return linkDetailObject;
    }
}
