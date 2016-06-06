package com.ginkgocap.parasol.organ.web.jetty.autoconfig;

import com.ginkgocap.parasol.organ.web.jetty.web.error.PersonErrorControl;

//@Configuration
public class ErrorControlAutoConfig {
	
	//@Bean
	public PersonErrorControl createMetadataErrorControl() {
		return new PersonErrorControl();
	}
}
