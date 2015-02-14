package cn.jpush.api.im.model;

import org.junit.Test;

public class VoiceMessageTest {

	@Test
	public void testCreate() {
		VoiceMessage voice = VoiceMessage.newBuilder()
				.setTarget("single", "javen", "Javen Fang")
				.setFrom("user", "fang", "Fang Javen")
				.setDuration(22)
				.setMedia("/path/image/aaaa", 43214321L, "voice")
				.build();
		
		System.out.println(voice.toJson());
	}
}
