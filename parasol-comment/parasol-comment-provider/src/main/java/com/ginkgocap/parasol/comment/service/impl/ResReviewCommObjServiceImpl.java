package com.ginkgocap.parasol.comment.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.comment.exception.CommObjServiceException;
import com.ginkgocap.parasol.comment.exception.ResReviewCommObjServiceException;
import com.ginkgocap.parasol.comment.model.CommObj;
import com.ginkgocap.parasol.comment.model.CommObjType;
import com.ginkgocap.parasol.comment.model.CommObjUpUser;
import com.ginkgocap.parasol.comment.model.ResReviewCommObj;
import com.ginkgocap.parasol.comment.model.Tipoff;
import com.ginkgocap.parasol.comment.service.CommObjService;
import com.ginkgocap.parasol.comment.service.ResReviewCommObjService;
import com.ginkgocap.parasol.comment.util.MakePrimaryKey;

@Service("resReviewCommObjService")
public class ResReviewCommObjServiceImpl implements ResReviewCommObjService {

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CommObjService commObjService;
	
	private static final String COMMOBJCOLL="res_review_commobj";
	
	private static final String COMMOBJUPUSER="commobj_up_user";
	
	private static final String TIPOFF="tipoff";
	
	@Override
	public ResReviewCommObj createResReviewCommObj(ResReviewCommObj obj) throws ResReviewCommObjServiceException {
		obj.setCommObjId(Long.valueOf(MakePrimaryKey.getPrimaryKey()));
		if(obj.getCommObjType()==CommObjType.res.getVal()){
			ResReviewCommObj root=this.createRootCommObj(obj);
			obj.setParentCommObjId(root.getId());
		}
		ResReviewCommObj parent=this.findResReviewCommObjById(obj.getParentCommObjId());
		obj.setRootCommObjId(parent.getRootCommObjId());
		obj.setId(obj.getCommObjId());
		obj.setDelStatus(false);
		obj.setCommTimes(0l);
		obj.setUpTimes(0l);
		this.mongoTemplate.save(obj,COMMOBJCOLL);
		Long ct=parent.getCommTimes().longValue()+1;
		parent.setCommTimes(ct);
		this.updateResReviewCommObj(parent);
		return obj;
	}

	@Override
	public ResReviewCommObj updateResReviewCommObj(ResReviewCommObj obj) {
		Criteria c = Criteria.where("_id").is(obj.getId());
	    Query query = new Query(c);
	    Update update=new Update();
	    update.set("upTimes", obj.getUpTimes());
	    update.set("commTimes", obj.getCommTimes());
	    this.mongoTemplate.updateFirst(query, update, COMMOBJCOLL);
		return this.findResReviewCommObjById(obj.getCommObjId());
	}

	@Override
	public Boolean deleteResReviewCommObj(Long id) {
		Criteria c = Criteria.where("_id").is(id);
	    Query query = new Query(c);
	    ResReviewCommObj obj=this.findResReviewCommObjById(id);
	    if(obj==null){
	    	return false;
	    }
	    Update update=new Update();
	    update.set("delStatus", true);
	    this.mongoTemplate.updateFirst(query, update, COMMOBJCOLL);
	    if(obj.getParentCommObjId()!=null){
	    	ResReviewCommObj parent=this.findResReviewCommObjById(obj.getParentCommObjId());
	    	parent.setCommTimes(parent.getCommTimes()>0?parent.getCommTimes()-1:0);
	    	this.updateResReviewCommObj(parent);
	    }
	    return true;
	}

	@Override
	public ResReviewCommObj findResReviewCommObjById(Long id) {
		Criteria c = Criteria.where("id").is(id);
	    Query query = new Query(c);
	    return this.mongoTemplate.findOne(query, ResReviewCommObj.class, COMMOBJCOLL);
	}

	@Override
	public ResReviewCommObj createRootCommObj(ResReviewCommObj obj) throws ResReviewCommObjServiceException {
		ResReviewCommObj root=this.findRootCommObj(obj);
		if(root==null){
			root=new ResReviewCommObj();
			root.setAppId(obj.getAppId());
			root.setResType(obj.getResType());
			root.setResId(obj.getResId());
			root.setId(Long.valueOf(MakePrimaryKey.getPrimaryKey()));
			root.setCommObjId(root.getId());
			root.setRootCommObjId(root.getId());
			root.setCommTimes(0l);
			root.setUpTimes(0l);
			root.setCommObjType(CommObjType.root.getVal());
			root.setDelStatus(false);
			this.mongoTemplate.save(root, COMMOBJCOLL);
		}
		return root;
	}

	@Override
	public ResReviewCommObj findRootCommObj(ResReviewCommObj obj) throws ResReviewCommObjServiceException {
		Criteria criatira = new Criteria();
		criatira.andOperator(Criteria.where("appId").is(obj.getAppId()), Criteria.where("resType").is(obj.getResType()),Criteria.where("resId").is(obj.getResId()));
	    Query query = new Query(criatira);
	    return this.mongoTemplate.findOne(query, ResReviewCommObj.class, COMMOBJCOLL);
	}

	@Override
	public List<ResReviewCommObj> listSubResReviewCommObjs(Long pid) throws ResReviewCommObjServiceException {
		Criteria criatira = new Criteria();
		criatira.andOperator(Criteria.where("parentCommObjId").is(pid), Criteria.where("delStatus").is(false));
	    Query query = new Query(criatira);
		return this.mongoTemplate.find(query, ResReviewCommObj.class, COMMOBJCOLL);
	}


	@Override
	public List<CommObjUpUser> listUpUsers(Long commObjId) throws ResReviewCommObjServiceException {
		Criteria c = Criteria.where("commObjId").is(commObjId);
	    Query query = new Query(c);
		return this.mongoTemplate.find(query, CommObjUpUser.class, COMMOBJUPUSER);
	}

	@Override
	public CommObjUpUser createCommObjUpUser(CommObjUpUser upUser) throws ResReviewCommObjServiceException {
		upUser.setCreateTime(new Date());
		upUser.setId(Long.valueOf(MakePrimaryKey.getPrimaryKey()));
		this.mongoTemplate.save(upUser, COMMOBJUPUSER);
		ResReviewCommObj obj=this.findResReviewCommObjById(upUser.getCommObjId());
		Long upTimes=obj.getUpTimes()+1;
		obj.setUpTimes(upTimes);
		this.updateResReviewCommObj(obj);
		return this.findCommObjUpUserById(upUser.getId());
	}

	@Override
	public CommObjUpUser findCommObjUpUserById(Long id) throws ResReviewCommObjServiceException {
		Criteria c = Criteria.where("id").is(id);
	    Query query = new Query(c);
	    return this.mongoTemplate.findOne(query, CommObjUpUser.class, COMMOBJUPUSER);
	}

	@Override
	public Tipoff createTipoff(Tipoff obj) throws ResReviewCommObjServiceException {
		obj.setCreateTime(new Date());
		obj.setId(Long.valueOf(MakePrimaryKey.getPrimaryKey()));
		this.mongoTemplate.save(obj, TIPOFF);
		return this.findTipoffById(obj.getId());
	}

	@Override
	public Tipoff findTipoffById(Long id) throws ResReviewCommObjServiceException {
		Criteria c = Criteria.where("id").is(id);
	    Query query = new Query(c);
	    return this.mongoTemplate.findOne(query, Tipoff.class, TIPOFF);
	}

}
