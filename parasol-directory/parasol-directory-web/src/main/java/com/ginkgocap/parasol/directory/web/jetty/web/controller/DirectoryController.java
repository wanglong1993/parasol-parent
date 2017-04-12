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

package com.ginkgocap.parasol.directory.web.jetty.web.controller;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ginkgocap.parasol.directory.exception.DirectoryServiceException;
import com.ginkgocap.parasol.directory.model.Directory;
import com.ginkgocap.parasol.directory.model.Page;
import com.ginkgocap.parasol.directory.service.DirectoryService;
import com.ginkgocap.parasol.directory.service.DirectorySourceService;
import com.ginkgocap.parasol.util.JsonUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
/**
 * @author allenshen
 * @date 2015年11月20日
 * @time 下午1:19:18
 * @Copyright Copyright©2015 www.gintong.com
 */
@RestController
public class DirectoryController extends BaseControl {
    private static Logger logger = Logger.getLogger(DirectoryController.class);

    private static final String parameterFields = "fields";
    private static final String parameterDebug = "debug";
    private static final String parameterRootType = "rootType"; // 查询的应用分类
    private static final String parameterName = "name"; // 目录名称
    private static final String parameterDirectoryId = "directoryId"; // 目录ID
    private static final String parameterToDirectoryId = "toDirectoryId"; // 移动目录的生活，移动那个目录下
    private static final String parameterParentId = "pId"; // 父目录ID
    private static final String parameterAssocPage = "page";
    private static final String parameterAssocSize = "size";
    private static final String parameterTypeId = "typeId";

    private static final int pageInitSize = 10;

    @Autowired
    private DirectoryService directoryService;

    @Autowired
    private DirectorySourceService directorySourceService;

    /**
     * 2.创建分类下的根目录
     *
     * @param request
     * @return
     * @throws DirectoryServiceException
     */
    @RequestMapping(path = { "/directory/directory/createRootDirectory" }, method = { RequestMethod.POST })
    public MappingJacksonValue createDirectoryRoot(@RequestParam(name = DirectoryController.parameterFields, defaultValue = "") String fileds,
                                                   @RequestParam(name = DirectoryController.parameterDebug, defaultValue = "") String debug,
                                                   @RequestParam(name = DirectoryController.parameterName, required = true) String name,
                                                   @RequestParam(name = DirectoryController.parameterRootType, required = true) long rootType,
                                                   HttpServletRequest request) throws Exception {
        MappingJacksonValue mappingJacksonValue = null;
        try {

            Long loginAppId = this.DefaultAppId;
            Long loginUserId = this.getUserId(request);
            List<Directory> directories = null;
            int total = 0;
            int totalSourceCount = 0;
            List<Directory> directoryList = new ArrayList<Directory>();
            // 0.校验输入参数（框架搞定，如果业务业务搞定）
            Directory directory = new Directory();
            directory.setAppId(loginAppId);
            directory.setUserId(loginUserId);
            if (name.length() > 20) {
                Map<String, Object > resultMap = new HashMap();
                resultMap.put("notifCode", "0002");
                resultMap.put("notifInfo", "目录名称最长不能超过20字哦");
                mappingJacksonValue = new MappingJacksonValue(resultMap);
                return mappingJacksonValue;
            }
            directory.setName(name);
            directory.setTypeId(rootType);
            directory.setOrderNo(1); // 根目录下是一级目录
            Long id = directoryService.createDirectoryForRoot(rootType, directory);

            directories = directoryService.getDirectoryListByUserIdType(loginAppId, loginUserId, rootType);
            total = directoryService.getMyDirectoriesCount(loginAppId, loginUserId, rootType);
            totalSourceCount = directorySourceService.getMyDirectoriesSouceCount(loginAppId, loginUserId, rootType);
            Map<Long, Directory> idMap = new HashMap<Long, Directory>();
            for (Directory direc: directories) {
                long dirId = direc.getId();
                long pid = direc.getPid();
                //根目录下所有目录
                if (pid == 0) {
                    directoryList.add(direc);
                }
                int sourceCount = directorySourceService.countDirectorySourcesByDirectoryId(loginAppId, loginUserId, dirId);
                directory.setSourceCount(sourceCount);
                idMap.put(dirId, direc);
            }
            for (Map.Entry<Long, Directory> map : idMap.entrySet()) {
                Directory dire = map.getValue();
                long pid = dire.getPid();
                Directory parent = idMap.get(pid);
                if (parent != null) {
                    parent.addChildList(dire);
                }
            }

            // 2.转成框架数据
            Map<String, Object> result = new HashMap();
            result.put("totalCount", total);
            result.put("list", directoryList);
            result.put("sourceCount", totalSourceCount);
            mappingJacksonValue = new MappingJacksonValue(result);
            SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
            mappingJacksonValue.setFilters(filterProvider);

            //resultMap.put("id", id);
           /* Map<String, Long> resultMap = new HashMap<String, Long>();
            mappingJacksonValue = new MappingJacksonValue(resultMap);*/
            return mappingJacksonValue;
        }  catch (Exception e) {
            throw e;
        }
    }

