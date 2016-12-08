package org.parasol.column.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.parasol.column.entity.ColumnCustom;
import org.parasol.column.entity.ColumnSelf;
import org.parasol.column.service.ColumnCustomService;
import org.parasol.column.service.ColumnSelfService;
import org.parasol.column.service.ColumnSysService;
import org.parasol.column.utils.JsonUtils;
import org.parasol.column.vo.ColumnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gintong.frame.util.dto.CommonResultCode;
import com.gintong.frame.util.dto.InterfaceResult;

@Controller
@RequestMapping("/columncustom")
public class ColumnCustomController extends BaseController {
	
//	@Resource(name="columnCustomService")
	@Autowired
	private ColumnCustomService customService;
	
	@Autowired
	private ColumnSelfService selfService;
	
	@Autowired
	private ColumnSysService sysService;
	
	@RequestMapping(value="/showColumn",method = RequestMethod.POST)
	@ResponseBody
	public InterfaceResult<List<ColumnSelf>> showColumn(HttpServletRequest request, HttpServletResponse response) throws Exception{

		InterfaceResult<List<ColumnSelf>> result = null;
		String jsonStr = this.readJSONString(request);
		ColumnVo vo = (ColumnVo)JsonUtils.jsonToBean(jsonStr, ColumnVo.class);
		if(vo == null|| vo.getPid() == null){
			result = InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
			return result;
		}
		Map<String,List<ColumnSelf>> map = null;
		Long pid = vo.getPid();
		Long uid = getUserId(request);
		if(uid == 0){
			result = InterfaceResult.getInterfaceResultInstance(CommonResultCode.PERMISSION_EXCEPTION);
			return result;
		}
		result = InterfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
		//显示自定义栏目数据
		map = returnColumnMap(pid, uid, request);
		List<ColumnSelf> selfList = map.get("selfList");
		result.setResponseData(selfList);
		return result;
	}

	@RequestMapping(value = "/showSelfColumn",method = RequestMethod.POST)
	@ResponseBody
	public InterfaceResult<Map<String,List<ColumnSelf>>> showSelfColumn(HttpServletRequest request, HttpServletResponse response) throws Exception{

		InterfaceResult<Map<String,List<ColumnSelf>>> result = null;
		String jsonStr = this.readJSONString(request);
		ColumnVo vo = (ColumnVo)JsonUtils.jsonToBean(jsonStr, ColumnVo.class);
		if(vo == null|| vo.getPid() == null){
			result = InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
			return result;
		}
		Map<String,List<ColumnSelf>> map = null;
		Long pid = vo.getPid();
		Long uid = getUserId(request);
		if(uid == 0){
			result = InterfaceResult.getInterfaceResultInstance(CommonResultCode.PERMISSION_EXCEPTION);
			return result;
		}
		result = InterfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
		//显示自定义栏目数据
		map = returnColumnMap(pid, uid, request);
		result.setResponseData(map);
		return result;
	}

	/**
	 * 修改自定义栏目
	 * @author 王飞
	 * @date 2016年12月7日 下午4:50:17
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value ="/replaceColumn",method = RequestMethod.POST)
	@ResponseBody
	public InterfaceResult<Boolean> replaceColumn(HttpServletRequest request, HttpServletResponse response) throws Exception{

		String jsonStr = this.readJSONString(request);
		List<ColumnSelf> newList = JsonUtils.jsonToList(jsonStr,ColumnSelf.class);
		long uid = this.getUserId(request);
		if(CollectionUtils.isEmpty(newList)|| uid == 0 || newList.size() > 10){
			InterfaceResult<Boolean> result = InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
			return result;
		}
		/*for(ColumnSelf c: newList){
			c.setCreatetime(new Date());
			c.setUpdateTime(new Date());
			c.setParentId(0l);
		}*/
		//Long pid = 0l;
		List<ColumnSelf> selfList = customService.queryListByPidAndUserId(0l, uid);
		if(selfList.size() == newList.size() && newList.containsAll(selfList)){
			InterfaceResult<Boolean> result = InterfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
			result.setResponseData(true);
			return result;
		}
		int n = this.customService.replace(uid, newList);
		InterfaceResult<Boolean> result = InterfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
		Boolean b = n > 0;
		result.setResponseData(b);
		return result;
	}

}
