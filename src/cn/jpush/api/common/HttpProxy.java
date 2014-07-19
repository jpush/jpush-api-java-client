package cn.jpush.api.common;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class HttpProxy {
    private String host;
    private int port;
    private String username;
    private String password;
    
    private boolean authenticationNeeded = false;
    
    public HttpProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    public HttpProxy(String host, int port, String username, String password) {
        this(host, port);
        this.username = username;
        this.password = password;
        authenticationNeeded = true;
    }
    
    public Proxy getProxy() {
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
    }
    
    public boolean isAuthenticationNeeded() {
        return this.authenticationNeeded;
    }
    
    public String getProxyAuthorization() {
        return ServiceHelper.getBasicAuthorization(username, password);
    }
}
