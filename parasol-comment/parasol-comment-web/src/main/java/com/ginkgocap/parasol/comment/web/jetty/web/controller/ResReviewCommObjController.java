package com.ginkgocap.parasol.comment.web.jetty.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ginkgocap.parasol.comment.model.CommObjType;
import com.ginkgocap.parasol.comment.model.CommObjUpUser;
import com.ginkgocap.parasol.comment.model.ResReviewCommObj;
import com.ginkgocap.parasol.comment.model.Tipoff;
import com.ginkgocap.parasol.comment.service.ResReviewCommObjService;
import com.ginkgocap.parasol.comment.web.jetty.web.util.JsonUtils;
import com.ginkgocap.parasol.oauth2.web.jetty.LoginUserContextHolder;

@Controller
public class ResReviewCommObjController extends BaseControl {
	@Autowired
	private ResReviewCommObjService rrs;
	
	@RequestMapping(value="/commobj/commobj/createCommToRes",method = RequestMethod.POST)
	@ResponseBody
	public ResReviewCommObj createCommToRes(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Long loginAppId = LoginUserContextHolder.getAppKey();
		Long loginUserId = LoginUserContextHolder.getUserId();
		String jsonStr=this.readJSONString(request);
		ResReviewCommObj obj=(ResReviewCommObj)JsonUtils.jsonToBean(jsonStr,ResReviewCommObj.class);
		obj.setCreateUserId(loginUserId);
		obj.setAppId(loginAppId);
		obj.setCommObjType(CommObjType.res.getVal());
		obj.setCommTimes(0l);
		obj.setUpTimes(0l);
		if(obj.getIsAno()==null){
			obj.setIsAno(0);
		}
		else if(1==obj.getIsAno()){
			obj.setCreateUserName("匿名用户");
		}
		else{
			obj.setIsAno(0);
		}
		obj=this.rrs.createResReviewCommObj(obj);
		return obj;
	}
	
	@RequestMapping(value="/commobj/commobj/loadCommsForRes",method = RequestMethod.GET)
	@ResponseBody
	public List<ResReviewCommObj> loadCommsToRes(HttpServletRequest request, HttpServletResponse response,Integer resType,Long pid) throws Exception{
		Long loginAppId = LoginUserContextHolder.getAppKey();
		Long loginUserId = LoginUserContextHolder.getUserId();
		List<ResReviewCommObj> subs=this.rrs.listSubResReviewCommObjs(pid);
		return subs;
	}
	
	@RequestMapping(value="/commobj/commobj/createCommToReview",method = RequestMethod.POST)
	@ResponseBody
	public ResReviewCommObj createCommToReview(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Long loginAppId = LoginUserContextHolder.getAppKey();
		Long loginUserId = LoginUserContextHolder.getUserId();
		String jsonStr=this.readJSONString(request);
		ResReviewCommObj obj=(ResReviewCommObj)JsonUtils.jsonToBean(jsonStr,ResReviewCommObj.class);
		obj.setCreateUserId(loginUserId);
		obj.setAppId(loginAppId);
		obj.setCommObjType(CommObjType.review.getVal());
		if(obj.getIsAno()==null){
			obj.setIsAno(0);
		}
		else if(1==obj.getIsAno()){
			obj.setCreateUserName("匿名用户");
		}
		else{
			obj.setIsAno(0);
		}
		obj=this.rrs.createResReviewCommObj(obj);
		return obj;
	}
	
	@RequestMapping(value="/commobj/commobj/deleteCommObj",method = RequestMethod.POST)
	@ResponseBody
	public Boolean deleteCommObj(HttpServletRequest request, HttpServletResponse response,Long id) throws Exception{
		Long loginAppId = LoginUserContextHolder.getAppKey();
		Long loginUserId = LoginUserContextHolder.getUserId();
		return this.rrs.deleteResReviewCommObj(id);
	}
	
	@RequestMapping(value="/commobj/commobj/createCommObjUpUser",method = RequestMethod.POST)
	@ResponseBody
	public CommObjUpUser createCommObjUpUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Long loginAppId = LoginUserContextHolder.getAppKey();
		Long loginUserId = LoginUserContextHolder.getUserId();
		String jsonStr=this.readJSONString(request);
		CommObjUpUser obj=(CommObjUpUser)JsonUtils.jsonToBean(jsonStr,CommObjUpUser.class);
		obj.setCreateUserId(loginUserId);
		obj=this.rrs.createCommObjUpUser(obj);
		return obj;
	}
	
	@RequestMapping(value="/commobj/commobj/createTipoff",method = RequestMethod.POST)
	@ResponseBody
	public Tipoff createTipoff(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Long loginAppId = LoginUserContextHolder.getAppKey();
		Long loginUserId = LoginUserContextHolder.getUserId();
		String jsonStr=this.readJSONString(request);
		Tipoff obj=(Tipoff)JsonUtils.jsonToBean(jsonStr,Tipoff.class);
		obj.setCreateUserId(loginUserId);
		obj.setAppId(loginAppId);
		obj.setCreateTime(new Date());
		obj=this.rrs.createTipoff(obj);
		return obj;
	}
	
	@RequestMapping(value="/commobj/commobj/listCommObjUpUser",method = RequestMethod.GET)
	@ResponseBody
	public List<CommObjUpUser> listCommObjUpUser(HttpServletRequest request, HttpServletResponse response,Integer resType,Long commObjId) throws Exception{
		Long loginAppId = LoginUserContextHolder.getAppKey();
		Long loginUserId = LoginUserContextHolder.getUserId();
		List<CommObjUpUser> list=this.rrs.listUpUsers(commObjId);
		return list;
	}
}
