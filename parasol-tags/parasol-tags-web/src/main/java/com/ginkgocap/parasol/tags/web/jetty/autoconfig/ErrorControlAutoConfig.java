package com.ginkgocap.parasol.tags.web.jetty.autoconfig;

import com.ginkgocap.parasol.tags.web.jetty.web.error.TagErrorControl;

//@Configuration
public class ErrorControlAutoConfig {
	
	//@Bean
	public TagErrorControl createMetadataErrorControl() {
		return new TagErrorControl();
	}
}
