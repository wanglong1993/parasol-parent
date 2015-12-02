package com.ginkgocap.parasol.directory.web.jetty.autoconfig;

import com.ginkgocap.parasol.directory.web.jetty.web.error.DirectoryErrorControl;

//@Configuration
public class ErrorControlAutoConfig {
	
	//@Bean
	public DirectoryErrorControl createMetadataErrorControl() {
		return new DirectoryErrorControl();
	}
}
