package org.parasol.column.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.parasol.column.api.model.ColumnFlag;
import org.parasol.column.dao.ColumnSelfDao;
import org.parasol.column.entity.ColumnCustom;
import org.parasol.column.entity.ColumnCustomExample;
import org.parasol.column.entity.ColumnSelf;
import org.parasol.column.entity.ColumnSys;
import org.parasol.column.service.ColumnCustomService;
import org.parasol.column.service.ColumnSelfService;
import org.parasol.column.service.ColumnSysService;
import org.springframework.stereotype.Component;

@Component("columnSelfService")
public class ColumnSelfServiceImpl implements ColumnSelfService {

	private static final Log log = LogFactory.getLog(ColumnSelfServiceImpl.class);
	
	@Resource
	private ColumnSelfDao columnSelfDao;
	
	@Resource(name ="columnSysService")
	private ColumnSysService columnSysService;
	
	@Resource(name ="columnCustomService")
	private ColumnCustomService columnCustomService;
	
	private ColumnCustom loadByCid(Long id){
		ColumnCustomExample exa=new ColumnCustomExample();
		ColumnCustomExample.Criteria c=exa.createCriteria();
		c.andCidEqualTo(id);
		ColumnCustom custom = columnCustomService.queryByCid(id);
		return custom;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		//int n= csd.deleteByPrimaryKey(id);
		ColumnSelf self=this.selectByPrimaryKey(id);
		if(self.getUserOrSystem().shortValue() == ColumnFlag.sys.getVal()){
			return 0;
		}
		self.setDelStatus((short)1);
		int n = this.updateByPrimaryKey(self);
		if(n > 0){
			ColumnCustom custom=this.loadByCid(id);
			if(custom != null){
				columnCustomService.deleteByPrimaryKey(custom.getId());
			}
		}
		return n;
	}

	@Override
	public ColumnSelf insert(ColumnSelf record) {
		// TODO Auto-generated method stub
		int n = columnSelfDao.insert(record);
		if(n>0&&record.getUserOrSystem().shortValue() == ColumnFlag.user.getVal()){
			ColumnCustom dest=new ColumnCustom();
			try {
				BeanUtils.copyProperties(dest,record);
				dest.setCid(record.getId());
				dest.setPcid(record.getParentId());
				dest.setId(null);
				columnCustomService.insert(dest);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				log.error(e);
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				log.error(e);
			}
		}
		return record;
	}

	@Override
	public ColumnSelf selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return columnSelfDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(ColumnSelf record) {
		// TODO Auto-generated method stub
		ColumnSelf self = columnSelfDao.selectByPrimaryKey(record.getId());
		if(self.getUserOrSystem().shortValue() == ColumnFlag.sys.getVal()){
			return 0;
		}
		int n = columnSelfDao.updateByPrimaryKey(record);
		if(n>0){
			ColumnCustom custom = this.loadByCid(record.getId());
			if(custom != null){
				Long id=custom.getId();
				try {
					BeanUtils.copyProperties(custom,record);
					custom.setCid(record.getId());
					custom.setId(id);
					columnCustomService.updateByPrimaryKey(custom);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					log.error(e);
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					log.error(e);
				}
				
			}
		}
		return n;
	}

	@Override
	public List<ColumnSelf> queryListByPidAndUserId(Long pid, Long uid) {
		// TODO Auto-generated method stub
		return columnSelfDao.queryListByPidAndUserId(pid, uid);
	}

	private ColumnSelf buidColumnSelf(ColumnSys source) throws Exception{
		ColumnSelf dest = new ColumnSelf();
		BeanUtils.copyProperties(dest,source);
		dest.setId(null);
		dest.setSysColId(source.getId());
		return dest;
	}

	@Override
	public ColumnSelf selectMaxOrderColumn(Long pid, Long uid) {
		// TODO Auto-generated method stub
		return columnSelfDao.selectMaxOrderColumn(pid, uid);
	}

	@Override
	public List<ColumnSelf> queryListByPid(Long pid) {
		// TODO Auto-generated method stub
		return columnSelfDao.queryListByPid(pid);
	}

}
