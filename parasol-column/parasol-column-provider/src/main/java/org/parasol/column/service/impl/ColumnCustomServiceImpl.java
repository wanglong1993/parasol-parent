package org.parasol.column.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.parasol.column.dao.ColumnCustomDao;
import org.parasol.column.entity.ColumnCustom;
import org.parasol.column.entity.ColumnSelf;
import org.parasol.column.service.ColumnCustomService;
import org.parasol.column.service.ColumnSelfService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("columnCustomService")
public class ColumnCustomServiceImpl implements ColumnCustomService {

	private static final Log log = LogFactory.getLog(ColumnCustomServiceImpl.class);
	
	@Resource
	private ColumnCustomDao ccd;
	
	@Resource(name="columnSelfService")
	private ColumnSelfService css;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return ccd.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ColumnCustom record) {
		// TODO Auto-generated method stub
		return ccd.insert(record);
	}

	@Override
	public ColumnCustom selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return ccd.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(ColumnCustom record) {
		// TODO Auto-generated method stub
		return ccd.updateByPrimaryKey(record);
	}

	@Override
	public List<ColumnSelf> queryListByPidAndUserId(Long pid, Long uid) {
		// TODO Auto-generated method stub
		List<ColumnSelf> result=null;
		if (pid == 0) {
			List<ColumnCustom> list = ccd.queryListByPidAndUserId(pid, uid);
			if (list == null || list.size() == 0) {
				this.init(uid);
				list = ccd.queryListByPidAndUserId(pid, uid);
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
			result=css.queryListByPid(pid);
		}
		return result;
	}

	@Override
	public int init(Long uid) {
		// TODO Auto-generated method stub
		List<ColumnCustom> list=ccd.queryListByPidAndUserId(0l, uid);
		if(list==null||list.size()==0){
			list=new ArrayList<ColumnCustom>();
			List<ColumnSelf> listSelf=css.queryListByPidAndUserId(0l, 0l);
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
				ccd.insert(cc);
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
		BeanUtils.copyProperties(dest,source);
		dest.setId(source.getCid());
		dest.setParentId(source.getPcid());
		return dest;
	}

	@Override
	public ColumnCustom queryByCid(Long cid) {
		// TODO Auto-generated method stub
		return ccd.queryByCid(cid);
	}

	@Override
	public int replace(Long uid, List<ColumnSelf> newList) {
		// TODO Auto-generated method stub
		ccd.deleteByUserId(uid);
		List<ColumnCustom> list=new ArrayList<ColumnCustom>();
		for(ColumnSelf source:newList){
			source.setUserId(uid);
			ColumnCustom dest=null;
			try {
				dest = this.buidColumnCustom(source);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error(e);
			}
			list.add(dest);
		}
		int n=ccd.insertBatch(list);
		return n;
	}

}
