package com.ginkgocap.parasol.organ.web.jetty.web.service.organ.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ginkgocap.parasol.organ.web.jetty.web.service.organ.DealCustomerConnectInfoService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.ginkgocap.ywxt.knowledge.service.KnowledgeHomeService;
import com.ginkgocap.ywxt.organ.model.ConnectionInfo;
import com.ginkgocap.ywxt.organ.model.Constants2;
import com.ginkgocap.ywxt.organ.model.SimpleCustomer;
import com.ginkgocap.ywxt.organ.service.CustomerConnectInfoService;
import com.ginkgocap.ywxt.organ.service.SimpleCustomerService;
import com.ginkgocap.ywxt.person.model.Person;
import com.ginkgocap.ywxt.person.model.PersonName;
import com.ginkgocap.ywxt.person.service.PersonService;
import com.ginkgocap.ywxt.requirement.model.Requirement;
import com.ginkgocap.ywxt.requirement.service.RequirementService;
import com.ginkgocap.ywxt.user.model.User;
import com.ginkgocap.ywxt.user.service.FriendsRelationService;
import com.ginkgocap.ywxt.user.service.UserService;
import com.ginkgocap.ywxt.util.JsonUtil;
/*
import com.ginkgocap.ywxt.people.domain.modelnew.PeopleName;
import com.ginkgocap.ywxt.people.domain.modelnew.PeopleSimple;
import com.ginkgocap.ywxt.people.service.PeopleMongoService;
*/

/**
 * 组织关联信息封装处理(调用组织项目的service)
 * @author hdy
 * @date 2015-3-16
 */
@Service("dealCustomerConnectInfoService")
public class DealCustomerConnectInfoServiceImpl implements DealCustomerConnectInfoService {

//	@Resource
//	private PeopleMongoService peopleMongoService;

	@Resource
	private KnowledgeHomeService knowledgeHomeService;

	@Resource
	private SimpleCustomerService simpleCustomerService;

	@Resource
	private RequirementService requirementService;
	@Resource
	private FriendsRelationService friendsRelationService;
	@Resource
	CustomerConnectInfoService customerConnectInfoService;
	@Resource
	UserService userService;
	@Resource
	private PersonService personService;

