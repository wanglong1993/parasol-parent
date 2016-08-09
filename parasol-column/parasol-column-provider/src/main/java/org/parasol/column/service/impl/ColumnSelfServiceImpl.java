package org.parasol.column.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
import org.parasol.column.entity.ColumnSelfExample;
import org.parasol.column.entity.ColumnSys;
import org.parasol.column.service.ColumnCustomService;
import org.parasol.column.service.ColumnSelfService;
import org.parasol.column.service.ColumnSysService;
import org.springframework.stereotype.Component;

@Component("columnSelfService")
public class ColumnSelfServiceImpl implements ColumnSelfService {

	private static final Log log = LogFactory.getLog(ColumnSelfServiceImpl.class);
	
	@Resource
	private ColumnSelfDao csd;
	
	@Resource(name="columnSysService")
	private ColumnSysService css;
	
	@Resource(name="columnCustomService")
	private ColumnCustomService ccs;
	
	private ColumnCustom loadByCid(Long id){
		ColumnCustom cc=ccs.queryByCid(id);
		return cc;
	}
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		//int n= csd.deleteByPrimaryKey(id);
		ColumnSelf cs=this.selectByPrimaryKey(id);
		if(cs.getUserOrSystem().shortValue()==ColumnFlag.sys.getVal()){
			return 0;
		}
		cs.setDelStatus((short)1);
		int n = this.updateByPrimaryKey(cs);
		if(n>0){
			ColumnCustom cc=this.loadByCid(id);
			if(cc!=null){
				ccs.deleteByPrimaryKey(cc.getId());
			}
		}
		return n;
	}

	@Override
	public ColumnSelf insert(ColumnSelf record) {
		int n = csd.insert(record);
		if(n>0&&record.getUserOrSystem().shortValue()==ColumnFlag.user.getVal()){
			ColumnCustom dest=new ColumnCustom();
			try {
				BeanUtils.copyProperties(dest,record);
				dest.setCid(record.getId());
				dest.setPcid(record.getParentId());
				dest.setId(null);
				ccs.insert(dest);
			} catch (IllegalAccessException e) {
				log.error(e.getMessage(),e);
			} catch (InvocationTargetException e) {
				log.error(e.getMessage(),e);
			}
		}
		return record;
	}

	@Override
	public ColumnSelf selectByPrimaryKey(Long id) {
		return csd.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(ColumnSelf record) {
		ColumnSelf self=csd.selectByPrimaryKey(record.getId());
		if(self.getUserOrSystem().shortValue()==ColumnFlag.sys.getVal()){
			return 0;
		}
		int n = csd.updateByPrimaryKey(record);
		if(n>0){
			ColumnCustom cc=this.loadByCid(record.getId());
			if(cc!=null){
				Long id=cc.getId();
				try {
					BeanUtils.copyProperties(cc,record);
					cc.setCid(record.getId());
					cc.setId(id);
					ccs.updateByPrimaryKey(cc);
				} catch (IllegalAccessException e) {
					log.error(e.getMessage(),e);
				} catch (InvocationTargetException e) {
					log.error(e.getMessage(),e);
				}
				
			}
		}
		return n;
	}

	@Override
	public List<ColumnSelf> queryListByPidAndUserId(Long pid, Long uid) {
		return csd.queryListByPidAndUserId(pid, uid);
	}

	private ColumnSelf buidColumnSelf(ColumnSys source) throws Exception{
		ColumnSelf dest=new ColumnSelf();
		BeanUtils.copyProperties(dest,source);
		dest.setId(null);
		dest.setSysColId(source.getId());
		return dest;
	}

	@Override
	public ColumnSelf selectMaxOrderColumn(Long pid, Long uid) {
		return csd.selectMaxOrderColumn(pid, uid);
	}

	@Override
	public List<ColumnSelf> queryListByPid(Long pid) {
		return csd.queryListByPid(pid);
	}

}
