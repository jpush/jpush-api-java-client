package cn.jpush.api.jmessage.base.connection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class APIRequestException extends Exception {
    private static final long serialVersionUID = -3921022835186996212L;
    
    protected static Gson _gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    
    private final ResponseWrapper responseWrapper;
    
    public APIRequestException(ResponseWrapper responseWrapper) {
        super(responseWrapper.responseContent);
        this.responseWrapper = responseWrapper;
    }
    
    public int getStatus() {
        return this.responseWrapper.responseCode;
    }

    
    public String getErrorMessage() {
        return this.responseWrapper.responseContent;
    }
    
    @Override
    public String toString() {
        return _gson.toJson(this);
    }
    

}

