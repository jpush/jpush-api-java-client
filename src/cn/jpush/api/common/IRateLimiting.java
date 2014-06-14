package cn.jpush.api.common;

public interface IRateLimiting {

    public int getRateLimitQuota();
    
    public int getRateLimitRemaining();
    
    public int getRateLimitReset();
    
}

