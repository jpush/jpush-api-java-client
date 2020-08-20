package cn.jpush.api.config;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * 发送消息的相关配置
 * @author seanything
 */
@ToString
public class PushConfig {

    @Getter
    private final AndroidConfig androidConfig = new AndroidConfig();

    @Getter
    private final IosConfig iosConfig = new IosConfig();

    public static PushConfig getDefaultConfig(String badgeClass){
        PushConfig pushConfig = new PushConfig();
        pushConfig.getAndroidConfig().setBadgeAddNum(1);
        pushConfig.getAndroidConfig().setBadgeClass(badgeClass);
        return pushConfig;
    }

    @Data
    public class IosConfig{
        /**
         * 	通知提示声音或警告通知
         */
        private String sound;

        /**
         * 应用角标
         */
        private Integer badge;

        /**
         * 推送唤醒
         */
        private Boolean contentAvailable;

        /**
         * 通知扩展
         */
        private Boolean mutableContent;

        /**
         *  通知栏条目过滤或排序
         */
        private String category;

        /**
         * 通知分组id
         */
        private String threadId;
    }

    @Data
    public class AndroidConfig{
        /**
         * 通知栏样式id
         */
        private Integer builderId;

        /**
         * 指定通知栏展示效果
         */
        private String channelId;

        /**
         * 通知栏展示优先级 默认0，范围 -2 ~ 2
         */
        private Integer priority;

        /**
         * 通知栏条目过滤或排序
         */
        private String category;

        /**
         * 通知栏样式类型<br/>
         * 默认为 0，还有 1，2，3 可选，用来指定选择哪种通知栏样式，其他值无效。有三种可选分别为 bigText=1，Inbox=2，bigPicture=3。
         */
        private Integer style;

        /**
         * 通知提醒方式
         * -1 ~ 7
         */
        private Integer alertType;

        /**
         * 声音文件路径
         */
        private String sound;

        /**
         * APP在前台，通知是否展示
         */
        private String displayForeground;

        /**
         * 角标数字，取值范围1-99
         */
        private Integer badgeAddNum;

        /**
         * 桌面图标对应的应用入口Activity类
         */
        private String badgeClass;
    }

}
