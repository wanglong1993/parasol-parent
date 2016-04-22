package org.parasol.column.controller;

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
		List<ColumnSelf> list=css.queryListByPidAndUserId(pid, uid);
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
		ColumnSelf source=css.selectMaxOrderColumn(pid, uid);
		if(source!=null){
			newCol.setId(null);
			newCol.setUserOrSystem(ColumnFlag.user.getVal());
			newCol.setCreatetime(new Date());
			newCol.setUpdateTime(new Date());
			newCol.setDelStatus((short)0);
			newCol.setPathName(newCol.getColumnname());
			newCol.setUserId(uid);
			newCol.setParentId(0l);
			int n= css.insert(newCol);
			b=true;
		}
		InterfaceResult<Boolean> result=InterfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
		result.setResponseData(b);
		return result;
	}

}
