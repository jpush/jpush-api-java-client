package cn.jpush.api.push.model.notification;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import cn.jiguang.common.utils.StringUtils;
import cn.jpush.api.push.model.PushModel;

public class IosAlert implements PushModel {

    private final String title;
    private final String subtitle;
    private final String body;
    private final String title_loc_key;
    private final String[] title_loc_args;
    private final String action_loc_key;
    private final String loc_key;
    private final String[] loc_args;
    private final String launch_image;

    private IosAlert(String title, String subtitle, String body, String title_loc_key, String[] title_loc_args,
                    String action_loc_key, String loc_key, String[] loc_args, String launch_image) {
        this.title = title;
        this.subtitle = subtitle;
        this.body = body;
        this.title_loc_key = title_loc_key;
        this.title_loc_args = title_loc_args;
        this.action_loc_key = action_loc_key;
        this.loc_key = loc_key;
        this.loc_args = loc_args;
        this.launch_image = launch_image;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();

        if( StringUtils.isNotEmpty(title) ) {
            json.addProperty("title", title);
        }

        if ( StringUtils.isNotEmpty(subtitle) ) {
            json.addProperty("subtitle", subtitle);
        }

        if( StringUtils.isNotEmpty(body) ) {
            json.addProperty("body", body);
        }

        if( StringUtils.isNotEmpty(title_loc_key) ) {
            json.addProperty("title-loc-key", title_loc_key);
            if( null != title_loc_args && title_loc_args.length > 0 ) {
                JsonArray args = new JsonArray();
                for(int i = 0; i < title_loc_args.length; i++) {
                    args.add(new JsonPrimitive(title_loc_args[i]));
                }
                json.add("title-loc-args", args);
            }
        }

        if( StringUtils.isNotEmpty(action_loc_key) ) {
            json.addProperty("action-loc-key", action_loc_key);
        }

        if( StringUtils.isNotEmpty(loc_key) ) {
            json.addProperty("loc-key", loc_key);
            if( null != loc_args && loc_args.length > 0 ) {
                JsonArray args = new JsonArray();
                for(int i = 0; i < loc_args.length; i++) {
                    args.add(new JsonPrimitive(loc_args[i]));
                }
                json.add("loc-args", args);
            }
        }

        if( StringUtils.isNotEmpty(launch_image) ) {
            json.addProperty("launch-image", launch_image);
        }

        return json;
    }

    @Override
    public String toString() {
        return gson.toJson(toJSON());
    }

    public static class Builder {
        private String title;
        private String subtitle;
        private String body;
        private String title_loc_key;
        private String[] title_loc_args;
        private String action_loc_key;
        private String loc_key;
        private String[] loc_args;
        private String launch_image;

        public Builder setTitleAndBody(String title, String subtitle, String body){
            this.title = title;
            this.subtitle = subtitle;
            this.body = body;
            return this;
        }

        public Builder setTitleLoc(String title_loc_key, String... title_loc_args) {
            this.title_loc_key = title_loc_key;
            this.title_loc_args = title_loc_args;
            return this;
        }

        public Builder setActionLocKey(String action_loc_key) {
            this.action_loc_key = action_loc_key;
            return this;
        }

        public Builder setLoc(String loc_key, String... loc_args) {
            this.loc_key = loc_key;
            this.loc_args = loc_args;
            return this;
        }

        public Builder setLaunchImage(String launch_image) {
            this.launch_image = launch_image;
            return this;
        }

        public IosAlert build() {
            return new IosAlert(title, subtitle, body, title_loc_key, title_loc_args, action_loc_key,
                    loc_key, loc_args, launch_image);
        }
    }

}
