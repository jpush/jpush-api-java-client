package cn.jpush.api;
import java.util.regex.Pattern;

public class ValidateRequestParams {
	private final static int sendNo = -1;
	private final static Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
	
	public static MessageResult vidateParams(MessageParams params){					
		if(null == params.getAppKey() || "".equals(params.getAppKey())
				|| params.getAppKey().length() != 24 
				|| pattern.matcher(params.getAppKey()).find()){
			return new MessageResult(sendNo, ErrorCodeEnum.MissingRequiredParameters.value(), 
							"app_key format is error.");		
		}
		
		if(null == params.getMasterSecret() || "".equals(params.getMasterSecret())
				|| params.getMasterSecret().length() != 24){
			return new MessageResult(sendNo, ErrorCodeEnum.ValidateFailed.value(), 
							"Failed to verify the request. (verification_code is incorrect).");
		}
		
		if(params.getSendNo() < 1 ){
			return new MessageResult(sendNo, ErrorCodeEnum.InvalidParameter.value(),
							"sendno value is error, (1-4294967295).");
		}
		
		if(params.getReceiverType().value() == ReceiverTypeEnum.TAG.value()
				&& params.getReceiverValue().split(",").length > 10){
			return new MessageResult(sendNo, ErrorCodeEnum.InvalidParameter.value(), 
							"exceed maximum of 10 tags.");
		}
		
		if(params.getReceiverType().value() == ReceiverTypeEnum.ALIAS.value()
				&& params.getReceiverValue().split(",").length > 1000){
			return new MessageResult(sendNo, ErrorCodeEnum.InvalidParameter.value(), 
							"exceed maximum of 1000 alias.");
		}
		
		return null;
		
	}
	
}


