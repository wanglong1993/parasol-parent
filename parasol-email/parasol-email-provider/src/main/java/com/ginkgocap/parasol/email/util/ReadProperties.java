package com.ginkgocap.parasol.email.util;
import java.io.InputStream;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class ReadProperties { 

    private static ResourceLoader resourceLoader = new DefaultResourceLoader();
    
    public static InputStream readStaticEmailHtml(String location) throws Exception {
       Resource resource = resourceLoader.getResource(location);
       return resource.getInputStream();
    } 
}