	 /**插入关联信息
	    * @param customerAsso
	    * 	 关联格式（r:事件,p:人脉,o:组织,k:知识）
			 customerAsso:{"r":[{"tag":"33","conn":[{"type":1,"id":2111,"title":"d d d d","ownerid":1,"ownername":"的的的的的的的","requirementtype":"tzxq"}]}],
					 "p":[{"tag":"111","conn":[{"type":2,"id":"141527672992500079","name":"五小六","ownerid":1,"ownername":"","caree":"","company":""}]}],
				 	 "o":[{"tag":"22","conn":[{"type":5,"id":618,"name":"我的测试客户","ownerid":"","ownername":"","address":"","hy":","},{"type":5,"id":617,"name":" 中国平安","ownerid":"","ownername":"","address":"","hy":",保险公司,"}]}],
					 "k":[{"tag":"44","conn":[{"type":5,"id":618,"title":"测试知识","ownerid":"","ownername":"","columntype":"","columnpath":""]}
	    * @param customerId 
	    * @param userid
	    * @param list
	    * @return
	    */
		public Map<String, Object> insertCustomerConnectInfo(String customerAsso,Long customerId,Long userid){

			Map<String, Object> result = new HashMap<String, Object>();
			//所有关联信息
			List<ConnectionInfo> allConneclist = new ArrayList<ConnectionInfo>();
			try{
				JSONObject j = JSONObject.fromObject(customerAsso);
				String jsonstr = "";
				String tag = "";
				String conn = "";
				String assotype[] = { Constants2.KnowledgeConnectType.event.c(),
						Constants2.KnowledgeConnectType.people.c(),
						Constants2.KnowledgeConnectType.organization.c(),
						Constants2.KnowledgeConnectType.knowledge.c() };
				for (int i = 0; i < assotype.length; i++) {

					if(j.get(assotype[i])==null){
						 continue;
					}
					jsonstr = j.get(assotype[i]).toString();
					if(StringUtils.isNotEmpty(jsonstr) && !StringUtils.equals(jsonstr, "[]")){
						JSONArray json = JSONArray.fromObject(jsonstr); // jsonstr:[{"tag":"33","conn":[{"type":1,"id":2111,"title":"d d d d","ownerid":1,"ownername":"的的的的的的的","requirementtype":"tzxq"}]
						for (int t = 0; t < json.size(); t++) {
							List<ConnectionInfo> conneclist = new ArrayList<ConnectionInfo>();
							JSONObject job = json.getJSONObject(t); // 遍历 jsonarray
							tag = getTag(job.toString());
							conn = getConn(job.toString());
							if( StringUtils.equals(conn, "-9")){
								//全部
								conneclist = allAsso(tag,assotype[i],customerId,userid);
							}else{
								conneclist = insertJsonAraay(conn, tag, customerId);
							}
							if (conneclist.size() < 0) {
								result.put(Constants2.status, Constants2.ResultType.fail.v());
								return result;
							}
							allConneclist.addAll(conneclist);
						}
					}

				}
			}catch(Exception e){
			}
			customerConnectInfoService.insertCustomerConnectInfo(allConneclist, customerId, userid);
			result.put(Constants2.status, Constants2.ResultType.success.v());
			return result;
		}
	/**
	 * 关联所有的
	 * @param tag
	 * @param assotype
	 * @param customerId
	 * @param userid
	 * @return
	 * liubang
	 */
	public List<ConnectionInfo> allAsso(String tag,String assotype,Long customerId,Long userid){
		List<ConnectionInfo> conneclist = new ArrayList<ConnectionInfo>();
		// 选人脉全部
		//TODO 新版人脉
		if (StringUtils.equals(assotype, "p")) {
			List<Person> peoples =    personService.selectByUserId(userid , 0,6)   ;
			if (peoples != null && peoples.size() > 0) {
				for (Person peopleSimple : peoples) {
					conneclist.add(getPConnectionInfo(tag,peopleSimple, customerId, userid));
				}
				if(peoples.size()<6){
					List<User> list = friendsRelationService.findAllFriends(userid, 0,
							"", "", 0, 6 - peoples.size());
					for (User user : list) {
						conneclist.add(getUConnectionInfo(tag,user, customerId, userid));
					}
				}

			}
		}
		//TODO 新版需求
		if (StringUtils.equals(assotype, "r")) {
			conneclist = new ArrayList<ConnectionInfo>();
			Map<String, Object> map = requirementService.selectMy(userid, 1, 6, -1, "");
			List<Requirement> results = (List<Requirement>) map.get("results");
			if (results != null && results.size() > 0) {
				for (Requirement requirement : results) {
					conneclist.add(this.getRConnectionInfo(tag,requirement, customerId, userid));
				}
			}

		}
		if (StringUtils.equals(assotype, "o")) {

			conneclist = new ArrayList<ConnectionInfo>();
			List<Long> ids = friendsRelationService.findFirendsIdByPram(userid, 2);
			Map<String, Object> map = simpleCustomerService.findByOrgAndCustmer(userid, ids, null, 1, 6);
			List<SimpleCustomer> lt = (List<SimpleCustomer>)map.get("results");
			if (lt != null && lt.size() > 0) {
				for (SimpleCustomer sc:lt) {
					conneclist.add(this.getOConnectionInfo(tag,sc, customerId, userid));
				}
			}

		}
		if (StringUtils.equals(assotype, "k")) {

			conneclist = new ArrayList<ConnectionInfo>();
			Map<String, Object> map = knowledgeHomeService.selectAllKnowledgeCategoryByParam("", "", 0, "",userid, "", 1, 6);
			List<Map<String, Object>> list= (List<Map<String, Object>>) map.get("list");
			if (list != null && list.size() > 0) {
				for (int m = 0; m < list.size(); m++) {
					Map<String, Object> mapk = list.get(m);
					if (mapk != null) {
						conneclist.add(getKConnectionInfo(tag,mapk, customerId, userid));
					}
				}
			}

		}
		return conneclist;
	}
	/*
	 * 获得人脉关联关系  
	 */
	private ConnectionInfo getPConnectionInfo(String tag,Person peopleSimple,long customerId,long userid){
		ConnectionInfo connectionInfo = new ConnectionInfo();
		connectionInfo.setConntype(Constants2.KnowledgeConnectType.people.v());
		connectionInfo.setCustomerId(customerId);
		connectionInfo.setConnid(peopleSimple.getId());
//		connectionInfo.setUrl("/people/"	+ peopleSimple.getId());
		//TODO 需要根据人脉修改链接
		connectionInfo.setOwnerid(userid);
		connectionInfo.setTag(tag);
		connectionInfo.setAllasso(-1); //全部标识
		connectionInfo.setPicpath(peopleSimple.getPortrait());
		List<PersonName> list = peopleSimple.getPeopleNameList();
		if (list != null && list.size() > 0) {
			PersonName peopleName=list.get(0);
			connectionInfo.setConnname(StringUtils.trimToEmpty(peopleName.getLastname())+StringUtils.trimToEmpty(peopleName.getFirstname()));
		}
		return connectionInfo;
	}
	/*
	 * 获得好友关系
	 */
	private ConnectionInfo getUConnectionInfo(String tag,User user,long customerId,long userid){
		ConnectionInfo connectionInfo = new ConnectionInfo();
		connectionInfo.setConntype(Constants2.KnowledgeConnectType.people.v());
		connectionInfo.setCustomerId(customerId);
		connectionInfo.setConnid(user.getId());
//		connectionInfo.setUrl("/member/view/?id="	+user.getId());
		//TODO 需要根据人脉修改链接
		connectionInfo.setOwnerid(userid);
		connectionInfo.setTag(tag);
		connectionInfo.setAllasso(-1); //全部标识
		connectionInfo.setPicpath(user.getPicPath());
		connectionInfo.setConnname(user.getName());
		return connectionInfo;
	}
	/**
	 * 获得事件关系
	 * @param requirement
	 * @param customerId
	 * @param userid
	 * @return
	 * liubang
	 */
	private ConnectionInfo getRConnectionInfo(String tag,Requirement requirement,long customerId,long userid){
		ConnectionInfo connectionInfo = new ConnectionInfo();
		connectionInfo = new ConnectionInfo();
		connectionInfo.setConntype(Constants2.KnowledgeConnectType.event.v());
		connectionInfo.setCustomerId(customerId);
		connectionInfo.setConnid(requirement.getId());
		connectionInfo.setConnname(requirement.getTitle());
		connectionInfo.setOwnerid(requirement.getFbrId());
		connectionInfo.setOwner(requirement.getFbr());
		connectionInfo.setTag(tag);
		connectionInfo.setAllasso(-1); //全部标识
		connectionInfo.setRequirementtype(requirement.getRequirementType() + "");
//		connectionInfo.setUrl("/requirement/detail/"+ requirement.getRequirementType() + ""+ "/" + requirement.getId()+ "/");
		//TODO 需要修改搜索的
		return connectionInfo;
		
	}
	/**
	 * 获得组织关系
	 * @param map
	 * @param customerId
	 * @param userid
	 * @return
	 * liubang
	 */
	private ConnectionInfo getOConnectionInfo(String tag,SimpleCustomer sc,long customerId,long userid){
		ConnectionInfo connectionInfo = new ConnectionInfo();
		connectionInfo.setConntype(Constants2.KnowledgeConnectType.organization.v());
		connectionInfo.setCustomerId(customerId);
		connectionInfo.setConnid(Long.parseLong(sc.getId() + ""));
		connectionInfo.setConnname(sc.getName());
		connectionInfo.setOwnerid(userid);
		connectionInfo.setTag(tag);
		connectionInfo.setAllasso(-1); //全部标识
		connectionInfo.setPicpath(sc.getPicLogo());
		return connectionInfo;
	}
	/*
	 * 获得知识关联关系
	 */
	private ConnectionInfo getKConnectionInfo(String tag,Map<String, Object> mapk,long customerId,long userid){
		ConnectionInfo connectionInfo = new ConnectionInfo();
		connectionInfo.setConntype(Constants2.KnowledgeConnectType.knowledge.v());
		connectionInfo.setCustomerId(customerId);
		connectionInfo.setConnid(Long.parseLong(mapk.get("knowledge_id") + ""));
		connectionInfo.setConnname(mapk.get("title")+ "");
		connectionInfo.setOwnerid(userid);
		connectionInfo.setTag(tag);
		connectionInfo.setAllasso(-1); //全部标识
		connectionInfo.setColumnpath(mapk.get("path")+ "");
		if( mapk.get("columntype") != null){
			connectionInfo.setColumntype(Integer.parseInt(mapk.get("columntype") + ""));
			connectionInfo.setUrl("/knowledge/reader?type="+ mapk.get("columntype") + ""+ "&kid="+ mapk.get("knowledge_id") + "");
		}else{
			connectionInfo.setColumntype(Integer.parseInt(mapk.get("column_type") + ""));
			connectionInfo.setUrl("/knowledge/reader?type="+ mapk.get("column_type") + ""+ "&kid="+ mapk.get("knowledge_id") + "");
		}
		return connectionInfo;
	}