    /**
     * 3.创建子目录
     *
     * @param request
     * @return
     * @throws DirectoryServiceException
     * @throws
     */
    @RequestMapping(path = { "/directory/directory/createSubDirectory" }, method = {RequestMethod.POST })
    public MappingJacksonValue createSubDirectory(@RequestParam(name = DirectoryController.parameterFields, defaultValue = "") String fileds,
                                                  @RequestParam(name = DirectoryController.parameterDebug, defaultValue = "") String debug,
                                                  @RequestParam(name = DirectoryController.parameterName, required = true) String name,
                                                  @RequestParam(name = DirectoryController.parameterParentId, required = true) long parentId,
                                                  HttpServletRequest request) throws Exception {
        MappingJacksonValue mappingJacksonValue = null;
        try {

            // Long loginAppId = LoginUserContextHolder.getAppKey();
            // Long loginUserId = LoginUserContextHolder.getUserId();
            Long loginAppId = this.DefaultAppId;
            Long loginUserId = this.getUserId(request);
            List<Directory> directories = null;
            int total = 0;
            List<Directory> directoryList = new ArrayList<Directory>();
            int totalSourceCount = 0;
            // 0.校验输入参数（框架搞定，如果业务业务搞定）
            Directory directory = new Directory();
            directory.setAppId(loginAppId);
            directory.setUserId(loginUserId);
            if (name.length() > 20) {
                Map<String, Object > resultMap = new HashMap();
                resultMap.put("notifCode", "0002");
                resultMap.put("notifInfo", "目录名称最长不能超过20字哦");
                mappingJacksonValue = new MappingJacksonValue(resultMap);
                return mappingJacksonValue;
            }
            directory.setName(name);
            directory.setPid(parentId);

            Directory dir = directoryService.getDirectory(loginAppId, loginUserId, parentId);
            directory.setOrderNo(dir.getOrderNo() + 1); // 设置目录级别
            String numberCode = dir.getNumberCode();
            int count = getCount(numberCode);
            if (count > 20) {
                Map<String, Object> result = new HashMap();
                result.put("notifCode", 0002);
                result.put("notifInfo", "目录级别不能超过20级哦");
                mappingJacksonValue = new MappingJacksonValue(result);
                return mappingJacksonValue;
            }
            long typeId = dir.getTypeId();
            directoryService.createDirectoryForChildren(parentId, directory);
            directories = directoryService.getDirectoryListByUserIdType(loginAppId, loginUserId, typeId);
            total = directoryService.getMyDirectoriesCount(loginAppId, loginUserId, typeId);
            totalSourceCount = directorySourceService.getMyDirectoriesSouceCount(loginAppId, loginUserId, typeId);
            Map<Long, Directory> idMap = new HashMap<Long, Directory>();
            for (Directory direc: directories) {
                long dirId = direc.getId();
                long pid = direc.getPid();
                //根目录下所有目录
                if (pid == 0) {
                    directoryList.add(direc);
                }
                int sourceCount = directorySourceService.countDirectorySourcesByDirectoryId(loginAppId, loginUserId, dirId);
                directory.setSourceCount(sourceCount);
                idMap.put(dirId, direc);
            }
            for (Map.Entry<Long, Directory> map : idMap.entrySet()) {
                Directory dire = map.getValue();
                long pid = dire.getPid();
                Directory parent = idMap.get(pid);
                if (parent != null) {
                    parent.addChildList(dire);
                }
            }

            // 2.转成框架数据
            Map<String, Object> result = new HashMap();
            result.put("totalCount", total);
            result.put("list", directoryList);
            result.put("sourceCount", totalSourceCount);
            mappingJacksonValue = new MappingJacksonValue(result);
            SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
            mappingJacksonValue.setFilters(filterProvider);
            //reusltMap.put("id", id);
            // 2.转成框架数据
            return mappingJacksonValue;
        }  catch (Exception e) {
            throw e;
        }
    }

