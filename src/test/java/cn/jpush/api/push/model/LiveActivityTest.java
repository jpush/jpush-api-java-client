package cn.jpush.api.push.model;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.FastTests;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.live_activity.LiveActivity;
import cn.jpush.api.push.model.live_activity.LiveActivityEvent;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(FastTests.class)
public class LiveActivityTest {

    @Test
    public void update() {
        LiveActivity liveActivity = new LiveActivity.Builder()
                .liveActivityId("LiveActivity-1")
                .apnsProduction(false)
                .iOSEvent(LiveActivityEvent.UPDATE)
                .iOSContentState("eventStr", "更新")
                .iOSContentState("eventTime", System.currentTimeMillis())
                .iOSContentState("eventBool", true)
                // 需要联系商务开通 alternate_set 才能使用
                // .iOSAlertTitle("alertTitle")
                // .iOSAlertAlternateTitle("alertAlternateTitle")
                // .iOSAlertBody("alertBody")
                // .iOSAlertAlternateBody("alertAlternateBody")
                // .iOSAlertSound("alertSound")
                .build();
        System.out.println("send liveActivity param:" + liveActivity.toJSON());

        try {
            JPushClient pushClient = new JPushClient("8d8623440ff329ff38597da3", "2785bc46145eaa91a00c0728");
            PushResult pushResult = pushClient.sendLiveActivity(liveActivity);
            System.out.println("send liveActivity result:" + pushResult);
        } catch (APIConnectionException e) {
            throw new RuntimeException(e);
        } catch (APIRequestException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void end() {
        LiveActivity liveActivity = new LiveActivity.Builder()
                .liveActivityId("LiveActivity-1")
                .apnsProduction(false)
                .iOSEvent(LiveActivityEvent.END)
                .iOSContentState("eventStr", "结束")
                .iOSContentState("eventTime", System.currentTimeMillis())
                .iOSDismissalDate((System.currentTimeMillis() / 1000) + 60)
                .build();
        System.out.println("send liveActivity param:" + liveActivity.toJSON());

        try {
            JPushClient pushClient = new JPushClient("8d8623440ff329ff38597da3", "2785bc46145eaa91a00c0728");
            PushResult pushResult = pushClient.sendLiveActivity(liveActivity);
            System.out.println("send liveActivity result:" + pushResult);
        } catch (APIConnectionException e) {
            throw new RuntimeException(e);
        } catch (APIRequestException e) {
            throw new RuntimeException(e);
        }
    }

}
