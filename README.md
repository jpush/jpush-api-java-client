# JiGuang Common for Java

Common lib for JiGuang Java clients. 

## 概述

这是极光 Java client 的公共封装开发包，为 [jpush](https://github.com/jpush/jpush-api-java-client), [jmessage](https://github.com/jpush/jmessage-api-java-client), [jsms](https://github.com/jpush/jsms-api-java-client) 等 client 提供公共依赖。

版本更新：[Release页面](https://github.com/jpush/jiguang-java-client-common/releases)。下载更新请到这里。

**新增 http2 分支，使用 Netty 第三方工具支持使用 Http/2 发送请求，详情参考 http2 分支下的 ReadMe**

**新增 ApacheHttpClient，效率更高**

**新增 NettyHttpClient，解决在多线程中使用 java sdk 请求超时问题。用法如下：**

- 同步方式（以 PushClient 为例），发送完请求后，请务必调用 close 方法，否则不会自动结束进程。

```
//在 PushClient 中将 NativeHttpClient 改为 NettyHttpClient, 发送请求方式和之前一样。
this._httpClient = new NettyHttpClient(authCode, proxy, conf);
...


```

- 异步方式，异步方式在调用结束后自动关闭。

```
public void testSendPushWithCallback() {
        ClientConfig clientConfig = ClientConfig.getInstance();
        String host = (String) clientConfig.get(ClientConfig.PUSH_HOST_NAME);
        NettyHttpClient client = new NettyHttpClient(ServiceHelper.getBasicAuthorization(APP_KEY, MASTER_SECRET),
                null, clientConfig);
        try {
            URI uri = new URI(host + clientConfig.get(ClientConfig.PUSH_PATH));
            PushPayload payload = buildPushObject_all_alias_alert();
            client.sendRequest(HttpMethod.POST, payload.toString(), uri, new NettyHttpClient.BaseCallback() {
                @Override
                public void onSucceed(ResponseWrapper responseWrapper) {
                    LOG.info("Got result: " + responseWrapper.responseContent);
                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
```

> 非常欢迎各位开发者提交代码，贡献一份力量，Review 过有效的代码将会合入本项目。


## 安装

### maven 方式

将下边的依赖条件放到你项目的 maven pom.xml 文件里。
> 其中 slf4j 可以与 logback, log4j, commons-logging 等日志框架一起工作，可根据你的需要配置使用。

```Java
<dependency>
    <groupId>cn.jpush.api</groupId>
    <artifactId>jiguang-common</artifactId>
    <version>1.0.8</version>
</dependency>
<dependency>
	<groupId>com.google.code.gson</groupId>
	<artifactId>gson</artifactId>
	<version>2.2.4</version>
</dependency>
<dependency>
	<groupId>org.slf4j</groupId>
	<artifactId>slf4j-api</artifactId>
	<version>1.7.5</version>
</dependency>
<!-- For log4j -->
<dependency>
	<groupId>org.slf4j</groupId>
	<artifactId>slf4j-log4j12</artifactId>
	<version>1.7.5</version>
</dependency>
<dependency>
	<groupId>log4j</groupId>
	<artifactId>log4j</artifactId>
	<version>1.2.16</version>
</dependency>
```

### jar 包方式

* jiguang-common的jar包下载。[请点击](https://github.com/jpush/jiguang-java-client-common/releases)
* [slf4j](http://www.slf4j.org/) / log4j (Logger)
* [gson](https://code.google.com/p/google-gson/) (Google JSON Utils)

[项目 libs/ 目录](https://github.com/jpush/jiguang-java-client-common/tree/master/libs)下可以找到 slf4j 及 gson jar 包复制到你的项目里去。

## 编译源码

> 如果开发者想基于本项目做一些扩展的开发，或者想了解本项目源码，可以参考此章，否则可略过此章。

