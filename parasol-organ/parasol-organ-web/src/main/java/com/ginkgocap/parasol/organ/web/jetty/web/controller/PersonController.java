package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ginkgocap.parasol.associate.model.Associate;
import com.ginkgocap.parasol.associate.model.AssociateType;
import com.ginkgocap.parasol.associate.service.AssociateService;
import com.ginkgocap.parasol.directory.model.DirectorySource;
import com.ginkgocap.parasol.directory.service.DirectorySourceService;
import com.ginkgocap.parasol.oauth2.web.jetty.LoginUserContextHolder;
import com.ginkgocap.parasol.person.model.PersonBasic;
import com.ginkgocap.parasol.person.model.PersonContactWay;
import com.ginkgocap.parasol.person.model.PersonDefined;
import com.ginkgocap.parasol.person.model.PersonEducationHistory;
import com.ginkgocap.parasol.person.model.PersonInfo;
import com.ginkgocap.parasol.person.model.PersonWorkHistory;
import com.ginkgocap.parasol.person.service.PersonBasicService;
import com.ginkgocap.parasol.person.service.PersonContactWayService;
import com.ginkgocap.parasol.person.service.PersonDefinedService;
import com.ginkgocap.parasol.person.service.PersonEducationHistoryService;
import com.ginkgocap.parasol.person.service.PersonInfoService;
import com.ginkgocap.parasol.person.service.PersonWorkHistoryService;
import com.ginkgocap.parasol.tags.model.TagSource;
import com.ginkgocap.parasol.tags.service.TagSourceService;
import com.ginkgocap.parasol.user.model.UserOrgPerCusRel;
import com.ginkgocap.parasol.user.service.UserOrgPerCusRelService;

/**
 * 人脉登录注册
 */
@RestController
public class PersonController extends BaseController {
	private static Logger logger = Logger.getLogger(PersonController.class);
	@Autowired
	private PersonBasicService personBasicService;
	@Autowired
	private PersonContactWayService personContactWayService;
	@Autowired
	private PersonDefinedService personDefinedService;
	@Autowired
	private PersonEducationHistoryService personEducationHistoryService;
	@Autowired
	private PersonInfoService personInfoService;
	@Autowired
	private PersonWorkHistoryService personWorkHistoryService;
	@Autowired
	private TagSourceService tagSourceService;
	@Autowired
	private AssociateService associateService;
	@Autowired
	private DirectorySourceService directorySourceService;
	@Autowired
	private UserOrgPerCusRelService userOrgPerCusRelService;

	/**
	 * 创建人脉
	 * 基本信息
	 * @param name 姓名
	 * @param gender 性别
	 * @param picId 人脉头像ID
	 * @param firstIndustryId 一级职能ID
	 * @param secondIndustryId 二级职能ID
	 * @param company 公司
	 * @param position 职位
	 * @param countryId 国外的洲或国内的省id
	 * @param cityId  国外的国家或国内的城市id
	 * @param countyId 县id
	 * @param remark 个人描述
	 * 个人情况
	 * @param birthday 出生日期
	 * @param provinceId 籍贯省ID
	 * @param cityId 籍贯市ID
	 * @param countyId 籍贯县ID
	 * @param interests 兴趣爱好
	 * @param skills   擅长技能
	 * 联系方式
	 * @param cellphone  手机
	 * @param email  邮箱
	 * @param weixin  微信
	 * @param qq  QQ
	 * @param weibo  微博
	 * 工作经历
	 * @param personWorkHistoryJson  json字符串
	 * 教育经历
	 * @param personEducationHistoryJson  json字符串
	 * 标签TagSource
	 * @param tagIds 标签ID tagIds=3933811599736848&tagIds=3933811561988102&tagIds=3933811356467203
	 * 目录DirectorySource
	 * @param directoryId 目录ID
	 * 关联Associate
	 * @param associateJson json字符串
	 * @throws Exception
	 * @return MappingJacksonValue
	 * http://www.jsjtt.com/java/Javakuangjia/67.html
	 */
	@RequestMapping(path = { "/organ/organ/Comment.json" }, method = { RequestMethod.POST })
	public MappingJacksonValue createPerson(HttpServletRequest request,HttpServletResponse response
			//基本信息
			,@RequestParam(name = "name",required = true) String name
			,@RequestParam(name = "gender",required = true) Byte gender
			,@RequestParam(name = "picId",required = false) Long picId
			,@RequestParam(name = "firstIndustryId",required = false) Long firstIndustryId
			,@RequestParam(name = "secondIndustryId",required = false) Long secondIndustryId
			,@RequestParam(name = "personDefinedsJson", required = false) String personDefinedsJson
			,@RequestParam(name = "company",required = false) String company
			,@RequestParam(name = "position",required = false) String position
			,@RequestParam(name = "countryId",required = false) Long countryId
			,@RequestParam(name = "cityId",required = false) Long cityId
			,@RequestParam(name = "countyId",required = false) Long countyId
			,@RequestParam(name = "remark",required = false) String remark
			,@RequestParam(name = "address",required = false) String address
			//个人情况
			,@RequestParam(name = "birthday",required = false) String birthday
			,@RequestParam(name = "provinceId",required = false) Long provinceId
			,@RequestParam(name = "cityId",required = false) Long cityId2
			,@RequestParam(name = "countyId",required = false) Long countyId2
			,@RequestParam(name = "interests",required = false) String interests
			,@RequestParam(name = "skills",required = false) String skills
			//联系方式
			,@RequestParam(name = "cellphone",required = false) String cellphone
			,@RequestParam(name = "email",required = false) String email
			,@RequestParam(name = "weixin",required = false) String weixin
			,@RequestParam(name = "qq",required = false) String qq
			,@RequestParam(name = "weibo",required = false) String weibo
			//工作经历
			,@RequestParam(name = "personWorkHistoryJson",required = false) String personWorkHistoryJson
			//教育经历
			,@RequestParam(name = "personEducationHistoryJson",required = false) String personEducationHistoryJson
			//标签TagSource
			,@RequestParam(name = "tagIds",required = false) Long[] tagIds
			//目录DirectorySource
			 ,@RequestParam(name = "directoryId",required = false) Long[] directoryIds
			 //关联Associate
			 ,@RequestParam(name = "associateJson",required = false) String associateJson
			)throws Exception {

        Long loginUserId = LoginUserContextHolder.getUserId();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
				if((StringUtils.isEmpty(name))){
					resultMap.put( "message", "人脉姓名不能为空！");
					resultMap.put( "status", 0);
					return new MappingJacksonValue(resultMap);
				}

				return new MappingJacksonValue(resultMap);
		}catch (Exception e ){
			//异常失败回滚
			personBasicService.realDeletePersonBasic(loginUserId);
			personInfoService.realDeletePersonInfo(loginUserId);
			personContactWayService.realDeletePersonContactWay(loginUserId);
			personWorkHistoryService.realDeletePersonWorkHistoryList(personWorkHistoryService.getIdList(loginUserId));
			personEducationHistoryService.realDeletePersonEducationHistoryList(personEducationHistoryService.getIdList(loginUserId));
			resultMap.put( "message", "保存人脉教育经历出错！");
			resultMap.put( "status", 0);
			logger.info(e.getStackTrace());
			throw e;
		}
	}

}