    /**
     * 删除目录
     *
     * @param debug
     * @param appId
     * @param userId
     * @param directoryId
     * @return
     * @throws DirectoryServiceException
     */
    @RequestMapping(path = { "/directory/directory/deleteDirectory" }, method = { RequestMethod.GET, RequestMethod.DELETE })
    public MappingJacksonValue deleteDirectoryRoot(@RequestParam(name = DirectoryController.parameterDebug, defaultValue = "") String debug,
                                                   @RequestParam(name = DirectoryController.parameterFields, defaultValue = "") String fileds,
                                                   @RequestParam(name = DirectoryController.parameterDirectoryId, required = true) Long directoryId,
                                                   HttpServletRequest request) throws Exception {
        MappingJacksonValue mappingJacksonValue = null;
        try {
            Long loginAppId = this.DefaultAppId;
            Long loginUserId = this.getUserId(request);
            List<Directory> directories = null;
            int total = 0;
            List<Directory> directoryList = new ArrayList<Directory>();
            int totalSourceCount = 0;
            // 0.校验输入参数（框架搞定，如果业务业务搞定）
            Directory directory = directoryService.getDirectory(loginAppId, loginUserId, directoryId);
            long typeId = directory.getTypeId();
            Boolean b = directoryService.removeDirectory(loginAppId, loginUserId, directoryId);
            if (b) {
                directories = directoryService.getDirectoryListByUserIdType(loginAppId, loginUserId, typeId);
                total = directoryService.getMyDirectoriesCount(loginAppId, loginUserId, typeId);
                totalSourceCount = directorySourceService.getMyDirectoriesSouceCount(loginAppId, loginUserId, typeId);
                Map<Long, Directory> idMap = new HashMap<Long, Directory>();
                for (Directory direc: directories) {
                    long dirId = direc.getId();
                    long pid = direc.getPid();
                    //根目录下所有目录
                    if (pid == 0) {
                        directoryList.add(direc);
                    }
                    int sourceCount = directorySourceService.countDirectorySourcesByDirectoryId(loginAppId, loginUserId, dirId);
                    directory.setSourceCount(sourceCount);
                    idMap.put(dirId, direc);
                }
                for (Map.Entry<Long, Directory> map : idMap.entrySet()) {
                    Directory dire = map.getValue();
                    long pid = dire.getPid();
                    Directory parent = idMap.get(pid);
                    if (parent != null) {
                        parent.addChildList(dire);
                    }
                }

                // 2.转成框架数据
                Map<String, Object> result = new HashMap();
                result.put("totalCount", total);
                result.put("list", directoryList);
                result.put("sourceCount", totalSourceCount);
                mappingJacksonValue = new MappingJacksonValue(result);
                SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
                mappingJacksonValue.setFilters(filterProvider);
                return mappingJacksonValue;
            }
            Map<String, Boolean> reusltMap = new HashMap<String, Boolean>();
            reusltMap.put("success", b);
            // 2.转成框架数据
            mappingJacksonValue = new MappingJacksonValue(reusltMap);
            return mappingJacksonValue;
        }  catch (Exception e) {
            throw e;
        }
    }

