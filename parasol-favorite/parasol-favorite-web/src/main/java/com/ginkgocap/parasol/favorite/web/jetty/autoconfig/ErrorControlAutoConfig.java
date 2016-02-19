package com.ginkgocap.parasol.favorite.web.jetty.autoconfig;

import com.ginkgocap.parasol.favorite.web.jetty.web.error.FavoriteErrorControl;

//@Configuration
public class ErrorControlAutoConfig {
	
	//@Bean
	public FavoriteErrorControl createMetadataErrorControl() {
		return new FavoriteErrorControl();
	}
}
