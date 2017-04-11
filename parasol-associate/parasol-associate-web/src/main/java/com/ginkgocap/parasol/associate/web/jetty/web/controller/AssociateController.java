/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ginkgocap.parasol.associate.web.jetty.web.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.ginkgocap.parasol.associate.service.AssociateTypeService;
import com.ginkgocap.parasol.util.JsonUtils;
import com.gintong.frame.util.dto.CommonResultCode;
import com.gintong.frame.util.dto.InterfaceResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ginkgocap.parasol.associate.exception.AssociateServiceException;
import com.ginkgocap.parasol.associate.model.Associate;
import com.ginkgocap.parasol.associate.model.AssociateType;
import com.ginkgocap.parasol.associate.service.AssociateService;
import com.ginkgocap.parasol.associate.model.Page;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author allenshen
 * @date 2015骞�11鏈�20鏃�
 * @time 涓嬪崍1:19:18
 * @Copyright Copyright漏2015 www.gintong.com
 */
@RestController
public class AssociateController extends BaseControl {
	private static Logger logger = Logger.getLogger(AssociateController.class);

	private static final String parameterFields = "fields";
	private static final String parameterDebug = "debug";

	private static final String parameterId = "id"; // id 鏍囪瘑

	private static final String parameterSourceTypeId = "sourceTypeId"; // 浠�涔堣祫婧愮被鍨嬶紝姣斿璧勬簮鏄竴涓煡璇嗙被鍨嬶紝鍙傝�傾ssociateType
	private static final String parameterSourceId = "sourceId"; // 璧勬簮鐨処D

	private static final String parameterAssocDesc = "assocDesc"; // 鍏宠仈鏃跺�欏～鍐欑殑鏍囩鍏宠仈鍏崇郴
	private static final String parameterAssocTypeId = "assocTypeId"; // 鍏宠仈鍒颁粈涔堢被鍨嬩笂锛堟瘮濡備竴绡囩煡璇嗭紝涓�涓簨鐗┿�佷竴涓渶姹傜瓑锛�
	private static final String parameterAssocId = "assocId"; // 琚叧鑱旇祫婧怚D
	private static final String parameterAssocTitle = "assocTitle"; // 鏄剧ず鐨勬爣棰橈紝涓昏鏄负浜嗘樉绀�
	private static final String parameterAssocMetadata = "assocMetadata"; // 鍏宠仈鐨勯檮浠舵秷鎭紙姣斿鍥剧墖鍦板潃锛孶RL杩炴帴绛夛紝搴旂敤鏍规嵁闇�姹傝嚜宸卞畾涔夛級

    private static final String parameteruserId = "userId"; // 琚叧鑱旂敤鎴稩D
    private static final String parameterAssocTypeName = "name"; //

    private static final String parameterAssocPage = "page";
    private static final String parameterAssocSize = "size";
    private static final int pageInitSize = 5;
	@Autowired
	private AssociateService associateService;
	@Autowired
	private AssociateTypeService assoTypeService;


