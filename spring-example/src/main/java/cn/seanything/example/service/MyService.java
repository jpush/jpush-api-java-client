package cn.seanything.example.service;

import cn.jpush.api.JPushClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author seanything
 * @date 2020/8/20
 */
@Service
public class MyService {

    @Autowired
    private JPushClient jPushClient;

    public void test(){
        try {
            jPushClient.sendAndroidNotificationWithAlias("标题","测试",null,"asd");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
