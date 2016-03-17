package com.ginkgocap.parasol.person.web.jetty.autoconfig;

import com.ginkgocap.parasol.person.web.jetty.web.error.PersonErrorControl;

//@Configuration
public class ErrorControlAutoConfig {
	
	//@Bean
	public PersonErrorControl createMetadataErrorControl() {
		return new PersonErrorControl();
	}
}
