package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import com.ginkgocap.parasol.organ.web.jetty.web.utils.CommonUtil;
import com.ginkgocap.ywxt.organ.model.Constants;
import com.ginkgocap.ywxt.organ.model.comment.CommentMain;
import com.ginkgocap.ywxt.organ.model.comment.CommentPraise;
import com.ginkgocap.ywxt.organ.model.comment.CommentReply;
import com.ginkgocap.ywxt.organ.service.comment.CommentMainService;
import com.ginkgocap.ywxt.organ.service.comment.CommentPraiseService;
import com.ginkgocap.ywxt.organ.service.comment.CommentReplyService;
import com.ginkgocap.ywxt.user.model.User;
import com.ginkgocap.ywxt.util.PageUtil;
import net.sf.json.JSONObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by jbqiu on 2016/6/10.
 * controller 组织点评controller
 */
@Controller
@RequestMapping("/organ/comment")
public class OrganCommentController  extends BaseController{
	
	private final Logger logger=LoggerFactory.getLogger(getClass());
	
     @Resource
     private CommentMainService commentMainService;
     @Resource
     private CommentPraiseService commentPraiseService;
     @Resource
     private CommentReplyService ommentReplyService;
     
     
     /**保存发布评论
 	 * @author zbb
 	 */
 	@ResponseBody
 	@RequestMapping(value="/saveComment.json",method=RequestMethod.POST)
 	public Map<String, Object> saveComment(HttpServletRequest request,
 			HttpServletResponse response) throws IOException {
 		String requestJson = getJsonParamStr(request);
 		Map<String, Object> model = new HashMap<String, Object>();
 		Map<String, Object> responseDataMap = new HashMap<String, Object>();
 		Map<String, Object> notificationMap = new HashMap<String, Object>();

        User userBasic=null;
        userBasic=getUser(request);
 		boolean flag = true;
 		if (requestJson != null && !"".equals(requestJson)){
 			JSONObject jo = JSONObject.fromObject(requestJson);
			if (userBasic == null) {
				setSessionAndErr(request, response, "-1", "请登录以后再操作");
			} else {
			       ObjectMapper objectMapper=new ObjectMapper();
			       int anonymous = jo.getInt( "anonymous");
			       String username=null;
			       if(anonymous==0){
			    	   username=userBasic.getName();
			       }else{
			    	   username="匿名用户";
			       }
			       CommentMain commentMain=objectMapper.readValue(jo.toString(),CommentMain.class);	
			       commentMain.setCommentuserid(userBasic.getId());
			       commentMain.setCommentusername(username);
			       commentMainService.savecommentMain(commentMain);

			}
 		}else{
 			setSessionAndErr(request, response, "-1", "请完善信息！");
 			 flag = false;
 		}
 		   responseDataMap.put("success", flag);
 	       notificationMap.put("notifCode", "0001");
 		   notificationMap.put("notifInfo", "hello mobile app!");
 		model.put("responseData", responseDataMap);
 		model.put("notification", notificationMap);
 	
 		return model;
 	}
 	
 	
	/**删除评论
	 * @author zbb
	 */
	@ResponseBody
	@RequestMapping(value="/deleteComment.json",method=RequestMethod.POST)
	public Map<String, Object> deleteComment(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		 String requestJson = getJsonParamStr(request);
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
        User userBasic=null;
        userBasic=getUser(request);
		boolean flag = true;
		if (requestJson != null && !"".equals(requestJson)){
			JSONObject jo = JSONObject.fromObject(requestJson);
			if (userBasic == null) {
				setSessionAndErr(request, response, "-1", "请登录以后再操作");
			} else {
				   String id = jo.optString( "id");
				   commentMainService.deleteById(Long.valueOf(id));
				   commentPraiseService.deleteByCommentid(Long.valueOf(id));
				   ommentReplyService.deleteByCommentid(Long.valueOf(id));
			}
		}else{
			setSessionAndErr(request, response, "-1", "请完善信息！");
			 flag = false;
		}
		   responseDataMap.put("success", flag);
	       notificationMap.put("notifCode", "0001");
			notificationMap.put("notifInfo", "hello mobile app!");
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		return model;
	}
	
	
	/**查询评论
	 * @author zbb
	 */
	@ResponseBody
	@RequestMapping(value="/selectComment.json",method=RequestMethod.POST)
	public Map<String, Object> selectComment(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		 String requestJson = getJsonParamStr(request);
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
        User userBasic=null;
        userBasic=getUser(request);
		boolean flag = true;
		if (requestJson != null && !"".equals(requestJson)){
			JSONObject jo = JSONObject.fromObject(requestJson);
				 Map<String,Object>  canshu=new HashMap<String,Object>();
				  long orgid = CommonUtil.getLongFromJSONObject(jo, "orgid");
				   int currentPage = jo.getInt( "currentPage");
				   int pageSize = jo.getInt( "pageSize");
				   canshu.put("orgid", orgid);
				   canshu.put("currentPage", (currentPage-1)*pageSize);
				   canshu.put("pageSize", pageSize);
				   List<CommentMain> commentMainlist = commentMainService.findByOrganId(canshu);
				   int count = commentMainService.selectcount(canshu);
				   pageSize=pageSize>Constants.max_select_count?Constants.max_select_count:pageSize;
				   PageUtil page = new PageUtil(count, currentPage, pageSize);
				   responseDataMap.put("page", page);
			       for(CommentMain commentMain:commentMainlist){
			    	  Long id = commentMain.getId();
			    	  Long praisecount = commentPraiseService.selectPraiseCount(id);
			    	  boolean praiseresult=false;
			    	  if(userBasic!=null){
			    		   praiseresult = commentPraiseService.selectUserPraiseCount(userBasic.getId(), id);
			    	  }
			    	  commentMain.setPraisecount(praisecount);
			    	  commentMain.setPraiseresult(praiseresult);
			    	  commentMain.setReplyMap(ommentReplyService.findByCommentid(id));
			       }
			       responseDataMap.put("commentMap", commentMainlist);
		}else{
			setSessionAndErr(request, response, "-1", "请完善信息！");
			 flag = false;
		}
		   responseDataMap.put("success", flag);
	       notificationMap.put("notifCode", "0001");
			notificationMap.put("notifInfo", "hello mobile app!");
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		return model;
	}
	
	
	/**点赞
	 * @author zbb
	 */
	@ResponseBody
	@RequestMapping(value="/clickPraise.json",method=RequestMethod.POST)
	public Map<String, Object> clickPraise(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		 String requestJson = getJsonParamStr(request);
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
        User userBasic=null;
        userBasic=getUser(request);
		boolean flag = true;
		if (requestJson != null && !"".equals(requestJson)){
			JSONObject jo = JSONObject.fromObject(requestJson);
			if (userBasic == null) {
				setSessionAndErr(request, response, "-1", "请登录以后再操作");
			} else {
				long commentid = CommonUtil.getLongFromJSONObject(jo, "commentid");
				long userid = userBasic.getId();
				boolean praiseresult = commentPraiseService.selectUserPraiseCount(userBasic.getId(), commentid);
				if(praiseresult){
				setSessionAndErr(request, response, "-1", "给用户已经点过赞");
				}else{
					CommentPraise commentPraise=new CommentPraise();
					commentPraise.setCommentid(commentid);
					commentPraise.setUserid(userid);
					commentPraiseService.insertPraiseUser(commentPraise);
				}
			}
		}else{
			setSessionAndErr(request, response, "-1", "请完善信息！");
			 flag = false;
		}
		   responseDataMap.put("success", flag);
	       notificationMap.put("notifCode", "0001");
		   notificationMap.put("notifInfo", "hello mobile app!");
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		return model;
	}
	
	
	/**取消点赞
	 * @author zbb
	 */
	@ResponseBody
	@RequestMapping(value="/removePraise.json",method=RequestMethod.POST)
	public Map<String, Object> removePraise(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		 String requestJson = getJsonParamStr(request);
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
        User userBasic=null;
        userBasic=getUser(request);
		boolean flag = true;
		if (requestJson != null && !"".equals(requestJson)){
			JSONObject jo = JSONObject.fromObject(requestJson);
			if (userBasic == null) {
				setSessionAndErr(request, response, "-1", "请登录以后再操作");
			} else {
				long commentid = CommonUtil.getLongFromJSONObject(jo, "commentid");
				long userid = userBasic.getId();
				commentPraiseService.deleteById(userid,commentid);
			}
		}else{
			setSessionAndErr(request, response, "-1", "请完善信息！");
			 flag = false;
		}
		   responseDataMap.put("success", flag);
	       notificationMap.put("notifCode", "0001");
		   notificationMap.put("notifInfo", "hello mobile app!");
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		return model;
	}
	
	
	/**回复评论
	 * @author zbb
	 */
	@ResponseBody
	@RequestMapping(value="/replyComment.json",method=RequestMethod.POST)
	public Map<String, Object> replyComment(HttpServletRequest request,
		HttpServletResponse response) throws IOException {
		String requestJson = getJsonParamStr(request);
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
        User userBasic=null;
        userBasic=getUser(request);

		boolean flag = true;
		if (requestJson != null && !"".equals(requestJson)){
			JSONObject jo = JSONObject.fromObject(requestJson);
			if (userBasic == null) {
				setSessionAndErr(request, response, "-1", "请登录以后再操作");
			} else {
				ObjectMapper objectMapper=new ObjectMapper();
				CommentReply commentReply=objectMapper.readValue(jo.toString(),CommentReply.class);	
				commentReply.setReplyuserid(userBasic.getId());
				commentReply.setReplyusername(userBasic.getName());
				Long id = ommentReplyService.savecommentReply(commentReply);
			}
		}else{
			setSessionAndErr(request, response, "-1", "请完善信息！");
			 flag = false;
		}
		   responseDataMap.put("success", flag);
	       notificationMap.put("notifCode", "0001");
		   notificationMap.put("notifInfo", "hello mobile app!");
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		return model;
	}
	
	/**删除回复
	 * @author zbb
	 */
	@ResponseBody
	@RequestMapping(value="/removeReply.json",method=RequestMethod.POST)
	public Map<String, Object> removereply(HttpServletRequest request,
		HttpServletResponse response) throws IOException {
		String requestJson = getJsonParamStr(request);
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
        User userBasic=null;
        userBasic=getUser(request);


        boolean flag = true;
		if (requestJson != null && !"".equals(requestJson)){
			JSONObject jo = JSONObject.fromObject(requestJson);
			if (userBasic == null) {
				setSessionAndErr(request, response, "-1", "请登录以后再操作");
			} else {
				long id = CommonUtil.getLongFromJSONObject(jo, "id");
				ommentReplyService.deleteByUserid(id);
			}
		}else{
			setSessionAndErr(request, response, "-1", "请完善信息！");
			 flag = false;
		}
		   responseDataMap.put("success", flag);
	       notificationMap.put("notifCode", "0001");
		   notificationMap.put("notifInfo", "hello mobile app!");
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);
		return model;
	}
}
