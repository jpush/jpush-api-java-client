package cn.jpush.api.example;
import java.util.List;

import cn.jpush.api.report.JPushReportClient;
import cn.jpush.api.report.ReportReceivedResult;

public class ReceiveClientExample {
	private static final String appKey ="3af172b794896c3e1de43fe7";	//必填，例如466f7032ac604e02fb7bda89
	private static final String masterSecret = "57c45646c772983eb0e7c455";//"13ac09b17715bd117163d8a1";//必填
	
	public static void main(String[] args) {
		JPushReportClient JPushClient = new JPushReportClient(appKey, masterSecret);
		
		String msgId = "1236722141";
		String[] msgIds = {"1236722141","910981248","911034889"};
		
		//获取一条
		ReportReceivedResult receiveResult =  JPushClient.getReceived(msgId);
        if (receiveResult == null) {
			System.out.println("获取 Received 数据失败！");
		}else{
			//gson toJson 之后，NULL 值的字段会被过滤掉
            System.out.println("Received result: " + receiveResult.toString());
		}
        
		// 获取多条
		List<ReportReceivedResult> receiveResults = JPushClient.getReceiveds(msgIds);
        if (receiveResults == null) {
			System.out.println("获取receive 数据失败！");
		}else{
			System.out.println("成功获取了：" + receiveResults);
		}
	
	}
}
