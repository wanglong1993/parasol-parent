package com.ginkgocap.parasol.organ.web.jetty.web.service;

import java.util.Map;

/**
 * 组织关联信息封装处理
 * @author hdy
 * @date 2015-3-16
 */
public interface DealCustomerConnectInfoService {
	
	Map<String, Object> insertCustomerConnectInfo(String customerAsso,Long customerId,Long userid);
	
	void deleteCustomerConnectInfo(Long customerId);

}
