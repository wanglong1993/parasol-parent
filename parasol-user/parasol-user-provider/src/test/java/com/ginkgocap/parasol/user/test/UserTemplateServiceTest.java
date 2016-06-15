package com.ginkgocap.parasol.user.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import com.ginkgocap.parasol.user.model.UserTemplateModel;
import com.ginkgocap.parasol.user.service.UserTemplateModelService;
import com.ginkgocap.parasol.user.service.UserTemplateService;

public class UserTemplateServiceTest extends TestBase implements Test {
	@Resource
	private UserTemplateService userTemplateService;
	@Resource
	private UserTemplateModelService userTemplateModelService;
	
	@Override
	public int countTestCases() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void run(TestResult result) {
		// TODO Auto-generated method stub

	}
	
/*	@org.junit.Test
	public void saveTemplate() {
		UserTemplate userTemplate = new UserTemplate();
		userTemplate.setIp("127.0.0.1");
		userTemplate.setUserId(0l);
		userTemplate.setTemplate_name("默认");
		userTemplate.setTemplate_code("default");
		userTemplate.setAppId(1l);
		UserTemplate userTemplate0 = new UserTemplate();
		userTemplate0.setIp("127.0.0.1");
		userTemplate0.setUserId(0l);
		userTemplate0.setTemplate_name("医生");
		userTemplate0.setTemplate_code("doctor");
		userTemplate0.setAppId(1l);
		UserTemplate userTemplate1 = new UserTemplate();
		userTemplate1.setIp("127.0.0.1");
		userTemplate1.setUserId(0l);
		userTemplate1.setTemplate_name("律师");
		userTemplate1.setTemplate_code("lawyer");
		userTemplate1.setAppId(1l);
		UserTemplate userTemplate2 = new UserTemplate();
		userTemplate2.setIp("127.0.0.1");
		userTemplate2.setUserId(0l);
		userTemplate2.setTemplate_name("教师");
		userTemplate2.setTemplate_code("teacher");
		userTemplate2.setAppId(1l);
		UserTemplate userTemplate3 = new UserTemplate();
		userTemplate3.setIp("127.0.0.1");
		userTemplate3.setUserId(0l);
		userTemplate3.setTemplate_name("历史人物");
		userTemplate3.setTemplate_code("history");
		userTemplate3.setAppId(1l);
		List<UserTemplate> templates = new ArrayList<UserTemplate>();
		templates.add(userTemplate);
		templates.add(userTemplate0);
		templates.add(userTemplate1);
		templates.add(userTemplate2);
		templates.add(userTemplate3);
		try {
			userTemplateService.createObjects(templates);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
	@org.junit.Test
	public void saveTemplateModel() {
//		UserTemplateModel userTemplateModel = new UserTemplateModel();
//		userTemplateModel.setAppId(1l);
//		userTemplateModel.setUserId(0l);
//		userTemplateModel.setIp("127.0.0.1");
//		userTemplateModel.setTemplate_id(3981873835671574l);
//		userTemplateModel.setModel_code("UB");
//		userTemplateModel.setModel_name("基本信息");
//		UserTemplateModel userTemplateModel9 = new UserTemplateModel();
//		userTemplateModel9.setAppId(1l);
//		userTemplateModel9.setUserId(0l);
//		userTemplateModel9.setIp("127.0.0.1");
//		userTemplateModel9.setTemplate_id(3981873835671574l);
//		userTemplateModel9.setModel_code("UDN");
//		userTemplateModel9.setModel_name("描述");
		UserTemplateModel userTemplateModel0 = new UserTemplateModel();
		userTemplateModel0.setId(100l);
		userTemplateModel0.setAppId(1l);
		userTemplateModel0.setUserId(0l);
		userTemplateModel0.setIp("127.0.0.1");
		userTemplateModel0.setTemplate_id(3981873835671574l);
		userTemplateModel0.setModel_code("test");
		userTemplateModel0.setModel_name("test");
//		UserTemplateModel userTemplateModel1 = new UserTemplateModel();
//		userTemplateModel1.setAppId(1l);
//		userTemplateModel1.setUserId(0l);
//		userTemplateModel1.setIp("127.0.0.1");
//		userTemplateModel1.setTemplate_id(3981873835671574l);
//		userTemplateModel1.setModel_code("UWH");
//		userTemplateModel1.setModel_name("工作经历");
//		UserTemplateModel userTemplateModel2 = new UserTemplateModel();
//		userTemplateModel2.setAppId(1l);
//		userTemplateModel2.setUserId(0l);
//		userTemplateModel2.setIp("127.0.0.1");
//		userTemplateModel2.setTemplate_id(3981873835671574l);
//		userTemplateModel2.setModel_code("UEH");
//		userTemplateModel2.setModel_name("教育经历");
//		UserTemplateModel userTemplateModel3 = new UserTemplateModel();
//		userTemplateModel3.setAppId(1l);
//		userTemplateModel3.setUserId(0l);
//		userTemplateModel3.setIp("127.0.0.1");
//		userTemplateModel3.setTemplate_id(3981873835671574l);
//		userTemplateModel3.setModel_code("US");
//		userTemplateModel3.setModel_name("擅长技能");
//		UserTemplateModel userTemplateModel4 = new UserTemplateModel();
//		userTemplateModel4.setAppId(1l);
//		userTemplateModel4.setUserId(0l);
//		userTemplateModel4.setIp("127.0.0.1");
//		userTemplateModel4.setTemplate_id(3981873835671574l);
//		userTemplateModel4.setModel_code("UIG");
//		userTemplateModel4.setModel_name("兴趣爱好");
//		UserTemplateModel userTemplateModel5 = new UserTemplateModel();
//		userTemplateModel5.setAppId(1l);
//		userTemplateModel5.setUserId(0l);
//		userTemplateModel5.setIp("127.0.0.1");
//		userTemplateModel5.setTemplate_id(3981873835671574l);
//		userTemplateModel5.setModel_code("UIO");
//		userTemplateModel5.setModel_name("个人情况");
//		UserTemplateModel userTemplateModel6 = new UserTemplateModel();
//		userTemplateModel6.setAppId(1l);
//		userTemplateModel6.setUserId(0l);
//		userTemplateModel6.setIp("127.0.0.1");
//		userTemplateModel6.setTemplate_id(3981873835671574l);
//		userTemplateModel6.setModel_code("UFM");
//		userTemplateModel6.setModel_name("家庭成员");
//		UserTemplateModel userTemplateModel7 = new UserTemplateModel();
//		userTemplateModel7.setAppId(1l);
//		userTemplateModel7.setUserId(0l);
//		userTemplateModel7.setIp("127.0.0.1");
//		userTemplateModel7.setTemplate_id(3981873835671574l);
//		userTemplateModel7.setModel_code("UD");
//		userTemplateModel7.setModel_name("用户自定义");
//		UserTemplateModel userTemplateModel8 = new UserTemplateModel();
//		userTemplateModel8.setAppId(1l);
//		userTemplateModel8.setUserId(0l);
//		userTemplateModel8.setIp("127.0.0.1");
//		userTemplateModel8.setTemplate_id(3981873835671574l);
//		userTemplateModel8.setModel_code("UA");
//		userTemplateModel8.setModel_name("相关附件");
		List<UserTemplateModel> userTemplateModels = new ArrayList<UserTemplateModel>();
//		userTemplateModels.add(userTemplateModel);
//		userTemplateModels.add(userTemplateModel9);
		userTemplateModels.add(userTemplateModel0);
//		userTemplateModels.add(userTemplateModel1);
//		userTemplateModels.add(userTemplateModel2);
//		userTemplateModels.add(userTemplateModel3);
//		userTemplateModels.add(userTemplateModel4);
//		userTemplateModels.add(userTemplateModel5);
//		userTemplateModels.add(userTemplateModel6);
//		userTemplateModels.add(userTemplateModel8);
//		userTemplateModels.add(userTemplateModel7);
		try {
			userTemplateModelService.createObjects(userTemplateModels);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
