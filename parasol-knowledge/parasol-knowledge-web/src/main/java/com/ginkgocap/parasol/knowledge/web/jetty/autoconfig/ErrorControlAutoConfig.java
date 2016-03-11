package com.ginkgocap.parasol.knowledge.web.jetty.autoconfig;

import com.ginkgocap.parasol.knowledge.web.jetty.web.error.KnowledgeErrorControl;

//@Configuration
public class ErrorControlAutoConfig {
	
	//@Bean
	public KnowledgeErrorControl createMetadataErrorControl() {
		return new KnowledgeErrorControl();
	}
}
