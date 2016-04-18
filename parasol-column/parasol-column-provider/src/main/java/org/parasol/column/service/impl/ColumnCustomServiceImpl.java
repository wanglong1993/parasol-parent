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
import org.parasol.column.service.ColumnSelftService;
import org.springframework.stereotype.Service;

@Service
public class ColumnCustomServiceImpl implements ColumnCustomService {

	private static final Log log = LogFactory.getLog(ColumnCustomServiceImpl.class);
	
	@Resource
	private ColumnCustomDao ccd;
	
	@Resource
	private ColumnSelftService css;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(ColumnCustom record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ColumnCustom selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return ccd.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(ColumnCustom record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ColumnCustom> queryListByPidAndUserId(Long pid, Long uid) {
		// TODO Auto-generated method stub
		List<ColumnCustom> list=ccd.queryListByPidAndUserId(pid, uid);
		if((list==null||list.size()==0)&&pid==0l){
			css.init(uid);
			this.init(uid);
			list=ccd.queryListByPidAndUserId(pid, uid);
		}
		return list;
	}

	@Override
	public int init(Long uid) {
		// TODO Auto-generated method stub
		List<ColumnCustom> list=ccd.queryListByPidAndUserId(0l, uid);
		if(list==null||list.size()==0){
			list=new ArrayList<ColumnCustom>();
			List<ColumnSelf> listSelf=css.queryListByPidAndUserId(0l, uid);
			for(ColumnSelf source:listSelf){
				try {
					ColumnCustom dest=buidColumnCustom(source);
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

}
