package com.ginkgocap.parasol.person.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;

import com.ginkgocap.parasol.person.model.PersonBasic;
import com.ginkgocap.parasol.person.model.PersonContact;
import com.ginkgocap.parasol.person.service.PersonInfoOperateService;
import com.ginkgocap.parasol.user.model.ModelType;

public class PersonInfoOperatorServiceTest extends TestBase implements Test{
	@Resource
	private PersonInfoOperateService personInfoOperateService;
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
		PersonBasic u = new PersonBasic();
		u.setAppId(1l);
		u.setName("test1");
		u.setCreateId(1l);
		u.setSex(new Byte("1"));
		param.put(ModelType.UB, u);
		PersonContact uc = new PersonContact();
		uc.setAppId(1l);
		uc.setPersonId(1l);
		uc.setName("手机");
		uc.setSubtype("1");
		uc.setContent("18600954359");
		PersonContact uc1 = new PersonContact();
		uc1.setAppId(1l);
		uc1.setPersonId(1l);
		uc1.setName("邮箱");
		uc1.setSubtype("1");
		uc1.setContent("12341@qq.com");
		List<PersonContact> userContacts = new ArrayList<PersonContact>();
		userContacts.add(uc);
		userContacts.add(uc1);
		param.put(ModelType.UC, userContacts);
		List<Integer> modelTypes = new ArrayList<Integer>();
		modelTypes.add(ModelType.UB);
		modelTypes.add(ModelType.UC);
		personInfoOperateService.deleteInfo(1l, modelTypes);
		personInfoOperateService.saveInfo(param);
		Map<Integer,Object> param1 = new HashMap<Integer,Object>();
		PersonBasic u1 = new PersonBasic();
		u1.setAppId(1l);
		u1.setName("test3");
		u1.setSex(new Byte("2"));
		param1.put(ModelType.UB, u1);
		
		uc = new PersonContact();
		uc.setId(3979699848216584l);
		uc.setAppId(1l);
		uc.setPersonId(1l);
		uc.setName("手机");
		uc.setSubtype("1");
		uc.setContent("18600954359");
		uc1 = new PersonContact();
		uc1.setId(3979699848216593l);
		uc1.setAppId(1l);
		uc1.setPersonId(1l);
		uc1.setName("邮箱");
		uc1.setSubtype("1");
		uc1.setContent("12356@qq.com");
		Map<String,List<PersonContact>> userContactMap = new HashMap<String,List<PersonContact>>();
		List<PersonContact> delete = new ArrayList<PersonContact>();
		delete.add(uc);
		userContactMap.put("delete", delete);
		List<PersonContact> update = new ArrayList<PersonContact>();
		update.add(uc1);
		userContactMap.put("update", update);
		param1.put(ModelType.UC, userContactMap);
		personInfoOperateService.updateInfo(param1);
	}

}
