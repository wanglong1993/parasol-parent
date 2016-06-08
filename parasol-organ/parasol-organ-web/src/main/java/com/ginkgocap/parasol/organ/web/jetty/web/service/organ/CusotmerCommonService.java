package com.ginkgocap.parasol.organ.web.jetty.web.service.organ;

import java.util.List;
import java.util.Map;

import com.ginkgocap.ywxt.organ.model.Customer;
import com.ginkgocap.ywxt.user.model.User;
import com.ginkgocap.parasol.organ.web.jetty.web.vo.organ.ColumnVo;
import com.ginkgocap.parasol.organ.web.jetty.web.vo.organ.CustomerProfileVoNew;

/**
*  组织公共的方法提取
* <p>Title: CusotmerCommonService.java<／p> 
* <p>Description: <／p> 
* @author wfl
* @date 2015-11-9 
* @version 1.0
 */
public interface CusotmerCommonService {

	//查询出所有的所选模块
	public List<ColumnVo>  findSelectModel(long orgId,CustomerProfileVoNew customer_new);
	//得到用户权限
	public void findCustomerAuth(String view,CustomerProfileVoNew customer_new,Customer customer_temp,User user);
	//查询组织客户详情中四大组件
	public void findFourModule(Map<String, Object> responseData,Customer customer_temp,String nginxRoot);
	//得到用户权限
	public String getCustomerAuth(String view,Customer customer_temp,User user);
}
