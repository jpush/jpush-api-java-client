package cn.jpush.api.common.resp;

public class DefaultResult extends BaseResult {

	public static DefaultResult fromResponse(ResponseWrapper responseWrapper, Class<DefaultResult> clazz) {
		DefaultResult result = null;

		if (responseWrapper.isServerResponse()) {
			result = new DefaultResult();
		} else {
			try {
				result = clazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		result.setResponseWrapper(responseWrapper);

		return result;
	}

}
