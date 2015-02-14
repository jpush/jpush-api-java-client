package cn.jpush.api.im.model;

import org.junit.Test;

public class TextMessageTest {

	@Test
	public void testCreate() {
		TextMessage text = TextMessage.newBuilder()
				.setTarget("single", "javen", "Javen Fang")
				.setText("Hello, JPush IM!")
				.setFrom("user", "fang", "Fang Javen")
				.build();
		
		System.out.println(text.toJson());
	}
}
