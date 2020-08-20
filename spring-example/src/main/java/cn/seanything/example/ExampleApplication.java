package cn.seanything.example;

import cn.jpush.api.JPushClient;
import cn.jpush.api.config.EnableJPushClient;
import cn.seanything.example.service.MyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableJPushClient
public class ExampleApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ExampleApplication.class, args);
        context.getBean(MyService.class).test();
    }

}
