package com.ginkgocap.parasol.common.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.ywxt.framework.dal.dao.Dao;
import com.ginkgocap.ywxt.framework.dal.dao.exception.DaoException;
/**
 * 
 * @author allenshen
 * @date 2015年11月12日
 * @time 上午9:51:27
 * @Copyright Copyright©2015 www.gintong.com
 * @param <T>
 */
public abstract class BaseService<T> {
	private static Logger logger = Logger.getLogger(BaseService.class);
	protected static final int TAKE_SIZE = 500; 
	private Class<T> entitlClass;
	
	@Autowired(required = true)
	private Dao dao;

	@SuppressWarnings("unchecked")
	public Class<T> getEntityClass() {
		if (entitlClass == null) {
			Type type = getClass().getGenericSuperclass();
	        Type[] params = ((ParameterizedType) type).getActualTypeArguments();  
	        entitlClass = (Class<T>) params[0]; 
		}
		return entitlClass;
	}
	
	@SuppressWarnings("unchecked")
	protected T getEntity(Serializable id) throws BaseServiceException {
		try {
			if (id != null) {
				return (T) dao.get(getEntityClass(), id);
			} else {
				logger.error("find entity by null id");
				return null;
			}
		} catch (DaoException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new BaseServiceException(e);

		}
	}
	
	
	protected Object getMapId(String mapping_region, Object ...parameters) throws BaseServiceException {
		try {
			if (ArrayUtils.isNotEmpty(parameters)) {
				return dao.getMapping(mapping_region, parameters);
			} else {
				logger.error("parameters is null or empty");
				return null;
			}
		} catch (DaoException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new BaseServiceException(e);

		}
	}
	
	
	

	protected Serializable saveEntity(T entity) throws BaseServiceException {
		try {
			if (entity != null) {
				return dao.save(entity);
			} else {
				logger.error("save entity is null");
				return null;
			}
		} catch (DaoException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new BaseServiceException(e);
		}
	}

	@SuppressWarnings("unchecked")
	protected List<Serializable> saveEntitys(List<T> entitys) throws BaseServiceException {
		try {
			if (CollectionUtils.isNotEmpty(entitys)) {
				return dao.batchSave(entitys);
			} else {
				return ListUtils.EMPTY_LIST;
			}
		} catch (DaoException e) {
			e.printStackTrace(System.err);
			throw new BaseServiceException(e);
		}
	}
	protected Boolean deleteEntity(Serializable id) throws BaseServiceException {
		try {
			if (id != null) {
				return dao.delete(getEntityClass(), id);
			} else {
				logger.error("delete entity by null id");
			}
			return false;
		} catch (DaoException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new BaseServiceException(e);
		}
	}
	protected Boolean fakeDeleteEntity(Serializable id) throws BaseServiceException {
		try {
			if (id != null) {
				return dao.fakeDelete(getEntityClass(), id);
			} else {
				logger.error("delete entity by null id");
			}
			return false;
		} catch (DaoException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new BaseServiceException(e);
		}
	}
	protected Boolean updateEntity(Serializable entity) throws BaseServiceException {
		try {
			if (entity != null) {
				return dao.update(entity);
			} else {
				logger.error("update null entity");
				return false;
			}
		} catch (DaoException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new BaseServiceException(e);
		}
	}


	@SuppressWarnings("unchecked")
	protected List<T> getEntitys(String region_name, Object... parameters) throws BaseServiceException {
		if (ArrayUtils.isEmpty(parameters)) {
			logger.error("getEntity parameters is null or empty");
			return ListUtils.EMPTY_LIST;
		} else {
			try {

				List<Long> ids = dao.getIdList(region_name, parameters);
				if (CollectionUtils.isNotEmpty(ids)) {
					return dao.getList(getEntityClass(), ids);
				} else {
					logger.warn("ids is empty or null in " + region_name + " by " + ArrayUtils.toString(parameters));
					return ListUtils.EMPTY_LIST;
				}
			} catch (DaoException e) {
				if (logger.isDebugEnabled()) {
					e.printStackTrace(System.err);
				}
				throw new BaseServiceException(e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected List<T> getSubEntitys(String region_name, int start, int count, Object... parameters) throws BaseServiceException {
		
		if (start < 0 || count == 0 || count < 0 ) {
			logger.error("getSubEntitys start:" + ObjectUtils.toString(start) + " count:" + ObjectUtils.toString(count));
			return ListUtils.EMPTY_LIST;
		}
		
		if (ArrayUtils.isEmpty(parameters)) {
			logger.error("getEntity parameters is null or empty");
			return ListUtils.EMPTY_LIST;
		} else {
			try {

				List<Long> ids = dao.getIdList(region_name, parameters, start, count);
				if (CollectionUtils.isNotEmpty(ids)) {
					return dao.getList(getEntityClass(), ids);
				} else {
					logger.warn("ids is empty or null in " + region_name + " by " + ArrayUtils.toString(parameters));
					return ListUtils.EMPTY_LIST;
				}
			} catch (DaoException e) {
				if (logger.isDebugEnabled()) {
					e.printStackTrace(System.err);
				}
				throw new BaseServiceException(e);
			}
		}
	}

	protected int countEntitys(String region_name, Object... parameters) throws BaseServiceException {

		if (ArrayUtils.isEmpty(parameters)) {
			logger.error("countEntitys parameters is null or empty");
			return 0;
		} else {
			try {
				return dao.count(region_name, parameters);
			} catch (DaoException e) {
				if (logger.isDebugEnabled()) {
					e.printStackTrace(System.err);
				}
				throw new BaseServiceException(e);
			}
		}

	}
	
	
	
	@SuppressWarnings("unchecked")
	protected List<Long> getIds(String region_name, Object... parameters) throws BaseServiceException {
		if (ArrayUtils.isEmpty(parameters)) {
			logger.error("getIds parameters is null or empty");
			return ListUtils.EMPTY_LIST;
		} else {
			try {
				List<Long> ids = dao.getIdList(region_name, parameters);
				return ids == null ? ListUtils.EMPTY_LIST: ids;
			} catch (DaoException e) {
				if (logger.isDebugEnabled()) {
					e.printStackTrace(System.err);
				}
				throw new BaseServiceException(e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected List<Long> getIds(String region_name, int start, int count, Object... parameters) throws BaseServiceException {
		if (ArrayUtils.isEmpty(parameters)) {
			logger.error("getIds parameters is null or empty");
			return ListUtils.EMPTY_LIST;
		} else {
			try {

				List<Long> ids = dao.getIdList(region_name, parameters, start, count);
				return ids == null ? ListUtils.EMPTY_LIST: ids;
			} catch (DaoException e) {
				if (logger.isDebugEnabled()) {
					e.printStackTrace(System.err);
				}
				throw new BaseServiceException(e);
			}
		}
	}
	
	protected int deleteList(String region_name, Object ...parameters) throws BaseServiceException{
		int iCount = countEntitys(region_name, parameters);
		int iResult = 0;
		if (iCount > 0 ) {
			int batchSize = 50;
			int batchCount = iCount / batchSize; 
			batchCount = iCount % batchSize == 0 ? batchCount : (batchCount + 1);
			
			for (int i = 0; i < batchCount; i++) {
				List<Long> ids = getIds(region_name, i * batchSize, batchSize,parameters);
				if (CollectionUtils.isNotEmpty(ids)) {
					for (Iterator<Long> iterator = ids.iterator(); iterator.hasNext();) {
						Long id = iterator.next();
						if (id != null) {
							//executorService.execute(command);
							deleteEntity(id);
							iResult += 1;
						}
					}
				}
			}
		}
		return iResult;
	}
}
