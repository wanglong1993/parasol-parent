package com.ginkgocap.parasol.document.web.jetty.autoconfig;

import com.ginkgocap.parasol.document.web.jetty.web.error.DocumentErrorControl;

//@Configuration
public class ErrorControlAutoConfig {
	
	//@Bean
	public DocumentErrorControl createMetadataErrorControl() {
		return new DocumentErrorControl();
	}
}
