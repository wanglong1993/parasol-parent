 package com.ginkgocap.parasol.organ.web.jetty.web.service.organ.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ginkgocap.parasol.organ.web.jetty.web.service.organ.CusotmerCommonService;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.TemplateColumn;
import com.ginkgocap.parasol.organ.web.jetty.web.vo.organ.ColumnVo;
import com.ginkgocap.parasol.organ.web.jetty.web.vo.organ.CustomerProfileVoNew;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ginkgocap.ywxt.organ.model.Customer;
import com.ginkgocap.ywxt.organ.model.CustomerGroup;
import com.ginkgocap.ywxt.organ.model.privilege.CustomerPermission;
import com.ginkgocap.ywxt.organ.model.profile.CustomerProfile;
import com.ginkgocap.ywxt.organ.service.CustomerCollectService;
import com.ginkgocap.ywxt.organ.service.CustomerGroupService;
import com.ginkgocap.ywxt.organ.service.privilege.CustomerPermissionService;
import com.ginkgocap.ywxt.organ.service.profile.CustomerProfileService;

import com.ginkgocap.ywxt.user.model.User;


@Service("CustomerCommentService")
public class CustomerCommentServiceImpl implements CusotmerCommonService {

	@Resource
	private CustomerProfileService customerProfileService;
	@Resource
	private CustomerCollectService customerCollectService;
	@Resource
	private CustomerPermissionService customerPermissionService;
	@Resource
	private CustomerGroupService customerGroupService;

	public static Logger logger=LoggerFactory.getLogger(CustomerCommentServiceImpl.class);
	@Override
	public List<ColumnVo> findSelectModel(long orgId,
			CustomerProfileVoNew customer_new) {
		List<ColumnVo> list_column = new ArrayList<ColumnVo>();
		List<Long> selectIds = new ArrayList<Long>();
		// 查询出所有的所选模块
		CustomerProfile customerProfile = customerProfileService.findOne(orgId);
		if (customerProfile != null) {
			selectIds = customerProfile.getSelectCoumnIds();
			for (int i = 0; i < selectIds.size(); i++) {
				int id = selectIds.get(i).intValue();
				TemplateColumn tc = TemplateColumn.getObject(id);
				if (tc != null) {
					ColumnVo cv = new ColumnVo();
					cv.setId(tc.getIndex());
					cv.setName(tc.getName());
					list_column.add(cv);
				}
			}
		}
		customer_new.setColumns(selectIds);
		return list_column;
	}

	@Override
	public void findCustomerAuth(String view, CustomerProfileVoNew customer_new,
			Customer customer_temp, User user) {
		if (isDalePermission(user,customer_temp,view)) {// 我的客户或者金桐脑推荐或者从畅聊进入,未登录查看也是大乐权限。
			customer_new.setAuth("2");
		} else {
			// 查询权限 权限值类型（2-大乐、3-中乐、4小乐、5独乐） 客户默认独乐,组织默认中乐
			List<CustomerPermission> list = customerPermissionService
					.qryPrivilegeByReceiveId(customer_temp.getId(),
							user.getId());
			if (list.size() > 0) {
				CustomerPermission cp = list.get(0);
				if (3 == cp.getType() || 4 == cp.getType()) {// 转发分享
					customer_new.setAuth("3");
				} else {
					customer_new.setAuth(String.valueOf(cp.getType()));// 大乐(保存
																		// 转发分享)，中乐(分享)
				}
			} else {
				customer_new
						.setAuth("1".equals(customer_temp.getVirtual()) ? "3"
								: "5");// 客户默认独乐,组织模块中乐
			}
		}

	}
	//是否是大乐权限
	public boolean isDalePermission(User user,Customer customer_temp,String view){
		return user.getId() == customer_temp.getCreateById()|| "2".equals(customer_temp.getVirtual()) || "1".equals(view) || "2".equals(view)|| 0 == user.getId();
	}

	@Override
	public void findFourModule(Map<String, Object> responseData,
			Customer customer_temp,String nginxRoot){
		try{
			String cps = customer_temp.getCustomerPermissions();
			Map<String, Object> customerPermissions = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(cps)) {
				ObjectMapper objectMapper = new ObjectMapper();
				customerPermissions = objectMapper.readValue(cps,
						new TypeReference<HashMap<String, Object>>() {
						});
				// if(isWebRequest(request)){//web端登录
				customerPermissions = customerPermissionService
						.fullPermissionProperty(customerPermissions,
								nginxRoot);
				// }
			}
			// 新增目录列表
			List<Map<String, Object>> directoryMap = new ArrayList<Map<String, Object>>();
			List<CustomerGroup> groups = customerGroupService
					.findByCustomerId(customer_temp.getId());
			if (groups != null && groups.size() > 0) {
				for (int i = 0; i < groups.size(); i++) {
					CustomerGroup group = groups.get(i);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", group.getId());
					map.put("name", group.getName());
					directoryMap.add(map);
				}
			}
			// 关联
			String relevance = customer_temp.getRelevance();
			Map<String, Object> relevanceMap = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(relevance)) {
				ObjectMapper objectMapper = new ObjectMapper();
				relevanceMap = objectMapper.readValue(relevance,
						new TypeReference<HashMap<String, Object>>() {
						});
			}
			responseData.put("customerPermissions", customerPermissions);
			responseData.put("directory", directoryMap);
			responseData.put("relevance", relevanceMap);
		}catch(Exception e){
			logger.info("查询组织客户详情时,四大组织报错，报错信息为:",e);
		}
		
	}

	@Override
	public String getCustomerAuth(String view,Customer customer_temp, User user) {
		if (isDalePermission(user,customer_temp,view)) {// 我的客户或者金桐脑推荐或者从畅聊进入,未登录查看也是大乐权限。
			return "2";
		} else {
			// 查询权限 权限值类型（2-大乐、3-中乐、4小乐、5独乐） 客户默认独乐,组织默认中乐
			List<CustomerPermission> list = customerPermissionService
//					.qryPrivilegeByReceiveId(customer_temp.getId(),
					.qryPrivilegeByReceiveId(customer_temp.getCustomerId(),// caizhigang
							user.getId());
			if (list.size() > 0) {
				CustomerPermission cp = list.get(0);
				if (3 == cp.getType() || 4 == cp.getType()) {// 转发分享
					return "3";
				} else {
				   return String.valueOf(cp.getType());// 大乐(保存// 转发分享)，中乐(分享)
				}
			} else {
                   return "1".equals(customer_temp.getVirtual()) ? "3": "5";// 客户默认独乐,组织模块中乐
			}
		}
	}

}
