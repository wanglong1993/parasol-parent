package com.ginkgocap.parasol.comment.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.comment.exception.CommObjServiceException;
import com.ginkgocap.parasol.comment.exception.ResReviewCommObjServiceException;
import com.ginkgocap.parasol.comment.model.CommObj;
import com.ginkgocap.parasol.comment.model.ResReviewCommObj;
import com.ginkgocap.parasol.comment.service.CommObjService;
import com.ginkgocap.parasol.comment.service.ResReviewCommObjService;

@Service("resReviewCommObjService")
public class ResReviewCommObjServiceImpl implements ResReviewCommObjService {

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CommObjService commObjService;
	
	@Override
	public ResReviewCommObj createResReviewCommObj(ResReviewCommObj obj) throws ResReviewCommObjServiceException {
		// TODO Auto-generated method stub
		try {
			
			CommObj obj1=new CommObj();
			BeanUtils.copyProperties(obj, obj1);
			obj1=this.commObjService.createCommObj(obj1);
			obj.setId(obj1.getCommObjId());
			obj.setCommObjId(obj1.getCommObjId());
			this.mongoTemplate.save(obj,"res_review_commobj");
		} catch (CommObjServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ResReviewCommObjServiceException(e);
		}
		return null;
	}

	@Override
	public ResReviewCommObj updateResReviewCommObj(ResReviewCommObj obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteResReviewCommObj(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResReviewCommObj findResReviewCommObjById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
