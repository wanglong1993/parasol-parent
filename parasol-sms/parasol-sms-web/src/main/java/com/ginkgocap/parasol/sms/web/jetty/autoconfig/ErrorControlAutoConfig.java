package com.ginkgocap.parasol.sms.web.jetty.autoconfig;

import com.ginkgocap.parasol.sms.web.jetty.web.error.MessageErrorControl;

//@Configuration
public class ErrorControlAutoConfig {
	
	//@Bean
	public MessageErrorControl createMetadataErrorControl() {
		return new MessageErrorControl();
	}
}
