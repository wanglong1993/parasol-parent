package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import com.ginkgocap.parasol.organ.web.jetty.web.utils.Utils;
import com.ginkgocap.ywxt.user.model.User;
import com.ginkgocap.ywxt.user.service.UserService;
import com.ginkgocap.ywxt.util.DateFunc;
import com.ginkgocap.ywxt.util.Encodes;
import com.gintong.ywxt.organization.model.OrganRegister;
import net.sf.json.JSONObject;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;



/**
 * Created by Administrator on 2016/6/24.
 */


@Controller
@RequestMapping("/login")
public class UserLoginController extends BaseController {



    @Autowired
    private UserService userService;






    /***
     * 登录配置服务 loginConfiguration¶
     *
     * @return model
     * @throws java.io.IOException
     */
    @ResponseBody
    @RequestMapping(value = "/loginConfiguration.json", method = RequestMethod.POST)
    public Map<String, Object> loginConfiguration(HttpServletRequest request,
                                                  HttpServletResponse response) throws Exception {
        String requestJson = "";
        requestJson = getJsonParamStr(request);
        Map<String, Object> model = new HashMap<String, Object>();
        Map<String, Object> responseDataMap = new HashMap<String, Object>();
        Map<String, Object> notificationMap = new HashMap<String, Object>();
        if (requestJson != null && !"".equals(requestJson)) {
            JSONObject j = JSONObject.fromObject(requestJson);
            Random random = new Random();
            String imei = j.getString("imei");
            int randomCount = random.nextInt(10000);
            Long curtm = System.currentTimeMillis();
            byte[] b = (imei + randomCount + curtm + "").getBytes();
            String userSesssionId = Encodes.encodeBase64(b);
            responseDataMap.put("sessionID", userSesssionId);

            j.put("userSesssionId", userSesssionId);
            // Const c = (Const)cache.getByRedis(Constant.CONST_KEY);
            //  responseDataMap.put("const", c);
            if (!"".equals(j.getString("loginString"))) {
                String userName = j.getString("loginString");
                User user = null;
                if (userName.indexOf("@") > 0) {
                    user = userService.selectByEmail(userName);
                } else {
                    List<User> listUser= userService.selectByMobile(userName);
                    if(listUser!=null && listUser.size()>0){
                        user = listUser.get(0);
                    }else{
                        user=null;
                    }
                    if(user == null){
                        user = userService.selectByUsername(userName);
                    }

                }
                if (user == null) {
                    setSessionAndErr(request, response, "-1", "账户或密码输入有误");
                } else {
                    int hashIterations = 5000;
                    RandomNumberGenerator saltGenerator = new SecureRandomNumberGenerator();
                    // Base64解密
                    String salt = user.getSalt();
                    String password=j.getString("password");
//					byte[] bt = Base64.decode(password);
                  //  byte[] bt = Utils.decode(password);
                    // 生成新的code
                  //  password = new String(bt);
                  //  String newPass = new Sha256Hash(password,
                  //          salt, hashIterations).toHex();
                    if (user.getPassword().equals(password)) {
                        user=userService.selectByPrimaryKey(user.getId());
                        user.setLastLoginTime(DateFunc.getDate());
                        userService.updateUserInfo(user);

                        OrganRegister or=new OrganRegister();
                        or.setId(1l);
                        or.setOrganAllName("组织全称");

                        setSessionAndErr(request, response, "0", "");
                        response.setHeader("sessionID", userSesssionId);
                    } else {
                        setSessionAndErr(request, response, "-1", "账户或密码输入有误");
                    }
                }
            }
            responseDataMap.put("inviteJoinGinTongInfo", "我正在使用金桐app，一款集商务社交、投融资项目对接、个人资源管理的商务应用神器！ 推荐给你，快来哦，轻点 http://app.gintong.com 即可下载。");
            model.put("responseData", responseDataMap);
            model.put("notification", notificationMap);
        }
        return model;
    }
}
