package org.parasol.column.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.parasol.column.api.model.ColumnFlag;
import org.parasol.column.entity.ColumnSelf;
import org.parasol.column.service.ColumnSelfService;
import org.parasol.column.utils.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ginkgocap.parasol.user.model.User;
import com.gintong.frame.util.dto.CommonResultCode;
import com.gintong.frame.util.dto.InterfaceResult;

@Controller
@RequestMapping("/columnself")
public class ColumnSelfController extends BaseController {
	
	@Resource(name="columnSelfService")
	private ColumnSelfService css;
	
	@RequestMapping(value="/showAllColumnSelf",method = RequestMethod.GET)
	@ResponseBody
	public InterfaceResult<List<ColumnSelf>> showAllColumnSelf(HttpServletRequest request, HttpServletResponse response) throws Exception{
		InterfaceResult<List<ColumnSelf>> result=InterfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
		Long pid=0l;
		Long uid=this.getUserId(request);
		List<ColumnSelf> list=css.queryListByPidAndUserId(pid, 0l);
		if (uid.longValue() != 0) {
			List<ColumnSelf> list1 = css.queryListByPidAndUserId(pid, uid);
			if (list1 != null) {
				list.addAll(list1);
			}
		}
		result.setResponseData(list);
		return result;
	}
	
	@RequestMapping(value="/addColumnSelf",method = RequestMethod.POST)
	@ResponseBody
	public InterfaceResult<Boolean> addColumnSelf(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Boolean b=false;
		String jsonStr=this.readJSONString(request);
		ColumnSelf newCol=(ColumnSelf)JsonUtils.jsonToBean(jsonStr, ColumnSelf.class);
		if(newCol==null||StringUtils.isEmpty(newCol.getColumnname())){
			InterfaceResult<Boolean> result=InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
			return result;
		}
		Long uid=this.getUserId(request);
		Long pid=0l;
		newCol.setId(null);
		newCol.setUserOrSystem(ColumnFlag.user.getVal());
		newCol.setCreatetime(new Date());
		newCol.setUpdateTime(new Date());
		newCol.setDelStatus((short)0);
		newCol.setPathName(newCol.getColumnname());
		newCol.setUserId(uid);
		newCol.setParentId(0l);
		int n= css.insert(newCol);
		b=n>0;
		InterfaceResult<Boolean> result=InterfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
		result.setResponseData(b);
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
		int n=this.css.updateByPrimaryKey(newCol);
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
		int n=this.css.deleteByPrimaryKey(newCol.getId());
		b=n>0;
		InterfaceResult<Boolean> result=InterfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
		result.setResponseData(b);
		return result;
	}

}
