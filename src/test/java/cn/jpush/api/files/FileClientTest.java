package cn.jpush.api.files;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.BaseTest;
import cn.jpush.api.file.FileClient;
import cn.jpush.api.file.model.FileModel;
import cn.jpush.api.file.model.FileModelPage;
import cn.jpush.api.file.model.FileType;
import cn.jpush.api.file.model.FileUploadResult;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author daixuan
 * @version 2020/2/24 14:01
 */
public class FileClientTest extends BaseTest {

    protected static final Logger LOG = LoggerFactory.getLogger(FileClientTest.class);

    String fileId = "d4ee2375846bc30fa51334f5-69653861-1408-4d0a-abef-117808632b23";

    @Test
    public void testUploadFile() {
        FileClient fileClient = new FileClient(MASTER_SECRET, APP_KEY);
        try {
            FileUploadResult result = fileClient.uploadFile(FileType.ALIAS, "README.md");
            LOG.info("uploadFile:{}", result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
        }
    }

    @Test
    public void testQueryEffFiles() {
        FileClient fileClient = new FileClient(MASTER_SECRET, APP_KEY);
        try {
            FileModelPage result = fileClient.queryEffectFiles();
            LOG.info("queryEffFiles:{}", result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
        }
    }

    @Test
    public void testQueryFile() {
        FileClient fileClient = new FileClient(MASTER_SECRET, APP_KEY);
        try {
            FileModel fileModel = fileClient.queryFile(fileId);
            LOG.info("fileModel:{}", fileModel);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
        }
    }

    @Test
    public void testDeleteFile() {
        FileClient fileClient = new FileClient(MASTER_SECRET, APP_KEY);
        try {
            fileClient.deleteFile(fileId);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
        }
    }
}
