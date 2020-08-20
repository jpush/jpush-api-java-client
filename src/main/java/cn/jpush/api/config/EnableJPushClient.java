package cn.jpush.api.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启动JPushClient
 * 将读取jpush和app配置创建jPushClient注入到spring
 * @author seanything
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(JPushClientConfiguration.class)
public @interface EnableJPushClient {
}
