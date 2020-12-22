package cn.jpush.api.image.model;

/**
 * @author fuyx
 * @version  2020-12-14
 */
public enum ImageSource {

    /**
     * Network Resource
     */
    URL("byurls"),
    /**
     * File Resource
     */
    FILE("byfiles");

    private final String value;

    ImageSource(final String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
