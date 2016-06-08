package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ginkgocap.parasol.user.service.UserBasicService;
import net.sf.json.JSONObject;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ginkgocap.ywxt.organ.model.comment.CommentMain;
import com.ginkgocap.ywxt.organ.service.comment.CommentMainService;
import com.ginkgocap.ywxt.organ.service.comment.CommentPraiseService;
import com.ginkgocap.ywxt.organ.service.comment.CommentReplyService;
import org.springframework.stereotype.Controller;

import com.ginkgocap.parasol.oauth2.web.jetty.LoginUserContextHolder;

import com.ginkgocap.parasol.user.model.UserBasic;
/**
 * Created by jbqiu on 2016/6/7.
 */
@Controller
@RequestMapping("/organ/comment")
public class OrganCommentController extends BaseController {


    private final Logger logger=LoggerFactory.getLogger(getClass());

    @Resource
    private CommentMainService commentMainService;
    @Resource
    private CommentPraiseService commentPraiseService;
    @Resource
    private CommentReplyService ommentReplyService;

    @Resource
    private UserBasicService userBasicService;
    /**保存发布评论
     * @author zbb
     */
    @ResponseBody
    @RequestMapping(value="/saveComment.json",method=RequestMethod.POST)
    public Map<String, Object> saveComment(HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        String requestJson = getJsonParamStr(request);
        Map<String, Object> model = new HashMap<String, Object>();
        Map<String, Object> responseDataMap = new HashMap<String, Object>();
        Map<String, Object> notificationMap = new HashMap<String, Object>();
        Long loginUserId = LoginUserContextHolder.getUserId();
        UserBasic userBasic= null;
        userBasic=userBasicService.getObject(loginUserId);
        boolean flag = false;
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
                commentMain.setCommentuserid(userBasic.getUserId());
                commentMain.setCommentusername(username);
                Long id = commentMainService.savecommentMain(commentMain);
                if(id>0){
                    flag = true;
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


}
