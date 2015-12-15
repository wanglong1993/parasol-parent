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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.ginkgocap.parasol.file.web.jetty.util.ImageProcessUtil;
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
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final String parameterFields = "fields";
	private static final String parameterDebug = "debug";
	private static final String parameterAppId = "appKey"; // 应用的Key
	private static final String parameterUserId = "userId"; // 访问的用户参数
	private static final String parameterFile = "file"; // 上传文件
	private static final String parameterFileType = "fileType"; // 文件类型
	private static final String parameterTaskId = "taskId"; // taskId
	private static final String parameterModuleType = "moduleType"; // 业务模块
	private static final String conf_filename = "/fdfs_client.conf";	//	配置文件
	private static final String parameterWidth = "width"; // 文件扩展名
	private static final String parameterHigth = "higth"; // 文件扩展名
	private static final String parameterXcoord = "xCoordinate"; // x坐标
	private static final String parameterYcoord = "yCoordinate"; // y坐标

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
	public MappingJacksonValue fileUpload(@RequestParam(name = FileController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = FileController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = FileController.parameterAppId, required = true) Long appId,
			@RequestParam(name = FileController.parameterFile, required = true) MultipartFile file,
			@RequestParam(name = FileController.parameterUserId, required = true) Long userId,
			@RequestParam(name = FileController.parameterFileType, defaultValue = "1") Integer fileType,
			@RequestParam(name = FileController.parameterModuleType, defaultValue = "1") Integer moduleType,
			@RequestParam(name = FileController.parameterTaskId, required = true) String taskId ) {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			ClientGlobal.init(getClass().getResource(conf_filename).getFile());
			byte[] file_buff = file.getBytes();
			TrackerClient tracker = new TrackerClient(); 
			TrackerServer trackerServer;
			trackerServer = tracker.getConnection();
			StorageServer storageServer = null;
			StorageClient storageClient = new StorageClient(trackerServer, storageServer);
			String fileName = file.getOriginalFilename();
			int f = fileName.lastIndexOf(".");
			String fileExtName = "";
			if (f>-1) fileExtName = fileName.substring(f+1);
			String fileIds[] = storageClient.upload_file(file_buff, fileExtName, null);
			for (String id : fileIds) {
				System.out.println("id="+id);
			}
			String thumbnailsPath = "";
			// 如果是moduleType是头像，且是图片fileType是1，且扩展名不为空时，生成头像缩略图
			if(fileType == 1 && moduleType == 1 && StringUtils.isNotBlank(fileExtName)) {
				// 生成140*140缩略图
				thumbnailsPath = getPicThumbnail(fileIds[0],fileIds[1],140,140);
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
			index.setThumbnailsPath(thumbnailsPath);
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

	@RequestMapping(path = { "/cut/file" }, method = { RequestMethod.GET })
	public MappingJacksonValue cutFile(@RequestParam(name = FileController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = FileController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = FileController.parameterAppId, required = true) Long appId,
			@RequestParam(name = FileController.parameterUserId, required = true) Long userId,
			@RequestParam(name = FileController.parameterTaskId, required = true) Long fileId,
			@RequestParam(name = FileController.parameterWidth, defaultValue = "140") Integer width,
			@RequestParam(name = FileController.parameterHigth, defaultValue = "140") Integer height,
			@RequestParam(name = FileController.parameterXcoord, required = true) Integer x,
			@RequestParam(name = FileController.parameterYcoord, required = true) Integer y
			) {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			ClientGlobal.init(getClass().getResource(conf_filename).getFile());
			FileIndex index = fileIndexService.getFileIndexById(fileId);
			
			String sFileId = getScissorImage(index.getServerHost(),index.getFilePath(),width,height,x,y);
			
			index.setThumbnailsPath(sFileId);
			fileIndexService.updateFileIndex(index);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(fileId);
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
			e.printStackTrace();
		}
		return mappingJacksonValue;
	}	
	
	/**
	 * 根据图片group、fileId、宽度及高度生成缩略图
	 * @param group
	 * @param fileId
	 * @param width
	 * @param height
	 * @return	缩略图fileId
	 * @throws IOException
	 * @throws MyException
	 */
	private String getPicThumbnail(String group, String fileId, int width, int height) throws IOException, MyException {
		TrackerClient tracker = new TrackerClient(); 
		TrackerServer trackerServer;
		trackerServer = tracker.getConnection();
		StorageServer storageServer = null;
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		// 获取源图字节流
		byte[] mImage = storageClient.download_file(group, fileId);
		// 获取缩略图字节流
		byte[] sImage = ImageProcessUtil.scaleImage(mImage, width, height);
		String suffix = new StringBuilder("_").append(width).append("_").append(height).toString();
		String[] fields = storageClient.upload_file(group, fileId, suffix, sImage, "jpg", null);
		for (String field : fields) {
			System.out.println("field="+field);
		}
		return fields[1];
	}
	
	/**
	 * 根据x、y坐标及width和height切图
	 * @param group
	 * @param fileId
	 * @param width
	 * @param height
	 * @param x
	 * @param y
	 * @return	切图后的fileId
	 * @throws IOException
	 * @throws MyException
	 */
	private String getScissorImage(String group, String fileId, int width, int height, int x, int y) throws IOException, MyException {
		TrackerClient tracker = new TrackerClient(); 
		TrackerServer trackerServer;
		trackerServer = tracker.getConnection();
		StorageServer storageServer = null;
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		// 获取源图字节流
		byte[] mImage = storageClient.download_file(group, fileId);
		byte[] sImage = ImageProcessUtil.scissorImage(x, width, y, height, mImage);
		String suffix = new StringBuilder("_").append(width).append("_").append(height).toString();
		String[] fields = storageClient.upload_file(group, fileId, suffix, sImage, "jpg", null);
		return fields[1];
	}
	
	/**
	 * 文件下载
	 * @param fileds
	 * @param debug
	 * @param appId
	 * @param userId
	 * @param taskId
	 * @return	文件索引列表
	 * @throws Exception
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
	protected <T> void processBusinessException(ResponseError error, Exception ex) {
		// TODO Auto-generated method stub
		
	}

}
