package com.ginkgocap.parasol.comment.web.jetty.autoconfig;

import com.ginkgocap.parasol.comment.web.jetty.web.error.CommentErrorControl;

//@Configuration
public class ErrorControlAutoConfig {
	
	//@Bean
	public CommentErrorControl createMetadataErrorControl() {
		return new CommentErrorControl();
	}
}
