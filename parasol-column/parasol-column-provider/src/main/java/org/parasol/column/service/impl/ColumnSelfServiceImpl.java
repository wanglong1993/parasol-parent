package org.parasol.column.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.parasol.column.api.model.ColumnFlag;
import org.parasol.column.dao.ColumnSelfDao;
import org.parasol.column.dao.NewColumnCustomDao;
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
	private ColumnSelfDao selfDao;
	
	@Resource(name ="columnSysService")
	private ColumnSysService columnSysService;
	
	@Resource(name ="columnCustomService")
	private ColumnCustomService columnCustomService;

	@Resource
	private NewColumnCustomDao newColumnCustomDao;
	
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
	public ColumnSelf insert(ColumnSelf columnSelf) {
		// TODO Auto-generated method stub
		int n = selfDao.insert(columnSelf);
		if(n > 0 && columnSelf.getUserOrSystem().shortValue() == ColumnFlag.user.getVal()){
			/*//ColumnCustom dest=new ColumnCustom();
			NewColumnCustom dest = new NewColumnCustom();
			try {

				BeanUtils.copyProperties(dest,record);
				dest.setCid(record.getId());
				dest.setPcid(record.getParentId());
				dest.setId(null);
				columnCustomService.insert(dest);
				dest.setSid(columnSelf.getId());
				dest.setUserId(userId);
				dest.setUserOrSystem((short)0);
				dest.setCreatetime(new Date());
				dest.setUpdateTime(new Date());
				newColumnCustomDao.insert(dest);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				log.error(e);
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				log.error(e);
			}*/
			return columnSelf;
		}
		return null;
	}

	@Override
	public ColumnSelf selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return selfDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(ColumnSelf record) {
		// TODO Auto-generated method stub
		ColumnSelf self = selfDao.selectByPrimaryKey(record.getId());
		int n = selfDao.updateByPrimaryKey(record);
		if(self.getUserOrSystem().shortValue() == ColumnFlag.sys.getVal() || n < 1){
			return 0;
		}
		/*if(n > 0){
			//ColumnCustom custom = this.loadByCid(record.getId());
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
		}*/
		return n;
	}

	@Override
	public List<ColumnSelf> queryListByPidAndUserId(Long pid, Long uid) {
		// TODO Auto-generated method stub
		return selfDao.queryListByPidAndUserId(pid, uid);
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
		return selfDao.selectMaxOrderColumn(pid, uid);
	}

	@Override
	public List<ColumnSelf> queryListByPid(Long pid) {
		// TODO Auto-generated method stub
		return selfDao.queryListByPid(pid);
	}

}
