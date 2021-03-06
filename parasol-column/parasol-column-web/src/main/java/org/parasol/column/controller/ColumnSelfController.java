package org.parasol.column.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.parasol.column.api.model.ColumnFlag;
import org.parasol.column.entity.ColumnSelf;
import org.parasol.column.service.ColumnCustomService;
import org.parasol.column.service.ColumnSelfService;
import org.parasol.column.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gintong.frame.util.dto.CommonResultCode;
import com.gintong.frame.util.dto.InterfaceResult;

@Controller
@RequestMapping("/columnself")
public class ColumnSelfController extends BaseController {
	
//	@Resource(name="columnSelfService")
	@Autowired
	private ColumnSelfService selfService;
	
	@Autowired
	private ColumnCustomService customService;
	
	@RequestMapping(value="/showAllColumnSelf",method = RequestMethod.GET)
	@ResponseBody
	public InterfaceResult<List<ColumnSelf>> showAllColumnSelf(HttpServletRequest request, HttpServletResponse response) throws Exception{

		InterfaceResult<List<ColumnSelf>> result = InterfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
		//Long pid = 0l;
		Long uid = this.getUserId(request);
		if(uid == 0){
			result = InterfaceResult.getInterfaceResultInstance(CommonResultCode.PERMISSION_EXCEPTION);
			return result;
		}
		List<ColumnSelf> sysList = returnSysList();
		result.setResponseData(sysList);
		return result;
	}
	
	private static Boolean isInList(ColumnSelf cs,List<ColumnSelf> list){
		for(ColumnSelf c : list){
			if(c.getId().longValue()==cs.getId().longValue()){
				return true;
			}
		}
		return false;
	}
	
	@RequestMapping(value="/addColumnSelf",method = RequestMethod.POST)
	@ResponseBody
	public InterfaceResult<ColumnSelf> addColumnSelf(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Boolean b=false;
		String jsonStr=this.readJSONString(request);
		ColumnSelf newCol=(ColumnSelf)JsonUtils.jsonToBean(jsonStr, ColumnSelf.class);
		Long uid=this.getUserId(request);
		if(newCol==null||StringUtils.isEmpty(newCol.getColumnname())||uid.longValue()==0){
			InterfaceResult<ColumnSelf> result=InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
			return result;
		}
		
		Long pid=0l;
		newCol.setId(null);
		newCol.setUserOrSystem(ColumnFlag.user.getVal());
		newCol.setCreatetime(new Date());
		newCol.setUpdateTime(new Date());
		newCol.setDelStatus((short)0);
		newCol.setPathName(newCol.getColumnname());
		newCol.setUserId(uid);
		newCol.setParentId(0l);
		ColumnSelf col= selfService.insert(newCol);
		InterfaceResult<ColumnSelf> result=InterfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
		if(col.getId()>0){
			result.setResponseData(col);
		}
		
		return result;
	}
	
	@RequestMapping(value="/updateColumnSelf",method = RequestMethod.POST)
	@ResponseBody
	public InterfaceResult<Boolean> updateColumnSelf(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Boolean b=false;
		String jsonStr=this.readJSONString(request);
		ColumnSelf newCol=(ColumnSelf)JsonUtils.jsonToBean(jsonStr, ColumnSelf.class);
		if(newCol==null||StringUtils.isEmpty(newCol.getColumnname())||newCol.getId()==null||newCol.getUserOrSystem().shortValue()!=ColumnFlag.user.getVal()){
			InterfaceResult<Boolean> result=InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
			return result;
		}
		newCol.setUpdateTime(new Date());
		Long uid=this.getUserId(request);
		int n=this.selfService.updateByPrimaryKey(newCol);
		b=n>0;
		InterfaceResult<Boolean> result=InterfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
		result.setResponseData(b);
		return result;
	}
	
	@RequestMapping(value="/deleteColumnSelf",method = RequestMethod.POST)
	@ResponseBody
	public InterfaceResult<Boolean> deleteColumnSelf(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Boolean b=false;
		String jsonStr=this.readJSONString(request);
		ColumnSelf newCol=(ColumnSelf)JsonUtils.jsonToBean(jsonStr, ColumnSelf.class);
		if(newCol==null||newCol.getId()==null){
			InterfaceResult<Boolean> result=InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
			return result;
		}
		int n=this.selfService.deleteByPrimaryKey(newCol.getId());
		b=n>0;
		InterfaceResult<Boolean> result=InterfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
		result.setResponseData(b);
		return result;
	}

}