    /**
     * 更新目录
     *
     * @param debug
     * @param appId
     * @param userId
     * @param directoryId
     * @return
     * @throws DirectoryServiceException
     */
    @RequestMapping(path = { "/directory/directory/updateDirectory" }, method = { RequestMethod.POST })
    public MappingJacksonValue updateDirectoryRoot(@RequestParam(name = DirectoryController.parameterFields, defaultValue = "") String fileds,
                                                   @RequestParam(name = DirectoryController.parameterDebug, defaultValue = "") String debug,
                                                   @RequestParam(name = DirectoryController.parameterDirectoryId, required = true) Long directoryId,
                                                   @RequestParam(name = DirectoryController.parameterName, required = true) String name,
                                                   HttpServletRequest request) throws DirectoryServiceException {
        MappingJacksonValue mappingJacksonValue = null;
        try {
            // Long loginAppId = LoginUserContextHolder.getAppKey();
            // Long loginUserId = LoginUserContextHolder.getUserId();
            Long loginAppId = this.DefaultAppId;
            Long loginUserId = this.getUserId(request);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            if (name.length() > 20) {
                resultMap.put("notifCode", "0002");
                resultMap.put("notifInfo", "目录名称最长不能超过20字哦");
            }
            Directory directory = new Directory();
            directory.setAppId(loginAppId);
            directory.setName(name);
            directory.setId(directoryId);

            Boolean b = directoryService.updateDirectory(loginAppId, loginUserId, directory);

            resultMap.put("success", b);
            // 2.转成框架数据
            mappingJacksonValue = new MappingJacksonValue(resultMap);
            return mappingJacksonValue;
        } catch (DirectoryServiceException e) {
            throw e;
        }
    }

    /**
     * 移动目录
     * @param debug
     * @param appId
     * @param userId
     * @param directoryId
     * @return
     * @throws DirectoryServiceException
     */
    @RequestMapping(path = { "/directory/directory/moveDirectory" }, method = { RequestMethod.POST })
    public MappingJacksonValue moveDirectory(@RequestParam(name = DirectoryController.parameterFields, defaultValue = "") String fileds,
                                             @RequestParam(name = DirectoryController.parameterDebug, defaultValue = "") String debug,
                                             @RequestParam(name = DirectoryController.parameterDirectoryId, required = true) Long directoryId,
                                             @RequestParam(name = DirectoryController.parameterToDirectoryId, required = true) Long toDirectoryId,
                                             HttpServletRequest request) throws DirectoryServiceException {
        MappingJacksonValue mappingJacksonValue = null;
        try {
            Long loginAppId = this.DefaultAppId;
            Long loginUserId = this.getUserId(request);
            int count = 0;

            Directory toDirectory = directoryService.getDirectory(loginAppId, loginUserId, toDirectoryId);
            int toOrderNo = toDirectory.getOrderNo();
            long typeId = toDirectory.getTypeId();
            String toNumberCode = toDirectory.getNumberCode();
            count = getCount(toNumberCode);
            Directory directory = directoryService.getDirectory(loginAppId, loginUserId, directoryId);
            String numberCode = directory.getNumberCode();
            count += getCount(numberCode);
            if (count > 20) {
                Map<String, Object> result = new HashMap();
                result.put("notifCode", 0002);
                result.put("notifInfo", "目录级别不能超过20级哦");
                mappingJacksonValue = new MappingJacksonValue(result);
                return mappingJacksonValue;
            }
            List<Directory> treeList = directoryService.getTreeDirectorysByParentId(loginAppId, loginUserId, directoryId, typeId);

            Boolean b = directoryService.moveDirectoryToDirectory(loginAppId, loginUserId, directoryId, toDirectoryId, treeList);
            Map<String, Boolean> reusltMap = new HashMap<String, Boolean>();
            reusltMap.put("success", b);
            // 2.转成框架数据
            mappingJacksonValue = new MappingJacksonValue(reusltMap);
            return mappingJacksonValue;
        }  catch (DirectoryServiceException e) {
            throw e;
        }
    }

    /**
     * 查询根目录
     *
     * @param request
     * @return
     * @throws DirectoryServiceException
     */
    @RequestMapping(path = { "/directory/directory/getRootList" }, method = { RequestMethod.GET })
    public MappingJacksonValue getRootList(@RequestParam(name = DirectoryController.parameterFields, defaultValue = "") String fileds,
                                           @RequestParam(name = DirectoryController.parameterDebug, defaultValue = "") String debug,
                                           @RequestParam(name = DirectoryController.parameterRootType, required = true) Long rootType,
                                           HttpServletRequest request) throws DirectoryServiceException {
        MappingJacksonValue mappingJacksonValue = null;
        try {
            // Long loginAppId = LoginUserContextHolder.getAppKey();
            // Long loginUserId = LoginUserContextHolder.getUserId();
            Long loginAppId = this.DefaultAppId;
            Long loginUserId = this.getUserId(request);
            // 0.校验输入参数（框架搞定，如果业务业务搞定）
            // 1.查询后台服务
            List<Directory> directories = directoryService.getDirectorysForRoot(loginAppId, loginUserId, rootType);
            // 2.转成框架数据
            mappingJacksonValue = new MappingJacksonValue(directories);
            // 3.创建页面显示数据项的过滤器
            SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
            mappingJacksonValue.setFilters(filterProvider);
            // 4.返回结果
            return mappingJacksonValue;
        } catch (DirectoryServiceException e) {
            throw e;
        }
    }

