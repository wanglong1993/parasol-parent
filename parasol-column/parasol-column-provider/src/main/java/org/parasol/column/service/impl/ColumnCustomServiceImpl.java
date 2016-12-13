package org.parasol.column.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.parasol.column.dao.ColumnCustomDao;
import org.parasol.column.dao.ColumnSelfDao;
import org.parasol.column.dao.NewColumnCustomDao;
import org.parasol.column.entity.ColumnCustom;
import org.parasol.column.entity.ColumnSelf;
import org.parasol.column.entity.NewColumnCustom;
import org.parasol.column.service.ColumnCustomService;
import org.parasol.column.service.ColumnSelfService;
import org.springframework.stereotype.Component;

@Component("columnCustomService")
public class ColumnCustomServiceImpl implements ColumnCustomService {

	private static final Log log = LogFactory.getLog(ColumnCustomServiceImpl.class);
	
	@Resource
	private ColumnCustomDao customDao;

	@Resource
	private ColumnSelfDao selfDao;
	
	@Resource
	private NewColumnCustomDao newCustomDao;
	
	@Resource(name="columnSelfService")
	private ColumnSelfService selfService;
	

    @Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return newCustomDao.deleteByUserId(id);
	}

	@Override
	public int insert(NewColumnCustom record) {
		// TODO Auto-generated method stub
		return newCustomDao.insert(record);
	}

	@Override
	public NewColumnCustom selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return newCustomDao.queryBySid(id);
	}

	/*@Override
	public List<ColumnSelf> queryListByPidAndUserId(Long pid, Long uid) {
		// TODO Auto-generated method stub
		List<ColumnSelf> result=null;
		if (pid == 0) {
			List<ColumnCustom> list = customDao.queryListByPidAndUserId(pid, uid);
			if (list == null || list.size() == 0) {
				this.init(uid);
				list = customDao.queryListByPidAndUserId(pid, uid);
			}
			result=new ArrayList<ColumnSelf>();
			for(ColumnCustom source:list){
				ColumnSelf dest;
				try {
					dest = this.buidColumnSelf(source);
					result.add(dest);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log.error(e);
				}
			}
		}
		else{
			//当pid不为0时，查询子系统栏目
			result= selfService.queryListByPid(pid);
		}
		return result;
	}*/

	@Override
	public List<ColumnSelf> queryListByPidAndUserId(Long pid, Long uid){
		// TODO Auto-generated method stub
		if (pid == 0){
			List<ColumnSelf> selfList = newCustomDao.queryListByUid(uid);
			if(selfList != null && selfList.size() != 0){
				return selfList;
			}
			return new ArrayList<ColumnSelf>();
		}
		//当pid不为0时，查询子系统栏目
		return selfDao.queryListByPid(pid);

	}

	/**
	 * 批量插入数据到NewcolumnCustom表中
	 */
	@Override
	public int insertBatch(List<ColumnSelf> selfList, Long userId){
		// TODO Auto-generated method stub
		int i =0;
		if(CollectionUtils.isNotEmpty(selfList)){
			NewColumnCustom custom = new NewColumnCustom();
			for (ColumnSelf columnSelf : selfList) {
				custom.setSid(columnSelf.getId());
				custom.setUserId(userId);
				custom.setUserOrSystem((short)0);
				custom.setCreatetime(new Date());
				custom.setUpdateTime(new Date());
				//插入数据到NewColumnCustom表中
				i = newCustomDao.insert(custom);
			}
			return i;
		}
		return i;
		
	}
	@Override
	public int init(Long uid) {
		// TODO Auto-generated method stub
		List<ColumnCustom> list= customDao.queryListByPidAndUserId(0l, uid);
		if(list==null||list.size()==0){
			list=new ArrayList<ColumnCustom>();
			List<ColumnSelf> listSelf= selfService.queryListByPidAndUserId(0l, 0l);
			for(ColumnSelf source:listSelf){
				try {
					ColumnCustom dest=buidColumnCustom(source);
					dest.setUserId(uid);
					list.add(dest);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log.error(e);
				}
			}
			for(ColumnCustom cc:list){
				customDao.insert(cc);
			}
		}
		return 0;
	}
	
	private ColumnCustom buidColumnCustom(ColumnSelf source) throws Exception{
		ColumnCustom dest=new ColumnCustom();
		BeanUtils.copyProperties(dest,source);
		dest.setId(null);
		dest.setCid(source.getId());
		dest.setPcid(source.getParentId());
		return dest;
	}
	
	private ColumnSelf buidColumnSelf(ColumnCustom source) throws Exception{
		ColumnSelf dest=new ColumnSelf();
		BeanUtils.copyProperties(dest, source);
		dest.setId(source.getCid());
		dest.setParentId(source.getPcid());
		return dest;
	}

	@Override
	public ColumnCustom queryByCid(Long cid) {
		// TODO Auto-generated method stub
		return customDao.queryByCid(cid);
	}

	/*@Override
	public int replace(Long uid, List<ColumnSelf> newList) {
		// TODO Auto-generated method stub
		customDao.deleteByUserId(uid);
		List<ColumnCustom> list = new ArrayList<ColumnCustom>();
		for(ColumnSelf source: newList){
			source.setUserId(uid);
			ColumnCustom dest = null;
			try {
				dest = this.buidColumnCustom(source);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error(e);
			}
			list.add(dest);
		}
		int n = customDao.insertBatch(list);
		return n;
	}*/

	/**
	 * 通过先删除再批量插入修改数据
	 * @param uid
	 * @param selfList
	 * @return
	 */
	@Override
	public int replace(Long uid, List<ColumnSelf> selfList){
		// TODO Auto-generated method stub
		newCustomDao.deleteByUserId(uid);
		int i = insertBatch(selfList, uid);
		return i;
		
	}


}
