package cn.jpush.api.im.model;

import org.junit.Test;

public class VoiceMessageTest {

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
}