	/**
	 * 1.鍒涘缓鍏宠仈
	 * 
	 * @param request
	 * @return
	 * @throws AssociateerviceException
	 * @throws CodeServiceException
	 */
	// @formatter:off
	@RequestMapping(path = { "/associate/associate/createAssociate" }, method = { RequestMethod.POST })
	public MappingJacksonValue createAssociateRoot(
			@RequestParam(name = AssociateController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = AssociateController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = AssociateController.parameterSourceTypeId, required = true) Long sourceTypeId,
			@RequestParam(name = AssociateController.parameterSourceId, required = true) Long sourceId,
			@RequestParam(name = AssociateController.parameterAssocDesc, required = true) String assocDesc,
			@RequestParam(name = AssociateController.parameterAssocTypeId, required = true) Long assocTypeId,
			@RequestParam(name = AssociateController.parameterAssocId, required = true) Long assocId,
			@RequestParam(name = AssociateController.parameterAssocTitle, required = true) String assocTitle,
			@RequestParam(name = AssociateController.parameterAssocMetadata, required = false) String assocMetadata,
			HttpServletRequest request) throws AssociateServiceException {
		MappingJacksonValue mappingJacksonValue = null;
	// @formatter:on
		Long loginAppId=this.DefaultAppId;
		InterfaceResult interfaceResult=null;
		Long loginUserId=this.getUserId(request);
		try {

			// 鐧婚檰浜虹殑淇℃伅
//			Long loginAppId = LoginUserContextHolder.getAppKey();
//			Long loginUserId = LoginUserContextHolder.getUserId();
			// 妫�鏌ユ暟鎹殑鍙傛暟
			if (StringUtils.isEmpty(assocDesc)) {
				throw new AssociateServiceException(107, "Required string parameter assocDesc is not empty or blank");
			}
			if (StringUtils.isEmpty(assocTitle)) {
				throw new AssociateServiceException(107, "Required string parameter assocTitle is not empty or blank");
			}
			Associate associate = new Associate();
			associate.setAppId(loginAppId);
			associate.setUserId(loginUserId);
			associate.setSourceTypeId(sourceTypeId);
			associate.setSourceId(sourceId);

			associate.setAssocTypeId(assocTypeId);
			associate.setAssocId(assocId);
			associate.setAssocDesc(assocDesc);
			associate.setAssocTitle(assocTitle);
			associate.setAssocMetadata(assocMetadata);

			Long id = associateService.createAssociate(loginAppId, loginUserId, associate);
			Map<String, Long> reusltMap = new HashMap<String, Long>();
			reusltMap.put("id", id);
			// 2.杞垚妗嗘灦鏁版嵁
			interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
			interfaceResult.setResponseData(reusltMap);
			mappingJacksonValue = new MappingJacksonValue(interfaceResult);
			return mappingJacksonValue;
		} catch (AssociateServiceException e) {
			interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION,"绯荤粺寮傚父锛�");
			return new MappingJacksonValue(interfaceResult);
		}
	}
	/**
	 * 鍒涘缓澶氫釜鍏宠仈
	 */
	@RequestMapping(path = { "/associate/associate/createAssociateList" }, method = { RequestMethod.POST })
	public MappingJacksonValue createAssociates(HttpServletRequest request,HttpServletRequest response) throws AssociateServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		Long loginAppId=this.DefaultAppId;
		InterfaceResult interfaceResult=null;
		Long loginUserId=this.getUserId(request);
		String requestJson=null;
		List < Associate > asso = null;
		List<Long> ids=new ArrayList<Long>();
		try {
			requestJson=this.getBodyParam(request);
			if(requestJson !=null && !"".equals(requestJson)){
				JSONObject jsonObject=JSONObject.fromObject(requestJson);
				long sourceId = jsonObject.getLong("sourceId");
				long sourceType = jsonObject.getLong("sourceType");
				asso=JsonUtils.getList4Json(jsonObject.getString("asso"),Associate.class);
				if(asso != null){
					for(Associate associate:asso){
						associate.setAppId(loginAppId);
						associate.setUserId(loginUserId);
						associate.setSourceId(sourceId);
						associate.setSourceTypeId(sourceType);
						Long id=associateService.createAssociate(loginAppId, loginUserId, associate);
						ids.add(id);
					}
				}
			}
			Map<String,List<Long>> reusltMap = new HashMap<String,List<Long>>();
			reusltMap.put("ids",ids);
			// 2.杞垚妗嗘灦鏁版嵁
			interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
			interfaceResult.setResponseData(reusltMap);
			mappingJacksonValue = new MappingJacksonValue(interfaceResult);
			return mappingJacksonValue;
		} catch (AssociateServiceException e) {
			interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION,"绯荤粺寮傚父锛�");
			return new MappingJacksonValue(interfaceResult);
		}
	}

	/**
	 * 2.鍒犻櫎涓�涓叧鑱�
	 * 
	 * @param request
	 * @return
	 * @throws AssociateerviceException
	 * @throws CodeServiceException
	 */
	// @formatter:off
	@RequestMapping(path = { "/associate/associate/deleteAssociate" }, method = { RequestMethod.POST })
	public MappingJacksonValue deleteAssociate(
			@RequestParam(name = AssociateController.parameterDebug, defaultValue = "") String debug, 
			@RequestParam(name = AssociateController.parameterId, required = true) Long id ,
			HttpServletRequest request) throws AssociateServiceException {
	// @formatter:on
		MappingJacksonValue mappingJacksonValue = null;
		try {

//			Long loginAppId = LoginUserContextHolder.getAppKey();
//			Long loginUserId = LoginUserContextHolder.getUserId();
			Long loginAppId=this.DefaultAppId;
			Long loginUserId=this.getUserId(request);

			// 0.鏍￠獙杈撳叆鍙傛暟锛堟鏋舵悶瀹氾紝濡傛灉涓氬姟涓氬姟鎼炲畾锛�
			boolean bResult = associateService.removeAssociate(loginAppId, loginUserId, id);
			Map<String, Boolean> reusltMap = new HashMap<String, Boolean>();
			reusltMap.put("success", bResult);
			// 2.杞垚妗嗘灦鏁版嵁
			mappingJacksonValue = new MappingJacksonValue(reusltMap);
			return mappingJacksonValue;
		} catch (AssociateServiceException e) {
			throw e;
		}
	}

	/**
	 * 3.鏌ヨ涓�涓叧鑱斿璞＄殑璇︽儏, 閫氳繃鍏宠仈Id
	 * 
	 * @param request
	 * @return
	 * @throws AssociateerviceException
	 * @throws CodeServiceException
	 */
	// @formatter:off
	@RequestMapping(path = { "/associate/associate/getAssociateDetail" }, method = { RequestMethod.POST })
	public MappingJacksonValue getAssociateDetail(
			@RequestParam(name = AssociateController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = AssociateController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = AssociateController.parameterId, required = true) Long id,
			HttpServletRequest request ) throws AssociateServiceException {
	// @formatter:on
		MappingJacksonValue mappingJacksonValue = null;
		try {

//			Long loginAppId = LoginUserContextHolder.getAppKey();
//			Long loginUserId = LoginUserContextHolder.getUserId();
			Long loginAppId=this.DefaultAppId;
			Long loginUserId=this.getUserId(request);
			Associate associate = associateService.getAssociate(loginAppId, loginUserId, id);
			Map<String, Object> reusltMap = new HashMap<String, Object>();
			reusltMap.put("data", associate);
			// 2.杞垚妗嗘灦鏁版嵁
			mappingJacksonValue = new MappingJacksonValue(reusltMap);
			mappingJacksonValue.setFilters(builderSimpleFilterProvider(fileds));
			return mappingJacksonValue;
		} catch (AssociateServiceException e) {
			throw e;
		}
	}

	/**
	 * 3.鏌ヨ涓�涓祫婧愭湁澶氬皯鍏宠仈璧勬簮
	 * 
	 * @param request
	 * @return
	 * @throws AssociateerviceException
	 * @throws CodeServiceException
	 */
	// @formatter:off
	@RequestMapping(path = { "/associate/associate/getSourceAssociates" }, method = { RequestMethod.POST })
	public MappingJacksonValue getSourceAssociates(
			@RequestParam(name = AssociateController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = AssociateController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = AssociateController.parameterSourceTypeId, required = true) Long sourceTypeId,
			@RequestParam(name = AssociateController.parameterSourceId, required = true) long sourceId,
			HttpServletRequest request) throws AssociateServiceException {
	// @formatter:on
		MappingJacksonValue mappingJacksonValue = null;
		InterfaceResult interfaceResult=null;
		Long loginAppId=this.DefaultAppId;
		Long loginUserId=this.getUserId(request);
		Map<AssociateType, List<Associate>> associateMap = associateService.getAssociatesBy(loginAppId, sourceTypeId, sourceId);
		Map<String, Object> reusltMap = new HashMap<String, Object>();
		if (MapUtils.isEmpty(associateMap)) {
			//return null;
			//e.printStackTrace();
			interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_NULL_EXCEPTION,"鑾峰彇澶辫触锛�");
			return new MappingJacksonValue(interfaceResult);
		}
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		for (AssociateType associateType : associateMap.keySet()) {
			Map<String,Object> recordMap = new HashMap<String,Object>();
			recordMap.put("id", associateType.getId());
			recordMap.put("name", associateType.getName());
			List<Map> associateList = new ArrayList<Map>();
			recordMap.put("data", associateList);
			Map<String, List<Object>> descMap = new HashMap<String,List<Object>>();
			List<Associate> associates =  associateMap.get(associateType);
			for (Associate associate : associates) {
				if (associate != null) {
					String associate_desc = associate.getAssocDesc();
					List<Object> desc_collect = descMap.get(associate_desc);
					if (desc_collect == null) {
						desc_collect = new ArrayList<Object>();
						descMap.put(associate_desc, desc_collect);
					}
					desc_collect.add(associate);
				}
			}
			for (String desc : descMap.keySet()) {
				Map<String, Object> desc_List_map = new HashMap<String,Object>();
				desc_List_map.put("associateCollect", desc);
				desc_List_map.put("associateList", descMap.get(desc));
				associateList.add(desc_List_map);
			}
			//--鍒嗙被涓�
			resultList.add(recordMap);
		}
		reusltMap.put("asso", resultList);
		// 2.杞垚妗嗘灦鏁版嵁
		interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
		interfaceResult.setResponseData(reusltMap);
		mappingJacksonValue = new MappingJacksonValue(interfaceResult);
		// 3.鍒涘缓椤甸潰鏄剧ず鏁版嵁椤圭殑杩囨护鍣�
		SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
		mappingJacksonValue.setFilters(filterProvider);
		return mappingJacksonValue;
	}


	// @formatter:off
	@RequestMapping(path = { "/associate/associate/getSourceAssociates" }, method = { RequestMethod.GET })
	public MappingJacksonValue getSources(
			@RequestParam(name = AssociateController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = AssociateController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = AssociateController.parameterSourceTypeId, required = true) Long sourceTypeId,
			@RequestParam(name = AssociateController.parameterSourceId, required = true) long sourceId,
			HttpServletRequest request) throws AssociateServiceException {
		// @formatter:on
		MappingJacksonValue mappingJacksonValue = null;
		InterfaceResult interfaceResult = null;
		Long loginAppId = this.DefaultAppId;
		Long loginUserId = this.getUserId(request);
		Map<AssociateType, List<Associate>> associateMap = associateService.getAssociatesBy(loginAppId, sourceTypeId, sourceId);
		Map<String,List> map=new HashMap();
		List assoList=null;
		if(associateMap.values()!=null){
			assoList = new ArrayList();
			for (Iterator i =  associateMap.values().iterator();i.hasNext();){
				List<Associate> associatelist = (List)i.next();
				for (int j = 0; j < associatelist.size(); j++) {
					assoList.add(associatelist.get(j));
				}
			}
		}
		map.put("asso",assoList);
		// 2.杞垚妗嗘灦鏁版嵁
		interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
		interfaceResult.setResponseData(map);
		mappingJacksonValue = new MappingJacksonValue(interfaceResult);
		// 3.鍒涘缓椤甸潰鏄剧ず鏁版嵁椤圭殑杩囨护鍣�
		SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
		mappingJacksonValue.setFilters(filterProvider);
		return mappingJacksonValue;
	}
    /**
     * 閫氳繃param鏌ヨAssociate鍒嗛〉
     *
     * @param fileds
     * @param debug
     * @param pageNoStr
     * @param pageSizeStr
     * @param request
     * @return
     * @throws AssociateServiceException
     */

	/*@RequestMapping(path = { "/associate/associate/getAssociateAll" }, method = { RequestMethod.GET })
    public MappingJacksonValue getPAssociates(
			@RequestParam(name = AssociateController.parameterFields, defaultValue = "") String fileds,
			@RequestParam(name = AssociateController.parameterDebug, defaultValue = "") String debug,
			@RequestParam(name = AssociateController.parameterAssocTypeId, required = true) long typeId,
			@RequestParam(name = AssociateController.parameterAssocPageNoStr, required = true) String pageNoStr,
			@RequestParam(name = AssociateController.parameterAssocPageSizeStr,required = false) String pageSizeStr,
			HttpServletRequest request ) throws AssociateServiceException {
		// @formatter:on
		MappingJacksonValue mappingJacksonValue = null;
		try {

			int pageNo = Integer.parseInt(pageNoStr);
			pageSize = Integer.parseInt(PageSizeStr);
			pageSize = pageSize == 0 ? 5 : pageSize;

			Long loginUserId=this.getUserId(request);

			Page<Associate> page = associateService.getassociatesByPage(loginUserId,typeId,pageNo,pageSize);
			Map<String, Object> reusltMap = new HashMap<String, Object>();
			reusltMap.put("data", page);
			// 2.杞垚妗嗘灦鏁版嵁
			mappingJacksonValue = new MappingJacksonValue(reusltMap);
			mappingJacksonValue.setFilters(builderSimpleFilterProvider(fileds));
			return mappingJacksonValue;
		} catch (AssociateServiceException e) {
			throw e;
		}
	}*/
    @RequestMapping(path = {"/associate/associate/getAssociateByType"}, method = {RequestMethod.GET})
    public MappingJacksonValue getPgAssociates(
            @RequestParam(name = AssociateController.parameterAssocPage, required = true) String pageNoStr,
            @RequestParam(name = AssociateController.parameterAssocSize, required = false) String pageSizeStr,
            @RequestParam(name = AssociateController.parameterFields, defaultValue = "") String fileds,
            @RequestParam(name = AssociateController.parameterDebug, defaultValue = "") String debug,
            @RequestParam(name = AssociateController.parameterAssocTypeId, required = true) Long typeId,
            HttpServletRequest request) throws AssociateServiceException {
        MappingJacksonValue mappingJacksonValue = null;
        try {

            int pageNo = Integer.parseInt(pageNoStr);
            int pageSize = Integer.parseInt(pageSizeStr);
            pageSize = pageSize == 0 ? AssociateController.pageInitSize : pageSize;

            Long loginUserId = this.getUserId(request);

            Page<Map<String, Object>> page = associateService.getAssociatesByPage(loginUserId,typeId, pageNo, pageSize);

            Map<String, Object> reusltMap = new HashMap<String, Object>();
            reusltMap.put("data", page);
            // 2.杞垚妗嗘灦鏁版嵁
            mappingJacksonValue = new MappingJacksonValue(reusltMap);
            mappingJacksonValue.setFilters(builderSimpleFilterProvider(fileds));
            return mappingJacksonValue;
        } catch (AssociateServiceException e) {
            throw e;
        }
    }

    /**
	 * 鎸囧畾鏄剧ず閭ｄ簺瀛楁
	 * 
	 * @param fileds
	 * @return
	 */
	private SimpleFilterProvider builderSimpleFilterProvider(String fileds) {
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		// 璇锋眰鎸囧畾瀛楁
		String[] filedNames = StringUtils.split(fileds, ",");
		Set<String> filter = new HashSet<String>();
		if (filedNames != null && filedNames.length > 0) {
			for (int i = 0; i < filedNames.length; i++) {
				String filedName = filedNames[i];
				if (!StringUtils.isEmpty(filedName)) {
					filter.add(filedName);
				}
			}
		} else {
			filter.add("id"); // id',
            filter.add("userId");
            filter.add("appId");
            filter.add("sourceTypeId");
            filter.add("sourceId");
			filter.add("assocDesc"); // '鍏宠仈鎻忚堪锛屾瘮濡傛枃绔犵殑浣滆�咃紝鎴栬�呯紪杈戠瓑锛涘叧鑱旀爣绛炬弿杩�',
			filter.add("assocTypeId"); // '琚叧鑱旂殑绫诲瀷鍙互鍙傝�傾ssociateType瀵硅薄锛屽锛氱煡璇�, 浜鸿剦,缁勭粐锛岄渶姹傦紝浜嬩欢绛�',
			filter.add("assocId"); // '琚叧鑱旀暟鎹甀D',
			filter.add("assocTitle"); // '琚叧鑱旀暟鎹爣棰�',
			filter.add("assocMetadata"); // '琚叧鑱旀暟鎹殑鐨勬憳瑕佺敤Json瀛樻斁锛屽鍥剧墖锛岃繛鎺RL瀹氫箟绛�',
		}

		filterProvider.addFilter(Associate.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept(filter));
		return filterProvider;
	}
	/**
	 * 鏇存柊鍏宠仈淇℃伅
	 *
	 * @param fileds
	 * @return
	 */
	@RequestMapping(path = {"/associate/associate/updateAssociate"}, method = {RequestMethod.POST})
	public MappingJacksonValue updateAssociate(@RequestParam(name = AssociateController.parameterFields, defaultValue = "") String fileds,
												HttpServletRequest request, HttpServletRequest response){
		String requestJson = null;
		Long loginAppId=this.DefaultAppId;
		Long loginUserId=this.getUserId(request);
		MappingJacksonValue mappingJacksonValue = null;
		Map<AssociateType, List<Associate>> assomap=null;
		InterfaceResult interfaceResult=null;
		List<Associate> asso=null;
		if(loginUserId == null && "".equals(loginUserId)){
			logger.error("userId can not be null");
			return null;
		}
		try{
			requestJson = this.getBodyParam(request);
			if(requestJson != null && !"".equals(requestJson) ){
				JSONObject jsonObject=JSONObject.fromObject(requestJson);
				long sourceId=jsonObject.getLong("sourceId");
				long sourceType=jsonObject.getLong("sourceType");
				asso=JsonUtils.getList4Json(jsonObject.getString("asso"), Associate.class);
				if (sourceId<=0) {
					logger.error("sourceId is null..");
					return null;
				}
				if (sourceType<=0) {
					logger.error("sourceType is null..");
					return null;
				}
			 assomap =  associateService.getAssociatesBy(loginAppId, sourceType, sourceId);
			if (assomap == null) {
				logger.error("asso it null or converted failed");
			}
			for (Iterator i =  assomap.values().iterator();i.hasNext();){
				List<Associate> associatelist = (List)i.next();
				for (int j = 0; j < associatelist.size(); j++) {
					associateService.removeAssociate(1l,loginUserId, associatelist.get(j).getId());
				}
			}
				if(asso != null){
					for(Associate associate : asso){
						associate.setSourceId(sourceId);
						associate.setSourceTypeId(sourceType);
						associate.setUserId(loginUserId);
						associateService.createAssociate(loginAppId,loginUserId,associate);
					}
				}
				// assomap =  associateService.getAssociatesBy(loginAppId, sourceType, sourceId);
			}
			// 2.杞垚妗嗘灦鏁版嵁
			interfaceResult = interfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
			//interfaceResult.setResponseData(reusltMap);
			mappingJacksonValue = new MappingJacksonValue(interfaceResult);
			// 3.鍒涘缓椤甸潰鏄剧ず鏁版嵁椤圭殑杩囨护鍣�
			SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
			mappingJacksonValue.setFilters(filterProvider);
			return mappingJacksonValue;
		}catch(Exception e0){
			e0.printStackTrace();
			logger.error("update asso remove failed");
			return null;
		}

	}
}
