<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>组织客户测试</title>
</head>
<script src="<%=request.getContextPath() %>/resources/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<body>
<%String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div id="urlDiv" style="color:red;">
	需要先登录，接口才能测试
</div>
<div id="methodChange" >
	测试方法:<select id="changeUrlSelect" onchange="javascript:changeUrl();" onkeydown="if(event.keyCode == 8){this.options[0].text = '';}" onkeypress="writeSelect(this);">
		<option value="0">请选择</option>
		<!-- 新组织接口 -->
		<optgroup label="=============newOrgan================">

		<optgroup label="=============newOrgan end================">
		
		<option value="500" >   /organ/preference/save.json 添加自定义偏好标签接口</option>
		<option value="499" >   /organ/preference/find.json 系统推荐自定义标签列表（偏好设置）</option>
		<option value="498" >   /organ/preference/delete.json 删除自定义标签</option>
		<option value="497" >   /organ/showOrganInfo.json 展示申请（再次）</option>
		<option value="496" >   /organ/authentication.json 申请组织 </option>
		<option value="495" >   /organ/showAccounts.json 暂时所有组织账号 </option>
		<option value="494" >   /organ/deleteManager.json 删除组织管理者账号 </option>
		<option value="493" >   /organ/showManagers.json 查询组织管理者账号 </option>
		<option value="492" >   /organ/addManager.json 添加组织管理者账号 </option>
		<option value="491" >   /organ/authorization.json 授权组织管理者账号 </option>
		<option value="490" >   /organ/transitionOrganManager.json 切换组织管理者账号 </option>
		<option value="489" >   /organ/findMyOrgan.json 查询可切换组织账号 </option>
		<option value="488" >   /organ/transitionUser.json 切换回个人 </option>
		
		<option value="511" >   /organ/register.json   注册接口第一步</option>
		<option value="501" >   /organ/registerSecond.json   注册接口第二步</option>
		<option value="502" >   /organ/registerThird.json   注册接口第三步</option>
		<option value="503" >   /organ/registerFourth.json   注册接口第四步</option>
		<option value="504" >   /organ/isExistEmail.json   账号邮箱</option>
		<option value="505" >   /organ/isExistOrganNumber.json   组织号</option>
		<option value="506" >   /organ/isExistOrganAllName.json   组织全称</option>
		<option value="507" >   /organ/updateOrganData.json   编辑资料</option>
		<option value="508" >   /organ/announcement/save.json 添加组织公告 </option>
		<option value="509" >   /organ/announcement/list.json 查询组织公告列表</option>
		<option value="510" >   /organ/announcement/getOne.json 根据Id查询组织公告</option>
		<option value="512" >   /organ/registerFifth.json   注册接口第五步</option>
		<option value="513" >   /validate/getVcode.json   验证码</option>
		<option value="514" >   /validate/getMobileVCode.json   手机验证码</option>
		<option value="515" >   /organ/queryUserLog.json   操作日志</option>
		<option value="516" >   /organ/regValidateEmail.json   发送邮件</option>
		<option value="517" >   /organ/announcement/update.json 修改组织公告</option>
		<option value="518" >	/organ/announcement/delete.json 删除组织公告</option>
		<option value="519" >	/organ/queryIndustry.json 行业</option>
		<option value="520" >	/org/saveOrganProfile.json 行业</option>
		
		</optgroup>
		
	</select><input type="button" style="cursor: pointer; margin-left: 10px;" onclick="login();" value="快捷登陆">
	<input type="checkbox" id="noLogin" checked="checked" title="勾选上则自动执行登陆，然后再执行指定方法">自动登陆设置
	<a href="http://192.168.120.140:8080/" target="_blank" title="服务没有开，无法使用" id="joint" style="display: none;">本接口请求远程服务,访问链接测试功能能否正确使用</a>
	<a href="http://192.168.130.119:8090/" target="_blank" title="服务没有开，无法使用" id="bigdata" style="display: none;">本接口请求大数据,访问链接测试功能能否正确使用</a>
</div>
<div id="urlDiv" >
	测试地址:<input id="url" size="100" value=""/>
</div>
<div id="urlDiv" >
	测试地址全路径<input id="allUrl" size="100" value=""/>
</div>
<div id="" >
	请求方式<select id="postType">
	<option selected=selected>POST</option><option>GET</option>
	</select>
</div>

<div id="" >
	errcode<input id="errCode" size="100" value=""/>
</div>
<div id="" >
	errmessage<input id="errMessage" size="100" value=""/>
</div>
<div id="" >
	errmessage<input id="mima" size="100" value="MTExMTEx"/>
