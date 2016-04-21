package org.parasol.column.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.parasol.column.entity.ColumnSelf;
import org.parasol.column.service.ColumnSelfService;
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
	
	@Resource
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
	
	public InterfaceResult<ColumnSelf> addColumnSelf(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Long uid=this.getUserId(request);
		Long pid=0l;
		ColumnSelf source=css.selectMaxOrderColumn(pid, uid);
		if(source!=null){
			ColumnSelf dest=new ColumnSelf();
			BeanUtils.copyProperties(dest,source);
//			dest.setOrderNum(orderNum);
		}
//		int id=css.i
		return null;
	}

}
