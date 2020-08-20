package cn.jpush.api.config;

import cn.jpush.api.JPushClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author seanything
 */
@Configuration
public class JPushClientConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "jpush")
    public PushConfig pushConfig(){
        return new PushConfig();
    }

    @Bean
    public JPushClient jPushClient(@Value("${app.app-key}") String appKey, @Value("${app.master-secret}")String masterSecret){
        return new JPushClient(masterSecret,appKey,pushConfig());
    }

}
