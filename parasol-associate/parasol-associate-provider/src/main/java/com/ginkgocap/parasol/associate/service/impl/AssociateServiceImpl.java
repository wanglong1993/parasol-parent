package com.ginkgocap.parasol.associate.service.impl;

import java.util.*;

import com.ginkgocap.parasol.associate.model.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.associate.exception.AssociateServiceException;
import com.ginkgocap.parasol.associate.exception.AssociateTypeServiceException;
import com.ginkgocap.parasol.associate.model.Associate;
import com.ginkgocap.parasol.associate.model.AssociateType;
import com.ginkgocap.parasol.associate.service.AssociateService;
import com.ginkgocap.parasol.associate.service.AssociateTypeService;
import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;

/**
 * 
 * @author allenshen
 * @date 2015年11月24日
 * @time 下午4:26:56
 * @Copyright Copyright©2015 www.gintong.com
 */
@Service("associateService")
public class AssociateServiceImpl extends BaseService<Associate> implements AssociateService {
    private static Logger logger = Logger.getLogger(AssociateServiceImpl.class);
    private final static int LEN_NAME = 50;
    private final static int LEN_ASSOCIATES = 500;

    private static String LIST_ASSOCIATE_ID_APPID_SOURCEID = "List_Associate_Id_AppId_SourceId";
    private static String LIST_ASSOCIATE_ID_APPID_SOURCETYPEID_SOURCEID = "List_Associate_Id_AppId_SourceTypeId_SourceId"; // 查询一个应用的分类根收藏夹

    private static String List_Associate_userId_AssociateType = "List_Associate_userId_AssociateType";
    private static String List_Associate_userId_assocId = "List_Associate_userId_assocId";

    private static String List_Associate_userId_AssociateTwoType = "List_Associate_userId_AssociateTwoType";
    @Autowired
    private AssociateTypeService associateTypeService;

    @Override
    public Long createAssociate(Long appId, Long userId, Associate associate) throws AssociateServiceException {
        // 1.check input parameters(appId, typeId, name，userId)
        ServiceError.assertAssociateForAssociate(associate);
        // 2.check appId
        ServiceError.assertAppIdForAssociate(appId);
        // 3.check userId
        ServiceError.assertUserIdForAssociate(userId);

        // 4.check Object properties
        for (String properName : new String[]{"appId", "sourceTypeId", "sourceId", "assocDesc", "assocTypeId", "assocId", "assocTitle"}) {
            ServiceError.assertPopertiesIsNullForAssociate(properName);
        }

        // 5. check isMyself
        if (!ObjectUtils.equals(appId, associate.getAppId()) || !ObjectUtils.equals(userId, associate.getUserId())) {
            throw new AssociateServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non ower associate!");
        }
        // 6. check assocType
        try {
            AssociateType associateTypeDict = associateTypeService.getAssociateType(appId, associate.getAssocTypeId());
            if (associateTypeDict == null) {
                throw new AssociateServiceException(ServiceError.ERROR_NOT_FOUND, "the AssociateType not find by associateTypeId [ " + associate.getAssocTypeId() + "]");
            }
        } catch (AssociateTypeServiceException e) {
            e.printStackTrace(System.err);
            throw new AssociateServiceException(e);
        }
        // 7. is exist?
        // @formatter:off
        Map<AssociateType, List<Associate>> map = getAssociatesBy(appId, associate.getSourceTypeId(), associate.getSourceId());
        if (MapUtils.isNotEmpty(map)) {
            for (AssociateType associateType : map.keySet()) {
                if (ObjectUtils.equals(associateType.getId(), associate.getSourceTypeId())) {
                    List<Associate> associates = map.get(associateType);
                    if (CollectionUtils.isNotEmpty(associates)) {
                        for (Associate existAssoc : associates) {
                            if (existAssoc != null &&
                                    (
                                            ObjectUtils.equals(existAssoc.getAssocTypeId(), associate.getAssocTypeId()) &&
                                                    ObjectUtils.equals(existAssoc.getAssocId(), associate.getAssocId())
                                    )
                                    ) {
                                throw new AssociateServiceException(ServiceError.ERROR_DUPLICATE, "the assocId[" + associate.getAssocId() + "] and assocType[" + associate.getAssocTypeId() + "]  already exists");
                            }
                        }
                    }
                }
            }
        }
        // @formatter:on

        try {
            associate.setCreateAt(System.currentTimeMillis());
            return (Long) this.saveEntity(associate);
        } catch (BaseServiceException e) {
            e.printStackTrace(System.err);
            throw new AssociateServiceException(e);
        }
    }

