package cn.jpush.example;
import cn.jpush.receive.api.ReceiveClient;
import cn.jpush.receive.api.ReceiveResult;


public class ReceiveClientExample {

	private static final String appKey ="3af172b794896c3e1de43fe7";	//必填，例如466f7032ac604e02fb7bda89

	private static final String masterSecret = "57c45646c772983eb0e7c455";//"13ac09b17715bd117163d8a1";//必填
	
	
	public static void main(String[] args) {
		ReceiveClient receiveClient = new ReceiveClient(masterSecret, appKey);
		
		String msgId = "1236722141";
		
		String[] msgIds = {"1236722141","910981248","911034889"};
		
		//获取一条
		ReceiveResult receiveResult =  receiveClient.getReceived(msgId);
		if(receiveResult == null){
			System.out.println("获取receive 数据失败！"+receiveResult);
		}else{
			//gson toJson 之后，NULL值的字段会被过滤掉
			System.out.println("received result:"+receiveResult.toString());
		}
	
	
	/*	// 获取多条
		List<ReceiveResult> receiveResults = receiveClient.getReceiveds(msgIds);
		if(receiveResults == null ){
			System.out.println("获取receive 数据失败！");
		}else{
			System.out.println("成功获取了："+receiveResults);
		}*/
	
	}
}
