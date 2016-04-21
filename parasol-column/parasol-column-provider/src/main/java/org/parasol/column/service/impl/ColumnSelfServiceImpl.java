package org.parasol.column.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.parasol.column.dao.ColumnSelfDao;
import org.parasol.column.entity.ColumnSelf;
import org.parasol.column.entity.ColumnSys;
import org.parasol.column.service.ColumnCustomService;
import org.parasol.column.service.ColumnSelfService;
import org.parasol.column.service.ColumnSysService;
import org.springframework.stereotype.Component;

@Component
public class ColumnSelfServiceImpl implements ColumnSelfService {

	private static final Log log = LogFactory.getLog(ColumnSelfServiceImpl.class);
	
	@Resource
	private ColumnSelfDao csd;
	
	@Resource
	private ColumnSysService css;
	
	@Resource
	private ColumnCustomService ccs;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return csd.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ColumnSelf record) {
		// TODO Auto-generated method stub
		return csd.insert(record);
	}

	@Override
	public ColumnSelf selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return csd.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(ColumnSelf record) {
		// TODO Auto-generated method stub
		return csd.updateByPrimaryKey(record);
	}

	@Override
	public List<ColumnSelf> queryListByPidAndUserId(Long pid, Long uid) {
		// TODO Auto-generated method stub
		return csd.queryListByPidAndUserId(pid, uid);
	}

	@Override
	public int init(Long uid) {
		// TODO Auto-generated method stub
		List<ColumnSelf> list=this.queryListByPidAndUserId(0l, uid);
		if(list==null||list.size()==0){
			list=new ArrayList<ColumnSelf>();
			List<ColumnSys> listSys=css.selectAll();
			for(ColumnSys source:listSys){
				try {
					ColumnSelf dest=buidColumnSelf(source);
					list.add(dest);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log.error(e);
				}
			}
			for(ColumnSelf cs:list){
				csd.insert(cs);
			}
		}
		return 0;
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
		// TODO Auto-generated method stub
		return csd.selectMaxOrderColumn(pid, uid);
	}

}
