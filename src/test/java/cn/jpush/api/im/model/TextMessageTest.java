package cn.jpush.api.im.model;

import org.junit.Test;

public class TextMessageTest {

	@Test
	public void testCreate() {
		ImMessage msg = ImMessage.newBuilder()
				.setTarget("single", "javen", "Javen Fang")
				.setFrom("user", "fang", "Fang Javen")
				.setMessage(TextMessage.newBuilder()
						.setText("test text ")
						.build())
				.build();
		
		System.out.println(msg.toJson());
	}
	
	@Test
	public void testParse() throws Exception {
		String json = "{\"msg_type\":\"text\", \"msg_body\":{  \"text\":\"test text msg\"    }}";
		
		ImMessage msg = ImMessage.fromJson(json);
		
		System.out.println(msg.toJson());
	}
	
}
