package cn.jpush.api.file;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.connection.IHttpClient;
import cn.jiguang.common.connection.NativeHttpClient;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jiguang.common.utils.Preconditions;
import cn.jiguang.common.utils.StringUtils;
import cn.jpush.api.file.model.FileModel;
import cn.jpush.api.file.model.FileModelPage;
import cn.jpush.api.file.model.FileType;
import cn.jpush.api.file.model.FileUploadResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author daixuan
 * @version 2020/2/23 19:38
 */
public class FileClient {

    protected static final Logger LOG = LoggerFactory.getLogger(FileClient.class);

    private IHttpClient _httpClient;
    private String _baseUrl;
    private String _filesPath;
    private Gson _gson = new Gson();

    public FileClient(String masterSecret, String appKey) {
        this(masterSecret, appKey, null, ClientConfig.getInstance());
    }

    public FileClient(String masterSecret, String appKey, HttpProxy proxy, ClientConfig conf) {
        _baseUrl = (String) conf.get(ClientConfig.PUSH_HOST_NAME);
        _filesPath = (String) conf.get(ClientConfig.V3_FILES_PATH);
        String authCode = ServiceHelper.getBasicAuthorization(appKey, masterSecret);
        this._httpClient = new NativeHttpClient(authCode, proxy, conf);
    }

    public FileUploadResult uploadFile(FileType type, String filename, Integer ttl)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(type != null, "type should not be null");
        Preconditions.checkArgument(StringUtils.isNotEmpty(filename), "filename should not be null");
        Preconditions.checkArgument(ttl >= 1 && ttl <= 720,"TTL is not in the range of 1 to 720");
        NativeHttpClient client = (NativeHttpClient) _httpClient;
        String typeStr = type.value();
        String url = _baseUrl + _filesPath + "/" + typeStr;
        Map<String, String> fileMap = new HashMap<String,String>();
        fileMap.put("filename", filename);
        Map<String, String> textMap = new HashMap<String,String>();
        textMap.put("ttl",String.valueOf(ttl));
        String response = client.formUploadByPost(url, textMap, fileMap, null);
        LOG.info("uploadFile:{}", response);
        return _gson.fromJson(response,
                new TypeToken<FileUploadResult>() {
                }.getType());
    }

    public FileUploadResult uploadFile(FileType type, String filename) throws APIConnectionException, APIRequestException {
        return uploadFile(type,filename,720);
    }

    public FileModelPage queryEffectFiles() throws APIConnectionException, APIRequestException {
        String url = _baseUrl + _filesPath + "/";
        ResponseWrapper response = _httpClient.sendGet(url);
        LOG.info("queryEffFiles:{}", response);
        return _gson.fromJson(response.responseContent,
                new TypeToken<FileModelPage>() {
                }.getType());
    }

    public FileModel queryFile(String fileId) throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(fileId), "fileId should not be null");
        String url = _baseUrl + _filesPath + "/" + fileId;
        ResponseWrapper response = _httpClient.sendGet(url);
        LOG.info("queryFile:{}", response);
        return _gson.fromJson(response.responseContent,
                new TypeToken<FileModel>() {
                }.getType());
    }

    public ResponseWrapper deleteFile(String fileId) throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(fileId), "fileId should not be null");
        String url = _baseUrl + _filesPath + "/" + fileId;
        ResponseWrapper response = _httpClient.sendDelete(url);
        LOG.info("deleteFile:{}", response);
        return response;
    }
}
