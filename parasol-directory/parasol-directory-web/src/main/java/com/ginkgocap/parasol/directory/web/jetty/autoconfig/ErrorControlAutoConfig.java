package com.ginkgocap.parasol.directory.web.jetty.autoconfig;

import com.ginkgocap.parasol.directory.web.jetty.web.error.MetadataErrorControl;

//@Configuration
public class ErrorControlAutoConfig {
	
	//@Bean
	public MetadataErrorControl createMetadataErrorControl() {
		return new MetadataErrorControl();
	}
}
