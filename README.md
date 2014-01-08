# JPush API client library for Java

## 概述
这是 JPush REST API 的 Java 版本封装开发包，是由极光推送官方提供的，一般支持最新的 API 功能。

对应的 REST API 文档：<http://docs.jpush.cn/display/dev/REST+API>

## 安装

### maven 方式
将下边的依赖条件放到你项目的 maven pom.xml 文件里。

```
<dependency>
    <groupId>cn.jpush.api</groupId>
    <artifactId>jpush-client</artifactId>
    <version>2.3.0</version>
</dependency>
```
### jar 包方式

请到每个 Release 版本 dist/ 目录里下载相应版本的 jar 包。

### 依赖包
* gson
* slf4j

> 其中 slf4j 可以与 logback, log4j, commons-logging 等日志框架一起工作，可根据你的需要配置使用。

### 构建本项目
建议直接使用 maven。

## 使用样例
下边是简单直接的使用样例。
详细地了解请参考：[API Docs](http://jpush.github.io/jpush-api-java-client/apidocs/)。

### 推送样例

```
JPushClient jpushClient = new JPushClient(masterSecret, appKey, 0, DeviceEnum.Android, false);
CustomMessageParams params = new CustomMessageParams();
params.setReceiverType(ReceiverTypeEnum.TAG);
params.setReceiverValue(tag);

MessageResult msgResult = jpushClient.sendCustomMessage(msgTitle, msgContent, params, null);
LOG.debug("responseContent - " + msgResult.responseResult.responseContent);
if (msgResult.isResultOK()) {
    LOG.info("msgResult - " + msgResult);
    LOG.info("messageId - " + msgResult.getMessageId());
} else {
    if (msgResult.getErrorCode() > 0) {
        // 业务异常
        LOG.warn("Service error - ErrorCode: "
                + msgResult.getErrorCode() + ", ErrorMessage: "
                + msgResult.getErrorMessage());
    } else {
        // 未到达 JPush 
        LOG.error("Other excepitons - "
                + msgResult.responseResult.exceptionString);
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


## 更新历史
### r2.3.0 - 2014.01.08

这是一个完全重构版本，对整个项目的源代码以及公开的方法都进行了变更，但其基本功能未变更 。

#### 新功能
* 增加支持 RegistrationID 推送。（如果 Android SDK r1.6.0 以上客户端支持；iOS 稍后发布的新版本SDK支持。）
* 调用返回里可以取得频率控制相关信息

#### BUG修复
* 未有



