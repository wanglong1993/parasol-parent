package org.parasol.column.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.parasol.column.entity.ColumnCustom;
import org.parasol.column.entity.ColumnSelf;
import org.parasol.column.service.ColumnCustomService;
import org.parasol.column.utils.JsonUtils;
import org.parasol.column.vo.ColumnVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ginkgocap.parasol.user.model.User;
import com.gintong.frame.util.dto.CommonResultCode;
import com.gintong.frame.util.dto.InterfaceResult;

@Controller
@RequestMapping("/columncustom")
public class ColumnCustomController extends BaseController {
	
	@Resource(name="columnCustomService")
	private ColumnCustomService ccs;
	
	@RequestMapping(value="/showColumn",method = RequestMethod.POST)
	@ResponseBody
	public InterfaceResult<List<ColumnSelf>> showColumn(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String jsonStr=this.readJSONString(request);
		ColumnVo vo=(ColumnVo)JsonUtils.jsonToBean(jsonStr, ColumnVo.class);
		if(vo==null||vo.getPid()==null){
			InterfaceResult<List<ColumnSelf>> result=InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
			return result;
		}
		InterfaceResult<List<ColumnSelf>> result=InterfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
		List<ColumnSelf> list=null;
		Long pid=vo.getPid();
		long uid=this.getUserId(request);
		list=this.ccs.queryListByPidAndUserId(pid, uid);
		result.setResponseData(list);
		return result;
	}
	
	@RequestMapping(value="/replaceColumn",method = RequestMethod.POST)
	@ResponseBody
	public InterfaceResult<Boolean> replaceColumn(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String jsonStr=this.readJSONString(request);
		List<ColumnSelf> newList=JsonUtils.jsonToList(jsonStr,ColumnSelf.class);
		if(newList==null||newList.size()==0){
			InterfaceResult<Boolean> result=InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
			return result;
		}
		for(ColumnSelf c:newList){
			c.setCreatetime(new Date());
			c.setUpdateTime(new Date());
			c.setParentId(0l);
		}
		Long pid=0l;
		long uid=this.getUserId(request);
		int n=this.ccs.replace(uid, newList);
		InterfaceResult<Boolean> result=InterfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS);
		Boolean b=n>0;
		result.setResponseData(b);
		return result;
	}

}
