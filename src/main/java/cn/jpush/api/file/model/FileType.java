package cn.jpush.api.file.model;

/**
 * @author daixuan
 * @version 2020/2/23 20:14
 */
public enum FileType {

    ALIAS("alias"),
    REGISTRATION_ID("registration_id");

    private final String value;

    private FileType(final String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
