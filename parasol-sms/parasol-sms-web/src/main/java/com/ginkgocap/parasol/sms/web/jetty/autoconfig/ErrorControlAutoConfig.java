package com.ginkgocap.parasol.message.web.jetty.autoconfig;

import com.ginkgocap.parasol.message.web.jetty.web.error.MessageErrorControl;

//@Configuration
public class ErrorControlAutoConfig {
	
	//@Bean
	public MessageErrorControl createMetadataErrorControl() {
		return new MessageErrorControl();
	}
}