	public void deleteCustomerConnectInfo(Long customerId) {
		customerConnectInfoService.deleteCustomerConnectInfo(customerId);
	}
	/**
	 * 获取关联tag值
	 * 
	 * @param str
	 * @return
	 */
	public String getTag(String str) {
		JSONObject j = JSONObject.fromObject(str);
		String strr = j.get("tag").toString();
		return strr;
	}
	
	public List<ConnectionInfo> insertJsonAraay(String str, String tag, Long customerId) {

		List<ConnectionInfo> conlist = new ArrayList<ConnectionInfo>();
		JSONArray json = JSONArray.fromObject(str); // 首先把字符串转成
		ConnectionInfo connectInfo = null;
		if (json.size() > 0) {
			for (int i = 0; i < json.size(); i++) {
				JSONObject job = json.getJSONObject(i); // 遍历 jsonarray
				connectInfo = new ConnectionInfo();
				connectInfo.setCustomerId(customerId);
				connectInfo.setTag(tag);
				int type =JsonUtil.getNodeToInt(job, "type",-2);
				connectInfo.setConntype(type);
				long id = JsonUtil.getNodeToLong(job, "id",-2);
				connectInfo.setConnid(id);
				if (type == Constants2.KnowledgeConnectType.event.v()
						|| type == Constants2.KnowledgeConnectType.knowledge.v()) {

					connectInfo.setConnname(JsonUtil.getNodeToString(job,"title")) ;
				} else {
					connectInfo.setConnname(JsonUtil.getNodeToString(job,"name"));
				}
				connectInfo.setOwnerid(JsonUtil.getNodeToLong(job,"ownerid",-2));
				connectInfo.setOwner(JsonUtil.getNodeToString(job,"ownername"));
				if (type == Constants2.KnowledgeConnectType.event.v()) {
					connectInfo.setRequirementtype(JsonUtil.getNodeToString(job,"requirementtype"));
					connectInfo.setCareer(JsonUtil.getNodeToString(job,"career") );
//					connectInfo.setUrl("/requirement/detail/"+ job.get("requirementtype") + "" + "/"+ job.get("id"));
				}
				if (type== Constants2.KnowledgeConnectType.people.v()) {
					connectInfo.setCompany(JsonUtil.getNodeToString(job,"company"));
//					connectInfo.setUrl("/people/" + job.get("id"));
				}
				if (type == Constants2.KnowledgeConnectType.organization.v()) {
					connectInfo.setCompany(JsonUtil.getNodeToString(job, "address"));
					connectInfo.setHy(JsonUtil.getNodeToString(job, "hy"));
					SimpleCustomer simpleCustomer = simpleCustomerService.findByCustomerId(id);
					if(simpleCustomer!=null){
						connectInfo.setPicpath(simpleCustomer.getPicLogo());
					}else{
						connectInfo.setPicpath("/web1/organ/default.jpeg");
					}
				}
				if (type == Constants2.KnowledgeConnectType.knowledge.v()) {
					connectInfo.setColumntype(JsonUtil.getNodeToInt(job, "columntype"));
					connectInfo.setColumnpath(JsonUtil.getNodeToString(job, "columnpath"));
//					connectInfo.setUrl("/knowledge/reader?type="+ job.get("columntype") + "&kid=" + job.get("id"));
				}

				if (3 == Constants2.KnowledgeConnectType.people.v()
						|| 4 == Constants2.KnowledgeConnectType.organization.v()) {
					User user = userService.selectByPrimaryKey(id);
					if (user != null) {
						connectInfo.setPicpath(user.getPicPath());
					}
				}
				conlist.add(connectInfo);
			}
		}
		return conlist;
	}

	/**
	 * 获取关联conn值
	 * 
	 * @param str
	 * @return
	 */
	public String getConn(String str) {
		JSONObject j = JSONObject.fromObject(str);
		String strr = "";
		if( j.get("conn") != null ){
			
			strr = j.get("conn").toString();
		}
		return strr;
	}

}
