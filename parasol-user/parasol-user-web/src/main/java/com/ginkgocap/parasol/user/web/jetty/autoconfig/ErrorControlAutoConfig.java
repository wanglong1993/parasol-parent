package com.ginkgocap.parasol.user.web.jetty.autoconfig;

import com.ginkgocap.parasol.user.web.jetty.web.error.UserErrorControl;

//@Configuration
public class ErrorControlAutoConfig {
	
	//@Bean
	public UserErrorControl createMetadataErrorControl() {
		return new UserErrorControl();
	}
}