</div>
<div id="showResultDiv" >
<div style="float: left;width:650px;">
	<div style="margin-top: 100px;float: left">输入参数</div>
	<div id="paramIf" width="400" height="330" style="margin-left: 10px;margin-top: 100px"><textarea id="paramsDiv" style="height: 295px;width: 470px"></textarea></div>
	</div>
<div style="float: right;width:650px;margin-right: 20px">
	<div  style="margin-top: 100px;float: right" width="400" height="330"><textarea  id="showResult" style="height: 295px;width: 470px"></textarea></div>
	<div style="margin-top: 100px;float: right;margin-right: 50px">输出参数</div>
	<input type="button" style="margin-top: 200px;margin-left: 50px;" onclick="getMethodReturnJsonValue();" value="提交">
	</div>
</div>

</body>

<script language="javascript">
var sessionId="";
$(document).ready(function(){
	$("#changeUrlSelect option").each(function(){
		var $_html=$(this).html();
		//$(this).html($(this).val()+": "+ $_html);
	});
	var selectId = "${id}";
	if(selectId!="1"){
		$('#changeUrlSelect option[value='+selectId+']').attr('selected','selected');
		changeUrl();
	}
	});
var loginInfo = "{'clientID':'客户端串号','clientPassword':'客户端配置登录密码','imei':'手机串号','version':'客户端版本号,四段数字,如1.6.0.0609','platform':'平台,如:iPhone','model':'型号,如:iPhone 3G','resolution':'分辨率,如:480x320','systemName':'系统名称,如:iOS','systemVersion':'系统版本 (版本号用点号分开)','channelID':'渠道id',  'loginString':'登录字符串，对于之前登陆过的用户自动登录时使用','password':'密码'}";
function changeUrl() {
	var selectValue = $("#changeUrlSelect").val();
	var arr_url = new Array();
	arr_url[500]='/organ/preference/save.json';
	arr_url[499]='/organ/preference/find.json';
	arr_url[498]='/organ/preference/delete.json';
	arr_url[497]='/organ/showOrganInfo.json';
	arr_url[496]='/organ/authentication.json';
	arr_url[495]='/organ/showAccounts.json';
	arr_url[494]='/organ/deleteManager.json';
	arr_url[493]='/organ/showManagers.json';
	arr_url[492]='/organ/addManager.json';
	arr_url[491]='/organ/authorization.json';
	arr_url[490]='/organ/transitionOrganManager.json';
	arr_url[489]='/organ/findMyOrgan.json';
	arr_url[488]='/organ/transitionUser.json';
	arr_url[501]='/organ/registerSecond.json';
	arr_url[502]='/organ/registerThird.json';
	arr_url[503]='/organ/registerFourth.json';
	arr_url[504]='/organ/isExistEmail.json';
	arr_url[505]='/organ/isExistOrganNumber.json';
	arr_url[506]='/organ/isExistOrganAllName.json';
	arr_url[507]='/organ/updateOrganData.json';
	arr_url[508]='/organ/announcement/save.json';
	arr_url[509]='/organ/announcement/list.json';
	arr_url[510]='/organ/announcement/getOne.json';
	arr_url[511]='/organ/registerOne.json';
	arr_url[512]='/organ/registerFifth.json';
	arr_url[513]='/validate/getVcode.json';
	arr_url[514]='/validate/getMobileVCode.json';
	arr_url[515]='/organ/queryUserLog.json';
	arr_url[516]='/organ/regValidateEmail.json';
	arr_url[517]='/organ/announcement/update.json';
	arr_url[518]='/organ/announcement/delete.json';
	arr_url[519]='/organ/queryIndustry.json';
	arr_url[520]='/org/saveOrganProfile.json';
	
	arr_url['myCountList']='/friend/myCountList.json';
	
	var loginInfo_arr = new Array();
	loginInfo_arr[500]='{"name":"偏好设置标签"}';
	loginInfo_arr[499]='{"index":1,"page":20}';
	loginInfo_arr[498]='{"preferenceId":1}';
	loginInfo_arr[497]='{}';
	loginInfo_arr[496]='{}';
	loginInfo_arr[495]='{"organId":1}';
	loginInfo_arr[494]='{"organId":1,"userId":1}';
	loginInfo_arr[493]='{"organId":1}';
	loginInfo_arr[492]='{"userAccount":"8422822603@qq.com",mobile:"163550114",organId:"1"}';
	loginInfo_arr[491]='{"account":"8422822603@qq.com",mobile:"163550114",organId:"1"}';
	loginInfo_arr[490]='{organId:"1"}';
	loginInfo_arr[489]='{}';
	loginInfo_arr[488]='{}';
	loginInfo_arr[501]='{"id":2,"status":2}';
	loginInfo_arr[502]='{"id":2,"status":3,"orgType":1,"secondType":"工商","islisted":true,"areaid":456,"area":"北京","organAllName":"注册组织全称","contactName":"联系人名称","contanctEmail":"lianxi@111.com","contanctMobile":"12345678910","organNumber":"12345678_oooo","organName":"组织名称","organLogo":"http://"}';
	loginInfo_arr[503]='{"id":11,"username":"qufengle","password":"111111"}';
	loginInfo_arr[504]='{"email":"email@email.com"}';
	loginInfo_arr[505]='{"organNumber":"123_ssss"}';
	loginInfo_arr[506]='{"organAllName":"测试组织全称"}';
	loginInfo_arr[507]='{"organName":"编辑后测试组织全称","orgType":3,"islisted":false,"areaid":4567,"area":"北京1111","organLogo":"http://"}';
	loginInfo_arr[508]='{"title":"controller测试组织公告保存","context":"一个长篇的文章，需要插入数据库工人提供查阅"}';
	loginInfo_arr[509]='{"organId":123,"index":0,"size":2}';
	loginInfo_arr[510]='{"announcementId":4}';
	loginInfo_arr[511]='{"email":4,"password":"111111","code":"1999"}';
	loginInfo_arr[512]='{"id":200839}';
	loginInfo_arr[513]='{}';
	loginInfo_arr[514]='{"mobile":"18611161575","type":0}';
	loginInfo_arr[515]='{"index":1,"size":20}';
	loginInfo_arr[516]='{"id":200838,"email":"oyangminghe@126.com"}';
	loginInfo_arr[517]='{"id":15,"title":"修改测试","content":"公告内容修改测试"}';
	loginInfo_arr[518]='{"id":30}';
	loginInfo_arr[519]='{"pid":0}';
	loginInfo_arr[520]='{"id":200887,"name":"zuhimingcheng","picLogo":"http://file.dev.gintong.com:81/public/c/phoenix-fe/0.0.1/common/images/default200.jpg","linkManName":"不知道","linkMobile":"18701323296","linkEmail":"fuyu@gintong.com","orgType":"1","industry":"行业-行业","industryId":"23","isListing":"0","stockNum":"","discribe":"","areaString":"北京-北京","areaid":"","virtual":"1","propertyList":[],"personalPlateList":[]}';
	
	$("#joint").hide();
	$("#bigdata").hide();
	var url  = arr_url[selectValue];
	$("#paramsDiv").val(loginInfo_arr[selectValue]);

	$("#url").val(url);
	$("#allUrl").val('<%=basePath %>'+url);
}

