[![Build Status](https://travis-ci.org/jpush/jpush-api-java-client.svg?branch=master)](https://travis-ci.org/jpush/jpush-api-java-client)
[![Dependency Status](https://www.versioneye.com/user/projects/53eff13a13bb06f0bb000518/badge.svg?style=flat)](https://www.versioneye.com/user/projects/53eff13a13bb06f0bb000518)
[![GitHub version](https://badge.fury.io/gh/jpush%2Fjpush-api-java-client.svg)](http://badge.fury.io/gh/jpush%2Fjpush-api-java-client)

# JPush API Java Library

## 概述

这是 JPush REST API 的 Java 版本封装开发包，此分支主要新增 Http2 支持，关于安装此处不再赘述，可以参考 master 分支下的 ReadMe。


## 使用
本项目使用了 Netty 第三方工具提供的 Http2 支持，建议使用 maven 方式导入，可以参考本项目的 pom.xml。其中有一个依赖需要注意：
```
<dependency>
	<groupId>io.netty</groupId>
	<artifactId>netty-tcnative-boringssl-static</artifactId>
	<version>1.1.33.Fork22</version>
	<classifier>windows-x86_64</classifier>
</dependency>
```
这个依赖根据平台不同而需要修改其中的 classifier 值，详情参考此网页 http://netty.io/wiki/forked-tomcat-native.html#wiki-h3-16 。

## 关于接口调用
首先要调用 NettyHttp2Client 的构造函数（之前用的是 NativeHttpClient）：
```
NettyHttp2Client client = new NettyHttp2Client(authCode, proxy, conf, _baseUrl);
```
前三个参数和 NativeHttpClient 一样，最后一个参数要传入一个 host url。可以参考 PushClient 的做法。

发送单个请求的做法和之前一致，可以参考 example 下的相关示例。NettyHttp2Client 新增发送批量请求的接口:
```
NettyHttp2Client.setRequest(HttpMethod, Queue<Http2Request>).execute(BaseCallback)
```
示例代码如下：
```
public void testSendPushesReuse() {
        ClientConfig config = ClientConfig.getInstance();
        String host = (String) config.get(ClientConfig.PUSH_HOST_NAME);
        NettyHttp2Client client = new NettyHttp2Client(ServiceHelper.getBasicAuthorization(APP_KEY, MASTER_SECRET),
                null, config, host);
        Queue<Http2Request> queue = new LinkedList<Http2Request>();
        String url = (String) config.get(ClientConfig.PUSH_PATH);
        PushPayload payload = buildPushObject_all_all_alert();
        for (int i=0; i<100; i++) {
            queue.offer(new Http2Request(url, payload.toString()));
        }
        try {
            long before = System.currentTimeMillis();
            LOG.info("before: " + before);
            client.setRequestQueue(HttpMethod.POST, queue).execute(new NettyHttp2Client.BaseCallback() {
                @Override
                public void onSucceed(ResponseWrapper wrapper) {
                    PushResult result = BaseResult.fromResponse(wrapper, PushResult.class);
                    LOG.info("Got result - " + result);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
此为链接复用的接口，相对于循环发送单个请求，效率明显提高。可以参考 PushClientTest，写一下测试用例验证一下。
