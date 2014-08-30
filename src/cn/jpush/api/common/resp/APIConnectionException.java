package cn.jpush.api.common.resp;

/**
 * Should retry for encountering this exception basically. 
 * Normally it is due to:
 * 1. Connect timed out.
 * 2. Read timed out.
 * 3. Cannot parse domain.
 * 
 * For Push action, if the exception is "Read timed out" you may not want to retry it.
 */
public class APIConnectionException extends Exception {
    private static final long serialVersionUID = -2615370590441195647L;
    private boolean readTimedout = false;
    private int doneRetriedTimes = 0;

    public APIConnectionException(String message, Throwable e) {
        super(message, e);
    }

    public APIConnectionException(String message, Throwable e, int doneRetriedTimes) {
        super(message, e);
        this.doneRetriedTimes = doneRetriedTimes;
    }

    public APIConnectionException(String message, Throwable e, boolean readTimedout) {
        super(message, e);
        this.readTimedout = readTimedout;
    }

    public boolean isReadTimedout() {
        return readTimedout;
    }
    
    public int getDoneRetriedTimes() {
        return this.doneRetriedTimes;
    }
}


