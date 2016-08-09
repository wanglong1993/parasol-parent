package org.parasol.column.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

@Component("columnCustomService")
public class ColumnCustomServiceImpl implements ColumnCustomService {

	private static final Log log = LogFactory.getLog(ColumnCustomServiceImpl.class);
	
	@Resource
	private ColumnCustomDao ccd;
	
	@Resource(name="columnSelfService")
	private ColumnSelfService css;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return ccd.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ColumnCustom record) {
		return ccd.insert(record);
	}

	@Override
	public ColumnCustom selectByPrimaryKey(Long id) {
		return ccd.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(ColumnCustom record) {
		return ccd.updateByPrimaryKey(record);
	}

	@Override
	public List<ColumnSelf> queryListByPidAndUserId(Long pid, Long uid) {
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
					log.error(e.getMessage(),e);
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
		List<ColumnCustom> list=ccd.queryListByPidAndUserId(0l, uid);
		Set<Long> set=new HashSet<Long>();
		if(list==null||list.size()==0){
			list=new ArrayList<ColumnCustom>();
			//系统栏目
			List<ColumnSelf> listSelf=css.queryListByPidAndUserId(0l, 0l);
			List<ColumnSelf> listSelf1=css.queryListByPidAndUserId(0l, uid);
			if(listSelf1!=null){
				listSelf.addAll(listSelf1);
			}
			for(ColumnSelf source:listSelf){
				try {
					if(source==null||source.getDelStatus()==1){
						continue;
					}
					if(set.contains(source.getId())){
						continue;
					}else{
						set.add(source.getId());
					}
					ColumnCustom dest=buidColumnCustom(source);
					dest.setUserId(uid);
					list.add(dest);
				} catch (Exception e) {
					log.error(e.getMessage(),e);
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
		return ccd.queryByCid(cid);
	}

	@Override
	public int replace(Long uid, List<ColumnSelf> newList) {
		ccd.deleteByUserId(uid);
		List<ColumnCustom> list=new ArrayList<ColumnCustom>();
		for(ColumnSelf source:newList){
			source.setUserId(uid);
			ColumnCustom dest=null;
			try {
				dest = this.buidColumnCustom(source);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
			list.add(dest);
		}
		int n=ccd.insertBatch(list);
		return n;
	}

}
