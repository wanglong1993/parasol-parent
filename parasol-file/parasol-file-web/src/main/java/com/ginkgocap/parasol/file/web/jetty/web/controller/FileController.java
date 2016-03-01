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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.rpc.RpcException;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ginkgocap.parasol.file.dataimporter.service.FileService;
import com.ginkgocap.parasol.file.dataimporter.service.PicUserService;
import com.ginkgocap.parasol.file.exception.FileIndexServiceException;
import com.ginkgocap.parasol.file.model.FileIndex;
import com.ginkgocap.parasol.file.model.PicUser;
import com.ginkgocap.parasol.file.service.FileIndexService;
import com.ginkgocap.parasol.file.web.jetty.util.ImageProcessUtil;
import com.ginkgocap.parasol.file.web.jetty.web.ResponseError;
import com.ginkgocap.parasol.oauth2.web.jetty.LoginUserContextHolder;

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
	private static final String parameterFile = "file"; // 上传文件
	private static final String parameterIndexId = "indexId"; // 索引文件id
	private static final String parameterFileType = "fileType"; // 文件类型
	private static final String parameterTaskId = "taskId"; // taskId
	private static final String parameterModuleType = "moduleType"; // 业务模块
	private static final String parameterXEnd = "xEnd"; // x结束坐标
	private static final String parameterYEnd = "yEnd"; // y结束坐标
	private static final String parameterXStart = "xStart"; // x开始坐标
	private static final String parameterYStart = "yStart"; // y开始坐标

	@Resource
	private FileIndexService fileIndexService;

	@Autowired
	private FileService fileService;
	
	@Autowired
	private PicUserService picUserService;
	/**
	 * 
	 * @param fileds
	 * @param debug
	 * @param appId
	 * @param file
	 * @param userId
	 * @param name
	 * @return
	 * @throws FileIndexServiceException 
	 * @throws IOException 
	 * @throws MyException 
	 */
	@RequestMapping(path = { "/file/upload" }, method = { RequestMethod.POST })
	public MappingJacksonValue fileUpload(@RequestParam(name = FileController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = FileController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = FileController.parameterFile, required = true) MultipartFile file,
			@RequestParam(name = FileController.parameterFileType, defaultValue = "1") Integer fileType,
			@RequestParam(name = FileController.parameterModuleType, defaultValue = "1") Integer moduleType,
			@RequestParam(name = FileController.parameterTaskId, required = true) String taskId ) throws FileIndexServiceException, IOException, MyException {
		MappingJacksonValue mappingJacksonValue = null;
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(file.getSize() == 0) {
				result.put("error", "上传文件无效，请重新上传！");
				return new MappingJacksonValue(result);
			}

			Long loginAppId = LoginUserContextHolder.getAppKey();
			Long loginUserId = LoginUserContextHolder.getUserId();
			byte[] file_buff = file.getBytes();
			StorageClient storageClient = getStorageClient();
			String fileName = file.getOriginalFilename();
			int f = fileName.lastIndexOf(".");
			String fileExtName = "";
			if (f>-1) fileExtName = fileName.substring(f+1);
			String fields[] = storageClient.upload_file(file_buff, fileExtName, null);
			logger.info("field, field[0]:{},field[1]:{}", fields[0],fields[1]);
			String thumbnailsPath = "";
			
			// 如果是moduleType是头像，且是图片fileType是1，且扩展名不为空时，生成头像缩略图
			if(fileType == 1 && moduleType == 1 && StringUtils.isNotBlank(fileExtName)) {
				generateAvatar(fields[0], fields[1], fileExtName, null);
				thumbnailsPath = fields[1].replace("."+fileExtName, "_140_140."+fileExtName);
			}
			FileIndex index = new FileIndex();
			index.setAppId(loginAppId);
			index.setCreaterId(loginUserId);
			index.setServerHost(fields[0]);
			index.setFilePath(fields[1]);
			index.setFileSize(file.getSize());
			index.setFileTitle(file.getOriginalFilename());
			index.setFileType(fileType);
			index.setModuleType(moduleType);
			index.setTaskId(taskId);
			index.setThumbnailsPath(thumbnailsPath);
			index = fileIndexService.insertFileIndex(index);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(index);
			// 3.创建页面显示数据项的过滤器
			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
			mappingJacksonValue.setFilters(filterProvider);
			storageClient=null;
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
		}
	}

	/**
	 * 通过坐标截图，生成头像，生成140*140,90*90,60*60px缩略图
	 * @param fileds
	 * @param debug
	 * @param appId
	 * @param userId
	 * @param indexId
	 * @param xEnd
	 * @param yEnd
	 * @param xStart
	 * @param yStart
	 * @return fileIndex索引文件
	 * @throws FileIndexServiceException 
	 * @throws MyException 
	 * @throws IOException 
	 */
	@RequestMapping(path = { "/file/scissorImage" }, method = { RequestMethod.GET })
	public MappingJacksonValue scissorImage(@RequestParam(name = FileController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = FileController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = FileController.parameterIndexId, required = true) Long indexId,
			@RequestParam(name = FileController.parameterXEnd, required = true) Integer xEnd,
			@RequestParam(name = FileController.parameterYEnd, required = true) Integer yEnd,
			@RequestParam(name = FileController.parameterXStart, required = true) Integer xStart,
			@RequestParam(name = FileController.parameterYStart, required = true) Integer yStart
			) throws FileIndexServiceException, IOException, MyException {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			FileIndex index = fileIndexService.getFileIndexById(indexId);
			Map<String, Object> result = new HashMap<String, Object>();
			if(index == null) {
				result.put("error", "文件索引id不存在，请检查参数！");
				return new MappingJacksonValue(result);
			} 
			getScissorImage(index.getServerHost(),index.getFilePath(),xEnd-xStart,yEnd-yStart,xStart,yStart);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(index);
			// 3.创建页面显示数据项的过滤器
			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
			mappingJacksonValue.setFilters(filterProvider);
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
		}
	}	
	
	/**
	 * 文件下载
	 * @param fileds
	 * @param debug
	 * @param appId
	 * @param userId
	 * @param taskId
	 * @return	文件索引列表
	 * @throws FileIndexServiceException 
	 * @throws Exception
	 */
	@RequestMapping(path = { "/file/getFileIndexesByTaskId" }, method = { RequestMethod.GET })
	public MappingJacksonValue getFileIndexesByTaskId(@RequestParam(name = FileController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = FileController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = FileController.parameterTaskId, required = true) String taskId
			) throws FileIndexServiceException {
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
		} 
	}
	
	/**
	 * 文件下载
	 * @param fileds
	 * @param debug
	 * @param appId
	 * @param userId
	 * @param taskId
	 * @return	文件索引列表
	 * @throws FileIndexServiceException 
	 * @throws Exception
	 */
	@RequestMapping(path = { "/file/deleteById" }, method = { RequestMethod.GET })
	public MappingJacksonValue deleteFileById(@RequestParam(name = FileController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = FileController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = FileController.parameterIndexId, required = true) long indexId
			) throws FileIndexServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		try {
			// 0.校验输入参数（框架搞定，如果业务业务搞定）
			FileIndex index = fileIndexService.getFileIndexById(indexId);
			Map<String, Object> result = new HashMap<String, Object>();
			
			if(index == null) {
				result.put("error", "文件索引id不存在，请检查参数！");
				return new MappingJacksonValue(result);
			} 
			// fastDFS中删除上传的文件
			deleteFileByFileId(index.getServerHost(),index.getFilePath(),index.getModuleType());
			// 1.查询后台服务
			boolean flag = fileIndexService.deleteFileIndexById(indexId);
			result.put("result", flag);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(result);
			// 3.创建页面显示数据项的过滤器
			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
			mappingJacksonValue.setFilters(filterProvider);
			// 4.返回结果
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
		}
	}
	
	private void deleteFileByFileId(String group, String fileId, int moduleType) {
		try {
			StorageClient storageClient = getStorageClient();
			storageClient.delete_file(group, fileId);
			// 文件为头像时，删除从文件140*140,90*90,60*60缩略图
			if(moduleType == 1) {
				int f = fileId.lastIndexOf(".");
				String fileExtName = "";
				if (f>-1) {
					fileExtName = fileId.substring(f+1);
				} else {
					return ;
				}
				String file140 = fileId.replace("."+fileExtName, "_140_140."+fileExtName);
				storageClient.delete_file(group, file140);
				String file90 = fileId.replace("."+fileExtName, "_90_90."+fileExtName);
				storageClient.delete_file(group, file90);
				String file60 = fileId.replace("."+fileExtName, "_60_60."+fileExtName);
				storageClient.delete_file(group, file60);
			}
		} catch (Exception e) {
			
		}
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
	private void getPicThumbnail(String group, String fileId, int width, int height, String fileExtName, byte[] mImage) throws IOException, MyException {
		
		StorageClient storageClient = getStorageClient();
		// 如果mImage为空，则通过fileId下载文件，生成字节数组
		if (mImage==null || mImage.length==0) {
			// 获取源图字节流
			mImage = storageClient.download_file(group, fileId);
		}
		// 获取缩略图字节流
		byte[] sImage = ImageProcessUtil.scaleImage(mImage, width, height, fileExtName);
		
		String suffix = new StringBuilder("_").append(width).append("_").append(height).toString();
		String mfileId = fileId.replace("."+fileExtName, suffix+"."+fileExtName);
		// 根据文件id删除文件
		storageClient.delete_file(group, mfileId);
		storageClient.upload_file(group, fileId, suffix, sImage, fileExtName, null);
		return;
	}
	
	/**
	 * 生成小头像，fastdfs从文件,三种格式140*140、90*90、60*60
	 * @param group
	 * @param fileId
	 * @param fileExtName
	 * @return	140*140图片地址作为缩略图
	 * @throws IOException
	 * @throws MyException
	 */
	private void generateAvatar(final String group, final String fileId, final String fileExtName, final byte[] mImage) throws IOException, MyException {
		Thread thread = new Thread(
					new Runnable(){
						@Override
						public void run() {
							try {
								// 生成140*140头像图片
								getPicThumbnail(group, fileId, 140, 140, fileExtName, mImage);
								// 生成90*90头像图片
								getPicThumbnail(group, fileId, 90, 90, fileExtName, mImage);
								// 生成90*90头像图片
								getPicThumbnail(group, fileId, 60, 60, fileExtName, mImage);
							} catch (IOException e) {
								logger.error("group:{}, fileId:{},fileExtName:{}", group,fileId, fileExtName);
								e.printStackTrace();
							} catch (MyException e) {
								logger.error("group:{}, fileId:{},fileExtName:{}", group,fileId, fileExtName);
								e.printStackTrace();
							}
						}
						
					}
				);
		thread.start();
	}
	
	/**
	 * 获取StorageClient
	 * @return
	 * @throws IOException
	 */
	private StorageClient getStorageClient() throws IOException {
		TrackerClient tracker = new TrackerClient(); 
		TrackerServer trackerServer;
		trackerServer = tracker.getConnection();
		StorageServer storageServer = null;
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		return storageClient;
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
	private void getScissorImage(String group, String fileId, int width, int height, int x, int y) throws IOException, MyException {
		StorageClient storageClient = getStorageClient();
		// 获取源图字节流
		byte[] mImage = storageClient.download_file(group, fileId);
		int f = fileId.lastIndexOf(".");
		String fileExtName = "";
		if (f>-1) {
			fileExtName = fileId.substring(f+1);
		} else {
			return ;
		}
		// 根据源图截图
		byte[] sImage = ImageProcessUtil.scissorImage(x, width, y, height, mImage, fileExtName);
		
		generateAvatar(group, fileId, fileExtName, sImage);

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
			filter.add("filePath"); // '文件路径',
			filter.add("serverHost"); // '分布式文件所在group',
			filter.add("fileTitle"); // '文件名称',
			filter.add("taskId"); // '上传时taskId',
			filter.add("moduleType"); // '上传模块类型',
			filter.add("fileType"); // '文件类型',
			filter.add("thumbnailsPath"); // '图片缩略图地址',
			filter.add("appId"); // '应用ID',
		}

		filterProvider.addFilter(FileIndex.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept(filter));
		return filterProvider;
	}
	
	/**
	 * 
	 * @param fileds
	 * @param debug
	 * @param file
	 * @param fileType
	 * @param moduleType
	 * @param appId
	 * @param userId
	 * @param taskId
	 * @return
	 * @throws FileIndexServiceException
	 * @throws IOException
	 * @throws MyException
	 */
	@RequestMapping(path = { "/online/getPicOnline" }, method = { RequestMethod.POST })
	public MappingJacksonValue getPicOnline(@RequestParam(name = FileController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = FileController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = FileController.parameterFile, required = true) MultipartFile file,
			@RequestParam(name = FileController.parameterFileType, defaultValue = "1") Integer fileType,
			@RequestParam(name = FileController.parameterModuleType, defaultValue = "1") Integer moduleType,
			@RequestParam(name = "appId", required = true) Long appId,
			@RequestParam(name = "userId", required = true) Long userId,
			@RequestParam(name = FileController.parameterTaskId, required = true) String taskId ) throws FileIndexServiceException, IOException, MyException {
		MappingJacksonValue mappingJacksonValue = null;
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			if(file.getSize() == 0) {
				result.put("error", "上传文件无效，请重新上传！");
				return new MappingJacksonValue(result);
			}

			Long loginAppId = LoginUserContextHolder.getAppKey();
			Long loginUserId = LoginUserContextHolder.getUserId();
			byte[] file_buff = file.getBytes();
			StorageClient storageClient = getStorageClient();
			String fileName = file.getOriginalFilename();
			int f = fileName.lastIndexOf(".");
			String fileExtName = "";
			if (f>-1) fileExtName = fileName.substring(f+1);
			String fields[] = storageClient.upload_file(file_buff, fileExtName, null);
			logger.info("field, field[0]:{},field[1]:{}", fields[0],fields[1]);
			String thumbnailsPath = "";
			
			// 如果是moduleType是头像，且是图片fileType是1，且扩展名不为空时，生成头像缩略图
			if(fileType == 1 && moduleType == 1 && StringUtils.isNotBlank(fileExtName)) {
				generateAvatar(fields[0], fields[1], fileExtName, null);
				thumbnailsPath = fields[1].replace("."+fileExtName, "_140_140."+fileExtName);
			}
			FileIndex index = new FileIndex();
			index.setAppId(loginAppId);
			index.setCreaterId(loginUserId);
			index.setServerHost(fields[0]);
			index.setFilePath(fields[1]);
			index.setFileSize(file.getSize());
			index.setFileTitle(file.getOriginalFilename());
			index.setFileType(fileType);
			index.setModuleType(moduleType);
			index.setTaskId(taskId);
			index.setThumbnailsPath(thumbnailsPath);
			index = fileIndexService.insertFileIndex(index);
			// 2.转成框架数据
			mappingJacksonValue = new MappingJacksonValue(index);
			// 3.创建页面显示数据项的过滤器
			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
			mappingJacksonValue.setFilters(filterProvider);
			storageClient=null;
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
		}
	}
	
	@RequestMapping(path = { "/online/initFiles" }, method = { RequestMethod.GET })
	public MappingJacksonValue fileInit() throws FileIndexServiceException, IOException, MyException {
		MappingJacksonValue mappingJacksonValue = null;
			// 获取旧的文件索引
//			List<Index> indexes = fileService.getAllFileIndexes(0, 100);
			List<PicUser> users = picUserService.getAllFileIndexes();
			int count = 0;
			importDefaultPic();
//			if(1==1) return null;
			for(PicUser in : users) {
				// 考虑到旧的数据不够完整，在NFS中很多文件并不存在
				try {
	            // 以流的形式下载文件。
//	            InputStream fis = new BufferedInputStream(new FileInputStream("/webserver/upload/"+in.getFile_path()));
				// 测试环境文件索引和实际文件不对应,先写死，测试程序 /webserver/upload/web/0051191508
//					InputStream fis = new BufferedInputStream(new FileInputStream("e:\\Chrysanthemum.jpg"));
					FileInputStream fis = new FileInputStream("/webserver/upload/"+in.getPic_path());
				    byte[] buffer = null;
				    if (fis != null) {
				    	int len = fis.available();
				    	buffer = new byte[len];
				    	fis.read(buffer);
				    }
		            if(buffer.length == 0) continue;
		            StorageClient storageClient = getStorageClient();
					String fileName = in.getPic_path();
//					String fileName = "头像";
					int f = fileName.lastIndexOf(".");
					String fileExtName = "";
					if (f>-1) fileExtName = fileName.substring(f+1).trim();
					String fields[] = storageClient.upload_file(buffer, fileExtName, null);
					
					logger.info("field, field[0]:{},field[1]:{}", fields[0],fields[1]);
					String thumbnailsPath = "";
					Integer fileType = 1;
					Integer moduleType = 1;
					// 如果是moduleType是头像，且是图片fileType是1，且扩展名不为空时，生成头像缩略图
//					if(fileType == 1 && moduleType == 1 && StringUtils.isNotBlank(fileExtName)) {
//						generateAvatar(fields[0], fields[1], fileExtName, null);
//						thumbnailsPath = fields[1].replace("."+fileExtName, "_140_140."+fileExtName);
//					}
					FileIndex index = new FileIndex();
//					index.setId(Long.valueOf(in.getId()));
					index.setAppId(1);
					index.setCreaterId(in.getId());
					index.setServerHost(fields[0]);
					index.setFilePath(fields[1]);
					index.setFileSize(0);
					index.setFileTitle(in.getPic_path());
					index.setFileType(fileType);
					// 标记导入文件状态为9
					index.setStatus(9);
					index.setModuleType(1);
					index.setModuleType(1);
					index.setTaskId("");
					// 原头像已经缩略过，不需要缩略
					index.setThumbnailsPath("");
					index = fileIndexService.insertFileIndex(index);
					count++;
					// 
					if(count%50 == 0) {
						Thread.sleep(500000);
					}
					storageClient=null;
				}catch (Exception e) {
					continue;
				}
			}
		return mappingJacksonValue;
	}	
	
	/**
	 * 导入默认头像
	 * 单独处理默认头像
	 */
	private void importDefaultPic(){
		List<PicUser> users = picUserService.getDefaultUserPics();
		for(PicUser in : users) {
			// 考虑到旧的数据不够完整，在NFS中很多文件并不存在
			try {
            // 以流的形式下载文件。
//            InputStream fis = new BufferedInputStream(new FileInputStream("/webserver/upload/"+in.getFile_path()));
			// 测试环境文件索引和实际文件不对应,先写死，测试程序 /webserver/upload/web/0051191508
//				InputStream fis = new BufferedInputStream(new FileInputStream("e:\\Chrysanthemum.jpg"));
				FileInputStream fis = new FileInputStream("/webserver/upload/"+in.getPic_path());
			    byte[] buffer = null;
			    if (fis != null) {
			    	int len = fis.available();
			    	buffer = new byte[len];
			    	fis.read(buffer);
			    }
	            if(buffer.length == 0) continue;
	            StorageClient storageClient = getStorageClient();
//				String fileName = in.getFile_title();
				String fileName = in.getPic_path();
				int f = fileName.lastIndexOf(".");
				String fileExtName = "";
				if (f>-1) fileExtName = fileName.substring(f+1).trim();
				String fields[] = storageClient.upload_file(buffer, fileExtName, null);
				
				logger.info("field, field[0]:{},field[1]:{}", fields[0],fields[1]);
				String thumbnailsPath = "";
				Integer fileType = 1;
				Integer moduleType = 1;
				
				// 如果是moduleType是头像，且是图片fileType是1，且扩展名不为空时，生成头像缩略图
//				if(fileType == 1 && moduleType == 1 && StringUtils.isNotBlank(fileExtName)) {
//					generateAvatar(fields[0], fields[1], fileExtName, null);
//					thumbnailsPath = fields[1].replace("."+fileExtName, "_140_140."+fileExtName);
//				}
				FileIndex index = new FileIndex();
//				index.setId(Long.valueOf(in.getId()));
				index.setAppId(1);
				index.setCreaterId(0);
				index.setServerHost(fields[0]);
				index.setFilePath(fields[1]);
				index.setFileSize(0);
				index.setFileTitle(in.getPic_path());
				index.setFileType(fileType);
				// 标记导入文件状态为9
				index.setStatus(9);
				index.setModuleType(1);
				index.setModuleType(1);
				index.setTaskId("");
				index.setThumbnailsPath(thumbnailsPath);
				index = fileIndexService.insertFileIndex(index);
			}catch (Exception e) {
				continue;
			}
		}
	}
	
	@Override
	protected <T> void processBusinessException(ResponseError error, Exception ex) {
		// TODO Auto-generated method stub
		
	}

}
