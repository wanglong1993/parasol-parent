package com.ginkgocap.parasol.util;

import org.junit.Test;

public class PinUtilTest {

	@Test
	public void testSomething() {
		System.out.println(PinyinUtils.getHeadByChar("【项羽【】".charAt(0)));
		System.out.println(PinyinUtils.getHeadByChar("项羽【】".charAt(0)));
		System.out.println(PinyinUtils.getHeadByChar("【x【】iangyu".charAt(0)));
		System.out.println(PinyinUtils.getHeadByChar("x【】iangyu".charAt(0)));
		System.out.println(PinyinUtils.stringToQuanPin("【【】羽"));
		System.out.println(PinyinUtils.stringToQuanPin("项【【】羽"));
		System.out.println(PinyinUtils.stringToHeads("【项【】羽"));
		System.out.println(PinyinUtils.stringToHeads("【【项】羽"));
	}
}