    @Override
    public boolean removeAssociate(Long appId, Long userId, Long associateId) throws AssociateServiceException {
        // 1.check appId
        ServiceError.assertAppIdForAssociate(appId);
        // 2.check userId
        ServiceError.assertUserIdForAssociate(userId);

        Associate associate = this.getAssociate(appId, userId, associateId);
        if (associate == null) {
            throw new AssociateServiceException(ServiceError.ERROR_NOT_FOUND, "remove " + associateId + " associate not exist");
        }
        //3. check is myself
        if (!ObjectUtils.equals(associate.getAppId(), appId) || !ObjectUtils.equals(associate.getUserId(), userId)) {
            throw new AssociateServiceException(ServiceError.ERROR_NOT_MYSELF, "Operation of the non own associate");// 删除的不是自己的收藏夹
        }
        try {
            return deleteEntity(associateId);
        } catch (BaseServiceException e) {
            e.printStackTrace(System.err);
            throw new AssociateServiceException(e);
        }

    }

    @Override
    public Associate getAssociate(Long appId, Long userId, Long associateId) throws AssociateServiceException {
        ServiceError.assertAppIdForAssociate(appId);
        ServiceError.assertUserIdForAssociate(userId);
        if (associateId == null) {
            return null;
        }
        try {
            return getEntity(associateId);
        } catch (BaseServiceException e) {
            e.printStackTrace(System.err);
            throw new AssociateServiceException(e);
        }
    }

    @Override
    public List<Associate> getAssociatesBySourceId(Long appId, Long userId, Long sourceId) throws AssociateServiceException {
        ServiceError.assertAppIdForAssociate(appId);
        ServiceError.assertUserIdForAssociate(userId);
        if (sourceId == null || sourceId <= 0) {
            return null;
        }
        try {
            return getEntitys(LIST_ASSOCIATE_ID_APPID_SOURCEID, new Object[]{appId, sourceId});
        } catch (BaseServiceException e) {
            e.printStackTrace(System.err);
            throw new AssociateServiceException(e);
        }
    }

    @Override
    public Map<AssociateType, List<Associate>> getAssociatesBy(Long appId, Long sourceType, Long sourceId) throws AssociateServiceException {
        try {
            List<Associate> associates = this.getSubEntitys(LIST_ASSOCIATE_ID_APPID_SOURCETYPEID_SOURCEID, 0, LEN_ASSOCIATES, appId, sourceType, sourceId);
            Map<Long, List<Associate>> tempMap = new HashMap<Long, List<Associate>>();
            if (CollectionUtils.isNotEmpty(associates)) {
                for (Associate associate : associates) {
                    if (associate != null) {
                        Long assocTypeId = associate.getAssocTypeId();
                        List<Associate> classificationList = tempMap.get(assocTypeId);
                        if (classificationList == null) {
                            classificationList = new ArrayList<Associate>();
                            tempMap.put(assocTypeId, classificationList);
                        }
                        classificationList.add(associate);
                    }
                }
            }

            Map<AssociateType, List<Associate>> resultMap = new HashMap<AssociateType, List<Associate>>();
            if (MapUtils.isNotEmpty(tempMap)) {
                for (Long associTypeId : tempMap.keySet()) {
                    AssociateType associateType = associateTypeService.getAssociateType(appId, associTypeId);
                    if (associateType != null && CollectionUtils.isNotEmpty(tempMap.get(associTypeId))) {
                        resultMap.put(associateType, tempMap.get(associTypeId));
                    }
                }
            }
            return resultMap;
        } catch (BaseServiceException e) {
            e.printStackTrace(System.err);
            throw new AssociateServiceException(e);
        } catch (AssociateTypeServiceException e) {
            e.printStackTrace(System.err);
            throw new AssociateServiceException(e);
        }
    }

