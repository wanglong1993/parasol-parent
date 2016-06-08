package com.ginkgocap.parasol.comment.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.comment.exception.CommObjServiceException;
import com.ginkgocap.parasol.comment.model.CommObj;
import com.ginkgocap.parasol.comment.service.CommObjService;
import com.ginkgocap.parasol.comment.util.MakePrimaryKey;
import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;

@Service("commObjService")
public class CommObjServiceImpl extends BaseService<CommObj> implements CommObjService {

	@Override
	public CommObj createCommObj(CommObj obj) throws CommObjServiceException {
		// TODO Auto-generated method stub
		try {
			obj.setCreateTime((new Date()).getTime());
			Long id= (Long) this.saveEntity(obj);
			return this.findCommObjById(id);
		} catch (BaseServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CommObjServiceException(e);
		}
	}

	@Override
	public CommObj updateCommObj(CommObj obj) throws CommObjServiceException {
		// TODO Auto-generated method stub
		try {
			Boolean b=this.updateEntity(obj);
			if(b){
				return this.findCommObjById(obj.getCommObjId());
			}
		} catch (BaseServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CommObjServiceException(e);
		}
		return null;
	}

	@Override
	public Boolean deleteCommObj(Long id) throws CommObjServiceException {
		// TODO Auto-generated method stub
		try {
			return this.deleteEntity(id);
		} catch (BaseServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CommObjServiceException(e);
		}
	}

	@Override
	public CommObj findCommObjById(Long id) throws CommObjServiceException {
		// TODO Auto-generated method stub
		try {
			return this.getEntity(id);
		} catch (BaseServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CommObjServiceException(e);
		}
	}

}