    /**
     * 查询子目录
     *
     * @param request
     * @return
     * @throws DirectoryServiceException
     */
    @RequestMapping(path = { "/directory/directory/getSubList" }, method = { RequestMethod.GET })
    public MappingJacksonValue getSubList(@RequestParam(name = DirectoryController.parameterFields, defaultValue = "") String fileds,
                                          @RequestParam(name = DirectoryController.parameterDebug, defaultValue = "") String debug,
                                          @RequestParam(name = DirectoryController.parameterParentId, required = true) Long pid,
                                          HttpServletRequest request) throws DirectoryServiceException {

        MappingJacksonValue mappingJacksonValue = null;
        try {
            // Long loginAppId = LoginUserContextHolder.getAppKey();
            // Long loginUserId = LoginUserContextHolder.getUserId();
            Long loginAppId = this.DefaultAppId;
            Long loginUserId = this.getUserId(request);
            // 0.校验输入参数（框架搞定，如果业务业务搞定）
            // 1.查询后台服务
            List<Directory> directories = directoryService.getDirectorysByParentId(loginAppId, loginUserId, pid);

            // 2.转成框架数据
            mappingJacksonValue = new MappingJacksonValue(directories);
            // 3.创建页面显示数据项的过滤器
            SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
            mappingJacksonValue.setFilters(filterProvider);
            // 4.返回结果
            return mappingJacksonValue;
        } catch (DirectoryServiceException e) {
            throw e;
        }
    }

    /**
     * 查询目录 ：树形结构
     *
     * @param request
     * @return
     * @throws DirectoryServiceException
     */
    @RequestMapping(path = { "/directory/directory/getTreeList" }, method = { RequestMethod.GET })
    public MappingJacksonValue getTreeList(@RequestParam(name = DirectoryController.parameterFields, defaultValue = "") String fileds,
                                           @RequestParam(name = DirectoryController.parameterDebug, defaultValue = "") String debug,
                                           @RequestParam(name = DirectoryController.parameterDirectoryId, required = true, defaultValue = "0") long directoryId,
                                           @RequestParam(name = parameterTypeId) long typeId,
                                           HttpServletRequest request) throws DirectoryServiceException {

        MappingJacksonValue mappingJacksonValue = null;
        try {
            Long loginAppId = this.DefaultAppId;
            Long loginUserId = this.getUserId(request);
            List<Directory> directories = null;
            List<Directory> directoryList = new ArrayList<Directory>();
            Map<Long, Directory> idMap = new HashMap();
            int total = 0;
            int totalSourceCount = 0;
            // 0.校验输入参数（框架搞定，如果业务业务搞定）
            // 1.查询后台服务
            if (directoryId == 0) {
                directories = directoryService.getDirectoryListByUserIdType(loginAppId, loginUserId, typeId);
                total = directoryService.getMyDirectoriesCount(loginAppId, loginUserId, typeId);
                totalSourceCount = directorySourceService.getMyDirectoriesSouceCount(loginAppId, loginUserId, typeId);
            } else {
                directories = directoryService.getTreeDirectorysByParentId(loginAppId, loginUserId, directoryId, typeId);
                total = directoryService.getMySubDirectoriesCount(loginAppId, loginUserId, directoryId, typeId);
                //int sourceCount = directorySourceService.countDirectorySourcesByDirectoryId(loginAppId, loginUserId, directoryId);
            }
            // 判断空
            if (CollectionUtils.isEmpty(directories)) {
                Map<String, Object> result = new HashMap();
                result.put("notifCode", 0001);
                result.put("notifInfo", "此目录下还没有目录！");
                mappingJacksonValue = new MappingJacksonValue(result);
                return mappingJacksonValue;
            }
            // 2.将返回数据转为树形结构
            for (Directory directory: directories) {
                long id = directory.getId();
                long pid = directory.getPid();
                //根目录下所有目录
                if (pid == 0 && directoryId == 0) {
                    directoryList.add(directory);
                    //其他目录及目录下所有目录
                } else if (directoryId > 0) {
                    if (directoryId == directory.getId()) {
                        directoryList.add(directory);
                    }
                }
                int sourceCount = directorySourceService.countDirectorySourcesByDirectoryId(loginAppId, loginUserId, id);
                directory.setSourceCount(sourceCount);
                idMap.put(id, directory);
            }
            for (Map.Entry<Long, Directory> map : idMap.entrySet()) {
                Directory directory = map.getValue();
                long pid = directory.getPid();
                Directory parent = idMap.get(pid);
                if (parent != null) {
                    parent.addChildList(directory);
                }
            }

            // 2.转成框架数据
            Map<String, Object> result = new HashMap();

            result.put("totalCount", total);
            result.put("list", directoryList);
            result.put("sourceCount", totalSourceCount);
            mappingJacksonValue = new MappingJacksonValue(result);
            // 3.创建页面显示数据项的过滤器
            SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
            mappingJacksonValue.setFilters(filterProvider);
            // 4.返回结果
            return mappingJacksonValue;
        } catch (Exception e) {
            //throw e;
            Map<String, Object> result = new HashMap();
            result.put("notifCode", 0002);
            result.put("notifInfo", "system error!");
            logger.error(e.getMessage());
            mappingJacksonValue = new MappingJacksonValue(result);
            return mappingJacksonValue;
        }
    }

