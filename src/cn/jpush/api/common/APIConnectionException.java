package cn.jpush.api.common;

/**
 * Should retry for encountering this exception
 */
public class APIConnectionException extends Exception {
    private static final long serialVersionUID = -2615370590441195647L;

    public APIConnectionException(String message, Throwable e) {
        super(message, e);
    }

    public APIConnectionException(Throwable e) {
        super(e);
    }


}

