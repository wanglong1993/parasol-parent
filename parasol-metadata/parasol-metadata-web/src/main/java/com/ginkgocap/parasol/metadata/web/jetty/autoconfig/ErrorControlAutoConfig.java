package com.ginkgocap.parasol.metadata.web.jetty.autoconfig;

import com.ginkgocap.parasol.metadata.web.jetty.web.error.MetadataErrorControl;

//@Configuration
public class ErrorControlAutoConfig {
	
	//@Bean
	public MetadataErrorControl createMetadataErrorControl() {
		return new MetadataErrorControl();
	}
}
