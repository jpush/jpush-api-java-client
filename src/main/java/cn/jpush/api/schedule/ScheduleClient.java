package cn.jpush.api.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.utils.Preconditions;
import cn.jiguang.common.utils.StringUtils;
import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.connection.NativeHttpClient;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jpush.api.schedule.model.SchedulePayload;

public class ScheduleClient {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduleClient.class);
    private final NativeHttpClient _httpClient;

    private String hostName;
    private String schedulePath;

    // If not present, true by default.
    private int apnsProduction;

    // If not present, the default value is 86400(s) (one day)
    private long timeToLive;

    public ScheduleClient(String masterSecret, String appkey) {
        this(masterSecret, appkey, null, ClientConfig.getInstance());
    }

    /**
     * This will be removed in the future. Please use ClientConfig{jiguang-common cn.jiguang.common.ClientConfig#setMaxRetryTimes} instead of this constructor.
     * @param masterSecret API access secret of the appKey.
     * @param appKey The KEY of one application on JPush.
     * @param maxRetryTimes The mas retry times.
     *
     */
    @Deprecated
    public ScheduleClient(String masterSecret, String appKey, int maxRetryTimes) {
        this(masterSecret, appKey, maxRetryTimes, null);
    }

    /**
     * This will be removed in the future. Please use ClientConfig{jiguang-common cn.jiguang.common.ClientConfig#setMaxRetryTimes} instead of this constructor.
     * @param masterSecret API access secret of the appKey.
     * @param appKey The KEY of one application on JPush.
     * @param maxRetryTimes The mas retry times.
     * @param proxy The proxy, if there is no proxy, should be null.
     */
    @Deprecated
    public ScheduleClient(String masterSecret, String appKey, int maxRetryTimes, HttpProxy proxy) {
        ServiceHelper.checkBasic(appKey, masterSecret);

        ClientConfig conf = ClientConfig.getInstance();
        conf.setMaxRetryTimes(maxRetryTimes);

        hostName = (String) conf.get(ClientConfig.SCHEDULE_HOST_NAME);
        schedulePath = (String) conf.get(ClientConfig.SCHEDULE_PATH);
        apnsProduction = (Integer) conf.get(ClientConfig.APNS_PRODUCTION);
        timeToLive = (Long) conf.get(ClientConfig.TIME_TO_LIVE);

        String authCode = ServiceHelper.getBasicAuthorization(appKey, masterSecret);
        this._httpClient = new NativeHttpClient(authCode, proxy, conf);
    }

    /**
     * Create a Schedule Client with custom configuration.
     * @param masterSecret API access secret of the appKey.
     * @param appKey The KEY of one application on JPush.
     * @param proxy The proxy, if there is no proxy, should be null.
     * @param conf The client configuration. Can use ClientConfig.getInstance() as default.
     */
    public ScheduleClient(String masterSecret, String appKey, HttpProxy proxy, ClientConfig conf) {
        ServiceHelper.checkBasic(appKey, masterSecret);
        hostName = (String) conf.get(ClientConfig.SCHEDULE_HOST_NAME);
        schedulePath = (String) conf.get(ClientConfig.SCHEDULE_PATH);
        apnsProduction = (Integer) conf.get(ClientConfig.APNS_PRODUCTION);
        timeToLive = (Long) conf.get(ClientConfig.TIME_TO_LIVE);

        String authCode = ServiceHelper.getBasicAuthorization(appKey, masterSecret);
        this._httpClient = new NativeHttpClient(authCode, proxy, conf);
    }

    public ScheduleResult createSchedule(SchedulePayload payload) throws APIConnectionException, APIRequestException {

        Preconditions.checkArgument(null != payload, "payload should not be null");

        if (apnsProduction > 0) {
            payload.resetPushApnsProduction(true);
        } else if(apnsProduction == 0) {
            payload.resetPushApnsProduction(false);
        }

        if (timeToLive >= 0) {
            payload.resetPushTimeToLive(timeToLive);
        }

        ResponseWrapper response = _httpClient.sendPost(hostName  + schedulePath, payload.toString());
        return ScheduleResult.fromResponse(response, ScheduleResult.class);
    }

    public ScheduleListResult getScheduleList(int page) throws APIConnectionException, APIRequestException{

        Preconditions.checkArgument(page > 0, "page should more than 0.");

        ResponseWrapper response = _httpClient.sendGet(hostName + schedulePath + "?page=" + page);
        return ScheduleListResult.fromResponse(response, ScheduleListResult.class);
    }

    public ScheduleResult getSchedule(String scheduleId) throws APIConnectionException, APIRequestException{

        Preconditions.checkArgument(StringUtils.isNotEmpty(scheduleId), "scheduleId should not be empty");

        ResponseWrapper response = _httpClient.sendGet(hostName + schedulePath + "/" + scheduleId);
        return ScheduleResult.fromResponse(response, ScheduleResult.class);
    }
    
    public ScheduleMsgIdsResult getScheduleMsgIds(String scheduleId) throws APIConnectionException, APIRequestException{

        Preconditions.checkArgument(StringUtils.isNotEmpty(scheduleId), "scheduleId should not be empty");

        ResponseWrapper response = _httpClient.sendGet(hostName + schedulePath + "/" + scheduleId + "/msg_ids");
        return ScheduleResult.fromResponse(response, ScheduleMsgIdsResult.class);
    }

    public ScheduleResult updateSchedule(String scheduleId, SchedulePayload payload) throws APIConnectionException, APIRequestException{

        Preconditions.checkArgument(StringUtils.isNotEmpty(scheduleId), "scheduleId should not be empty");
        Preconditions.checkArgument(null != payload, "payload should not be null");

        if (apnsProduction > 0) {
            payload.resetPushApnsProduction(true);
        } else if(apnsProduction == 0) {
            payload.resetPushApnsProduction(false);
        }

        if (timeToLive >= 0) {
            payload.resetPushTimeToLive(timeToLive);
        }

        ResponseWrapper response = _httpClient.sendPut(hostName +  schedulePath + "/" + scheduleId,
                payload.toString());
        return ScheduleResult.fromResponse(response, ScheduleResult.class);
    }

    public void deleteSchedule(String scheduleId) throws APIConnectionException, APIRequestException{

        Preconditions.checkArgument(StringUtils.isNotEmpty(scheduleId), "scheduleId should not be empty");

        _httpClient.sendDelete(hostName + schedulePath + "/" + scheduleId);
    }


}