    private int getCount(String numberCode) {

        char[] c = numberCode.toCharArray();
        Map<String, Integer> map = new HashMap();
        for (int i = 0; i < c.length; i++) {
            String s = String.valueOf(c[i]);
            if (null != map.get(s)) {
                int count = map.get(s);
                map.put(s, count + 1);
            } else {
                map.put(s, 1);
            }
        }
        Integer count = map.get("-");
        if (count == null) {
            return 1;
        }
        return count + 1;
    }

    /**
     * 目录搜索 通过 name 模糊查询
     * @param fileds
     * @param debug
     * @param pageNoStr
     * @param pageSizeStr
     * @param name
     * @param request
     * @return
     * @throws DirectoryServiceException
     */
    @RequestMapping(path = {"/directory/directory/getDirectoryName"}, method = {RequestMethod.POST})
    public MappingJacksonValue getPgDirectoryName(
                    @RequestParam(name = DirectoryController.parameterFields,defaultValue = "") String fileds,
                    @RequestParam(name = DirectoryController.parameterDebug,defaultValue = "") String debug,
                    @RequestParam(name = DirectoryController.parameterAssocPage) String pageNoStr,
                    @RequestParam(name = DirectoryController.parameterAssocSize,required = false) String pageSizeStr,
                    @RequestParam(name = DirectoryController.parameterName) String name,
                    @RequestParam(name = parameterTypeId) long typeId,
                    HttpServletRequest request) throws DirectoryServiceException {
        MappingJacksonValue mappingJacksonValue = null;
        try {

            String utfName = null;
            try {
                utfName = URLDecoder.decode(name, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            int pageNo = Integer.parseInt(pageNoStr);
            int size = Integer.parseInt(pageSizeStr);
            size = size == 0 ? DirectoryController.pageInitSize : size;

            Long loginUserId = this.getUserId(request);
            String requestJson = this.getBodyParam(request);
            Directory directory = null;
            try {
                directory = (Directory) JsonUtils.jsonToBean(requestJson, Directory.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Page<Directory> page = directoryService.getDirectoryName(loginUserId, name, typeId, pageNo, size);

            mappingJacksonValue = new MappingJacksonValue(page);
            SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
            mappingJacksonValue.setFilters(filterProvider);
            return mappingJacksonValue;
        } catch (DirectoryServiceException e) {
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
            filter.add("pid"); // pid',
            filter.add("name"); // '分类名称',
            filter.add("typeId"); // '应用的分类分类ID',
            //filter.add("appId"); // '应用ID',
            filter.add("userId"); // '应用的创建者ID',
            filter.add("numberCode"); //'应用的级别'
            filter.add("childDirectory"); //子目录
            filter.add("totalCount"); //总个数
            filter.add("orderNo"); // 级别
            filter.add("list"); //
            filter.add("sourceCount");
        }

        filterProvider.addFilter(Directory.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept(filter));
        return filterProvider;
    }
}
