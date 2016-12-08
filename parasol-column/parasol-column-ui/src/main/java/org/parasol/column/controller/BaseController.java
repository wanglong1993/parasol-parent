package org.parasol.column.controller;

import java.io.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.collections.CollectionUtils;
import org.parasol.column.entity.ColumnSelf;
import org.parasol.column.service.ColumnCustomService;
import org.parasol.column.service.ColumnSelfService;
import org.parasol.column.service.ColumnSysService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.ginkgocap.ywxt.user.model.User;

import net.sf.json.JSONObject;

public class BaseController implements InitializingBean {

	private Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@Autowired
	private ColumnCustomService customService;
	
	@Autowired
	private ColumnSelfService selfService;
	
	@Autowired
	private ColumnSysService sysService;
	
	protected List<ColumnSelf> sysList;
	
	/**
	 * 获取用户
	 * @param request
	 * @return
	 */
	public User getUser(HttpServletRequest request) {
		//在AppFilter过滤器里面从cache获取了当前用户对象并设置到request中了
		return (User) request.getAttribute("sessionUser");
	}
	
	public Long getUserId(HttpServletRequest request){
		User user=this.getUser(request);
		Long uid=0l;
		if(user!=null){
			uid=user.getId();
		}
		return uid;
	}
	
	/**
	 * 获取前端数据
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public JSONObject getRequestJson(HttpServletRequest request) throws IOException {
		
		String requestString = (String)request.getAttribute("requestJson");
		
		JSONObject requestJson = JSONObject.fromObject(requestString == null ? "" : requestString);
		
		return requestJson;
	}
	
	public String readJSONString(HttpServletRequest request){
        StringBuffer json = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while((line = reader.readLine()) != null) {
                json.append(line);
            }
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }
        return json.toString();
		
    }
	
	/**
	 * 获取栏目
	 * @author 王飞
	 * @date 2016年12月7日 下午4:52:17
	 * @param pid
	 * @param uid
	 * @return Map<String,List<ColumnSelf>>
	 */
	public Map<String,List<ColumnSelf>> returnColumnMap(Long pid, Long uid, HttpServletRequest request) {
		//系统栏目
		//List<ColumnSelf> sysList = sysService.queryDefaultSysColumn(pid, 0l);
		Map<String, List<ColumnSelf>> map = new HashMap<String, List<ColumnSelf>>(2);
		if (uid.longValue() != 0) {
			//自定义栏目
			List<ColumnSelf> custList = customService.queryListByPidAndUserId(pid, uid);
			//返回子目录栏目
			if (pid != 0){
				map.put("subList",custList);
				return map;
			}
			//当前用户没有自定义栏目时，设置10条默认栏目且插入到数据库
			if (custList == null || custList.size() == 0) {
				custList = new ArrayList<ColumnSelf>();
				for (int i = 0; i < 10; i++) {
					ColumnSelf columnSelf = sysList.get(i);
					if (columnSelf.getDelStatus() == (short) 0) {
						custList.add(columnSelf);
					}
				}
				customService.insertBatch(custList, uid);
				map.put("selfList", sysList.subList(0, 9));
				map.put("restList", sysList.subList(10, 29));
				return map;
			} else {
				Set<Long> idList = new HashSet<Long>();
				map.put("selfList", custList);
				for (ColumnSelf self : custList) {
					idList.add(self.getId().longValue());
				}
				List<ColumnSelf> restList = new ArrayList<ColumnSelf>();
				for (ColumnSelf sys : sysList) {
					if (sys != null && !idList.contains(sys.getId().longValue())) {
						restList.add(sys);
					}
				}
				map.put("restList", restList);
				return map;
			}
		}
		return new HashMap<String, List<ColumnSelf>>();
	}


	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		sysList= sysService.queryDefaultSysColumn(0l, 0l);
		if (CollectionUtils.isEmpty(sysList)) {
			logger.error("数据库数据出现问题，请联系DBA！");
		}
	}

	/**
	 * 返回系统栏目
	 * @return
	 */
	public List<ColumnSelf> returnSysList(){
		return sysList;
	}
}