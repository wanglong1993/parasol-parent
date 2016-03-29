package com.ginkgocap.parasol.mapping.service.impl;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.mapping.enumtype.MappingType;
import com.ginkgocap.parasol.mapping.exception.MappingServiceException;
import com.ginkgocap.parasol.mapping.model.Mapping;
import com.ginkgocap.parasol.mapping.service.MappingService;

/**
 * 
 * @author allenshen
 * @date 2015年11月24日
 * @time 下午4:26:56
 * @Copyright Copyright©2015 www.gintong.com
 */
@Service("mappingService")
public class MappingServiceImpl extends BaseService<Mapping> implements MappingService {
	private static Logger logger = Logger.getLogger(MappingServiceImpl.class);
	private static final String MAP_MAPPING_ID_OPENID_IDTYPE = "Map_Mapping_Id_OpenId_IdType";
	private static final String MAP_MAPPING_ID_UID_IDTYPE = "Map_Mapping_Id_UId_IdType";

	private static final int MAX_TAG = 300; // 最多创建的标签数量
	private static final int MAX_LEN_TAG = 30; // Tag的长度30个字符

	@Override
	public Long saveMapping(Long openId, Long uId, MappingType type) throws MappingServiceException {
		Long resultId = null;
		ServiceError.assertInputMappingType(type);
		ServiceError.assertOpenIdAndUidNull(openId, uId); // openId 和
															// uId两个参数中必须有一个有值
		// 操作的数据记录
		StringBuffer record = new StringBuffer();
		record.append("openId: ").append(openId).append(", ");
		record.append("uId: ").append(uId).append(", ");
		record.append("idType: ").append(type.value()).append("");
		logger.info("begin process record -> " + record.toString());

		StringBuffer sb = null;

		Long id = null;
		try {
			id = openId != null && openId > 0 ? (Long) this.getMapId(MAP_MAPPING_ID_OPENID_IDTYPE, openId, type.value()) : null;
			id = id == null && uId != null && uId > 0 ? (Long) this.getMapId(MAP_MAPPING_ID_UID_IDTYPE, uId, type.value()) : id;
			resultId = id;
			if (id != null && id > 0l) { // 已经存在了。
				Mapping mapping = getEntity(id);
				if (mapping != null) { // 说明是更新Map
					if (ObjectUtils.equals(mapping.getuId(), uId) && ObjectUtils.equals(mapping.getOpenId(), openId)) { // 说明已经存在了,啥都不用做了，打印个日志吧
						if (logger.isDebugEnabled()) {
							logger.debug(record + " Already exist.");
						}
					} else { // 那就是更新了。
						if (logger.isDebugEnabled()) {
							logger.debug(record + " Update idType.");
						}

						if (uId != null && uId > 0 && mapping.getuId() <= 0l) {
							id =  (Long) this.getMapId(MAP_MAPPING_ID_UID_IDTYPE, uId, type.value());
							if (id != null) { //已经存在了一个Uid，删除掉。
								this.deleteEntity(id);
							}
							mapping.setuId(uId);
						}
						
						if (openId != null && openId > 0 && mapping.getOpenId() <= 0l) {
							mapping.setOpenId(openId);
						}
						
						this.updateEntity(mapping);
					}
				} else {
					sb = new StringBuffer();
					sb.append("find id [").append(id).append("] by OpenId [").append(openId).append("], but don't find entity!!!!!");
					logger.error(sb.toString());
				}
			} else {
				// 创建一个新的对应记录
				Mapping mapping = new Mapping();
				if (openId != null && openId > 0l) {
					mapping.setOpenId(openId);
				}

				if (uId != null && uId > 0l) {
					mapping.setuId(uId);
				}
				mapping.setIdType(type.value());
				resultId = (Long) this.saveEntity(mapping);
			}

		} catch (BaseServiceException e) {
			logger.error("exception process record -> " + record.toString());
			e.printStackTrace(System.err);
		}
		return resultId;
	}

	@Override
	public Long getUid(Long openId, MappingType type) throws MappingServiceException {
		ServiceError.assertOpenIdAndMappingTypeNull(openId, type);

		try {
			Long id = (Long) this.getMapId(MAP_MAPPING_ID_OPENID_IDTYPE, openId, type.value());
			if (id != null && id > 0) {
				Mapping mapping = this.getEntity(id);
				return mapping == null ? null : mapping.getuId();
			}
			return null;
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new MappingServiceException(e);
		}
	}

	@Override
	public Long getOpenId(Long uId, MappingType type) throws MappingServiceException {
		ServiceError.assertUIdAndMappingTypeNull(uId, type);
		try {
			Long id = (Long) this.getMapId(MAP_MAPPING_ID_UID_IDTYPE, uId, type.value());
			if (id != null && id > 0) {
				Mapping mapping = this.getEntity(id);
				return mapping == null ? null : mapping.getOpenId();
			}
			return null;
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new MappingServiceException(e);
		}
	}

}