    @Override
    public Page<Associate> getassociatesByPage(Long userId, Long typeId, int pageNo, int pageSize)
            throws AssociateServiceException {
        Page<Associate> page = new Page();
        ServiceError.assertUserIdForAssociate(userId);
        ServiceError.assertAssociateTypeIdForAssociate(typeId);

        try {
            int index = pageNo * pageSize;
            long totalCount = countEntitys(List_Associate_userId_AssociateType, userId, typeId);

            page.setTotalCount(totalCount);
            page.setPageNo(pageNo);
            page.setPageSize(pageSize);

            List<Associate> list = getSubEntitys(List_Associate_userId_AssociateType, index, pageSize, userId, typeId);
            page.setList(list);
            return page;
        } catch (BaseServiceException e) {
            e.printStackTrace(System.err);
            throw new AssociateServiceException(e);

        }
    }

    /**
     * @param userId
     * @param typeId
     * @return
     * @throws AssociateServiceException
     */
    @Override
    public Page<Map<String, Object>> getAssociatesByPage(Long userId, Long typeId, int page, int size) throws AssociateServiceException {
        ServiceError.assertUserIdForAssociate(userId);
        ServiceError.assertAssociateTypeIdForAssociate(typeId);
        Page<Map<String, Object>> response = new Page<Map<String, Object>>();
        logger.info("userId: " + userId + " typeId: " + typeId + " page: " + page + " size: " + size);
        long totalCount = 0;
        List<Associate> associateList = null;
        try {
            int index = page*size;
            if (typeId == 1L || typeId == 2L) {
                totalCount = countEntitys(List_Associate_userId_AssociateTwoType, userId, 1L, 2L);
                associateList = this.getSubEntitys(List_Associate_userId_AssociateTwoType, index, size, userId, 1L, 2L);
            } else if (typeId == 3L || typeId == 4L) {
                totalCount = countEntitys(List_Associate_userId_AssociateTwoType, userId, 3L, 4L);
                associateList = this.getSubEntitys(List_Associate_userId_AssociateTwoType, index, size, userId, 3L, 4L);
            } else {
                totalCount = countEntitys(List_Associate_userId_AssociateType, userId, typeId);
                associateList = this.getSubEntitys(List_Associate_userId_AssociateType, index, size, userId, typeId);
            }

            if (CollectionUtils.isEmpty(associateList)) {
                return null;
            }

            Set<String> assocSet = new HashSet<String>();
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(associateList.size());
            for (Associate assoc : associateList) {
                final String key = assoc.getAssocId() + "-" + assoc.getAssocTypeId();
                if (assocSet.contains(key)) {
                    logger.info("As this associate have added to list, so skip. key: " + key);
                    continue;
                }
                if (assoc != null) {
                    long count = countEntitys(List_Associate_userId_assocId, userId, assoc.getAssocId());
                    Map<String, Object> map = new HashedMap(2);
                    map.put("assoc", assoc);
                    map.put("count", count);
                    list.add(map);
                    assocSet.add(key);
                }
            }

            response.setTotalCount(totalCount);
            response.setPageNo(page);
            response.setPageSize(size);
            response.setList(list);

            return response;
        } catch (BaseServiceException e) {
            e.printStackTrace(System.err);
            throw new AssociateServiceException(e);
        }
    }
}
