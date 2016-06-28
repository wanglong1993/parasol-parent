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
		<!-- 新组织接口 -->6212260200008080083
		<optgroup label="=============newOrgan================">

            <option value="211" >1.3  组织注册 orgSave</option>
            <option value="253" >1.4 获取验证码 getVCodeForPassword</option>
            <option value="254" >1.5 删除用户手机号码和邮箱（仅测试用） clearUserMobile</option>
            <option value="255" >1.6  组织注册信息查询 orgSaveFind</option>
            <option value="256" >1.7  批量注册组织用户（仅测试用）batchRegOrgUser </option>
            <option value="257" >1.8  激活邮箱（仅测试用）activateEmail </option>
            <option value="258" >1.9  验证组织全称与email是否注册 checkOrganRegister </option>
			<option value="319" >2.1动态列表---dynamicNewsList</option> 
			<option value="320" >2.2转发组织或转发知识---forwardOrg</option> 
			<option value="327" >2.4添加动态---addDynamic</option> 


			<option value="220" >3.1 获得组织列表数据 /org/getDiscoverList.json</option>
			

			<option value="317" >4.2查询评论列表---findCommentList</option> 
			<option value="318" >4.2.1保存评论---save</option> 
			<option value="310" >4.4组织详情接口---orgAndProInfo</option>
			<option value="216" >4.5 添加客户详情 customerSaveCusProfile</option>
			<option value="215" >4.6 添加组织详情 orgSaveCusProfile</option>
			<option value="226" >4.6 获得最新公告列表接口 getOrgNoticesList</option>
			 <option value="262" >4.7 获得最新资讯接口 getOrgNewsList</option>      
			<option value="316" >4.8查询资源需求---findResource</option> 
			<option value="210" >4.9 添加客户资源需求 resourceSave</option>
			<option value="222" >4.10.1获得权限模块查询getModleList</option>
			<option value="223" >4.10.2获得权限查列表用户查询getPermissonUser</option>
			<option value="225" >4.10.3 获得客户标签分组列表 /customer/tag/group.json</option>
			<option value="266" >4.10.4 客户标签添加 /customer/tag/add.json</option>
			<option value="267" >4.10.5 获得客户标签删除 /customer/tag/delete.json</option>
			<option value="263" >4.11 按年份，报表类型,季度，获取财务分析详情 /customer/finance/details.json</option>
			<option value="230" >4.12 查询高层治理</option>
			<option value="217" >4.13 添加高层治理 hightSave </option>
			<option value="231" >4.14 查询股东研究</option>
			<option value="218" >4.15 添加股东研究 stockSaveOrUpdate </option>
			<option value="232" >4.16 查询行业动态</option>
			<option value="233" >4.17 行业动态根据行业查询</option>
			<option value="219" >4.18 添加行业动态 industrySave  </option>
			<option value="234" >4.19 查询同业竞争列表</option>
			<option value="235" >4.20 同业竞争根据公司名称查询接口</option>
			<option value="240" >4.21 添加同业竞争  peerSave</option>
			<option value="380" >4.21.1 查看初始化同业竞争  peerSave</option>
			<option value="236" >4.22 查询研究报告</option>


			<option value="241" >4.23 添加研究报告  reportSave</option>
			<option value="242" >4.24 添加财务分析相关描述  financeSave</option>
			<option value="243" >4.25 添加政府地区概况  areaInfoSave</option>
			<option value="244" >4.26 添加主要职能部门  departMentsSave</option>
			<option value="247" >4.27 删除客户信息  customerDelete</option>
			 
			<option value="260" >4.28 标签公共组件查询 /tag/list.json</option>
			<option value="224" >4.29 客户分组列表查询 /customer/group/list.json</option>
			<option value="227" >4.29.1 添加分组 /customer/group/add.json</option> 
			<option value="228" >4.29.2 删除客户分组 /customer/group/delete.json</option> 
			<option value="229" >4.29.3 更新客户分组 /customer/group/update.json</option>
			<option value="212" >4.30 得到客户主键Id getcustomerId</option>
			<option value="264" >4.31 获得十大股东和流通股东 /customer/findTenStockDetail.json</option>
			<option value="325" >4.32 查询政府地区概况  areaInfoFind</option>
			<option value="326" >4.33 查询主要职能部门  departMentsFind</option>
			<option value="331" >4.34 收藏组织客户  operateCollect</option>
			<option value="332" >4.35 举报组织客户  /custome/inform/save.json</option>
			<option value="333" >4.36 查询客户模板列表  /organ/template/list.json</option>
			<option value="334" >4.37 保存客户模板  /organ/template/save.json</option>
			<option value="335" >4.38 修改客户模板名称  /organ/template/update.json</option>
			<option value="336" >4.39 删除客户模板  /organ/template/delete.json</option>
			<option value="337" >4.10 WEB-批量删除标签  /customer/tag/batchDelete.json</option>
	        <option value="338" >4.11 WEB-批量删除目录  /customer/group/batchDelete.json</option>
	        <option value="339" >4.12 WEB-获取目录列表  /customer/group/web/list.json</option>
			<option value="340" >4.13 WEB-获取我的组织客户列表数量  /org/resourceManageCount.json</option>
		   

			<option value="221" >5.2组织客户（字母正序）列表查询接口getOrgAndCustomer</option>
			<option value="268" >5.3删除组织客户deleteOrgAndCustomer.json</option>

			<option value="311" >6.1查询客户详情接口---findCusProfile</option>
			<option value="321" >6.2组织转客户接口---</option>
			<option value="213" >6.3 查询模板下的栏目columnList</option>
			<option value="214" >6.4 保存所选模块和栏目columnSaveRelation</option>
			<option value="245" >6.5  新增和修改自定义栏目  columnSave</option>
			<option value="246" >6.6  删除自定义栏目  columnDelete</option>
            <option value="248" >6.7 获取对该用户的评价 customerFindEvaluate</option>
            <option value="249" >6.8 添加评价 customerAddEvaluate</option>
            <option value="250" >6.9 赞同与取消赞同  customerFeedbackEvaluate</option>
            <option value="251" >6.10 删除评价  customerDeleteEvaluate</option>
            <option value="252" >6.11 查看更多评价  customerMoreOrganEvaluate</option>


			<option value="312" >7.1增加会面情况接口---save</option>
			<option value="313" >7.2修改会面情况接口---update</option> 
			<option value="314" >7.3查询会面情况列表---findlist</option> 
			<option value="315" >7.4查询会单个面情况对象---findone</option>
			<option value="327" >7.5删除会面情况---delone</option>  
			<option value="328" >7.6查询相关当事人</option>  
			<option value="329" >7.7添加好友</option>  
			<option value="330" >7.8解除好友</option>  
				
			 
			
			
			<option value="265" >8.1 全局搜索接口 getSearchList</option>
					
			
	
		
			 
		<option value="400" >10.1 热门搜索 hotList</option>
	    <option value="401" >10.2 获取关联资源 getRelatedResources</option>
	    <option value="402" >10.3  统计用户动态各维度数量接口 /dynamicNews/counts.json</option>
	    <option value="403" >10.4 获取关联接口  /knowledge/getKnowledgeRelatedResources.json </option>
	    <option value="404" >10.5 通讯录接口  /addressBook.json </option>
			
		<optgroup label="=============newOrgan end================">
		
		
		
		
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
	arr_url[210]='/customer/resource/save.json';
	arr_url[211]='/org/save.json';
	arr_url[212]='/customer/getId.json';
	arr_url[213]='/customer/column/list.json';
	arr_url[214]='/customer/column/saveRelation.json';
	arr_url[215]='/org/saveCusProfile.json';
	arr_url[216]='/customer/saveCusProfile.json';
	arr_url[217]='/customer/hight/save.json';
	arr_url[218]='/customer/stock/saveOrUpdate.json';
	arr_url[219]='/customer/industry/save.json';

	arr_url[220]='/org/getDiscoverList.json';
	arr_url[221]='/org/getOrgAndCustomer.json';
	arr_url[222]='/customer/getModleList.json';
	arr_url[223]='/customer/getPermissonUser.json';
	arr_url[224]='/customer/group/list.json';
	arr_url[225]='/customer/tag/group.json';
	arr_url[226]='/org/getOrgNoticesList.json';
	arr_url[227]='/customer/group/add.json';
	arr_url[228]='/customer/group/delete.json';
	arr_url[229]='/customer/group/update.json';


	arr_url[230]='/customer/hight/findHightOne.json';
	arr_url[231]='/customer/stock/findStockOne.json';
	arr_url[232]='/customer/industry/findIndustry.json';
	arr_url[233]='/customer/industry/searchPeer.json';
	arr_url[234]='/customer/peer/findPeer.json';
	arr_url[235]='/customer/peer/searchPeerByName.json';
	arr_url[236]='/customer/findReport.json';
	
	

	arr_url[240]='/customer/peer/save.json';
	arr_url[241]='/customer/report/save.json';
	arr_url[242]='/customer/finance/save.json';
	arr_url[243]='/customer/areaInfo/save.json';
	arr_url[244]='/customer/departMents/save.json';
	arr_url[245]='/customer/column/save.json';
	arr_url[246]='/customer/column/delete.json';
	arr_url[247]='/customer/allPart/delete.json';
	arr_url[248]='/customer/findEvaluate.json';
	arr_url[249]='/customer/addEvaluate.json';
	arr_url[250]='/customer/feedbackEvaluate.json';
	arr_url[251]='/customer/deleteEvaluate.json';
	arr_url[252]='/customer/moreOrganEvaluate.json';
	arr_url[253]='/register/getVCodeForPassword.json';
	arr_url[254]='/org/clearUserMobile.json';
	arr_url[255]='/org/save/find.json';
	arr_url[256]='/org/batchRegOrgUser.json';
	arr_url[257]='/org/activateEmail.json';
	arr_url[258]='/register/checkOrganRegister.json';

	arr_url[260]='/tag/list.json';
	arr_url[261]=' /customer/getCustomerRelatedResources.json';
	arr_url[262]='/org/getOrgNewsList.json';
	arr_url[263]='/customer/finance/details.json';
	arr_url[264]='/customer/findTenStockDetail.json';
	arr_url[265]='/search/getSearchList.json';
	arr_url[266]='/customer/tag/add.json';
	arr_url[267]='/customer/tag/delete.json';
	arr_url[268]='/org/deleteOrgAndCustomer.json';
	
	arr_url[310]='/org/orgAndProInfo.json';
	arr_url[311]='/customer/findCusProfile.json';
	arr_url[312]='/customer/meet/save.json';
	arr_url[313]='/customer/meet/update.json';
	arr_url[314]='/customer/meet/findList.json';
	arr_url[315]='/customer/meet/findOne.json';
	arr_url[316]='/customer/resource/findResource.json';
	arr_url[317]='/ customer/comment/findCommentList.json';
	arr_url[318]='/ customer/comment/save.json';
	arr_url[319]='/dynamicNews/getListDynamicNews.json';
	arr_url[320]=' /org/forwardOrg.json';
	arr_url[321]=' /org/changeCustomer.json';
	arr_url[327]=' /dynamicNews/addDynamic.json';
	arr_url[325]='/customer/areaInfo/findOne.json';
	arr_url[326]='/customer/departMents/findDepartOne.json';
	arr_url[327]='/customer/meet/deleteById.json';
	arr_url[328]='/customer/findRelevantParties.json';
	arr_url[329]='/friend/addFriend.json';
	arr_url[330]='/friend/deleteFriend.json';
	arr_url[331]='/customer/collect/operate.json';
	arr_url[332]='/customer/inform/save.json';
	arr_url[333]='/organ/template/list.json';
	arr_url[334]='/organ/template/save.json';
	arr_url[335]='/organ/template/update.json';
	arr_url[336]='/organ/template/delete.json';
	arr_url[337]='/customer/tag/batchDelete.json';
	arr_url[338]='/customer/group/batchDelete.json';
	arr_url[339]='/customer/group/web/list.json';
	arr_url[340]='/org/resourceManageCount.json';
	
	
	arr_url[380]='/customer/peer/findOne.json';

	
	arr_url[400]='/resource/hotList.json';
	arr_url[401]='/resource/getRelatedResources.json';
	arr_url[402]='/dynamicNews/counts.json';
	arr_url[403]='/knowledge/getKnowledgeRelatedResources.json';
	arr_url[404]='/addressBook.json';
	
	arr_url['myCountList']='/friend/myCountList.json';
	
	var loginInfo_arr = new Array();
	loginInfo_arr[210]='{"id":48,"investmentdemandList":[{"industryIds":["1","2"],"industryNames":["行业1","行业2"],"parentType":1,"address":{"areaType":0,"cityName":"郑州","countyName":"安阳县","parentType":2,"stateName":"河南省"},"personalLineList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"typeIds":["1","2"],"typeNames":["类型1","类型2"]}]}';
	loginInfo_arr[211]='{"orgType":"1","isListing":"1","stockNum":"12345678","name":"北京金桐网","shotName":"金桐","licensePic":"/pic/url","linkIdPic":"/pic/url2","linkIdPicReverse":"/reverse/pic/url2","picLogo":"/pic/logo/url2","linkName":{"relation":"张三","type":"3","relationId":""},"linkMobile":"18500000000","mobileCode":"123456"}';
	loginInfo_arr[212]='{}';
	loginInfo_arr[213]='{"templateId":1,"orgId":57}';
	loginInfo_arr[214]='{"columnIds":["2","3","4"],"orgId":57}';
	loginInfo_arr[215]='{"linkMobile":"12345678912","discribe":"这是个客户","email":"ceshi@gintong.com","id":57,"industryIds":["1","2"],"industrys":["行业1","行业2"],"isListing":"1","linkName":{"id":0,"relation":"","relationId":"","type":0},"name":"北京当代培训有限公司","picLogo":"/web1/organ/default.jpg",'
		+'"profile":{"braceIndustry":{"fileIndex":[],"id":"","propertyList":[],"remark":"re","taskId":""},"branchList":[{"address":"address","email":"email","fax":"fax","id":"","leader":{"id":0,"relation":"姓名","relationId":"","type":1},"phone":"phone","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"relation":{"id":0,"relation":"姓名","relationId":"","type":1},"sponsor":{"id":0,"relation":"","relationId":"","type":0},"website":""}],"businessList":[{"id":"","leader":{"id":0,"relation":"姓名","relationId":"","type":1},"phone":"联系方式","propertyList":[],"remark":"业务简介","type":"业务分析"}],"enterpriseList":[{"id":"","ifControl":"0","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"relation":{"id":0,"relation":"姓名","relationId":"","type":1},"relationship":"relationship","stockPercent":"持股比例","stockQty":"持股数量"}],"fileIndex":[],"id":0,"incomeFeature":{"fileIndex":[],"id":"","propertyList":[],"remark":"rema","taskId":""},"info":{"address":"","area":{"address":"","city":"","country":"","county":"","id":"","province":""},"capital":"注册资金","childArea":"","circulation":"","columnList":[],"controler":{"id":0,"relation":"实际控股人","relationId":"","type":1},"corpn":{"id":0,"relation":"法人","relationId":"","type":1},"cur":"币种","cycle":"","devArea":"","expertList":[],"famous":"","fax":"传真","history":"历史沿革","hostess":{"id":0,"relation":"","relationId":"","type":0},"id":"","leader":{"id":0,"relation":"","relationId":"","type":0},"number":"","parentOrg":{"id":0,"relation":"","relationId":"","type":0},"phone":"","product":"产品描述","profile":"公司概况","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"relation":{"id":0,"relation":"","relationId":"","type":0},"stkcd":"证券代码","typesList":[],"website":"网址"},"linkMans":[{"email":"email","id":0,"mobile":"mobile","name":"name","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}]}],"listing":{"beginTime":"上市时间","fileIndex":[],"id":"","price":"股票发行价格","profit":"发行市盈率","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"referee":{"id":0,"relation":"上市推荐人","relationId":"","type":1},"shares":"股票发行数量","sponsor":{"id":0,"relation":"保健机构","relationId":"","type":1},"taskId":"","type":"发行方式","underwriter":"陈晓尚"},"partnerList":[{"address":"办公地点","companyJob":"公司职务","detailAddress":"","email":"","expertise":"装爷领域","id":"","name":{"id":0,"relation":"姓名","relationId":"","type":1},"percent":"出资比例","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}]}],"personalPlateList":[{"name":"自定义模块","personalLineList":[{"content":"自定义内容","name":"自定义名称","type":"1"}]}],"practiceList":[{"remark":"内容","taskId":""}],'
        +'"prodectList":[{"fileIndex":[],"funds":"资金规模","id":"","limit":"期限","name":"name","profit":"收益分配","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"startTime":"成立日期","status":"zhuagntai","taskId":""}],"publication":{"fileIndex":[],"id":"","propertyList":[],"remark":"内容","taskId":""},"selectCoumnIds":[],"sponsorCustomerList":[{"fileIndex":[],"id":"","propertyList":[],"remark":"redf","taskId":""}],"taskId":"","teamList":[{"address":"办公地点","companyJob":"公司职务","detailAddress":"详细地址","email":"邮箱","expertise":"装爷领域","id":"","name":{"id":0,"relation":"姓名","relationId":"","type":1},"percent":"出资比例","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}]}],'
        +'"relevantParties":{'
        +'"lawFirmList":[{"type":"101","address":"地址","email":"","fax":"传真","id":"","leader":{"id":0,"relation":"姓名","relationId":"","type":1},"phone":"电话","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"relation":{"id":0,"relation":"姓名","relationId":"","type":1},"sponsor":{"id":0,"relation":"姓名","relationId":"","type":1},"website":"网址"}],'
        +'"sponsorBankList":[{"type":"102","address":"地址","email":"","fax":"传真","id":"","leader":{"id":0,"relation":"姓名","relationId":"","type":1},"phone":"电话","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"relation":{"id":0,"relation":"姓名","relationId":"","type":1},"sponsor":{"id":0,"relation":"姓名","relationId":"","type":1},"website":"网址"}],'
        +'"sponsorBranchList":[{"type":"100","address":"ddd","email":"","fax":"传真","id":"","leader":{"id":0,"relation":"姓名","relationId":"","type":1},"phone":"联系电话","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"relation":{"id":0,"relation":"姓名","relationId":"","type":1},"sponsor":{"id":0,"relation":"姓名","relationId":"","type":1},"website":"网址"}]'
        +'}'
        +'},'
        +'"propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"shotName":"当代","stockNum":"123456789","taskId":"12134","type":"1","virtual":"1",'
        +'"customerPermissions":{"dule":false,"xiaoles":[{"id":13556,"name":"张斌"}],"zhongles":[{"id":12454,"name":"林美霞"},{"id":13835,"name":"张桂珍"}],"dales":[{"id":13414,"name":"股市水晶球"},{"id":10089,"name":"杨楠"},{"id":13247,"name":"曾添_dataplayer"}],"modelType":[1,2,3],"mento":"哈哈"},'
        +'"directory":["1","2","3"],'
        +'"lableList":[{"tagId":"1","tagName":"标签名称"}],'
        +'"relevance":{"r":[{"tag":"33","conn":[{"type":1,"id":2111,"title":"d d d d","ownerid":1,"ownername":"的的的的的的的","requirementtype":"tzxq"}]}],"p":[{"tag":"111","conn":[{"type":2,"id":"141527672992500079","name":"五小六","ownerid":1,"ownername":"","caree":"","company":""}]}],"o":[{"tag":"22","conn":[{"type":5,"id":618,"name":"我的测试客户","ownerid":"","ownername":"","address":"","hy":","},{"type":5,"id":617,"name":" 中国平安","ownerid":"","ownername":"","address":"","hy":",保险公司,"}]}],"k":[{"tag":"44","conn":[{"type":5,"id":618,"title":"测试知识","ownerid":"","ownername":"","columntype":"","columnpath":""}]}]}'
        +'}';
	
	loginInfo_arr[216]='{"isOrgChange":"0","area":{"address":"具体地址"},"phoneList":[{"type":"联系电话","areaCode":"","phone":"15800223145","extension":"分机号"}],"linkMobile":"12345678912","discribe":"这是个客户","email":"ceshi@gintong.com","id":57,"industryIds":["1","2"],"industrys":["行业1","行业2"],"isListing":"1","linkName":{"id":0,"relation":"","relationId":"","type":0},"name":"北京当代培训有限公司","picLogo":"/web1/organ/default.jpg",'
        +'"profile":{"braceIndustry":{"fileIndex":[],"id":"","propertyList":[],"remark":"re","taskId":""},"branchList":[{"address":"address","email":"email","fax":"fax","id":"","leader":{"id":0,"relation":"姓名","relationId":"","type":1},"phone":"phone","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"relation":{"id":0,"relation":"姓名","relationId":"","type":1},"sponsor":{"id":0,"relation":"","relationId":"","type":0},"website":""}],"businessList":[{"id":"","leader":{"id":0,"relation":"姓名","relationId":"","type":1},"phone":"联系方式","propertyList":[],"remark":"业务简介","type":"业务分析"}],"enterpriseList":[{"id":"","ifControl":"0","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"relation":{"id":0,"relation":"姓名","relationId":"","type":1},"relationship":"relationship","stockPercent":"持股比例","stockQty":"持股数量"}],"fileIndex":[],"id":0,"incomeFeature":{"fileIndex":[],"id":"","propertyList":[],"remark":"rema","taskId":""},"info":{"address":"","area":{"address":"","city":"","country":"","county":"","id":"","province":""},"capital":"注册资金","childArea":"","circulation":"","columnList":[],"controler":{"id":0,"relation":"实际控股人","relationId":"","type":1},"corpn":{"id":0,"relation":"法人","relationId":"","type":1},"cur":"币种","cycle":"","devArea":"","expertList":[],"famous":"","fax":"传真","history":"历史沿革","hostess":{"id":0,"relation":"","relationId":"","type":0},"id":"","leader":{"id":0,"relation":"","relationId":"","type":0},"number":"","parentOrg":{"id":0,"relation":"","relationId":"","type":0},"phone":"","product":"产品描述","profile":"公司概况","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"relation":{"id":0,"relation":"","relationId":"","type":0},"stkcd":"证券代码","typesList":[],"website":"网址"},"linkMans":[{"email":"email","id":0,"mobile":"mobile","name":"name","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}]}],"listing":{"beginTime":"上市时间","fileIndex":[],"id":"","price":"股票发行价格","profit":"发行市盈率","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"referee":{"id":0,"relation":"上市推荐人","relationId":"","type":1},"shares":"股票发行数量","sponsor":{"id":0,"relation":"保健机构","relationId":"","type":1},"taskId":"","type":"发行方式","underwriter":"陈晓尚"},"partnerList":[{"address":"办公地点","companyJob":"公司职务","detailAddress":"","email":"","expertise":"装爷领域","id":"","name":{"id":0,"relation":"姓名","relationId":"","type":1},"percent":"出资比例","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}]}],"personalPlateList":[{"name":"自定义模块","personalLineList":[{"content":"自定义内容","name":"自定义名称","type":"1"}]}],"practiceList":[{"remark":"内容","taskId":""}],'
        +'"prodectList":[{"fileIndex":[],"funds":"资金规模","id":"","limit":"期限","name":"name","profit":"收益分配","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"startTime":"成立日期","status":"zhuagntai","taskId":""}],"publication":{"fileIndex":[],"id":"","propertyList":[],"remark":"内容","taskId":""},"selectCoumnIds":[],"sponsorCustomerList":[{"fileIndex":[],"id":"","propertyList":[],"remark":"redf","taskId":""}],"taskId":"","teamList":[{"address":"办公地点","companyJob":"公司职务","detailAddress":"详细地址","email":"邮箱","expertise":"装爷领域","id":"","name":{"id":0,"relation":"姓名","relationId":"","type":1},"percent":"出资比例","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}]}],'
        +'"relevantParties":{'
        +'"lawFirmList":[{"type":"101","address":"地址","email":"","fax":"传真","id":"","leader":{"id":0,"relation":"姓名","relationId":"","type":1},"phone":"电话","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"relation":{"id":0,"relation":"姓名","relationId":"","type":1},"sponsor":{"id":0,"relation":"姓名","relationId":"","type":1},"website":"网址"}],'
        +'"sponsorBankList":[{"type":"102","address":"地址","email":"","fax":"传真","id":"","leader":{"id":0,"relation":"姓名","relationId":"","type":1},"phone":"电话","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"relation":{"id":0,"relation":"姓名","relationId":"","type":1},"sponsor":{"id":0,"relation":"姓名","relationId":"","type":1},"website":"网址"}],'
        +'"sponsorBranchList":[{"type":"100","address":"ddd","email":"","fax":"传真","id":"","leader":{"id":0,"relation":"姓名","relationId":"","type":1},"phone":"联系电话","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"relation":{"id":0,"relation":"姓名","relationId":"","type":1},"sponsor":{"id":0,"relation":"姓名","relationId":"","type":1},"website":"网址"}]'
        +'}'
        +'},'
        +'"propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"shotName":"当代","stockNum":"123456789","taskId":"12134","type":"1","virtual":"1",'
        +'"customerPermissions":{"dule":false,"xiaoles":[{"id":13556,"name":"张斌"}],"zhongles":[{"id":12454,"name":"林美霞"},{"id":13835,"name":"张桂珍"}],"dales":[{"id":13414,"name":"股市水晶球"},{"id":10089,"name":"杨楠"},{"id":13247,"name":"曾添_dataplayer"}],"modelType":[1,2,3],"mento":"哈哈"},'
        +'"directory":["1","2","3"],'
        +'"lableList":[{"tagId":"1","tagName":"标签名称"}],'
        +'"relevance":{"r":[{"tag":"33","conn":[{"type":1,"id":2111,"title":"d d d d","ownerid":1,"ownername":"的的的的的的的","requirementtype":"tzxq"}]}],"p":[{"tag":"111","conn":[{"type":2,"id":"141527672992500079","name":"五小六","ownerid":1,"ownername":"","caree":"","company":""}]}],"o":[{"tag":"22","conn":[{"type":5,"id":618,"name":"我的测试客户","ownerid":"","ownername":"","address":"","hy":","},{"type":5,"id":617,"name":" 中国平安","ownerid":"","ownername":"","address":"","hy":",保险公司,"}]}],"k":[{"tag":"44","conn":[{"type":5,"id":618,"title":"测试知识","ownerid":"","ownername":"","columntype":"","columnpath":""}]}]}'
        +'}';
	loginInfo_arr[217]='{"gczlList":[{"birth":"2010-10-10","eduational":"大学","job":"开发主管","relation":{"relation":"马云","relationId":"","type":0},"type":"高层信息"}],"id":57,"personalLineList":[{"content":"自定义内容","name":"自定义名称","type":"1"}]}';
	loginInfo_arr[218]='{"cShareholder":"wangfeiliang","cStockPercent":"60%","fShareholder":"bb","fStockPercent":"60%","id":57,"personalLineList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"rShareholder":"xyb","rStockPercent":"60%"}';
	loginInfo_arr[219]='{"id":57,"tagNames":["金融","行业","巨头"],"personalLineList":[{"content":"自定义内容","name":"自定义名称","type":"1"}]}';
	
	loginInfo_arr[220]='{"area":"北京","type":"1","industry":"金融","index":"0","size":"20"}';
	loginInfo_arr[221]='{"tagId":"","groupId":"0","type":"-1","name":"","index":"0","size":"20"}';
	loginInfo_arr[222]='{"customerId":"148"}';
	loginInfo_arr[223]='{"customerId":"1"}';
	loginInfo_arr[224]='{}';
	loginInfo_arr[225]='{index:0,size:99999}';
	loginInfo_arr[226]='{ "customerId":"108209","index":"0","size":"20"}';
	

	loginInfo_arr[230]='{"customerId":"1"}';
	loginInfo_arr[231]='{"customerId":"2"}';
	loginInfo_arr[232]='{"customerId":"1"}';
	loginInfo_arr[233]='{"customerId":"1","tagNames":"金融","currentPage":"1","pageSize":"20"}';
	loginInfo_arr[234]='{"customerId":"1"}';
	loginInfo_arr[235]='{"customerId":"1","comName":"中国电力","tagNames":"金融","currentPage":"1","pageSize":"20"}';
	loginInfo_arr[236]='{"customerId":"1"}';
	
	loginInfo_arr[240]='{"id":57,"peerList":[{"inc":{"relation":"中国电力","relationId":"","type":1},"tagNames":["金融","行业","大拿"]}],"personalLineList":[{"content":"自定义内容","name":"自定义名称","type":"1"}]}';
	loginInfo_arr[241]='{"id":57,"personalLineList":[{"content":"自定义内容","name":"自定义名称","type":"1"},{"content":"自定义内容2","name":"自定义名称2","type":"1"}]}';
	loginInfo_arr[242]='{"id":57,"personalLineList":[{"content":"自定义内容","name":"自定义名称","type":"1"}]}';
	loginInfo_arr[243]='{"gdp":"gdp","area":"面积","type":"2","airport":"11d1","mainCom":{"relation":"www3","relationId":"","type":1},"famousList":[{"relation":"www3","relationId":"","type":1}],"id":57,"mainColleges":"主要高校","name":"地区概况","parentArea":"上级行政区域","population":"人口","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"remark":"地区简介","resource":"资源","shotName":"111","train":"火车站"}';	
	loginInfo_arr[244]='{"areaType":"2","cztDepart":{"address":"地址","departName":"财政局333","fax":"传真","list":[{"relation":"www3","relationId":"","type":1}],"phone":"电话","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"website":"www.cz333j.com"},"fgwDepart":{"address":"地址","departName":"财政局333","fax":"传真","list":[{"relation":"www3","relationId":"","type":1}],"phone":"电话","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"website":"www.cz333j.com"},'
		              +'"gzwDepart":{"address":"地址","departName":"财政局333","fax":"传真","list":[{"relation":"www3","relationId":"","type":1}],"phone":"电话","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"website":"www.cz333j.com"},"sjrbDepart":{"address":"地址","departName":"财政局333","fax":"传真","list":[{"relation":"www3","relationId":"","type":1}],"phone":"电话","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"website":"www.cz333j.com"},'
		              +'"ghjDepart":{"address":"地址","departName":"财政局333","fax":"传真","list":[{"relation":"www3","relationId":"","type":1}],"phone":"电话","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"website":"www.cz333j.com"},"gtzytDepart":{"address":"地址","departName":"财政局333","fax":"传真","list":[{"relation":"www3","relationId":"","type":1}],"phone":"电话","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"website":"www.cz333j.com"},'
		              +'"swtDepart":{"address":"地址","departName":"财政局333","fax":"传真","list":[{"relation":"www3","relationId":"","type":1}],"phone":"电话","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"website":"www.cz333j.com"},"gxwDepart":{"address":"地址","departName":"财政局333","fax":"传真","list":[{"relation":"www3","relationId":"","type":1}],"phone":"电话","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"website":"www.cz333j.com"},'
		              +'"houseDepart":{"address":"地址","departName":"财政局333","fax":"传真","list":[{"relation":"www3","relationId":"","type":1}],"phone":"电话","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"website":"www.cz333j.com"},"sftDepart":{"address":"地址","departName":"财政局333","fax":"传真","list":[{"relation":"www3","relationId":"","type":1}],"phone":"电话","propertyList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"website":"www.cz333j.com"},'
		              +'"id":57}';
	loginInfo_arr[245]='{"columnId":0,"orgId":57,"name":"自定义栏目一","orgType":"1"}';
	loginInfo_arr[246]='{"columnId":33}';
	loginInfo_arr[247]='{"orgIds":["57","58","59"]}';
	loginInfo_arr[248]='{"userId":57,"isSelf":false,"type":"1"}';
	loginInfo_arr[249]='{"userId":57,"comment":"技术控","type":"2"}';
	loginInfo_arr[250]='{"id":501,"homeUserId":60,"feedback":true,"type":"2"}';
	loginInfo_arr[251]='{"id":503,"type":"2"}';
	loginInfo_arr[252]='{"userId":60,"type":"2"}';
	loginInfo_arr[253]='{"mobile":"16478885525","type":2,"mobileAreaCode":"86"}';
	loginInfo_arr[254]='{"phoneNumber":"18500000000","email":"123@163.com"}';
	loginInfo_arr[255]='{"orgId":"57"}';
	loginInfo_arr[256]='["testOrg1","testOrg2","testOrg3","testOrg4","testOrg5"]';
	loginInfo_arr[257]='{"email":"123@163.com"}';
	loginInfo_arr[258]='{"orgName":"金桐网","email":"123@163.com"}';
	
	loginInfo_arr[227]='{"name":"名称1","parentId":"-1"}';
	loginInfo_arr[228]='{"id":"1"}';
	loginInfo_arr[229]='{id:"4","name":"bbbb"}';
	loginInfo_arr[260]='{index:"0",size:"5", type:"1"}';
	loginInfo_arr[261]='{"name":"名称1","parentId":"0"}';
	loginInfo_arr[262]='{"customerId":"108209","index":"0","size":"20"}';
	loginInfo_arr[263]='{"customerId":"2","year":"2014","type":"1","quarter":1}';
	loginInfo_arr[264]='{"customerId":"2","year":"2014","type":"1"}';
	loginInfo_arr[265]='{"keyword":"北京","type":"6","index":"0","size":"20"}';
	loginInfo_arr[266]='{"tag":"北京"}';
	loginInfo_arr[267]='{"id":"502"}';
	loginInfo_arr[268]='{"customerId":"1000"}';




	loginInfo_arr[310]='{"orgId":1}';
	loginInfo_arr[311]='{"orgId":1}';
	loginInfo_arr[312]='{"customerId":3,"time":"2015-11-02","meetDate":"14:40","color":"1","minTime":"2","repeadType":"1","picId":"","tags":[{"tagName":"标签名称"}],"remindTime":"15","remindType":"1","title":"30号上午进行会面交流","content":"在西奥中心11层开会讨论下月接话","address":"北京市朝阳区大屯路","remark":"备注信息飞电风扇等",'
						+'"relevance":{"r":[{"tag":"33","conn":[{"type":1,"id":2111,"title":"d d d d","ownerid":1,"ownername":"的的的的的的的","requirementtype":"tzxq"}]}],"p":[{"tag":"111","conn":[{"type":2,"id":"141527672992500079","name":"五小六","ownerid":1,"ownername":"","caree":"","company":""}]}],"o":[{"tag":"22","conn":[{"type":5,"id":618,"name":"我的测试客户","ownerid":"","ownername":"","address":"","hy":","},{"type":5,"id":617,"name":" 中国平安","ownerid":"","ownername":"","address":"","hy":",保险公司,"}]}],"k":[{"tag":"44","conn":[{"type":5,"id":618,"title":"测试知识","ownerid":"","ownername":"","columntype":"","columnpath":""}]}]}'
					    +'}';
	loginInfo_arr[313]='{"customerId":3,"time":"2015-11-02","color":"1","meetDate":"14:40","minTime":"2","repeadType":"1","picId":"","tags":[{"tagName":"标签名称"}],"remindTime":"15","remindType":"1","title":"30号上午进行会面交流","content":"在西奥中心11层开会讨论下月接话","address":"北京市朝阳区大屯路","remark":"备注信息飞电风扇等",'
						+'"relevance":{"r":[{"tag":"33","conn":[{"type":1,"id":2111,"title":"d d d d","ownerid":1,"ownername":"的的的的的的的","requirementtype":"tzxq"}]}],"p":[{"tag":"111","conn":[{"type":2,"id":"141527672992500079","name":"五小六","ownerid":1,"ownername":"","caree":"","company":""}]}],"o":[{"tag":"22","conn":[{"type":5,"id":618,"name":"我的测试客户","ownerid":"","ownername":"","address":"","hy":","},{"type":5,"id":617,"name":" 中国平安","ownerid":"","ownername":"","address":"","hy":",保险公司,"}]}],"k":[{"tag":"44","conn":[{"type":5,"id":618,"title":"测试知识","ownerid":"","ownername":"","columntype":"","columnpath":""}]}]}'
					    +'}';
	loginInfo_arr[314]='{"id":2}';
	loginInfo_arr[315]='{"id":32}';
	loginInfo_arr[316]='{"id":48,"type":1}';
	loginInfo_arr[317]='{"id":2}';
	loginInfo_arr[318]='{"id":1,"dynamicId":2,"content":"评论内容","createrId":10,"createrName":"评论人","createTime":"2015-03-22 12:00:01"}';
	loginInfo_arr[319]='{"userId":1,"modelType":1,"index":0,"size":10}';
	loginInfo_arr[320]='{"type":61,"content":"动态内容","title":"动态标题", "forwardingContent":"转发内容","imgPath":"","targetId":3,"listReceiverId":["1","2","3","4"],"lowType":0}';
	loginInfo_arr[321]='{"orgId":1}';
	loginInfo_arr[327]='{"type61":16,"content":"动态内容","title":"动态标题", "forwardingContent":"转发内容","imgPath":"","targetId":3,"listReceiverId":["1","3","4"],"lowType":0}';
	loginInfo_arr[325]='{"id":57}';
	loginInfo_arr[326]='{"id":57}';
	loginInfo_arr[327]='{"detailId":168}';
	loginInfo_arr[328]='{"orgId":1}';
	loginInfo_arr[329]='{"id":1}';
	loginInfo_arr[330]='{"id":14197,"userType":1}';
	loginInfo_arr[331]='{"type":"1","customerIds":["1","2","3"]}';
	loginInfo_arr[332]='{"customerId":1,"reason":"广告欺诈","content":"经常发广告"}';
	loginInfo_arr[333]='{"userId":1}';
	loginInfo_arr[334]='{"template":"{\'key\':\'value\'}","templateName":"模板名称"}';
	loginInfo_arr[335]='{"templateId":11,"templateName":"新模板名称"}';
	loginInfo_arr[336]='{"templateId":11}';
	loginInfo_arr[337]='{"tagIds":["12","14"]}';
	loginInfo_arr[338]='{"ids":["12","14"]}';
	loginInfo_arr[339]='{"index":1,"size":20,"pid":0}';
	loginInfo_arr[340]='{}';
	
	loginInfo_arr[380]='{"id":1}';
		
	loginInfo_arr[400]='{"page":1,"rows":20,"type":"2","hot":"2"}';
	loginInfo_arr[401]='{"targetId":128,"targetType":3,"page":0,"rows":9,"scope":3}';
	loginInfo_arr[402]='{"type":100,"userId":"14611"}';
	loginInfo_arr[403]='{"type":3,"keyword":"","page":1,"size":20}';
	loginInfo_arr[404]='{"keyword":"","char":"","index":1,"size":20,"type":2}';
	
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

