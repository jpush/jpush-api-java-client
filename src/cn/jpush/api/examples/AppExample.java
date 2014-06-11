package cn.jpush.api.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.app.AppClient;
import cn.jpush.api.app.AppCreateResult;
import cn.jpush.api.app.model.AppModel;

public class AppExample {
    protected static final Logger LOG = LoggerFactory.getLogger(AppExample.class);

    // demo App defined in resources/jpush-api.conf 
	private static final String devKey ="903724367fadb34e4820b6e0";
	private static final String devSecret = "f1b45af26e90e332210dc52f";
	
	public static void main(String[] args) {
	    testCreateApp();
	}
	
	
	public static void testCreateApp() {
        AppClient appClient = new AppClient(devKey, devSecret);
        AppModel app = new AppModel("test", "com.example.test3", "group2");
        
        AppCreateResult result = appClient.create(app);
        if (result.isResultOK()) {
            LOG.debug(result.toString());
        } else {
            if (result.getErrorCode() > 0) {
                LOG.warn(result.getOriginalContent());
            } else {
                LOG.debug("Maybe connect error. Retry laster. ");
            }
        }
	}

    
}

