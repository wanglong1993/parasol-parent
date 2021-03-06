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
import com.ginkgocap.parasol.util.PinyinComparatorList;
import com.ginkgocap.parasol.util.PinyinComparatorList4ObjectName;
import com.gintong.frame.util.dto.CommonResultCode;
import com.gintong.frame.util.dto.InterfaceResult;
import com.gintong.frame.util.dto.Notification;
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
import java.sql.SQLException;
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
    @RequestMapping(path = { "/directory/directory/createRootDirectory.json" }, method = { RequestMethod.POST })
    public MappingJacksonValue createDirectoryRoot(@RequestParam(name = DirectoryController.parameterFields, defaultValue = "") String fileds,
                                                   @RequestParam(name = DirectoryController.parameterDebug, defaultValue = "") String debug,
                                                   @RequestParam(name = DirectoryController.parameterName, required = true) String name,
                                                   @RequestParam(name = DirectoryController.parameterRootType, required = true) long rootType,
                                                   HttpServletRequest request) throws Exception {
        return createDirectoryRootTemp(fileds, debug, name, rootType, request, false);
    }

    /**
     * 2.创建分类下的根目录 老版本
     *
     * @param request
     * @return
     * @throws DirectoryServiceException
     */
    @RequestMapping(path = { "/directory/directory/createRootDirectory" }, method = { RequestMethod.POST })
    public MappingJacksonValue createDirectoryRootOld(@RequestParam(name = DirectoryController.parameterFields, defaultValue = "") String fileds,
                                                   @RequestParam(name = DirectoryController.parameterDebug, defaultValue = "") String debug,
                                                   @RequestParam(name = DirectoryController.parameterName, required = true) String name,
                                                   @RequestParam(name = DirectoryController.parameterRootType, required = true) long rootType,
                                                   HttpServletRequest request) throws Exception {
        return createDirectoryRootTemp(fileds, debug, name, rootType, request, true);
    }

    private MappingJacksonValue createDirectoryRootTemp(String fields, String debug, String name, long rootType, HttpServletRequest request, boolean isOld) throws Exception{

        MappingJacksonValue mappingJacksonValue = null;
        InterfaceResult interfaceResult = null;

        logger.info("----createDirectoryRootTemp---start---" + System.currentTimeMillis());
        Long loginUserId = this.getUserId(request);
        Long loginAppId = this.DefaultAppId;
        try {
            // 0.校验输入参数（框架搞定，如果业务业务搞定）
            Directory directory = new Directory();
            directory.setAppId(loginAppId);
            directory.setUserId(loginUserId);
            mappingJacksonValue = assertNameLength(name);
            if (mappingJacksonValue != null) {
                return mappingJacksonValue;
            }
            directory.setName(name);
            directory.setTypeId(rootType);
            directory.setOrderNo(1); // 根目录下是一级目录
            // 创建根节点
            Long id = null;
            try {
                id = directoryService.createDirectoryForRoot(rootType, directory);
            } catch (Exception e) {
                logger.error("invoke createDirectoryForRoot method failed userId : " + loginUserId);
                interfaceResult = InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_DB_OPERATION_EXCEPTION);
                mappingJacksonValue = new MappingJacksonValue(interfaceResult);
                return mappingJacksonValue;
            }
            // id < 0 sqlException 插入数据库错误，验证是否重名
            if (id < 0) {
                logger.info("-------------return result time : " + System.currentTimeMillis());
                interfaceResult = InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
                interfaceResult.getNotification().setNotifInfo("已经有同名的目录了哦");
                mappingJacksonValue = new MappingJacksonValue(interfaceResult);
                return mappingJacksonValue;
            }
            if (isOld) {
                interfaceResult = InterfaceResult.getSuccessInterfaceResultInstance(id);
                // 2.转成框架数据
                mappingJacksonValue = new MappingJacksonValue(interfaceResult);
                return mappingJacksonValue;
            }
            // 在 mysql 创建成功后，准备同步到 mongo 中，但需先检查 mongo 中是否有数据，
            // 若有数据，则直接同步；若无数据，则不同步，在下面returnMyDirectoriesTreeList方法中整体同步（这种情况是 mysql 没同步到 mongo 中）
            logger.info("-----getDirectoriesTreeByCache----start time : " + System.currentTimeMillis());
            List<Directory> directoryList = directoryService.getDirectoriesTreeByCache(loginUserId, rootType);
            logger.info("-----getDirectoriesTreeByCache----end time : " + System.currentTimeMillis());
            if (CollectionUtils.isNotEmpty(directoryList)) {
                Directory directoryDB = directoryService.getDirectory(loginAppId, loginUserId, id);
                if (null != directoryDB) {
                    try {
                        logger.info("-----saveDirectoryByCache-----start time : " + System.currentTimeMillis());
                        directoryService.saveDirectoryByCache(directoryDB);
                        logger.info("-----saveDirectoryByCache-----end time : " + System.currentTimeMillis());
                    } catch (Exception e) {
                        logger.error("invoke directoryService failed! method : [ saveDirectoryByCache ] userId : " + loginUserId);
                    }
                }
            }
            logger.info("----createDirectoryRootTemp---end---" + System.currentTimeMillis());
            return returnMyDirectoriesTreeList(loginAppId, loginUserId, rootType, fields);
        } catch (Exception e) {
            if (isOld) {
                logger.error("create root directory failed! userId :" + loginUserId);
                //resultMap.put("id", 0l);
                interfaceResult = InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_DB_OPERATION_EXCEPTION);
                mappingJacksonValue = new MappingJacksonValue(interfaceResult);
                return mappingJacksonValue;
            }
            interfaceResult = InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
            mappingJacksonValue = new MappingJacksonValue(interfaceResult);
            return mappingJacksonValue;

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
    @RequestMapping(path = { "/directory/directory/createSubDirectory.json" }, method = {RequestMethod.POST })
    public MappingJacksonValue createSubDirectory(@RequestParam(name = DirectoryController.parameterFields, defaultValue = "") String fields,
                                                  @RequestParam(name = DirectoryController.parameterDebug, defaultValue = "") String debug,
                                                  @RequestParam(name = DirectoryController.parameterName, required = true) String name,
                                                  @RequestParam(name = DirectoryController.parameterParentId, required = true) long parentId,
                                                  HttpServletRequest request) throws Exception {
            return createSubDirectoryTemp(fields, debug, name, parentId, request, false);
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
    public MappingJacksonValue createSubDirectoryOld(@RequestParam(name = DirectoryController.parameterFields, defaultValue = "") String fields,
                                                     @RequestParam(name = DirectoryController.parameterDebug, defaultValue = "") String debug,
                                                     @RequestParam(name = DirectoryController.parameterName, required = true) String name,
                                                     @RequestParam(name = DirectoryController.parameterParentId, required = true) long parentId,
                                                     HttpServletRequest request) throws Exception {
        return createSubDirectoryTemp(fields, debug, name, parentId, request, true);
    }

    private MappingJacksonValue createSubDirectoryTemp(String fields, String debug, String name, long parentId, HttpServletRequest request, boolean isOld) throws Exception {

        MappingJacksonValue mappingJacksonValue = null;
        InterfaceResult interfaceResult = null;
        Map<String, Long> resultMap = new HashMap<String, Long>(2);
        Long loginUserId = this.getUserId(request);
        try {
            Long loginAppId = this.DefaultAppId;
            // 0.校验输入参数（框架搞定，如果业务业务搞定）
            Directory directory = new Directory();
            directory.setAppId(loginAppId);
            directory.setUserId(loginUserId);
            mappingJacksonValue = assertNameLength(name);
            if (mappingJacksonValue != null) {
                return mappingJacksonValue;
            }
            directory.setName(name);
            directory.setPid(parentId);
            Directory dir = directoryService.getDirectory(loginAppId, loginUserId, parentId);
            directory.setOrderNo(dir.getOrderNo() + 1); // 设置目录级别
            String numberCode = dir.getNumberCode();
            int orderNo = dir.getOrderNo();
            int level = getCount(numberCode);
            if (level >= 20 || orderNo >= 20) {
                interfaceResult = InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
                Notification notification = interfaceResult.getNotification();
                notification.setNotifInfo("目录级别不能超过20级哦");
                mappingJacksonValue = new MappingJacksonValue(interfaceResult);
                return mappingJacksonValue;
            }
            long typeId = dir.getTypeId();
            // 创建非根节点
            /*List<Directory> directories = null;
            directories = directoryService.getDirectorysByParentId(loginAppId, loginUserId, parentId);
            MappingJacksonValue isDuplicate = assertDuplicateName(directories, directory);
            if (isDuplicate != null) {
                return isDuplicate;
            }*/
            Long id = null;
            try {
                 id = directoryService.createDirectoryForChildren(parentId, directory);
            } catch (Exception e) {
                interfaceResult = InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_DB_OPERATION_EXCEPTION);
                mappingJacksonValue = new MappingJacksonValue(interfaceResult);
                return mappingJacksonValue;
            }
            if (id < 0) {
                interfaceResult = InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
                interfaceResult.getNotification().setNotifInfo("已经有同名的目录了哦");
                mappingJacksonValue = new MappingJacksonValue(interfaceResult);
                return mappingJacksonValue;
            }
            if (isOld) {
                interfaceResult = InterfaceResult.getSuccessInterfaceResultInstance(id);
                // resultMap.put("id", id);
                // 2.转成框架数据
                mappingJacksonValue = new MappingJacksonValue(interfaceResult);
                return mappingJacksonValue;
            }
            return returnMyDirectoriesTreeList(loginAppId, loginUserId, typeId, fields);
        }  catch (Exception e) {
            if (isOld) {
                logger.error("create children directory failed! userId : " + loginUserId);
                // resultMap.put("id", 0l);
                interfaceResult = InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_DB_OPERATION_EXCEPTION);
                mappingJacksonValue = new MappingJacksonValue(interfaceResult);
                return mappingJacksonValue;
            }
            interfaceResult = InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
            mappingJacksonValue = new MappingJacksonValue(interfaceResult);
            return mappingJacksonValue;
        }
    }

    /**
     * 删除目录
     * @param debug
     * @param fields
     * @param directoryId
     * @param request
     * @return
     * @throws Exception
     */


    @RequestMapping(path = { "/directory/directory/deleteDirectory.json" }, method = { RequestMethod.GET, RequestMethod.DELETE })
    public MappingJacksonValue deleteDirectoryRoot(@RequestParam(name = DirectoryController.parameterDebug, defaultValue = "") String debug,
                                                   @RequestParam(name = DirectoryController.parameterFields, defaultValue = "") String fields,
                                                   @RequestParam(name = DirectoryController.parameterDirectoryId, required = true) Long directoryId,
                                                   HttpServletRequest request) throws Exception {
        return deleteMyDirectoriesTemp(debug, fields, directoryId, request ,false);
    }

    @RequestMapping(path = { "/directory/directory/deleteDirectory" }, method = { RequestMethod.GET, RequestMethod.DELETE })
    public MappingJacksonValue deleteDirectoriesOld(@RequestParam(name = DirectoryController.parameterDebug, defaultValue = "") String debug,
                                                   @RequestParam(name = DirectoryController.parameterFields, defaultValue = "") String fields,
                                                   @RequestParam(name = DirectoryController.parameterDirectoryId, required = true) Long directoryId,
                                                   HttpServletRequest request) throws Exception {
        return deleteMyDirectoriesTemp(debug, fields, directoryId, request ,true);
    }

    private MappingJacksonValue deleteMyDirectoriesTemp(String debug, String fields, Long directoryId, HttpServletRequest request, boolean isOld) throws Exception{

        MappingJacksonValue mappingJacksonValue = null;
        InterfaceResult interfaceResult = null;
        Map<String, Boolean> resultMap = new HashMap<String, Boolean>(2);
        Long loginUserId = this.getUserId(request);
        try {
            Long loginAppId = this.DefaultAppId;
            // 0.校验输入参数（框架搞定，如果业务业务搞定）
            Directory directory = directoryService.getDirectory(loginAppId, loginUserId, directoryId);
            long typeId = directory.getTypeId();
            Boolean b = directoryService.removeDirectory(loginAppId, loginUserId, directoryId);
            if (b && !isOld) {
                return returnMyDirectoriesTreeList(loginAppId, loginUserId, typeId, fields);
            }
            interfaceResult = InterfaceResult.getSuccessInterfaceResultInstance(b);
            // resultMap.put("success", b);
            // 2.转成框架数据
            mappingJacksonValue = new MappingJacksonValue(interfaceResult);
            return mappingJacksonValue;
        }  catch (Exception e) {
            if (isOld) {
                logger.error("delete directory failed! userId : " + loginUserId);
                interfaceResult = InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_DB_OPERATION_EXCEPTION);
                //resultMap.put("success", false);
                mappingJacksonValue = new MappingJacksonValue(interfaceResult);
                return mappingJacksonValue;
            }
            interfaceResult = InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
            mappingJacksonValue = new MappingJacksonValue(interfaceResult);
            return mappingJacksonValue;
        }
    }

    /**
     * 更新目录
     * @param fields
     * @param debug
     * @param directoryId
     * @param name
     * @param request
     * @return
     * @throws DirectoryServiceException
     */
    @RequestMapping(path = { "/directory/directory/updateDirectory" }, method = { RequestMethod.POST })
    public MappingJacksonValue updateDirectoryRoot(@RequestParam(name = DirectoryController.parameterFields, defaultValue = "") String fields,
                                                   @RequestParam(name = DirectoryController.parameterDebug, defaultValue = "") String debug,
                                                   @RequestParam(name = DirectoryController.parameterDirectoryId, required = true) Long directoryId,
                                                   @RequestParam(name = DirectoryController.parameterName, required = true) String name,
                                                   HttpServletRequest request) throws DirectoryServiceException {
        MappingJacksonValue mappingJacksonValue = null;
        InterfaceResult result = null;
        try {
            Long loginAppId = this.DefaultAppId;
            Long loginUserId = this.getUserId(request);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            mappingJacksonValue = assertNameLength(name);
            if (mappingJacksonValue != null) {
                return mappingJacksonValue;
            }
            Directory directory = new Directory();
            directory.setAppId(loginAppId);
            directory.setName(name);
            directory.setId(directoryId);
            List<Directory> directories = null;
            // 创建非根节点
            Directory dirDB = directoryService.getDirectory(loginAppId, loginUserId, directoryId);
            if (dirDB == null) {
                result = InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_DB_OPERATION_EXCEPTION);
                result.getNotification().setNotifInfo("没有该目录");
                mappingJacksonValue = new MappingJacksonValue(result);
                return mappingJacksonValue;
            }
            long pid = dirDB.getPid();
            if (pid != 0) {
                // 非一级目录重命名
                directories = directoryService.getDirectorysByParentId(loginAppId, loginUserId, pid);
            } else {
                // 一级目录重命名
                directories = directoryService.getDirectorysForRoot(loginAppId, loginUserId, dirDB.getTypeId());
            }
            MappingJacksonValue isDuplicate = assertDuplicateName(directories, directory);
            if (isDuplicate != null) {
                return isDuplicate;
            }
            Boolean b = directoryService.updateDirectory(loginAppId, loginUserId, directory);
            resultMap.put("success", b);
            // 2.转成框架数据
            mappingJacksonValue = new MappingJacksonValue(resultMap);
            return mappingJacksonValue;
        } catch (DirectoryServiceException e) {
            result = InterfaceResult.getInterfaceResultInstanceWithException(CommonResultCode.SYSTEM_EXCEPTION, e);
            mappingJacksonValue = new MappingJacksonValue(result);
            return mappingJacksonValue;
        }
    }

    /**
     * 移动目录 返回tree结构
     * @param fields
     * @param debug
     * @param directoryId
     * @param toDirectoryId
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(path = { "/directory/directory/moveDirectory" }, method = { RequestMethod.POST })
    public MappingJacksonValue moveDirectory(@RequestParam(name = DirectoryController.parameterFields, defaultValue = "") String fields,
                                             @RequestParam(name = DirectoryController.parameterDebug, defaultValue = "") String debug,
                                             @RequestParam(name = DirectoryController.parameterDirectoryId, required = true) Long directoryId,
                                             @RequestParam(name = DirectoryController.parameterToDirectoryId, required = true) Long toDirectoryId,
                                             HttpServletRequest request) throws Exception {
        MappingJacksonValue mappingJacksonValue = null;
        InterfaceResult interfaceResult = null;
        try {
            Long loginAppId = this.DefaultAppId;
            Long loginUserId = this.getUserId(request);
            interfaceResult = directoryService.moveDirectoryAndReturnTree(loginAppId, loginUserId, directoryId, toDirectoryId);
            // 2.转成框架数据
            mappingJacksonValue = new MappingJacksonValue(interfaceResult);
            SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fields);
            mappingJacksonValue.setFilters(filterProvider);
            return mappingJacksonValue;
        }  catch (Exception e) {
            interfaceResult = InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
            mappingJacksonValue = new MappingJacksonValue(interfaceResult);
            return mappingJacksonValue;
        }
    }

    /**
     * 移动目录 不返回tree结构
     * @param fields
     * @param debug
     * @param directoryId
     * @param toDirectoryId
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(path = { "/directory/directory/moveDirectory.json" }, method = { RequestMethod.POST })
    public MappingJacksonValue moveDirectoryNew(@RequestParam(name = DirectoryController.parameterFields, defaultValue = "") String fields,
                                             @RequestParam(name = DirectoryController.parameterDebug, defaultValue = "") String debug,
                                             @RequestParam(name = DirectoryController.parameterDirectoryId, required = true) Long directoryId,
                                             @RequestParam(name = DirectoryController.parameterToDirectoryId, required = true) Long toDirectoryId,
                                             HttpServletRequest request) throws Exception {
        MappingJacksonValue mappingJacksonValue = null;
        InterfaceResult interfaceResult = null;
        try {
            Long loginAppId = this.DefaultAppId;
            Long loginUserId = this.getUserId(request);
            interfaceResult = directoryService.moveDirectoryToDirectory(loginAppId, loginUserId, directoryId, toDirectoryId);
            // 2.转成框架数据
            mappingJacksonValue = new MappingJacksonValue(interfaceResult);
            SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fields);
            mappingJacksonValue.setFilters(filterProvider);
            return mappingJacksonValue;
        } catch (Exception e) {
            interfaceResult = InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
            mappingJacksonValue = new MappingJacksonValue(interfaceResult);
            return mappingJacksonValue;
        }
    }

    /**
     * 检查目录级别不能超过20级
     */
    private MappingJacksonValue checkOrderNo(int subOrderNo, int orderNo, int toOrderNo) {

        MappingJacksonValue mappingJacksonValue = null;
        InterfaceResult result = null;
        int level = subOrderNo - orderNo + toOrderNo + 1;
        if (level > 20) {
            result = InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
            result.getNotification().setNotifInfo("目录级别不能超过20级哦");
            mappingJacksonValue = new MappingJacksonValue(result);
        }
        return mappingJacksonValue;
    }
    /**
     * 查询根目录
     *
     * @param request
     * @return
     * @throws DirectoryServiceException
     */
    @RequestMapping(path = { "/directory/directory/getRootList" }, method = { RequestMethod.GET })
    public MappingJacksonValue getRootList(@RequestParam(name = DirectoryController.parameterFields, defaultValue = "") String fields,
                                           @RequestParam(name = DirectoryController.parameterDebug, defaultValue = "") String debug,
                                           @RequestParam(name = DirectoryController.parameterRootType, required = true) Long rootType,
                                           HttpServletRequest request) throws DirectoryServiceException {
        MappingJacksonValue mappingJacksonValue = null;
        try {
            Long loginAppId = this.DefaultAppId;
            Long loginUserId = this.getUserId(request);
            // 0.校验输入参数（框架搞定，如果业务业务搞定）
            // 1.查询后台服务
            List<Directory> directories = directoryService.getDirectorysForRoot(loginAppId, loginUserId, rootType);
            // 2.转成框架数据
            mappingJacksonValue = new MappingJacksonValue(directories);
            // 3.创建页面显示数据项的过滤器
            SimpleFilterProvider filterProvider = builderSimpleFilterProviderOld(fields);
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
            Long loginAppId = this.DefaultAppId;
            Long loginUserId = this.getUserId(request);
            // 0.校验输入参数（框架搞定，如果业务业务搞定）
            // 1.查询后台服务
            List<Directory> directories = directoryService.getDirectorysByParentId(loginAppId, loginUserId, pid);

            // 2.转成框架数据
            mappingJacksonValue = new MappingJacksonValue(directories);
            // 3.创建页面显示数据项的过滤器
            SimpleFilterProvider filterProvider = builderSimpleFilterProviderOld(fileds);
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
        InterfaceResult interfaceResult = null;
        Long loginAppId = this.DefaultAppId;
        Long loginUserId = this.getUserId(request);
        List<Directory> directories = null;
        List<Directory> directoryList = null;
        Map<Long, Directory> idMap = null;
        Map<String, Object> result = new HashMap(3);
        int total = 0;
        int totalSourceCount = 0;
        try {
            // 0.校验输入参数（框架搞定，如果业务业务搞定）
            // 1.查询后台服务
            if (directoryId == 0) {
                logger.info("-----------------mysql query-startTime-- :" + System.currentTimeMillis());
                //total = directoryService.getMyDirectoriesCount(loginAppId, loginUserId, typeId);
                directories = directoryService.getDirectoryListByUserIdType(loginAppId, loginUserId, typeId);
                logger.info("-----------------mysql query-endTime-- :" + System.currentTimeMillis());
                total = directories.size();
                //totalSourceCount = directorySourceService.getMyDirectoriesSouceCount(loginAppId, loginUserId, typeId);
                //directoryList = directoryService.getDirectoriesTreeByCache(loginUserId, typeId);
                if (CollectionUtils.isNotEmpty(directoryList)) {
                    result.put("totalCount", total);
                    result.put("list", directoryList);
                    result.put("sourceCount", totalSourceCount);
                    interfaceResult = InterfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
                    interfaceResult.setResponseData(result);
                    mappingJacksonValue = new MappingJacksonValue(interfaceResult);
                    // 3.创建页面显示数据项的过滤器
                    SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
                    mappingJacksonValue.setFilters(filterProvider);
                    // 4.返回结果
                    return mappingJacksonValue;
                }
                //下一版本 不再使用 temp
                //logger.info(loginUserId + " ----directories : " + directories);
            } else {
                directories = directoryService.getTreeDirectorysByParentId(loginAppId, loginUserId, directoryId, typeId);
                total = directoryService.getMySubDirectoriesCount(loginAppId, loginUserId, directoryId, typeId);
            }
            directoryList = new ArrayList<Directory>(total);
            idMap = new HashMap(total);
            // 判断空
            if (CollectionUtils.isNotEmpty(directories)) {
                // 2.将返回数据转为树形结构
                logger.info("--------------遍历排序 sort start ------------------ ：" + System.currentTimeMillis());
                for (Directory directory: directories) {
                    long id = directory.getId();
                    // 在移动目录（修改目录结构） 之后 目录的 childDirectory 会有数据缓存 设为null 则无缓存
                    if (CollectionUtils.isNotEmpty(directory.getChildDirectory())) {
                        directory.setChildDirectory(null);
                    }
                    //logger.info("*****" + directory.getName() + "**list{" + directory.getChildDirectory() + "}***");
                    //logger.info("---------------");
                    long pid = directory.getPid();
                    idMap.put(id, directory);
                }
                Map<Long, Directory> test = new HashMap<Long, Directory>(total);
                logger.info("1.start");
                for (Map.Entry<Long, Directory> map : idMap.entrySet()) {
                    Directory directory = map.getValue();
                    //logger.info("directory : [ " + directory.getName() + "********* list {" + directory.getChildDirectory() + "}*****]");
                    long pid = directory.getPid();
                    Directory parent = idMap.get(pid);
                    if (parent != null) {
                        //logger.info("parent : [" + parent.getName() + "********* list {" + parent.getChildDirectory() + "}***********]");
                        parent.addChildList(directory);
                        //logger.info("child list [: " + parent.getChildDirectory() + "------------------]");
                        // 将目录按照拼音 自定义排序
                        Collections.sort(parent.getChildDirectory(), new PinyinComparatorList4ObjectName());
                        test.put(parent.getId(), parent);
                    } else {
                        test.put(directory.getId(), directory);
                    }
                }
                logger.info("1.end");
                for (Map.Entry<Long, Directory> map : test.entrySet()) {
                    Directory dir = map.getValue();
                    if (dir.getPid() == 0) {
                        directoryList.add(dir);
                        //logger.info(dir.getName() + " sourceCount : " + dir.getSourceCount());
                    }
                }
            }
            // 将目录按照拼音 自定义排序
            Collections.sort(directoryList, new PinyinComparatorList4ObjectName());
            // 将排好序的 tree 目录同步到 mongoDB
            // directoryService.saveDirectoriesTreeByCache(directoryList);
            // 2.转成框架数据
            logger.info("--------------遍历排序 sort end ------------------ ：" + System.currentTimeMillis());
            result.put("totalCount", total);
            result.put("list", directoryList);
            result.put("sourceCount", totalSourceCount);
            interfaceResult = InterfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
            interfaceResult.setResponseData(result);
            mappingJacksonValue = new MappingJacksonValue(interfaceResult);
            // 3.创建页面显示数据项的过滤器
            SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
            mappingJacksonValue.setFilters(filterProvider);
            // 4.返回结果
            return mappingJacksonValue;
        } catch (Error o) {
            interfaceResult = InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION, "OOM error");
            mappingJacksonValue = new MappingJacksonValue(interfaceResult);
            logger.error("OOM: 1." + o.getCause());
            logger.error("OOM: 2." + o.getMessage());
            return mappingJacksonValue;
        } catch (Exception e) {
            interfaceResult = InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
            mappingJacksonValue = new MappingJacksonValue(interfaceResult);
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
     * @param fields
     * @return
     */
    private SimpleFilterProvider builderSimpleFilterProvider(String fields) {
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        // 请求指定字段
        String[] filedNames = StringUtils.split(fields, ",");
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
            filter.add("appId"); // '应用ID',
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

    /**
     * 指定显示那些字段 老版本
     *
     * @param fields
     * @return
     */
    private SimpleFilterProvider builderSimpleFilterProviderOld(String fields) {
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        // 请求指定字段
        String[] filedNames = StringUtils.split(fields, ",");
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
            filter.add("appId"); // '应用ID',
            filter.add("userId"); // '应用的创建者ID',
            filter.add("sourceCount"); //资源数
        }

        filterProvider.addFilter(Directory.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept(filter));
        return filterProvider;
    }

    private MappingJacksonValue returnMyDirectoriesTreeList(long loginAppId, long loginUserId, long typeId, String fileds) throws Exception{

        MappingJacksonValue mappingJacksonValue = null;
        Map<String, Object> result = new HashMap(3);
        List<Directory> directoryList = null;
        //int total = directoryService.getMyDirectoriesCount(loginAppId, loginUserId, typeId);
        //int totalSourceCount = directorySourceService.getMyDirectoriesSouceCount(loginAppId, loginUserId, typeId);
        try {
            logger.info("------getDirectoriesTreeByCache------start---time : " + System.currentTimeMillis());
            directoryList = directoryService.getDirectoriesTreeByCache(loginUserId, typeId);
            logger.info("------getDirectoriesTreeByCache------end---time : " + System.currentTimeMillis());
        } catch (Exception e) {
            logger.error("invoke directoryService failed, method : [ getDirectoriesTreeByCache ]. userId :" + loginUserId);
        }
        if (CollectionUtils.isNotEmpty(directoryList)) {
            result.put("totalCount", 0);
            result.put("list", directoryList);
            result.put("sourceCount", 0);
            InterfaceResult interfaceResult = InterfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
            interfaceResult.setResponseData(result);
            mappingJacksonValue = new MappingJacksonValue(interfaceResult);
            SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
            mappingJacksonValue.setFilters(filterProvider);
            return mappingJacksonValue;
        }
        logger.info("------byCache------end---time : " + System.currentTimeMillis());
        List<Directory> directories = directoryService.getDirectoryListByUserIdType(loginAppId, loginUserId, typeId);
        int size = directories.size();
        Map<Long, Directory> idMap = new HashMap<Long, Directory>(size);
        directoryList = new ArrayList<Directory>(size);
        for (Directory direc: directories) {
            long dirId = direc.getId();
            long pid = direc.getPid();
            //根目录下所有目录
            if (pid == 0) {
                directoryList.add(direc);
            }
            idMap.put(dirId, direc);
        }
        for (Map.Entry<Long, Directory> map : idMap.entrySet()) {
            Directory dire = map.getValue();
            long pid = dire.getPid();
            Directory parent = idMap.get(pid);
            if (parent != null) {
                parent.addChildList(dire);
                // 将目录按照拼音 自定义排序
                Collections.sort(parent.getChildDirectory(), new PinyinComparatorList4ObjectName());
            }
        }
        // 将目录按照拼音 自定义排序
        Collections.sort(directoryList, new PinyinComparatorList4ObjectName());
        // 将排好序的目录 同步到 mongo 中 （一般情况执行此代码是因为 第一次加载目录mongo中没数据）
        logger.info("-----saveDirectoriesTreeByCache------start time ---" + System.currentTimeMillis());
        directoryService.saveDirectoriesTreeByCache(directoryList);
        logger.info("-----saveDirectoriesTreeByCache------end time ---" + System.currentTimeMillis());
        // 2.转成框架数据
        result.put("totalCount", size);
        result.put("list", directoryList);
        result.put("sourceCount", 0);
        InterfaceResult interfaceResult = InterfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
        interfaceResult.setResponseData(result);
        mappingJacksonValue = new MappingJacksonValue(interfaceResult);
        SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    /**
     * 检查是否重名 ：1.同一父目录下的子目录名称不能重复
     *              2.根目录下目录不能重名
     * @param directories
     * @param directory
     * @return
     */
    private MappingJacksonValue assertDuplicateName(List<Directory> directories, Directory directory) {

        MappingJacksonValue mappingJacksonValue = null;
        if (!CollectionUtils.isEmpty(directories)) {
            for (Directory dir : directories) {
                if (dir != null && dir.getName().equalsIgnoreCase(directory.getName())) {
                    InterfaceResult interfaceResult = InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
                    interfaceResult.getNotification().setNotifInfo("已经有同名的目录了哦");
                    mappingJacksonValue = new MappingJacksonValue(interfaceResult);
                }
            }
        }
        return mappingJacksonValue;
    }

    /**
     * 检查名字 长度
     * 不能超过 20
     * @param name
     * @return
     */
    private MappingJacksonValue assertNameLength(String name) {

        MappingJacksonValue mappingJacksonValue = null;
        if (name.length() > 20) {
            InterfaceResult interfaceResult = InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
            interfaceResult.getNotification().setNotifInfo("目录名称最长不能超过20字哦");
            mappingJacksonValue = new MappingJacksonValue(interfaceResult);
        }
        return mappingJacksonValue;
    }
}
