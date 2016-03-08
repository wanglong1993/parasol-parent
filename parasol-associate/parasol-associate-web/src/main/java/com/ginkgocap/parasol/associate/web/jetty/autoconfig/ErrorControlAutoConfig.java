package com.ginkgocap.parasol.associate.web.jetty.autoconfig;

import com.ginkgocap.parasol.associate.web.jetty.web.error.AssociateErrorControl;

//@Configuration
public class ErrorControlAutoConfig {
	
	//@Bean
	public AssociateErrorControl createMetadataErrorControl() {
		return new AssociateErrorControl();
	}
}
