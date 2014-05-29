# JPush API client library for Java

## 概述
这是 JPush REST API 的 Java 版本封装开发包，是由极光推送官方提供的，一般支持最新的 API 功能。

对应的 REST API 文档：<http://docs.jpush.cn/display/dev/REST+API>

## 安装

### maven 方式（3.0.0 版本 maven 库里还没有，请直接用 jar 的方式）
将下边的依赖条件放到你项目的 maven pom.xml 文件里。

```
<dependency>
    <groupId>cn.jpush.api</groupId>
    <artifactId>jpush-client</artifactId>
    <version>2.3.1</version>
</dependency>
```
### jar 包方式

请到 [Release页面](https://github.com/jpush/jpush-api-java-client/releases)下载相应版本的发布包。

### 依赖包
* gson
* slf4j

> 其中 slf4j 可以与 logback, log4j, commons-logging 等日志框架一起工作，可根据你的需要配置使用。

如果使用 Maven 构建项目，则需要在你的项目 pom.xml 里增加：
```
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
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>4.11</version>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>com.google.guava</groupId>
			<artifactId>guava</artifactId>
			<version>17.0</version>
		</dependency>

		<dependency>
			<groupId>com.squareup.okhttp</groupId>
			<artifactId>mockwebserver</artifactId>
			<version>1.5.4</version>
			<scope>test</scope>
		</dependency>

```

如果不使用 Maven 构建项目，则项目 libs/ 目录下有依赖的 jar 可复制到你的项目里去。

### 构建本项目
建议直接使用 maven，执行命令：
```
maven package
```

## 使用样例
下边是简单直接的使用样例。
详细地了解请参考：[API Docs](http://jpush.github.io/jpush-api-java-client/apidocs/)。

### 推送样例

```
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
        PushPayload payload = PushPayload.alertAll("Hi, JPush!");
        LOG.info("Paylaod JSON - " + payload.toString());

        PushResult result = jpushClient.sendPush(payload);
        if (result.isResultOK()) {
            LOG.debug(result.toString());
        } else {
            if (result.getErrorCode() > 0) {
                LOG.warn(result.getOriginalContent());
            } else {
                LOG.debug("Maybe connect error. Retry laster. ");
            }
        }

```

### 统计获取样例

```
JPushClient jpushClient = new JPushClient(masterSecret, appKey);
ReceivedsResult receivedsResult = jpushClient.getReportReceiveds("1708010723,1774452771");
LOG.debug("responseContent - " + receivedsResult.responseResult.responseContent);
if (receivedsResult.isResultOK()) {
    LOG.info("Receiveds - " + receivedsResult);
} else {
    if (receivedsResult.getErrorCode() > 0) {
        // 业务异常
        LOG.warn("Service error - ErrorCode: "
                + receivedsResult.getErrorCode() + ", ErrorMessage: "
                + receivedsResult.getErrorMessage());
    } else {
        // 未到达 JPush
        LOG.error("Other excepitons - "
                + receivedsResult.responseResult.exceptionString);
    }
}
```


## 版本更新

[Release页面](https://github.com/jpush/jpush-api-java-client/releases) 有详细的版本发布记录与下载。
