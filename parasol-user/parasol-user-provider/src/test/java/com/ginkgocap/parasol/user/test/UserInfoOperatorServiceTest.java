package com.ginkgocap.parasol.user.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import com.ginkgocap.parasol.user.model.ModelType;
import com.ginkgocap.parasol.user.model.UserBasic;
import com.ginkgocap.parasol.user.model.UserContact;
import com.ginkgocap.parasol.user.service.UserInfoOperateService;

public class UserInfoOperatorServiceTest extends TestBase implements Test{
	@Resource
	private UserInfoOperateService userInfoOperateService;
	@Override
	public int countTestCases() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void run(TestResult arg0) {
		
	}
	@org.junit.Test
	public void testSaveInfo() throws Exception{
		Map<Integer,Object> param = new HashMap<Integer,Object>();
		UserBasic u = new UserBasic();
		u.setAppId(1l);
		u.setName("test1");
		u.setUserId(1l);
		u.setSex(new Byte("1"));
		param.put(ModelType.UB, u);
		UserContact uc = new UserContact();
		uc.setAppId(1l);
		uc.setUserId(1l);
		uc.setName("手机");
		uc.setSubtype("1");
		uc.setContent("18600954359");
		UserContact uc1 = new UserContact();
		uc1.setAppId(1l);
		uc1.setUserId(1l);
		uc1.setName("邮箱");
		uc1.setSubtype("1");
		uc1.setContent("12341@qq.com");
		List<UserContact> userContacts = new ArrayList<UserContact>();
		userContacts.add(uc);
		userContacts.add(uc1);
		param.put(ModelType.UC, userContacts);
		List<Integer> modelTypes = new ArrayList<Integer>();
		modelTypes.add(ModelType.UB);
		modelTypes.add(ModelType.UC);
		userInfoOperateService.deleteInfo(1l, modelTypes);
		userInfoOperateService.saveInfo(param);
		Map<Integer,Object> param1 = new HashMap<Integer,Object>();
		UserBasic u1 = new UserBasic();
		u1.setAppId(1l);
		u1.setName("test3");
		u1.setUserId(1l);
		u1.setSex(new Byte("2"));
		param1.put(ModelType.UB, u1);
		
		uc = new UserContact();
		uc.setId(3979699848216584l);
		uc.setAppId(1l);
		uc.setUserId(1l);
		uc.setName("手机");
		uc.setSubtype("1");
		uc.setContent("18600954359");
		uc1 = new UserContact();
		uc1.setId(3979699848216593l);
		uc1.setAppId(1l);
		uc1.setUserId(1l);
		uc1.setName("邮箱");
		uc1.setSubtype("1");
		uc1.setContent("12356@qq.com");
		Map<String,List<UserContact>> userContactMap = new HashMap<String,List<UserContact>>();
		List<UserContact> delete = new ArrayList<UserContact>();
		delete.add(uc);
		userContactMap.put("delete", delete);
		List<UserContact> update = new ArrayList<UserContact>();
		update.add(uc1);
		userContactMap.put("update", update);
		param1.put(ModelType.UC, userContactMap);
		userInfoOperateService.updateInfo(param1);
	}

}
