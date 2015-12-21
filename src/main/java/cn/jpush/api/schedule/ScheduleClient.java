package cn.jpush.api.schedule;

import cn.jpush.api.common.ClientConfig;
import cn.jpush.api.common.ServiceHelper;
import cn.jpush.api.common.connection.HttpProxy;
import cn.jpush.api.common.connection.NativeHttpClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.common.resp.ResponseWrapper;
import cn.jpush.api.schedule.model.SchedulePayload;
import cn.jpush.api.utils.Preconditions;
import cn.jpush.api.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduleClient {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduleClient.class);
    private final NativeHttpClient _httpClient;

    private String hostName;
    private String schedulePath;

    public ScheduleClient(String masterSecret, String appkey) {
        this(masterSecret, appkey, null, ClientConfig.getInstance());
    }

    /**
     * This will be removed in the future. Please use ClientConfig{@link cn.jpush.api.common.ClientConfig#setMaxRetryTimes} instead of this constructor.
     *
     */
    @Deprecated
    public ScheduleClient(String masterSecret, String appKey, int maxRetryTimes) {
        this(masterSecret, appKey, maxRetryTimes, null);
    }

    /**
     * This will be removed in the future. Please use ClientConfig{@link cn.jpush.api.common.ClientConfig#setMaxRetryTimes} instead of this constructor.
     *
     */
    @Deprecated
    public ScheduleClient(String masterSecret, String appKey, int maxRetryTimes, HttpProxy proxy) {
        ServiceHelper.checkBasic(appKey, masterSecret);

        ClientConfig conf = ClientConfig.getInstance();
        conf.setMaxRetryTimes(maxRetryTimes);

        hostName = (String) conf.get(ClientConfig.SCHEDULE_HOST_NAME);
        schedulePath = (String) conf.get(ClientConfig.SCHEDULE_PATH);

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

        String authCode = ServiceHelper.getBasicAuthorization(appKey, masterSecret);
        this._httpClient = new NativeHttpClient(authCode, proxy, conf);
    }

    public ScheduleResult createSchedule(SchedulePayload payload) throws APIConnectionException, APIRequestException {

        Preconditions.checkArgument(null != payload, "payload should not be null");

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

    public ScheduleResult updateSchedule(String scheduleId, SchedulePayload payload) throws APIConnectionException, APIRequestException{

        Preconditions.checkArgument(StringUtils.isNotEmpty(scheduleId), "scheduleId should not be empty");
        Preconditions.checkArgument(null != payload, "payload should not be null");

        ResponseWrapper response = _httpClient.sendPut(hostName +  schedulePath + "/" + scheduleId,
                payload.toString());
        return ScheduleResult.fromResponse(response, ScheduleResult.class);
    }

    public void deleteSchedule(String scheduleId) throws APIConnectionException, APIRequestException{

        Preconditions.checkArgument(StringUtils.isNotEmpty(scheduleId), "scheduleId should not be empty");

        _httpClient.sendDelete(hostName + schedulePath + "/" + scheduleId);
    }


}
