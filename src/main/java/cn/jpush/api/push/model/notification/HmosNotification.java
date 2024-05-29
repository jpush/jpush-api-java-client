package cn.jpush.api.push.model.notification;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.Map;

/**
 * <p><b>HMOS Phone 通知类</b></p>
 * <br>
 * 具体使用方法请参考官方文档 https://docs.jiguang.cn/jpush/server/push/rest_api_v3_push/#platform
 * 支持 HMOS Notification 的参数：
 * <ul>
 * <li>alert: 继承自父类 PlatformNotification 的 alert 属性；本类设置则覆盖。</li>
 * <li>title: 支持 setTitle(string) 方法来设置；可替换通知标题。 </li>
 * <li>_open_page: 支持 setOpenPage(String) 方法来设置；可设置点击打开的页面名称</li>
 * <li>extras: 继承自父类 PlatformNotification 的 extras 属性；支持通过 addExtra(key, value) 来添加自定义字段，具体看代码。 </li>
 * </ul>
 * <br>
 */
public class HmosNotification extends PlatformNotification {
    private static final String NOTIFICATION_HMOS = "hmos";

    private static final String TITLE = "title";
    private static final String CATEGORY = "category";
    private static final String LARGE_ICON = "large_icon";
    private static final String INTENT = "intent";
    private static final String BADGE_ADD_NUM = "badge_add_num";
    private static final String TEST_MESSAGE = "test_message";
    private static final String RECEIPT_ID = "receipt_id";

    private final String title;
    private final String category;
    private final String large_icon;
    private final JsonObject intent;
    private final Integer badge_add_num;
    private final Boolean test_message;
    private final String receipt_id;

    private HmosNotification(Object alert,
                             String title,
                             String category,
                             String large_icon,
                             JsonObject intent,
                             Integer badge_add_num,
                             Boolean test_message,
                             String receipt_id,
                             Map<String, String> extras,
                             Map<String, Number> numberExtras,
                             Map<String, Boolean> booleanExtras,
                             Map<String, JsonObject> jsonExtras,
                             Map<String, JsonPrimitive> customData) {
    	super(alert, extras, numberExtras, booleanExtras, jsonExtras, customData);
        
        this.title = title;
        this.category = category;
        this.large_icon = large_icon;
        this.intent = intent;
        this.badge_add_num = badge_add_num;
        this.test_message = test_message;
        this.receipt_id = receipt_id;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public static HmosNotification alert(String alert) {
        return newBuilder().setAlert(alert).build();
    }
    
    
    @Override
    public String getPlatform() {
        return NOTIFICATION_HMOS;
    }
    
    @Override
    public JsonElement toJSON() {
        JsonObject json = super.toJSON().getAsJsonObject();
        
        if (null != title) {
            json.add(TITLE, new JsonPrimitive(title));
        }
        if (null != category) {
            json.add(CATEGORY, new JsonPrimitive(category));
        }
        if (null != large_icon) {
            json.add(LARGE_ICON, new JsonPrimitive(large_icon));
        }
        if (null != intent) {
            json.add(INTENT, intent);
        }
        if (null != badge_add_num) {
            json.add(BADGE_ADD_NUM, new JsonPrimitive(badge_add_num));
        }
        if (null != test_message) {
            json.add(TEST_MESSAGE, new JsonPrimitive(test_message));
        }
        if (null != receipt_id) {
            json.add(RECEIPT_ID, new JsonPrimitive(receipt_id));
        }
        return json;
    }
    
    
    public static class Builder extends PlatformNotification.Builder<HmosNotification, Builder> {
        private String title;
        private String category;
        private String large_icon;
        private JsonObject intent;
        private Integer badge_add_num;
        private Boolean test_message;
        private String receipt_id;
        
        @Override
        protected Builder getThis() {
        	return this;
        }

        @Override
        public Builder setAlert(Object alert) {
            this.alert = alert;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setCategory(String category) {
            this.category = category;
            return this;
        }

        public Builder setLarge_icon(String large_icon) {
            this.large_icon = large_icon;
            return this;
        }

        public Builder setIntent(JsonObject intent) {
            this.intent = intent;
            return this;
        }

        public Builder setBadge_add_num(Integer badge_add_num) {
            this.badge_add_num = badge_add_num;
            return this;
        }

        public Builder setTest_message(Boolean test_message) {
            this.test_message = test_message;
            return this;
        }

        public Builder setReceipt_id(String receipt_id) {
            this.receipt_id = receipt_id;
            return this;
        }

        public HmosNotification build() {
            return new HmosNotification(alert, title, category,large_icon,intent,badge_add_num,test_message,receipt_id,
            		extrasBuilder, numberExtrasBuilder, booleanExtrasBuilder, jsonExtrasBuilder, super.customData);
        }
    }
}