function getMethodReturnJsonValue() {
	//判断自动登陆功能是否开启
	if($("#noLogin").attr("checked")=='checked') {
		login();
	}
	
	var selectValue = $("#changeUrlSelect option:selected").val();
	if(selectValue=='0'){
		alert("请选择");
		return;
	}
	loginInfo=$("#paramsDiv").val();
	var url = $("#url").val();
	url='<%=request.getContextPath()%>'+url;
	$.ajax({
		url : url,
		data :  loginInfo,
		beforeSend: function(xhr){xhr.setRequestHeader('sessionID', sessionId);},
		async : false,
		type : $('#postType').val(),
		dataType : "text",
		cache : false,
		success : function(data,statusText, jqXHR) {
			sessionId=jqXHR.getResponseHeader("sessionID");
			$("#showResult").val(data);
			//$("#resultDiv").html(data);
		}
	});
	
}

//zhangzhen 加入 2014-11-3 
function login() {
	url = '<%=basePath %>' + 'login/loginConfiguration.json';
	loginInfo = '{"clientID":"131321321321","clientPassword":"","imei":"yss-3434-dsf55-22256","version":"1.6.0.0609","platform":"iPhone","model":"iPhone 3G","resolution":"480x320","systemName":"iOS","systemVersion":"1.5.7","channelID":"10086111445441","loginString":"18500223015","password":"MTExMTEx"}';
	$.ajax({
		url : url,
		data :  loginInfo,
		beforeSend: function(xhr){xhr.setRequestHeader('sessionID', sessionId);},
		async : false,
		type : 'POST',
		dataType : "text",
		cache : false,
		success : function(data,statusText, jqXHR) {
			sessionId=jqXHR.getResponseHeader("sessionID");
			$("#resultDiv").html(data);
		}
	});
}

//zhangzhen 加入 2014-11-13 快速进入
   function writeSelect(obj){  
        obj.options[0].selected = "select";  
        obj.options[0].text = obj.options[0].text + String.fromCharCode(event.keyCode);  
        event.returnValue=false;  
        return obj.options[0].text;  
    }
</script>


</html>

