package cn.jpush.api.im.model;

import org.junit.Test;

public class ImageMessageTest {

	@Test
	public void testCreate() {
		ImageMessage voice = ImageMessage.newBuilder()
				.setTarget("single", "javen", "Javen Fang")
				.setFrom("user", "fang", "Fang Javen")
				.setWidthHeight(55, 56)
				.setMedia("/path/image/bbb", 43214324L, "png")
				.build();
		
		System.out.println(voice.toJson());
	}
}
