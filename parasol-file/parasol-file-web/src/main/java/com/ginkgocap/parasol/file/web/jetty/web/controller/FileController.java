/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ginkgocap.parasol.file.web.jetty.web.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.cluster.Directory;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ginkgocap.parasol.file.model.FileIndex;
import com.ginkgocap.parasol.file.service.FileIndexService;
import com.ginkgocap.parasol.file.web.jetty.web.ResponseError;

/**
 * 
* @Title: FileController.java
* @Package com.ginkgocap.parasol.file.web.jetty.web.controller
* @Description: TODO(文件上传控制器)
* @author fuliwen@gintong.com  
* @date 2015年12月9日 上午8:55:41
* @version V1.0
 */
@RestController
public class FileController extends BaseControl {
	private static Logger logger = Logger.getLogger(FileController.class);

	private static final String parameterFields = "fields";
	private static final String parameterDebug = "debug";
	private static final String parameterAppId = "appKey"; // 应用的Key
	private static final String parameterUserId = "userId"; // 访问的用户参数
	private static final String parameterFile = "file"; // 上传文件
	private static final String parameterName = "name"; // 文件名
	private static final String parameterFileType = "fileType"; // 文件类型
	private static final String parameterTaskId = "taskId"; // taskId
	private static final String parameterModuleType = "moduleType"; // 业务模块
	private static final String parameterExtName = "fileExtName"; // 文件扩展名
	private static final String conf_filename = "/fdfs_client.conf";	//	配置文件

//	static{
//	    try {
//	        ClientGlobal.init(getClass().getResource(conf_filename).getFile());
//	    } catch (Exception e) {
//	        throw new RuntimeException(e);
//	    }
//	}
	
	@Resource
	private FileIndexService fileIndexService;

	
	/**
	 * 
	 * @param fileds
	 * @param debug
	 * @param appId
	 * @param file
	 * @param userId
	 * @param name
	 * @return
	 */
	@RequestMapping(path = { "/file/upload" }, method = { RequestMethod.POST })
	public MappingJacksonValue createMessageEntity(@RequestParam(name = FileController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = FileController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = FileController.parameterAppId, required = true) Long appId,
			@RequestParam(name = FileController.parameterFile, required = true) MultipartFile file,
			@RequestParam(name = FileController.parameterUserId, required = true) Long userId,
			@RequestParam(name = FileController.parameterFileType, defaultValue = "1") Integer fileType,
			@RequestParam(name = FileController.parameterModuleType, defaultValue = "1") Integer moduleType,
			@RequestParam(name = FileController.parameterTaskId, required = true) String taskId,
			@RequestParam(name = FileController.parameterExtName, required = true) String fileExtName,
			@RequestParam(name = FileController.parameterName, required = true) String name ) {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			ClientGlobal.init(getClass().getResource(conf_filename).getFile());
			byte[] file_buff = file.getBytes();
			TrackerClient tracker = new TrackerClient(); 
			TrackerServer trackerServer;
			trackerServer = tracker.getConnection();
			StorageServer storageServer = null;
			StorageClient storageClient = new StorageClient(trackerServer, storageServer); 
			//        NameValuePair nvp = new NameValuePair("age", "18"); 
			NameValuePair nvp [] = new NameValuePair[3];
			nvp[0] = new NameValuePair("filename", name);  
			nvp[1] = new NameValuePair("fileExtName", fileExtName);  
			nvp[2] = new NameValuePair("fileLength", String.valueOf(file_buff.length));
			System.out.println("...............url");
			String fileIds[] = storageClient.upload_file(file_buff, fileExtName, nvp);
				for (String id : fileIds) {
				System.out.println("id="+id);
			}
			FileIndex index = new FileIndex();
			index.setAppid(appId.toString());
			index.setCreaterId(userId);
			index.setServerHost(fileIds[0]);
			index.setFilePath(fileIds[1]);
			index.setFileSize(file.getSize());
			index.setFileTitle(file.getOriginalFilename());
			index.setFileType(fileType);
			index.setModuleType(moduleType);
			index.setTaskId(taskId);
			index = fileIndexService.insertFileIndex(index);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(index);
			return mappingJacksonValue;
		} catch (RpcException e) {
			Map<String, Serializable> resultMap = new HashMap<String, Serializable>();
			ResponseError error = processResponseError(e);
			if (error != null) {
				resultMap.put("error", error);
			}
			if (ObjectUtils.equals(debug, "all")) {
				// if (e.getErrorCode() > 0 ) {
				resultMap.put("__debug__", e.getMessage());
				// }
			}
			mappingJacksonValue = new MappingJacksonValue(resultMap);
			e.printStackTrace(System.err);
			return mappingJacksonValue;
		} catch (Exception e) {
			
		}
		return mappingJacksonValue;
	}

	/**
	 * 1. 根据entityid获取消息实体
	 * 
	 * @param request
	 * @return
	 * @throws DirectoryServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = { "/file/download" }, method = { RequestMethod.GET })
	public MappingJacksonValue getEntityById(@RequestParam(name = FileController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = FileController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = FileController.parameterAppId, required = true) Long appId,
			@RequestParam(name = FileController.parameterUserId, required = true) Long userId,
			@RequestParam(name = FileController.parameterTaskId, required = true) String taskId
			) throws Exception {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			// 0.校验输入参数（框架搞定，如果业务业务搞定）
			// 1.查询后台服务
			List<FileIndex> files = fileIndexService.getFileIndexesByTaskId(taskId);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(files);
			// 3.创建页面显示数据项的过滤器
			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
			mappingJacksonValue.setFilters(filterProvider);
			// 4.返回结果
			return mappingJacksonValue;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 指定显示那些字段
	 * 
	 * @param fileds
	 * @return
	 */
	private SimpleFilterProvider builderSimpleFilterProvider(String fileds) {
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		// 请求指定字段
		String[] filedNames = StringUtils.split(fileds, ",");
		Set<String> filter = new HashSet<String>();
		if (filedNames != null && filedNames.length > 0) {
			for (int i = 0; i < filedNames.length; i++) {
				String filedName = filedNames[i];
				if (!StringUtils.isEmpty(filedName)) {
					filter.add(filedName);
				}
			}
		} else {
			filter.add("id"); // id',
			filter.add("name"); // '分类名称',
			filter.add("typeId"); // '应用的分类分类ID',
			filter.add("appId"); // '应用的分类分类ID',
			filter.add("userId"); // '应用的分类分类ID',
		}

		filterProvider.addFilter(Directory.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept(filter));
		return filterProvider;
	}

	@Override
	protected <T> void processBusinessException(ResponseError error,
			Exception ex) {
		// TODO Auto-generated method stub
		
	}
}
