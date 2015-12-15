package com.ginkgocap.parasol.file.web.jetty.init;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;

public class FastdfsClientInit {
	
	private static final String conf_filename = "/fdfs_client.conf";	//	配置文件
	
	public void init () throws FileNotFoundException, IOException, MyException {
		URL url = FastdfsClientInit.class.getResource(conf_filename);
		ClientGlobal.init(url.getFile());
	}
}
