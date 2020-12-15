package cn.jpush.api.image.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author fuyx
 * @version 2020-12-14
 */
public enum ImageType {

    @SerializedName("1")
    BIG_PICTURE(1),
    @SerializedName("2")
    LARGE_ICON(2),
    @SerializedName("3")
    SMALL_ICON(3);

    private final int value;

    ImageType(final int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
