<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>测试</title>
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
<option value="1">loginConfiguration</option>
<option value="528">查询目录下的资源</option>
<option value="529">查询标签下的资源</option>
<option value="530">修改客户目录</option>
<option value="531">修改客户标签</option>
<option value="532">删除客户</option>
<option value="111">addArticle</option>
<option value="33">add2BlackList</option>
<option value="14">addBusinessRequirement</option>
<option value="52">addComment</option>
<option value="34">addFriend</option>
<option value="9">addJTContact</option>
<option value="10">addOrginazitionGuest</option>
<option value="26">addProject</option>
<option value="2">addRecord</option>
<option value="11">addRequirement</option>
<option value="15">addTask</option>
<option value="46">allowConnectionsRequest</option>
<option value="23">closeBusinessRequirement</option>
<option value="28">closeProject</option>
<option value="21">closeRequirement</option>
<option value="25">closeTask</option>
<option value="13">createMUC</option>
<option value="39">deleteFriend</option>
<option value="22">editBusinessRequirement</option>
<option value="27">editProject</option>
<option value="20">editRequirement</option>
<option value="24">editTask</option>
<option value="40">exitOrganization</option>
<option value="5">fullContactInfo</option>
<option value="6">fullOrganizationAuth</option>
<option value="4">fullPersonMemberInfo</option>
<option value="50">focusRequirement</option>
<option value="63">getBusinessRequirementDetailByID</option>
<option value="16">getConnections</option>
<option value="getFriends">getFriends</option>
<option value="getAllRelations">getAllRelations</option>
<option value="62">getFlow</option>
<option value="48">getJTContactDetail</option>
<option value="71">getKnowledgeDetailByID</option>
<option value="100">getListAffair</option>
<option value="51">getListComment</option>
<option value="44">getListConnections</option>
<option value="55">getListConnectionsMini</option>
<option value="37">getListMUC</option>
<option value="43">getListRequirement</option>
<option value="17">getListRequirementByOrganizationID</option>
<option value="49">getListRequirementByUserID</option>
<option value="72">getMatchConnectionsMini</option>
<option value="70">getMatchKnowledgeMini</option>
<option value="69">getMatchRequirementMini</option>
<option value="65">getMyAffair</option>
<option value="38">getNewConnections</option>
<option value="54">getOrganizationDetail</option>
<option value="64">getProjectDetailByID</option>
<option value="12">getRequirementByID</option>
<option value="42">getSearchList</option>
<option value="99">getUserByName</option>
<option value="7">getVCodeForPassword</option>
<option value="19">getWorkmate</option>
<option value="103">groupAndCustomer</option>
<option value="104">groupAndPeople</option>
<option value="31">invite2Conference</option>
<option value="36">invite2MUC</option>
<option value="58">inviteJoinGinTong</option>
<option value="18">inviteJoinOrganization</option>
<option value="101">operateProject</option>
<option value="30">recommend2Friend</option>
<option value="3">register</option>
<option value="105">relatedPeopleAndCustomer</option>
<option value="102">removeCustomer</option>
<option value="56">requirementToBusinessRequirement</option>
<option value="57">requirementToProject</option>
<option value="35">sendMessage</option>
<option value="45">sendValidateEmail</option>
<option value="32">setJTContactAccessControl</option>
<option value="8">setNewPassword</option>
<option value="29">upPhoneBook</option>
<option value="47">userlogin</option>
<option value="53">userLoginOut</option>
<option value="60">testAddFeed</option>
<option value="61">testSelectFeed</option>
<option value="66">testAddKnowledge</option>
<option value="67">testSelectKnowledge</option>
<option value="68">testGetDetail</option>
<option value="98">delJtContact</option>
<option value="97">testGetAllReq</option>
<option value="106">getNewDynamicCount</option>
<option value="107">deleteDynamic</option>
<option value="108">getMyKnowledge</option>
<option value="109">addMyKnowledge</option>
<option value="110">initAllNewconnection</option>
<option value="112">getJTContactTemplet</option>
<option value="113">getActionList</option>
<option value="114">getPeopleRelatedResources</option>
<option value="115">getVisible</option>

<!-- 云知识接口组 start -->

<option value="116">getKnowledgeByColumnAndSource-Z==========================</option>
<option value="117">getSubscribedColumnByUserId-Z</option>
<option value="118">getColumnByUserId-Z</option>
<option value="119">editSubscribedColumn-Z</option>
<option value="120">createKnowledge-J</option>
<option value="121">getUserCategory-J</option>
<option value="122">addUserCategory-J----------------------------------</option>
<option value="123">deleteUserCategory-J</option>
<option value="124">getKnowledgeCommentlist-J</option>
<option value="125">addKnowledgeComment-J</option>
<option value="126">getKnowledgeByUserCategoryAndKeyword-Z----------------</option>
<option value="127">getKnowledgeByTagAndKeyword-Z</option>
<option value="128">getKnowledgeByTypeAndKeyword-Z------------------</option>
<option value="129">editUserKnowledgeTag-J=========================================</option>
<option value="130">getKnowledgeTagList-J</option>
<option value="131">editKnowledgeTagById-J</option>
<option value="132">deleteKnowledgeById-J</option>
<option value="133">getKnowledgeRelatedResources-Z]]]]]]]]]]]]]]]]]]]]]]]]</option>
<option value="134">getJointResources-Z</option>
<option value="135">collectKnowledge-Z</option>
<option value="136">updateSubscribedColumn-Z</option>
<option value="137">getKnowledgByKeyword-Z</option>
<option value="138">getKnowledgeDetails-J</option>
<option value="139">correctJointResult-Z</option>
<option value="140">editUserCategory</option>
<option value="141">fetchExternalKnowledgeUrl</option>
<!-- 云知识接口组 end -->

<!-- 组织接口 -->
<optgroup label="=============organization================">
    <option value="142" >2.1-organizationRegister </option>
    <option value="143" >3.1-organizationCensorship </option>
    <option value="144" >3.3-userIconUploading </option>
    <option value="145" >5.1-getOrganizationList </option>
    <option value="146" >5.3-addOrganizationFriend </option>
    <option value="147" >5.4-deleteOrganizationFriend </option>
    <option value="148" >6.1-getFilteredConditions </option>
    <option value="149" >7.1-getOrganizationCustomerDetail </option>
    <option value="150" >7.2-createOrModifyOrganizationCustomerInfo  </option>
    <option value="151" >7.4-deleteOrganizationCustomerFriend </option>
    <option value="152" >8.1-getOrganizationFlowList </option>
    <option value="153" >8.1.1-createFlow </option>
    <option value="154" >8.2-focusOnDynamic </option>
    <option value="155" >8.3-organizationCollectKnowledge </option>
    <option value="156" >8.4-organizationSaveDynamic </option>
    <option value="157" >10.1-getOrganizationDiscussionAndComment </option>
    <option value="158" >10.2-postOrganizationDiscussionAndComment </option>
    <option value="159" >11.1-getOrganizationKnowledge </option>
    <option value="160" >12.1-getOrganizationCustomerDetailInfo </option>
    <option value="161" >13.1-getOrganizationCustomerShareholderDetailInfo </option>
    <option value="162" >14.1-getOrganizationCustomerFinancialAnalysis </option>
    <option value="163" >15.1-postDiscussionCommentOnOrganizationCustomer </option>
    <option value="164" >17.1-createQRCodeFromString </option>
    <option value="165" >18.1-organizationManagementGetDetailInfo </option>
    <option value="166" >18.2-organizationManagementSaveDetailInfo </option>
    <option value="167" >22.1-getOrganizationDetailByQRCode</option>
    <option value="168" >24.1-getOfflineCustomerDetail </option>
    <option value="169" >24.2-getOfflineOrganizationCustomerDetail </option>
    <option value="170" >27.1-createOfModifyMeetingWithCustomer </option>
    <option value="171" >30.1-getOrganizationCustomerDetailNDataNMeetingRecord </option>
    <option value="172" >31.1-getPersonalOrganizationCustomerList </option>
    <option value="173" >31.2-getMyOrganizationCustomerFolderList </option>
    <option value="174" >31.2-getOrganizationCustomerTagList </option>
</optgroup>

<optgroup label="=============organization end================"/>

<!-- 新框架接口 -->
<optgroup label="=============newFrame================">
    <option value="175" >findEvaluate</option>
    <option value="176" >addEvaluate</option>
    <option value="177" >feedbackEvaluate</option>
    <option value="178" >deleteEvaluate</option>
    <option value="179" >moreEvaluate</option>

    <option value="180" >updatePassword</option>
    <option value="181" >pushPeopleList</option>
    <option value="182" >blackList</option>
    <option value="183" >editBlack</option>
    <option value="185" >updateUserConfig</option>
    <option value="186" >getInterestIndustry</option>
    <option value="187" >perfectPersonMemberInfo</option>
    <option value="188" >customMade</option>


    <option value="191" >getListDynamicNews</option>
    <option value="192" >getDynamicComment</option>
    <option value="193" >setDynamicComment</option>
    <option value="194" >addDynamic</option>

    <option value="200" >getUserQRUrl-POST</option>
    <option value="201" >checkFriend-POST</option>
    <option value="202" >getCountryCode-POST</option>
    <option value="203" >getEmailSuffix-POST</option>
    <option value="204" >checkMobiles-POST</option>
    <option value="205" >sendSMS-POSt</option>

    <option value="216" >通用投融资类型、行业</option>
    <option value="myCountList" >myCountList“我的”首页关系列表</option>
</optgroup>

<optgroup label="=============newFrame end================"/>
<optgroup label="=============newDemand start================">
    <option value="207" >demandtemplateList--模版列表</option>
    <option value="208" >deletedemandtemplate--删除模版</option>
    <option value="209" >addDemandComment</option>
    <option value="210" >getDemandCommentList</option>
    <option value="211" >peopleCodeList</option>
    <option value="212" >创建需求</option>
    <option value="213" >createdemandtemplate--创建模版</option>
    <option value="217" >getDemandDetail</option>
    <option value="230" >getDemandList</option>
    <option value="231" >collectOthersDemand</option>
    <option value="232" >saveOthersDemand</option>
    <option value="233" >deleteMyDemand</option>
    <option value="234" >reportDemand</option>
    <option value="235" >deleteDemandComment</option>
</optgroup>
<optgroup label="=============newDemand end================"/>
<!-- 新组织接口 -->
<optgroup label="=============newOrgan================">
    <option value="210" >resourceSave</option>
    <option value="211" >4.6  添加组织详情 orgSave</option>
    <option value="212" >4.30 得到客户主键Id getcustomerId</option>
    <option value="213" >6.3 查询模板下的栏目columnList</option>
    <option value="214" >6.4 保存所选模块和栏目columnSaveRelation</option>
    <option value="215" >4.6 添加组织详情 orgSaveCusProfile</option>
    <option value="216" >4.5 添加客户详情 customerSaveCusProfile</option>
    <option value="217" >4.13 添加高层治理 hightSave </option>
    <option value="218" >4.15 添加股东研究 stockSaveOrUpdate </option>
    <option value="219" >4.18 添加行业动态 industrySave  </option>
    <option value="220" >3.1 发现中获得组织列表数据getOrgAndCustomer</option>
    <option value="221" >5.2 组织客户（字母正序）列表查询接口 getDiscoverList</option>
    <option value="222" >4.10.1获得权限模块查询getModleList</option>
    <option value="223" >4.10.2获得权限查列表用户查询getPermissonUser</option>
    <option value="224" >4.29 客户分组列表查询 /customer/group/list.json</option>
    <option value="225" >4.10.3 获得客户标签分组列表 /customer/tag/group.json</option>
    <option value="226" >4.6 获得最新公告列表接口 getOrgNoticesList</option>


    <option value="230" >4.12 查询高层治理</option>
    <option value="231" >4.14 查询股东研究</option>

    <option value="240" >4.21 添加同业竞争  peerSave</option>
    <option value="310" >4.4组织详情接口---orgAndProInfo</option>
    <option value="311" >6.1查询客户详情接口---findCusProfile</option>

    <option value="312" >7.1保存发布评论</option>
    <option value="313" >7.2 删除评论</option>
    <option value="314" >7.3 查询评论</option>
    <option value="315" >7.4 点赞</option>
    <option value="316" >7.5 取消点赞</option>
    <option value="317" >7.6 回复评论</option>
    <option value="318" >7.7 删除回复</option>
</optgroup>
<optgroup label="=============newOrgan end================"/>
<optgroup label="=============人脉begin================">
    <!-- 风行添加begin -->
    <option value="407" >/person/createPeopleDetail.json 新增、修改人脉</option>
    <option value="408" >/person/removePeople.json 删除人脉</option>
    <option value="409" >/person/getPeopleDetail.json 获取人脉详情</option>
    <option value="410" >/person/personList.json 人脉列表</option>
    <option value="411" >/person/convertToPeople.json 转为人脉</option>
    <option value="412" >/person/peopleHomeList.json 人脉首页列表</option>
    <option value="413" >/person/peopleMergeList.json 可合并资料的人脉列表</option>
    <option value="414" >/person/peopleMerge.json 合并人脉资料</option>
    <option value="415" >/organ/meet/save.json 保存会面记录</option>
    <option value="430" >/customer/meet/update.json 修改会面记录</option>
    <option value="416" >/organ/meet/findList.json 查询会面情况列表</option>
    <option value="417" >/organ/meet/findOne.json 查询会面情况</option>
    <option value="418" >/organ/meet/deleteById.json 删除会面情况</option>
    <option value="419" >/person/saveOrUpdateCategory.json 新增、修改目录</option>
    <option value="420" >/person/findCategory.json 查询目录</option>
    <option value="421" >/person/removeCategory.json 删除目录</option>
    <option value="422" >/categoryRelation/collectPeople.json 收藏人脉</option>
    <option value="423" >/categoryRelation/cancelCollect.json 取消收藏</option>
    <option value="424" >/code/peopleCodeList.json 职业列表查询、分类列表查询</option>
    <option value="425" >/person/findEvaluate.json 获取该用户的评价</option>
    <option value="426" >/person/addEvaluate.json 添加评价</option>
    <option value="427" >/person/feedbackEvaluate.json 赞同与取消赞同</option>
    <option value="428" >/person/deleteEvaluate.json 删除评价</option>
    <option value="429" >/person/moreEvaluate.json 更多评价</option>
    <option value="431" >/personTag/addTag.json 添加标签</option>
    <option value="432" >/personTag/deleteById.json 删除标签</option>
    <option value="433" >/personTag/list.json 查询标签</option>
    <option value="434" >/resource/hotList.json  查询标签</option>
    <option value="435" >/resource/getRelatedResources.json  查询标签</option>
    <option value="personReport" >/person/report/save.json 人脉举报</option>
    <!-- 风行添加end -->
</optgroup>

<optgroup label="=============人脉end================">

    <!-- 新知识接口 -->
    <optgroup label="=============newOrgan================">
        <option value="500" >/webknowledge/insertKnowledge.json------创建知识</option>
        <option value="501" >/webknowledge/version/list.json------不同版本</option>
        <option value="502" >/webknowledge/init.json------初始化页面</option>
        <option value="503" >/getKnowledgeByTypeAndKeyword.json------获取知识</option>
        <option value="504" >/webknowledge/deleteUserCategory.json------删除目录</option>
        <option value="505" >/webknowledge/home/getRecommendedKnowledge.json------推荐</option>
        <option value="506" >/webknowledge/home/getDockingKnowledge.json------对接</option>
        <option value="507" >/knowledge/updateKnowledge.json-----[]][][]]]]]]]]]]]]]]]]]]]]]]-</option>
        <option value="508" >/webknowledge/home/queryColumns.json------栏目分类</option>
        <option value="509" >/webknowledge/home/getKnowledgeList.json------栏目下的知识</option>
        <option value="510" >/webknowledge/home/queryMore.json------栏目下的知识</option>
        <option value="511" >/webknowledge/home/queryOrderColumn.json------栏目下的知识</option>
        <option value="512" >/webknowledge/deleteKnowledgeById.json------删除知识</option>
        <option value="513" >/webknowledge/report/add.json------举报</option>
        <option value="514" >/webknowledge/home/getHotList.json------热门</option>
        <option value="515" >/webknowledge/home/getCommentList.json------热门</option>
        <option value="516" >/webknowledge/home/separate.json------热门</option>
        <option value="517" >/webknowledge/home/getAggregationRead.json------热门</option>
        <option value="518" >/webknowledge/getHotTag.json------热门</option>
        <option value="519" >/webknowledge/addColumn.json------热门</option>
        <option value="520" >/webknowledge/delColumn.json------热门</option>
        <option value="521" >/webknowledge/home/queryColumnsById.json------热门</option>
        <option value="522" >/webknowledge/home/orderColumn/showColumn.json------热门</option>
        <option value="523" >/webknowledge/home/setOrderColumn.json------热门</option>
        <option value="524" >/webknowledge//home/column/list.json------热门</option>
        <option value="525" >/friend/myCountList.json------总数</option>
        <option value="526" >/file/download.json------总数</option>
        <option value="527" >/organ/announcement/list.json------获取公告列表</option>
    </optgroup>
    <optgroup label="=============newOrgan end================"/>


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
        <input type="text" id="aaaa" value="">
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
    arr_url[143] = '/organization/organizationCensorship.json';
    arr_url[144]='/organization/userIconUploading.json';
    arr_url[145]='/organization/getOrganizationList.json';
    arr_url[146]='/organization/addOrganizationFriend.json';
    arr_url[147]='/organization/deleteOrganizationFriend.json';
    arr_url[148]='/organization/getFilteredConditions.json';
    arr_url[149]='/organization/getOrganizationCustomerDetail.json';
    arr_url[150]='/organization/createOrModifyOrganizationCustomerInfo.json';
    arr_url[151]='/organization/deleteOrganizationCustomerFriend.json';
    arr_url[152]='/organization/getOrganizationFlowList.json';
    arr_url[153]='/organization/createFlow.json';
    arr_url[154]='/organization/focusOnDynamic.json';
    arr_url[155]='/organization/organizationCollectKnowledge.json';
    arr_url[156]='/organization/organizationSaveDynamic.json';
    arr_url[157]='/organization/getOrganizationDiscussionAndComment.json';
    arr_url[158]='/organization/postOrganizationDiscussionAndComment.json';
    arr_url[159]='/organization/getOrganizationKnowledge.json';
    arr_url[160]='/organization/getOrganizationCustomerDetailInfo.json';
    arr_url[161]='/organization/getOrganizationCustomerShareholderDetailInfo.json';
    arr_url[162]='/organization/getOrganizationCustomerFinancialAnalysis.json';
    arr_url[163]='/organization/postDiscussionCommentOnOrganizationCustomer.json';
    arr_url[164]='/organization/createQRCodeFromString.json';
    arr_url[165]='/organization/organizationManagementGetDetailInfo.json';
    arr_url[166]='/organization/organizationManagementSaveDetailInfo.json';
    arr_url[167]='/organization/getOrganizationDetailByQRCode.json';
    arr_url[168]='/organization/getOfflineCustomerDetail.json';
    arr_url[169]='/organization/getOfflineOrganizationCustomerDetail.json';
    arr_url[170]='/organization/createOfModifyMeetingWithCustomer.json';
    arr_url[171]='/organization/getOrganizationCustomerDetailNDataNMeetingRecord.json';
    arr_url[172]='/organization/getPersonalOrganizationCustomerList.json';
    arr_url[173]='/organization/getMyOrganizationCustomerFolderList.json';
    arr_url[174]='/organization/getOrganizationCustomerTagList.json';
    arr_url[175]='/mydata/findEvaluate.json';
    arr_url[176]='/mydata/addEvaluate.json';
    arr_url[177]='/mydata/feedbackEvaluate.json';
    arr_url[178]='/mydata/deleteEvaluate.json';
    arr_url[179]='/mydata/moreEvaluate.json';
    arr_url[180]='/user/set/updatePassword.json';
    arr_url[181]='/register/pushPeopleList.json';
    arr_url[182]='/user/set/blackList.json';
    arr_url[183]='/user/set/editBlack.json';
    arr_url[185]='/user/set/updateUserConfig.json';
    arr_url[186]='/register/getInterestIndustry.json';
    arr_url[187]='/register/perfectPersonMemberInfo.json';
    arr_url[188]='/user/set/customMade.json';

    arr_url[191]='/dynamicNews/getListDynamicNews.json';
    arr_url[192]='/dynamicNews/getDynamicComment.json';
    arr_url[193]='/dynamicNews/setDynamicComment.json';
    arr_url[194]='/dynamicNews/addDynamic.json';

    arr_url[200]='/mobileApp/getUserQRUrl.json';
    arr_url[201]='/mobileApp/checkFriend.json';
    arr_url[202]='/mobileApp/getCountryCode.json';
    arr_url[203]='/mobileApp/getEmailSuffix.json';
    arr_url[204]='/mobileApp/checkMobiles.json';
    arr_url[205]='/mobileApp/sendSMS.json';
    arr_url[206]='/friend/addFriends.json';
    arr_url[207]='/demandtemplate/demandtemplateList.json';
    arr_url[208]='/demandtemplate/deletedemandtemplate.json';
    arr_url[209]='/demandComment/addDemandComment.json';
    arr_url[210]='/demandComment/getDemandCommentList.json';
    arr_url[211]='/person/peopleCodeList.json';
    arr_url[212]='/demand/createDemand.json';
    arr_url[213]='/demandtemplate/createdemandtemplate.json';
    arr_url[216]='/metadata/get/code.json';
    arr_url[217]='/demand/getDemandDetail.json';
    arr_url[230]='/demand/getDemandList.json';
    arr_url[231]='/demandOpt/collectOthersDemand.json';
    arr_url[232]='/demandOpt/saveOthersDemand.json';
    arr_url[233]='/demand/deleteMyDemand.json';
    arr_url[234]='/demandReport/reportDemand.json';
    arr_url[235]='/demandComment/deleteDemandComment.json';


    arr_url[210]='/customer/resource/save.json';
    arr_url[211]='/org/save.json';
    arr_url[212]='/customer/getId.json';
    arr_url[213]='/customer/column/list.json';
    arr_url[214]='/customer/column/saveRelation.json';
    arr_url[215]='/org/saveCusProfile.json';
    arr_url[217]='/customer/hight/save.json';
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

    arr_url[220]='/org/getOrgAndCustomer.json';
    arr_url[221]='/org/getDiscoverList.json';
    arr_url[222]='/customer/getModleList.json';
    arr_url[223]='/customer/getPermissonUser.json';
    arr_url[224]='/customer/group/list.json';
    arr_url[225]='/customer/tag/group.json';
    arr_url[226]='/org/getOrgNoticesList.json';

    arr_url[230]='/customer/hight/findHightOne.json';
    arr_url[231]='/customer/stock/findStockOne.json';

    arr_url[240]='/customer/peer/save.json';


    arr_url[220]='/org/getOrgAndCustomer.json';
    arr_url[221]='/org/getDiscoverList.json';

    arr_url[222]='/customer/getModleList.json';
    arr_url[223]='/customer/getPermissonUser.json';
    arr_url[224]='/customer/group/list.json';
    arr_url[225]='/customer/tag/group.json';
    arr_url[226]='/org/getOrgNoticesList.json';

    arr_url[230]='/customer/hight/findHightOne.json';
    arr_url[231]='/customer/stock/findStockOne.json';

    arr_url[310]='/org/orgAndProInfo.json';
    arr_url[311]='/customer/findCusProfile.json';


    arr_url[312]='/organ/comment/saveComment.json';
    arr_url[313]='/organ/comment/deleteComment.json';
    arr_url[314]='/organ/comment/selectComment.json';
    arr_url[315]='/organ/comment/clickPraise.json';
    arr_url[316]='/organ/comment/removePraise.json';
    arr_url[317]='/organ/comment/replyComment.json';
    arr_url[318]='/organ/comment/removeReply.json';

    //风行添加begin
    arr_url[407] = '/person/createPeopleDetail.json'; //创建人脉
    arr_url[408] = '/person/removePeople.json';//删除人脉
    arr_url[409] = '/person/getPeopleDetail.json';//人脉详情
    arr_url[410] = '/person/personList.json';//好友/人脉列表
    arr_url[411] = '/person/convertToPeople.json';//转为人脉
    arr_url[412] = '/person/peopleHomeList.json';//人脉首页列表
    arr_url[413] = '/person/peopleMergeList.json';//可合并资料的人脉列表
    arr_url[414] = '/person/peopleMerge.json';//合并人脉资料
    arr_url[415] = '/organ/meet/save.json';//新增会面
    arr_url[430] = '/organ/meet/update.json';//修改会面记录
    arr_url[416] = 'organ/meet/findList.json';//查询会面情况列表
    arr_url[417] = '/organ/meet/findOne.json';//查询会面情况
    arr_url[418] = '/organ/meet/deleteById.json';//删除会面情况
    arr_url[419] = '/person/saveOrUpdateCategory.json';//添加目录
    arr_url[420] = '/person/findCategory.json';//查询目录
    arr_url[421] = '/person/removeCategory.json';//删除目录
    arr_url[422] = '/categoryRelation/collectPeople.json';//收藏人脉
    arr_url[423] = '/categoryRelation/cancelCollect.json';//取消收藏
    arr_url[424] = '/code/peopleCodeList.json';//职业列表查询、分类列表查询
    arr_url[425] = '/person/findEvaluate.json';//获取该用户的评价
    arr_url[426] = '/person/addEvaluate.json';//添加评价
    arr_url[427] = '/person/feedbackEvaluate.json';//赞同与取消赞同
    arr_url[428] = '/person/deleteEvaluate.json';//删除评价
    arr_url[429] = '/person/moreEvaluate.json';//更多评价
    arr_url[431] = '/personTag/addTag.json';//添加标签
    arr_url[432] = '/personTag/deleteById.json';//删除标签
    arr_url[433] = '/personTag/list.json';//查询标签
    arr_url[434] = '/resource/hotList.json';//查询标签
    arr_url[435] = '/resource/getRelatedResources.json';//查询标签
    arr_url[500] = '/webknowledge/insertKnowledge.json';//创建知识
    arr_url[501] = '/webknowledge/version/list.json';//不同版本知识
    arr_url[502] = '/webknowledge/init.json';//不
    arr_url[503] = '/knowledge/getKnowledgeByTagAndKeyword.json';//不
    arr_url[504] = '/webknowledge/deleteUserCategory.json';//不
    arr_url[505] = '/webknowledge/home/getRecommendedKnowledge.json';//不
    arr_url[506] = '/webknowledge/home/getDockingKnowledge.json';//不
    arr_url[507] = '/knowledge/updateKnowledge.json';//不
    arr_url[508] = '/webknowledge/home/queryColumnsType.json';//不
    arr_url[509] = '/webknowledge/home/getKnowledgeList.json';//不
    arr_url[510] = '/webknowledge/queryMore.json';//不
    arr_url[511] = '/webknowledge/home/queryOrderColumn.json';//不
    arr_url[512] = '/webknowledge/deleteKnowledgeById.json';//不
    arr_url[513] = '/webknowledge/report/add.json';//不
    arr_url[514] = '/webknowledge/home/getHotList.json';//不
    arr_url[515] = '/webknowledge/home/getCommentList.json';//不
    arr_url[516] = '/webknowledge/home/separate.json';//不
    arr_url[517] = '/webknowledge/home/getAggregationRead.json';//不
    arr_url[518] = '/webknowledge/home/getHotTag.json';//不
    arr_url[519] = '/webknowledge/addColumn.json';//不
    arr_url[528] = '/org/findDirectoryIdsCustomer.json';//不
    arr_url[529] = '/org/findTagIdCustomer.json';//不
    arr_url[530] = '/org/updateCustomerDirectory.json';//不
    arr_url[531] = '/org/updateCustomerTags.json';//不
    arr_url[532] = '/org/deleteResourcesCustomer.json';//不
    arr_url['personReport'] = '/person/report/save.json';//人脉举报
    //风行添加end
    arr_url['myCountList']='/friend/myCountList.json';

    var loginInfo_arr = new Array();
    loginInfo_arr[143]='{"organization":{"areaInfo":{"GDP":"￥19500.6","airport":"首都国际机场、北京南苑机场","area":"16410.54平方公里","customerId":1003,"famousList":[""],"id":0,"mainColleges":"北京大学","mainCom":"中国石油化工集团公司","name":"北京","parentArea":"","population":"2114.8万","remark":"全国第二大城市及政治、交通和文化中心","resource":"中国政治、文化、教育和国际交流中心","shotName":"北平","train":"北京站/北京西站/北京南站/北京北站","type":1},"customer":{"area":{"address":"Mountain View","city":"Santa Clara","country":1,"county":"Menlo Park","province":"California"},"auth":0,"banner":"/web/1003.jpg","comeId":12,"customerBase":{"createById":1002,"ctime":"2014-12-28 10:20:20","id":1001,"name":"Google","nameFirst":"Google","nameIndex":"Google","nameIndexAll":"Google","utime":"2014-12-28 14:20:20"},"describe":"一家美国的跨国科技企业，业务范围涵盖互联网搜索、云计算、广告技术等领域，开发并提供大量基于互联网的产品与服务，其主要利润来自于AdWords等广告服务。 ","email":"google@gmail.com","group":{"creatorId":1002,"ctime":"2014-12-28 14:20:20","id":1001,"name":"Internet"},"industryIds":[1],"industrys":["互联网"],"labelList":["互联网"],"licenceNo":"B2-20070004","linkEmail":"","linkIdPic":"/web/1003.jpg","linkMobile":"+1 650-253-0000","linkName":"Lawrence Edward","listing":true,"phoneList":[{"creatorId":1002,"ctime":"2014-12-28 14:20:20","id":1001,"name":"+1 650-253-0000"}],"picLogo":"/web/1002.jgp","propertyList":[],"shortName":"Google","stockNum":"GOOG","taskId":"10000000000012","type":1,"userId":12000,"virtual":1},"departMentMap":{"address":"北京市朝阳区大屯路科学园南里风林西奥中心A座11层","fax":"64843955","phone":"4000-7000-11","super_":{"id":1002,"relation":"122222","relationId":1003,"type":1},"website":"www.gintong.com"},"finance":{"customerId":1002,"id":1003,"stkcd":"601099","taskId":"10000001"},"listBalance":[{"currentAssets":"$200000000","currentLiab":"$500000","date":"2015-02-10","equity":"$2300000000","fixedAssets":"$6000000000","id":20000001,"intangibleAssets":"$500000000","stkcd":"601099","totalAssets":"$2500000000","year":"2014"}],"listCashFLow":[{"date":"2015-03-02","fund":"$9500","id":200001,"invest":"$6321","ncfo":"$5200000","stkcd":"601099","year":"2015"}],"listEarning":[{"date":"2012-07-31 08:20:00","financeCosts":"$23000","id":3600,"investProfit":"$2000336","netProfit":"$6300000","operateProfit":"$650000","otherIncome":"$36000","otherProfit":"$230000","stkcd":"601099","tax":"$56002","totalProfit":"$250063","year":"2014"}],"listHigh":[{"customerId":1002,"dshList":[{"birth":"1973","eduational":"graduate degree","id":3600,"job":"Chief Executive Officer","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"sex":"男","shares":"6.67%","type":0}],"ggList":[{"birth":"1973","eduational":"graduate degree","id":3600,"job":"Chief Executive Officer","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"sex":"男","shares":"6.67%","type":0}],"ggjzList":[{"birth":"1973","eduational":"graduate degree","id":3600,"job":"Chief Executive Officer","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"sex":"男","shares":"6.67%","type":0}],"id":10032,"jshList":[{"birth":"1973","eduational":"graduate degree","id":3600,"job":"Chief Executive Officer","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"sex":"男","shares":"6.67%","type":0}],"taskId":"1002"}],"listIndustry":[{"customerId":100036,"id":1001,"industry":"互联网","taskId":"6300"}],"listPeer":[],"listProfile":[{"accountingFirm":{"address":"Prestige Sigma, No. 3, 1st FloorVittal Mallya Road (Grant Road)Corporation Division No. 61","email":"India@google.com","fax":"+91 80-5692-9100","id":1003,"leader":{"id":1002,"relation":"122222","relationId":1003,"type":1},"name":"Google Online India Private Lt","phone":"+91 80-5692-9000","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"sponsor":{"id":1002,"relation":"122222","relationId":1003,"type":1},"website":"www.google.com"},"branchList":[{"address":"Prestige Sigma, No. 3, 1st FloorVittal Mallya Road (Grant Road)Corporation Division No. 61","email":"India@google.com","fax":"+91 80-5692-9100","id":1003,"leader":{"id":1002,"relation":"122222","relationId":1003,"type":1},"name":"Google Online India Private Lt","phone":"+91 80-5692-9000","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"sponsor":{"id":1002,"relation":"122222","relationId":1003,"type":1},"website":"www.google.com"}],"bussinessList":[{"id":10056,"leader":{"id":1002,"relation":"122222","relationId":1003,"type":1},"phone":"+1 212-624-9600","remark":"互联网","type":"广告搜索"}],"customerId":10003,"customerInfo":{"address":" 美国加利福尼亚州山景城","area":{"address":"Mountain View","city":"Santa Clara","country":1,"county":"Menlo Park","province":"California"},"capital":"500000","childArea":[],"circulation":"","columnList":["科技","互联网"],"controler":"PAGE LAWRENCE","corpn":"David C. Drummond","cur":"英镑","cycle":0,"devArea":null,"expertList":[],"famous":"","fax":"63000045","history":"1996年1月，身为加州斯坦福大学理学博士生的拉里·佩奇和谢尔盖·布林在学校开始一项关于搜索的研究项目。[29]区别于传统的搜索靠搜索字眼在页面中出现次数来进行结果排序的方法，两人开发了一个对网站之间的关系做精确分析的搜寻引擎。","hostess":null,"leader":null,"number":"","parentOrg":null,"phone":" +1 650-253-0000","product":"Google搜索是Google公司重要也是最普及的一项功能，是多个国家内使用率最高的互联网搜索引擎","profile":"是一家美国的跨国科技企业，业务范围涵盖互联网搜索、云计算、广告技术等领域，开发并提供大量基于互联网的产品与服务，[7]其主要利润来自于AdWords等广告服务","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"stkcd":"","typesList":[5],"website":"www.google.com"},"enterpriceList":[{"id":30006,"ifControl":2,"name":"google","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"stockPercent":"2%","stockQty":"50006"}],"id":42003,"lawFirm":{"address":"Prestige Sigma, No. 3, 1st FloorVittal Mallya Road (Grant Road)Corporation Division No. 61","email":"India@google.com","fax":"+91 80-5692-9100","id":1003,"leader":{"id":1002,"relation":"122222","relationId":1003,"type":1},"name":"Google Online India Private Lt","phone":"+91 80-5692-9000","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"sponsor":{"id":1002,"relation":"122222","relationId":1003,"type":1},"website":"www.google.com"},"listing":{"beginTime":"2004-08-09","price":"$85","profit":"28.11","referee":{"id":1002,"relation":"122222","relationId":1003,"type":1},"shares":"19,605,052","sponsor":"","taskId":"12300","type":"网上拍卖","underwriter":"摩根士丹利和瑞士信贷"},"partnerList":[{"address":"Prestige Sigma, No. 3, 1st FloorVittal Mallya Road (Grant Road)Corporation Division No. 61","companyJob":"CTO","email":"china@google.com","expertise":"IT","id":10005,"name":{"id":1002,"relation":"122222","relationId":1003,"type":1},"percent":"20%"}],"practice":{"remark":"公司概况","taskId":"1002"},"productList":[{"funds":"$10005","id":1005,"limit":"2012-07-31 08:20:00","name":"product","profit":"5.5","startTime":"2012-07-31 08:20:00","status":0,"taskId":"10002"}],"publication":{"remark":"公司概况","taskId":"1002"},"spnsorBankList":[{"address":"Prestige Sigma, No. 3, 1st FloorVittal Mallya Road (Grant Road)Corporation Division No. 61","email":"India@google.com","fax":"+91 80-5692-9100","id":1003,"leader":{"id":1002,"relation":"122222","relationId":1003,"type":1},"name":"Google Online India Private Lt","phone":"+91 80-5692-9000","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"sponsor":{"id":1002,"relation":"122222","relationId":1003,"type":1},"website":"www.google.com"}],"sponsorBranchList":[],"sponsorCustomer":{"remark":"公司概况","taskId":"1002"},"taskId":"3002","teamList":[{"address":"Prestige Sigma, No. 3, 1st FloorVittal Mallya Road (Grant Road)Corporation Division No. 61","companyJob":"CTO","email":"china@google.com","expertise":"IT","id":1006,"name":{"id":1002,"relation":"122222","relationId":1003,"type":1},"percent":"20%"}]}],"listReport":[{"customerId":1003,"id":1002,"taskId":"1003"}],"listStock":[{"cShareholder":"BRIN SERGEY MIKHAYLOVICH","cStockPercent":"6.54%","customerId":10066,"fShareholder":"BRIN SERGEY MIKHAYLOVICH","fStockPercent":"6.54%","id":2004,"rSharedholder":"BRIN SERGEY MIKHAYLOVICH","rStockPercent":"6.54%","taskId":"1004"},{"cShareholder":"","cStockPercent":"","customerId":0,"fShareholder":"","fStockPercent":"","id":0,"rSharedholder":"","rStockPercent":"","taskId":""}],"meetingInfo":{"customerId":42011,"id":3600,"meetingList":[{"area":{"address":"Mountain View","city":"Santa Clara","country":1,"county":"Menlo Park","province":"California"},"content":"介绍最新的数据库动态","createrId":40200,"id":5600,"list":[{"id":1002,"relation":"122222","relationId":1003,"type":1}],"notify":true,"repead":true,"taskId":"2506","time":"2012-07-31 08:20:00","title":"中国数据库大会"}]},"resource":{"expertDemandList":[{"address":{"address":"北京","areaType":0,"areaTypeName":"1","cityName":"朝阳","countyName":"","createTime":"2014-12-28 14:20:20","createUserId":1001,"createUserName":"google","id":1002,"parentType":1,"postalCode":"100010","stateName":"北京","typeTag":{"code":"1001","createTime":"","createUserId":0,"createUserName":"","id":1003,"name":"","type":0,"updateTime":"","updateUserId":0,"updateUserName":""},"updateTime":"2014-12-28 14:20:20","updateUserId":1002,"updateUserName":""},"createTime":"","createUserId":0,"createUserName":"","id":1001,"industryIds":"12","industryNames":"互联网","otherInformation":"附加信息","parentType":1,"personalLineList":[],"typeIds":"256","typeNames":"type","updateTime":"","updateUserId":0,"updateUserName":""}],"expertIdentityDemandList":[{"address":{"address":"北京","areaType":0,"areaTypeName":"1","cityName":"朝阳","countyName":"","createTime":"2014-12-28 14:20:20","createUserId":1001,"createUserName":"google","id":1002,"parentType":1,"postalCode":"100010","stateName":"北京","typeTag":{"code":"1001","createTime":"","createUserId":0,"createUserName":"","id":1003,"name":"","type":0,"updateTime":"","updateUserId":0,"updateUserName":""},"updateTime":"2014-12-28 14:20:20","updateUserId":1002,"updateUserName":""},"createTime":"","createUserId":0,"createUserName":"","id":1001,"industryIds":"12","industryNames":"互联网","otherInformation":"附加信息","parentType":1,"personalLineList":[],"typeIds":"256","typeNames":"type","updateTime":"","updateUserId":0,"updateUserName":""}],"financialDemandList":[{"address":{"address":"北京","areaType":0,"areaTypeName":"1","cityName":"朝阳","countyName":"","createTime":"2014-12-28 14:20:20","createUserId":1001,"createUserName":"google","id":1002,"parentType":1,"postalCode":"100010","stateName":"北京","typeTag":{"code":"1001","createTime":"","createUserId":0,"createUserName":"","id":1003,"name":"","type":0,"updateTime":"","updateUserId":0,"updateUserName":""},"updateTime":"2014-12-28 14:20:20","updateUserId":1002,"updateUserName":""},"createTime":"","createUserId":0,"createUserName":"","id":1001,"industryIds":"12","industryNames":"互联网","otherInformation":"附加信息","parentType":1,"personalLineList":[],"typeIds":"256","typeNames":"type","updateTime":"","updateUserId":0,"updateUserName":""}],"investmentDemandList":[{"address":{"address":"北京","areaType":0,"areaTypeName":"1","cityName":"朝阳","countyName":"","createTime":"2014-12-28 14:20:20","createUserId":1001,"createUserName":"google","id":1002,"parentType":1,"postalCode":"100010","stateName":"北京","typeTag":{"code":"1001","createTime":"","createUserId":0,"createUserName":"","id":1003,"name":"","type":0,"updateTime":"","updateUserId":0,"updateUserName":""},"updateTime":"2014-12-28 14:20:20","updateUserId":1002,"updateUserName":""},"createTime":"","createUserId":0,"createUserName":"","id":1001,"industryIds":"12","industryNames":"互联网","otherInformation":"附加信息","parentType":1,"personalLineList":[],"typeIds":"256","typeNames":"type","updateTime":"","updateUserId":0,"updateUserName":""}],"remark":"financing","taskId":"1002"}}}';
    loginInfo_arr[144]='';
    loginInfo_arr[145]='{"index" :0,"size": 20,"listFriendType" :0,"listListingInfo" :0,"listOrganization":0,"listArea":[1,2],"listIndustry" : [1,2]}';
    loginInfo_arr[146]='';
    loginInfo_arr[147]='';
    loginInfo_arr[148]='{}';
    loginInfo_arr[149]='{"organizationId" : 10,"doGetAllFields":true,"doGetFields" : {"doGetCustomerBasicInfo" :true,"doGetResource": false,"doGetFinance" : false,"doGetBalance" : false,"doGetCashFLow" : false,"doGetEarning" : false,"doGetStock" :false,"doGetHighManagement" :false,"doGetIndustry" : false,"doGetResearchReport" :false,"doGetPeer" : false,"doGetProfile" :false,"doGetAreaInfo" :false,"doGetMeetingInfo" : false}}';
    loginInfo_arr[150]='{"organizationId" : 10,"isOffline" : true, "organization" : {"areaInfo":{"GDP":"￥19500.6","airport":"首都国际机场、北京南苑机场","area":"16410.54平方公里","customerId":1003,"famousList":[""],"id":0,"mainColleges":"北京大学","mainCom":"中国石油化工集团公司","name":"北京","parentArea":"","population":"2114.8万","remark":"全国第二大城市及政治、交通和文化中心","resource":"中国政治、文化、教育和国际交流中心","shotName":"北平","train":"北京站/北京西站/北京南站/北京北站","type":1},"customer":{"area":{"address":"Mountain View","city":"Santa Clara","country":1,"county":"Menlo Park","province":"California"},"auth":0,"banner":"/web/1003.jpg","comeId":12,"customerBase":{"createById":1002,"ctime":"2014-12-28 10:20:20","id":1001,"name":"Google","nameFirst":"Google","nameIndex":"Google","nameIndexAll":"Google","utime":"2014-12-28 14:20:20"},"describe":"一家美国的跨国科技企业，业务范围涵盖互联网搜索、云计算、广告技术等领域，开发并提供大量基于互联网的产品与服务，其主要利润来自于AdWords等广告服务。 ","email":"google@gmail.com","group":{"creatorId":1002,"ctime":"2014-12-28 14:20:20","id":1001,"name":"Internet"},"industryIds":[1],"industrys":["互联网"],"labelList":["互联网"],"licenceNo":"B2-20070004","linkEmail":"","linkIdPic":"/web/1003.jpg","linkMobile":"+1 650-253-0000","linkName":"Lawrence Edward","listing":true,"phoneList":[{"creatorId":1002,"ctime":"2014-12-28 14:20:20","id":1001,"name":"+1 650-253-0000"}],"picLogo":"/web/1002.jgp","propertyList":[],"shortName":"Google","stockNum":"GOOG","taskId":"10000000000012","type":1,"userId":12000,"virtual":1},"departMentMap":{"address":"北京市朝阳区大屯路科学园南里风林西奥中心A座11层","fax":"64843955","phone":"4000-7000-11","super_":{"id":1002,"relation":"122222","relationId":1003,"type":1},"website":"www.gintong.com"},"finance":{"customerId":1002,"id":1003,"stkcd":"601099","taskId":"10000001"},"listBalance":[{"currentAssets":"$200000000","currentLiab":"$500000","date":"2015-02-10","equity":"$2300000000","fixedAssets":"$6000000000","id":20000001,"intangibleAssets":"$500000000","stkcd":"601099","totalAssets":"$2500000000","year":"2014"}],"listCashFLow":[{"date":"2015-03-02","fund":"$9500","id":200001,"invest":"$6321","ncfo":"$5200000","stkcd":"601099","year":"2015"}],"listEarning":[{"date":"2012-07-31 08:20:00","financeCosts":"$23000","id":3600,"investProfit":"$2000336","netProfit":"$6300000","operateProfit":"$650000","otherIncome":"$36000","otherProfit":"$230000","stkcd":"601099","tax":"$56002","totalProfit":"$250063","year":"2014"}],"listHigh":[{"customerId":1002,"dshList":[{"birth":"1973","eduational":"graduate degree","id":3600,"job":"Chief Executive Officer","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"sex":"男","shares":"6.67%","type":0}],"ggList":[{"birth":"1973","eduational":"graduate degree","id":3600,"job":"Chief Executive Officer","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"sex":"男","shares":"6.67%","type":0}],"ggjzList":[{"birth":"1973","eduational":"graduate degree","id":3600,"job":"Chief Executive Officer","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"sex":"男","shares":"6.67%","type":0}],"id":10032,"jshList":[{"birth":"1973","eduational":"graduate degree","id":3600,"job":"Chief Executive Officer","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"sex":"男","shares":"6.67%","type":0}],"taskId":"1002"}],"listIndustry":[{"customerId":100036,"id":1001,"industry":"互联网","taskId":"6300"}],"listPeer":[],"listProfile":[{"accountingFirm":{"address":"Prestige Sigma, No. 3, 1st FloorVittal Mallya Road (Grant Road)Corporation Division No. 61","email":"India@google.com","fax":"+91 80-5692-9100","id":1003,"leader":{"id":1002,"relation":"122222","relationId":1003,"type":1},"name":"Google Online India Private Lt","phone":"+91 80-5692-9000","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"sponsor":{"id":1002,"relation":"122222","relationId":1003,"type":1},"website":"www.google.com"},"branchList":[{"address":"Prestige Sigma, No. 3, 1st FloorVittal Mallya Road (Grant Road)Corporation Division No. 61","email":"India@google.com","fax":"+91 80-5692-9100","id":1003,"leader":{"id":1002,"relation":"122222","relationId":1003,"type":1},"name":"Google Online India Private Lt","phone":"+91 80-5692-9000","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"sponsor":{"id":1002,"relation":"122222","relationId":1003,"type":1},"website":"www.google.com"}],"bussinessList":[{"id":10056,"leader":{"id":1002,"relation":"122222","relationId":1003,"type":1},"phone":"+1 212-624-9600","remark":"互联网","type":"广告搜索"}],"customerId":10003,"customerInfo":{"address":" 美国加利福尼亚州山景城","area":{"address":"Mountain View","city":"Santa Clara","country":1,"county":"Menlo Park","province":"California"},"capital":"500000","childArea":[],"circulation":"","columnList":["科技","互联网"],"controler":"PAGE LAWRENCE","corpn":"David C. Drummond","cur":"英镑","cycle":0,"devArea":null,"expertList":[],"famous":"","fax":"63000045","history":"1996年1月，身为加州斯坦福大学理学博士生的拉里·佩奇和谢尔盖·布林在学校开始一项关于搜索的研究项目。[29]区别于传统的搜索靠搜索字眼在页面中出现次数来进行结果排序的方法，两人开发了一个对网站之间的关系做精确分析的搜寻引擎。","hostess":null,"leader":null,"number":"","parentOrg":null,"phone":" +1 650-253-0000","product":"Google搜索是Google公司重要也是最普及的一项功能，是多个国家内使用率最高的互联网搜索引擎","profile":"是一家美国的跨国科技企业，业务范围涵盖互联网搜索、云计算、广告技术等领域，开发并提供大量基于互联网的产品与服务，[7]其主要利润来自于AdWords等广告服务","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"stkcd":"","typesList":[5],"website":"www.google.com"},"enterpriceList":[{"id":30006,"ifControl":2,"name":"google","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"stockPercent":"2%","stockQty":"50006"}],"id":42003,"lawFirm":{"address":"Prestige Sigma, No. 3, 1st FloorVittal Mallya Road (Grant Road)Corporation Division No. 61","email":"India@google.com","fax":"+91 80-5692-9100","id":1003,"leader":{"id":1002,"relation":"122222","relationId":1003,"type":1},"name":"Google Online India Private Lt","phone":"+91 80-5692-9000","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"sponsor":{"id":1002,"relation":"122222","relationId":1003,"type":1},"website":"www.google.com"},"listing":{"beginTime":"2004-08-09","price":"$85","profit":"28.11","referee":{"id":1002,"relation":"122222","relationId":1003,"type":1},"shares":"19,605,052","sponsor":"","taskId":"12300","type":"网上拍卖","underwriter":"摩根士丹利和瑞士信贷"},"partnerList":[{"address":"Prestige Sigma, No. 3, 1st FloorVittal Mallya Road (Grant Road)Corporation Division No. 61","companyJob":"CTO","email":"china@google.com","expertise":"IT","id":10005,"name":{"id":1002,"relation":"122222","relationId":1003,"type":1},"percent":"20%"}],"practice":{"remark":"公司概况","taskId":"1002"},"productList":[{"funds":"$10005","id":1005,"limit":"2012-07-31 08:20:00","name":"product","profit":"5.5","startTime":"2012-07-31 08:20:00","status":0,"taskId":"10002"}],"publication":{"remark":"公司概况","taskId":"1002"},"spnsorBankList":[{"address":"Prestige Sigma, No. 3, 1st FloorVittal Mallya Road (Grant Road)Corporation Division No. 61","email":"India@google.com","fax":"+91 80-5692-9100","id":1003,"leader":{"id":1002,"relation":"122222","relationId":1003,"type":1},"name":"Google Online India Private Lt","phone":"+91 80-5692-9000","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"sponsor":{"id":1002,"relation":"122222","relationId":1003,"type":1},"website":"www.google.com"}],"sponsorBranchList":[],"sponsorCustomer":{"remark":"公司概况","taskId":"1002"},"taskId":"3002","teamList":[{"address":"Prestige Sigma, No. 3, 1st FloorVittal Mallya Road (Grant Road)Corporation Division No. 61","companyJob":"CTO","email":"china@google.com","expertise":"IT","id":1006,"name":{"id":1002,"relation":"122222","relationId":1003,"type":1},"percent":"20%"}]}],"listReport":[{"customerId":1003,"id":1002,"taskId":"1003"}],"listStock":[{"cShareholder":"BRIN SERGEY MIKHAYLOVICH","cStockPercent":"6.54%","customerId":10066,"fShareholder":"BRIN SERGEY MIKHAYLOVICH","fStockPercent":"6.54%","id":2004,"rSharedholder":"BRIN SERGEY MIKHAYLOVICH","rStockPercent":"6.54%","taskId":"1004"},{"cShareholder":"","cStockPercent":"","customerId":0,"fShareholder":"","fStockPercent":"","id":0,"rSharedholder":"","rStockPercent":"","taskId":""}],"meetingInfo":{"customerId":42011,"id":3600,"meetingList":[{"area":{"address":"Mountain View","city":"Santa Clara","country":1,"county":"Menlo Park","province":"California"},"content":"介绍最新的数据库动态","createrId":40200,"id":5600,"list":[{"id":1002,"relation":"122222","relationId":1003,"type":1}],"notify":true,"repead":true,"taskId":"2506","time":"2012-07-31 08:20:00","title":"中国数据库大会"}]},"resource":{"expertDemandList":[{"address":{"address":"北京","areaType":0,"areaTypeName":"1","cityName":"朝阳","countyName":"","createTime":"2014-12-28 14:20:20","createUserId":1001,"createUserName":"google","id":1002,"parentType":1,"postalCode":"100010","stateName":"北京","typeTag":{"code":"1001","createTime":"","createUserId":0,"createUserName":"","id":1003,"name":"","type":0,"updateTime":"","updateUserId":0,"updateUserName":""},"updateTime":"2014-12-28 14:20:20","updateUserId":1002,"updateUserName":""},"createTime":"","createUserId":0,"createUserName":"","id":1001,"industryIds":"12","industryNames":"互联网","otherInformation":"附加信息","parentType":1,"personalLineList":[],"typeIds":"256","typeNames":"type","updateTime":"","updateUserId":0,"updateUserName":""}],"expertIdentityDemandList":[{"address":{"address":"北京","areaType":0,"areaTypeName":"1","cityName":"朝阳","countyName":"","createTime":"2014-12-28 14:20:20","createUserId":1001,"createUserName":"google","id":1002,"parentType":1,"postalCode":"100010","stateName":"北京","typeTag":{"code":"1001","createTime":"","createUserId":0,"createUserName":"","id":1003,"name":"","type":0,"updateTime":"","updateUserId":0,"updateUserName":""},"updateTime":"2014-12-28 14:20:20","updateUserId":1002,"updateUserName":""},"createTime":"","createUserId":0,"createUserName":"","id":1001,"industryIds":"12","industryNames":"互联网","otherInformation":"附加信息","parentType":1,"personalLineList":[],"typeIds":"256","typeNames":"type","updateTime":"","updateUserId":0,"updateUserName":""}],"financialDemandList":[{"address":{"address":"北京","areaType":0,"areaTypeName":"1","cityName":"朝阳","countyName":"","createTime":"2014-12-28 14:20:20","createUserId":1001,"createUserName":"google","id":1002,"parentType":1,"postalCode":"100010","stateName":"北京","typeTag":{"code":"1001","createTime":"","createUserId":0,"createUserName":"","id":1003,"name":"","type":0,"updateTime":"","updateUserId":0,"updateUserName":""},"updateTime":"2014-12-28 14:20:20","updateUserId":1002,"updateUserName":""},"createTime":"","createUserId":0,"createUserName":"","id":1001,"industryIds":"12","industryNames":"互联网","otherInformation":"附加信息","parentType":1,"personalLineList":[],"typeIds":"256","typeNames":"type","updateTime":"","updateUserId":0,"updateUserName":""}],"investmentDemandList":[{"address":{"address":"北京","areaType":0,"areaTypeName":"1","cityName":"朝阳","countyName":"","createTime":"2014-12-28 14:20:20","createUserId":1001,"createUserName":"google","id":1002,"parentType":1,"postalCode":"100010","stateName":"北京","typeTag":{"code":"1001","createTime":"","createUserId":0,"createUserName":"","id":1003,"name":"","type":0,"updateTime":"","updateUserId":0,"updateUserName":""},"updateTime":"2014-12-28 14:20:20","updateUserId":1002,"updateUserName":""},"createTime":"","createUserId":0,"createUserName":"","id":1001,"industryIds":"12","industryNames":"互联网","otherInformation":"附加信息","parentType":1,"personalLineList":[],"typeIds":"256","typeNames":"type","updateTime":"","updateUserId":0,"updateUserName":""}],"remark":"financing","taskId":"1002"}}}';
    loginInfo_arr[151]='';
    loginInfo_arr[152]='';
    loginInfo_arr[153]='';
    loginInfo_arr[154]='';
    loginInfo_arr[155]='';
    loginInfo_arr[156]='';
    loginInfo_arr[157]='{"organizationId" : 10,"parentCommentId" : 12,"index" : 0, "size" : 20 }';
    loginInfo_arr[158]='{"organizationId":1002,"discussionAndCommentId" : 10036,"parentDiscussionAndCommentId" : 0,"content": "nice","createtime" : "2014-12-31","subCommentCount" : 0, "listOrganizationDiscussionComment" : "","connectionsMini" : {"id":1001,name:"john","image":"/web/1001.jgp"}}';
    loginInfo_arr[159]='{"knowledgeType":1,"index":0,"size":26}';
    loginInfo_arr[160]='{"organizationId" : 10,"doGetAllFields":true,"doGetFields" : {"doGetCustomerBasicInfo" :true,"doGetResource": false,"doGetFinance" : false,"doGetBalance" : false,"doGetCashFLow" : false,"doGetEarning" : false,"doGetStock" :false,"doGetHighManagement" :false,"doGetIndustry" : false,"doGetResearchReport" :false,"doGetPeer" : false,"doGetProfile" :false,"doGetAreaInfo" :false,"doGetMeetingInfo" : false}}';
    loginInfo_arr[161]='{"organizationId" : 10,"doGetAllFields":true,"doGetFields" : {"doGetCustomerBasicInfo" :true,"doGetResource": false,"doGetFinance" : false,"doGetBalance" : false,"doGetCashFLow" : false,"doGetEarning" : false,"doGetStock" :false,"doGetHighManagement" :false,"doGetIndustry" : false,"doGetResearchReport" :false,"doGetPeer" : false,"doGetProfile" :false,"doGetAreaInfo" :false,"doGetMeetingInfo" : false}}';
    loginInfo_arr[162]='{"organizationId" : 10,"doGetAllFields":true,"doGetFields" : {"doGetCustomerBasicInfo" :true,"doGetResource": false,"doGetFinance" : false,"doGetBalance" : false,"doGetCashFLow" : false,"doGetEarning" : false,"doGetStock" :false,"doGetHighManagement" :false,"doGetIndustry" : false,"doGetResearchReport" :false,"doGetPeer" : false,"doGetProfile" :false,"doGetAreaInfo" :false,"doGetMeetingInfo" : false}}';
    loginInfo_arr[163]='{"organizationId":1002,"discussionAndCommentId" : 10036,"parentDiscussionAndCommentId" : 0,"content": "nice","createtime" : "2014-12-31","subCommentCount" : 0, "listOrganizationDiscussionComment" : "","connectionsMini" : {"id":1001,name:"john","image":"/web/1001.jgp"}}';
    loginInfo_arr[164]='{"organizationId" : 10,"doGetAllFields":true,"doGetFields" : {"doGetCustomerBasicInfo" :true,"doGetResource": false,"doGetFinance" : false,"doGetBalance" : false,"doGetCashFLow" : false,"doGetEarning" : false,"doGetStock" :false,"doGetHighManagement" :false,"doGetIndustry" : false,"doGetResearchReport" :false,"doGetPeer" : false,"doGetProfile" :false,"doGetAreaInfo" :false,"doGetMeetingInfo" : false}}';
    loginInfo_arr[165]='{"organizationId" : 10,"doGetAllFields":true,"doGetFields" : {"doGetCustomerBasicInfo" :true,"doGetResource": false,"doGetFinance" : false,"doGetBalance" : false,"doGetCashFLow" : false,"doGetEarning" : false,"doGetStock" :false,"doGetHighManagement" :false,"doGetIndustry" : false,"doGetResearchReport" :false,"doGetPeer" : false,"doGetProfile" :false,"doGetAreaInfo" :false,"doGetMeetingInfo" : false}}';
    loginInfo_arr[166]='{"organizationId" : 10,"isOffline" : true, "organization" : {"areaInfo":{"GDP":"￥19500.6","airport":"首都国际机场、北京南苑机场","area":"16410.54平方公里","customerId":1003,"famousList":[""],"id":0,"mainColleges":"北京大学","mainCom":"中国石油化工集团公司","name":"北京","parentArea":"","population":"2114.8万","remark":"全国第二大城市及政治、交通和文化中心","resource":"中国政治、文化、教育和国际交流中心","shotName":"北平","train":"北京站/北京西站/北京南站/北京北站","type":1},"customer":{"area":{"address":"Mountain View","city":"Santa Clara","country":1,"county":"Menlo Park","province":"California"},"auth":0,"banner":"/web/1003.jpg","comeId":12,"customerBase":{"createById":1002,"ctime":"2014-12-28 10:20:20","id":1001,"name":"Google","nameFirst":"Google","nameIndex":"Google","nameIndexAll":"Google","utime":"2014-12-28 14:20:20"},"describe":"一家美国的跨国科技企业，业务范围涵盖互联网搜索、云计算、广告技术等领域，开发并提供大量基于互联网的产品与服务，其主要利润来自于AdWords等广告服务。 ","email":"google@gmail.com","group":{"creatorId":1002,"ctime":"2014-12-28 14:20:20","id":1001,"name":"Internet"},"industryIds":[1],"industrys":["互联网"],"labelList":["互联网"],"licenceNo":"B2-20070004","linkEmail":"","linkIdPic":"/web/1003.jpg","linkMobile":"+1 650-253-0000","linkName":"Lawrence Edward","listing":true,"phoneList":[{"creatorId":1002,"ctime":"2014-12-28 14:20:20","id":1001,"name":"+1 650-253-0000"}],"picLogo":"/web/1002.jgp","propertyList":[],"shortName":"Google","stockNum":"GOOG","taskId":"10000000000012","type":1,"userId":12000,"virtual":1},"departMentMap":{"address":"北京市朝阳区大屯路科学园南里风林西奥中心A座11层","fax":"64843955","phone":"4000-7000-11","super_":{"id":1002,"relation":"122222","relationId":1003,"type":1},"website":"www.gintong.com"},"finance":{"customerId":1002,"id":1003,"stkcd":"601099","taskId":"10000001"},"listBalance":[{"currentAssets":"$200000000","currentLiab":"$500000","date":"2015-02-10","equity":"$2300000000","fixedAssets":"$6000000000","id":20000001,"intangibleAssets":"$500000000","stkcd":"601099","totalAssets":"$2500000000","year":"2014"}],"listCashFLow":[{"date":"2015-03-02","fund":"$9500","id":200001,"invest":"$6321","ncfo":"$5200000","stkcd":"601099","year":"2015"}],"listEarning":[{"date":"2012-07-31 08:20:00","financeCosts":"$23000","id":3600,"investProfit":"$2000336","netProfit":"$6300000","operateProfit":"$650000","otherIncome":"$36000","otherProfit":"$230000","stkcd":"601099","tax":"$56002","totalProfit":"$250063","year":"2014"}],"listHigh":[{"customerId":1002,"dshList":[{"birth":"1973","eduational":"graduate degree","id":3600,"job":"Chief Executive Officer","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"sex":"男","shares":"6.67%","type":0}],"ggList":[{"birth":"1973","eduational":"graduate degree","id":3600,"job":"Chief Executive Officer","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"sex":"男","shares":"6.67%","type":0}],"ggjzList":[{"birth":"1973","eduational":"graduate degree","id":3600,"job":"Chief Executive Officer","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"sex":"男","shares":"6.67%","type":0}],"id":10032,"jshList":[{"birth":"1973","eduational":"graduate degree","id":3600,"job":"Chief Executive Officer","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"sex":"男","shares":"6.67%","type":0}],"taskId":"1002"}],"listIndustry":[{"customerId":100036,"id":1001,"industry":"互联网","taskId":"6300"}],"listPeer":[],"listProfile":[{"accountingFirm":{"address":"Prestige Sigma, No. 3, 1st FloorVittal Mallya Road (Grant Road)Corporation Division No. 61","email":"India@google.com","fax":"+91 80-5692-9100","id":1003,"leader":{"id":1002,"relation":"122222","relationId":1003,"type":1},"name":"Google Online India Private Lt","phone":"+91 80-5692-9000","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"sponsor":{"id":1002,"relation":"122222","relationId":1003,"type":1},"website":"www.google.com"},"branchList":[{"address":"Prestige Sigma, No. 3, 1st FloorVittal Mallya Road (Grant Road)Corporation Division No. 61","email":"India@google.com","fax":"+91 80-5692-9100","id":1003,"leader":{"id":1002,"relation":"122222","relationId":1003,"type":1},"name":"Google Online India Private Lt","phone":"+91 80-5692-9000","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"sponsor":{"id":1002,"relation":"122222","relationId":1003,"type":1},"website":"www.google.com"}],"bussinessList":[{"id":10056,"leader":{"id":1002,"relation":"122222","relationId":1003,"type":1},"phone":"+1 212-624-9600","remark":"互联网","type":"广告搜索"}],"customerId":10003,"customerInfo":{"address":" 美国加利福尼亚州山景城","area":{"address":"Mountain View","city":"Santa Clara","country":1,"county":"Menlo Park","province":"California"},"capital":"500000","childArea":[],"circulation":"","columnList":["科技","互联网"],"controler":"PAGE LAWRENCE","corpn":"David C. Drummond","cur":"英镑","cycle":0,"devArea":null,"expertList":[],"famous":"","fax":"63000045","history":"1996年1月，身为加州斯坦福大学理学博士生的拉里·佩奇和谢尔盖·布林在学校开始一项关于搜索的研究项目。[29]区别于传统的搜索靠搜索字眼在页面中出现次数来进行结果排序的方法，两人开发了一个对网站之间的关系做精确分析的搜寻引擎。","hostess":null,"leader":null,"number":"","parentOrg":null,"phone":" +1 650-253-0000","product":"Google搜索是Google公司重要也是最普及的一项功能，是多个国家内使用率最高的互联网搜索引擎","profile":"是一家美国的跨国科技企业，业务范围涵盖互联网搜索、云计算、广告技术等领域，开发并提供大量基于互联网的产品与服务，[7]其主要利润来自于AdWords等广告服务","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"stkcd":"","typesList":[5],"website":"www.google.com"},"enterpriceList":[{"id":30006,"ifControl":2,"name":"google","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"stockPercent":"2%","stockQty":"50006"}],"id":42003,"lawFirm":{"address":"Prestige Sigma, No. 3, 1st FloorVittal Mallya Road (Grant Road)Corporation Division No. 61","email":"India@google.com","fax":"+91 80-5692-9100","id":1003,"leader":{"id":1002,"relation":"122222","relationId":1003,"type":1},"name":"Google Online India Private Lt","phone":"+91 80-5692-9000","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"sponsor":{"id":1002,"relation":"122222","relationId":1003,"type":1},"website":"www.google.com"},"listing":{"beginTime":"2004-08-09","price":"$85","profit":"28.11","referee":{"id":1002,"relation":"122222","relationId":1003,"type":1},"shares":"19,605,052","sponsor":"","taskId":"12300","type":"网上拍卖","underwriter":"摩根士丹利和瑞士信贷"},"partnerList":[{"address":"Prestige Sigma, No. 3, 1st FloorVittal Mallya Road (Grant Road)Corporation Division No. 61","companyJob":"CTO","email":"china@google.com","expertise":"IT","id":10005,"name":{"id":1002,"relation":"122222","relationId":1003,"type":1},"percent":"20%"}],"practice":{"remark":"公司概况","taskId":"1002"},"productList":[{"funds":"$10005","id":1005,"limit":"2012-07-31 08:20:00","name":"product","profit":"5.5","startTime":"2012-07-31 08:20:00","status":0,"taskId":"10002"}],"publication":{"remark":"公司概况","taskId":"1002"},"spnsorBankList":[{"address":"Prestige Sigma, No. 3, 1st FloorVittal Mallya Road (Grant Road)Corporation Division No. 61","email":"India@google.com","fax":"+91 80-5692-9100","id":1003,"leader":{"id":1002,"relation":"122222","relationId":1003,"type":1},"name":"Google Online India Private Lt","phone":"+91 80-5692-9000","relation":{"id":1002,"relation":"122222","relationId":1003,"type":1},"sponsor":{"id":1002,"relation":"122222","relationId":1003,"type":1},"website":"www.google.com"}],"sponsorBranchList":[],"sponsorCustomer":{"remark":"公司概况","taskId":"1002"},"taskId":"3002","teamList":[{"address":"Prestige Sigma, No. 3, 1st FloorVittal Mallya Road (Grant Road)Corporation Division No. 61","companyJob":"CTO","email":"china@google.com","expertise":"IT","id":1006,"name":{"id":1002,"relation":"122222","relationId":1003,"type":1},"percent":"20%"}]}],"listReport":[{"customerId":1003,"id":1002,"taskId":"1003"}],"listStock":[{"cShareholder":"BRIN SERGEY MIKHAYLOVICH","cStockPercent":"6.54%","customerId":10066,"fShareholder":"BRIN SERGEY MIKHAYLOVICH","fStockPercent":"6.54%","id":2004,"rSharedholder":"BRIN SERGEY MIKHAYLOVICH","rStockPercent":"6.54%","taskId":"1004"},{"cShareholder":"","cStockPercent":"","customerId":0,"fShareholder":"","fStockPercent":"","id":0,"rSharedholder":"","rStockPercent":"","taskId":""}],"meetingInfo":{"customerId":42011,"id":3600,"meetingList":[{"area":{"address":"Mountain View","city":"Santa Clara","country":1,"county":"Menlo Park","province":"California"},"content":"介绍最新的数据库动态","createrId":40200,"id":5600,"list":[{"id":1002,"relation":"122222","relationId":1003,"type":1}],"notify":true,"repead":true,"taskId":"2506","time":"2012-07-31 08:20:00","title":"中国数据库大会"}]},"resource":{"expertDemandList":[{"address":{"address":"北京","areaType":0,"areaTypeName":"1","cityName":"朝阳","countyName":"","createTime":"2014-12-28 14:20:20","createUserId":1001,"createUserName":"google","id":1002,"parentType":1,"postalCode":"100010","stateName":"北京","typeTag":{"code":"1001","createTime":"","createUserId":0,"createUserName":"","id":1003,"name":"","type":0,"updateTime":"","updateUserId":0,"updateUserName":""},"updateTime":"2014-12-28 14:20:20","updateUserId":1002,"updateUserName":""},"createTime":"","createUserId":0,"createUserName":"","id":1001,"industryIds":"12","industryNames":"互联网","otherInformation":"附加信息","parentType":1,"personalLineList":[],"typeIds":"256","typeNames":"type","updateTime":"","updateUserId":0,"updateUserName":""}],"expertIdentityDemandList":[{"address":{"address":"北京","areaType":0,"areaTypeName":"1","cityName":"朝阳","countyName":"","createTime":"2014-12-28 14:20:20","createUserId":1001,"createUserName":"google","id":1002,"parentType":1,"postalCode":"100010","stateName":"北京","typeTag":{"code":"1001","createTime":"","createUserId":0,"createUserName":"","id":1003,"name":"","type":0,"updateTime":"","updateUserId":0,"updateUserName":""},"updateTime":"2014-12-28 14:20:20","updateUserId":1002,"updateUserName":""},"createTime":"","createUserId":0,"createUserName":"","id":1001,"industryIds":"12","industryNames":"互联网","otherInformation":"附加信息","parentType":1,"personalLineList":[],"typeIds":"256","typeNames":"type","updateTime":"","updateUserId":0,"updateUserName":""}],"financialDemandList":[{"address":{"address":"北京","areaType":0,"areaTypeName":"1","cityName":"朝阳","countyName":"","createTime":"2014-12-28 14:20:20","createUserId":1001,"createUserName":"google","id":1002,"parentType":1,"postalCode":"100010","stateName":"北京","typeTag":{"code":"1001","createTime":"","createUserId":0,"createUserName":"","id":1003,"name":"","type":0,"updateTime":"","updateUserId":0,"updateUserName":""},"updateTime":"2014-12-28 14:20:20","updateUserId":1002,"updateUserName":""},"createTime":"","createUserId":0,"createUserName":"","id":1001,"industryIds":"12","industryNames":"互联网","otherInformation":"附加信息","parentType":1,"personalLineList":[],"typeIds":"256","typeNames":"type","updateTime":"","updateUserId":0,"updateUserName":""}],"investmentDemandList":[{"address":{"address":"北京","areaType":0,"areaTypeName":"1","cityName":"朝阳","countyName":"","createTime":"2014-12-28 14:20:20","createUserId":1001,"createUserName":"google","id":1002,"parentType":1,"postalCode":"100010","stateName":"北京","typeTag":{"code":"1001","createTime":"","createUserId":0,"createUserName":"","id":1003,"name":"","type":0,"updateTime":"","updateUserId":0,"updateUserName":""},"updateTime":"2014-12-28 14:20:20","updateUserId":1002,"updateUserName":""},"createTime":"","createUserId":0,"createUserName":"","id":1001,"industryIds":"12","industryNames":"互联网","otherInformation":"附加信息","parentType":1,"personalLineList":[],"typeIds":"256","typeNames":"type","updateTime":"","updateUserId":0,"updateUserName":""}],"remark":"financing","taskId":"1002"}}}';
    loginInfo_arr[167]='{"organizationId" : 10,"doGetAllFields":true,"doGetFields" : {"doGetCustomerBasicInfo" :true,"doGetResource": false,"doGetFinance" : false,"doGetBalance" : false,"doGetCashFLow" : false,"doGetEarning" : false,"doGetStock" :false,"doGetHighManagement" :false,"doGetIndustry" : false,"doGetResearchReport" :false,"doGetPeer" : false,"doGetProfile" :false,"doGetAreaInfo" :false,"doGetMeetingInfo" : false}}';
    loginInfo_arr[168]='{"organizationId" : 10,"doGetAllFields":true,"doGetFields" : {"doGetCustomerBasicInfo" :true,"doGetResource": false,"doGetFinance" : false,"doGetBalance" : false,"doGetCashFLow" : false,"doGetEarning" : false,"doGetStock" :false,"doGetHighManagement" :false,"doGetIndustry" : false,"doGetResearchReport" :false,"doGetPeer" : false,"doGetProfile" :false,"doGetAreaInfo" :false,"doGetMeetingInfo" : false}}';
    loginInfo_arr[169]='{"organizationId" : 10,"doGetAllFields":true,"doGetFields" : {"doGetCustomerBasicInfo" :true,"doGetResource": false,"doGetFinance" : false,"doGetBalance" : false,"doGetCashFLow" : false,"doGetEarning" : false,"doGetStock" :false,"doGetHighManagement" :false,"doGetIndustry" : false,"doGetResearchReport" :false,"doGetPeer" : false,"doGetProfile" :false,"doGetAreaInfo" :false,"doGetMeetingInfo" : false}}';
    loginInfo_arr[170]='{"id" : 1001, "time" : "2014-13-31 15:00:00","repead" : false, "title" : "QCon上海2014大会" ,"content" : "QCon是由InfoQ主办的全球顶级技术盛会，每年在旧金山、上海、伦敦、北京、东京、纽约、圣保罗召开", {"area":{"address":"Mountain View","city":"Santa Clara","country":1,"county":"Menlo Park","province":"California"},"list" : "{"id":1002,"relation":"122222","relationId":1003,"type":1}","notify" : "false","taskId": "10000000000012","createrId" : "1002"}';
    loginInfo_arr[171]='{"organizationId" : 10,"doGetAllFields":true,"doGetFields" : {"doGetCustomerBasicInfo" :true,"doGetResource": false,"doGetFinance" : false,"doGetBalance" : false,"doGetCashFLow" : false,"doGetEarning" : false,"doGetStock" :false,"doGetHighManagement" :false,"doGetIndustry" : false,"doGetResearchReport" :false,"doGetPeer" : false,"doGetProfile" :false,"doGetAreaInfo" :false,"doGetMeetingInfo" : false}}';
    loginInfo_arr[172]=
            loginInfo_arr[173]=
                    loginInfo_arr[174]='{"userId":1002}';
    loginInfo_arr[175]='{"userId":1002,"isSelf":true}';
    loginInfo_arr[176]='{"userId":1002,"comment":"测试评价"}';
    loginInfo_arr[177]='{"id":1002,"feedback":true}';
    loginInfo_arr[178]='{"id":1002}';
    loginInfo_arr[179]='{"userId":1002}';
    loginInfo_arr[180]='{"oldPassword":"111111","newPassword":"666666"}';
    loginInfo_arr[181]='{}';
    loginInfo_arr[182]='{"index":1,"size":10}';
    loginInfo_arr[183]='{"listUserId":["2","4","5"],"type":"1"}';
    loginInfo_arr[185]='{"type":"1","sign":"1"}';
    loginInfo_arr[186]='{"index":1,"size":10}';
    loginInfo_arr[187]='{"image":"/src/user/1","shortName":"张三","listCareIndustryIds":["1","2","3"],"listCareIndustryNames":["能源","太阳能","热能"]}';
    loginInfo_arr[188]='{"listCareIndustryIds":["1","2","3"],"listCareIndustryNames":["能源","太阳能","热能"]}';


    loginInfo_arr[191]='{"userId":10040,modelType:"1","index":0,"size":20,"type":10}';
    loginInfo_arr[192]='{"newsId":142122189885200001,"size":20,"index":0}';
    loginInfo_arr[193]='{"newsId":142114624716000001,"content":"what a fucking day!!"}';
    loginInfo_arr[194]='{"forwardDynamicNews":{"type":"41","lowType":"0","title":"张岱邀请您参会","content":"谢谢参加","targetId":"1046","imgPath":"动态源图片地址","listReceiverId":["4","2","7","5","49"],"forwardingContent":"欢迎大家参加"}}';

    loginInfo_arr[200]='{"userId":1}';
    loginInfo_arr[201]="{'userId':1,'friendId':2}";
    loginInfo_arr[202]="{}";
    loginInfo_arr[203]="{}";
    loginInfo_arr[204]='{"listPhoneBookItem":[{"firstName":"张","lastName":"飞","listMobilePhone":["13011111111"],"listEmail":["111@126.com","few@163.com"]}'
            +',{"firstName":"张","lastName":"乐","listMobilePhone":["13426164936"],"listEmail":["email"]}'
            +']}';
    loginInfo_arr[205]="{'listMobile':['18211081791','15000000000']}";
    loginInfo_arr[206]="{'listId':[2,3]}";
    loginInfo_arr[207]="{'demandType':0,'index':1,'size':10}";
    loginInfo_arr[208]="{'id':1}";
    loginInfo_arr[209]="{'demandId':100,'visable':0,'content':'随便评论评论'}";
    loginInfo_arr[210]="{'demandId':40,'creatorId':8734,'index':3,'page':5}";
    loginInfo_arr[211]="{'type':1}";
    loginInfo_arr[212]='{"demand":{"id":null,"title":{"value":"这是标题。。。","isVisable":false},"note":{"value":"这是需求说明信息","isVisable":null,"taskId":"010101010"},"type":{"value":null,"isVisable":null,"list":[{"id":2,"name":"类型内容222"},{"id":2,"name":"类型内容222"}]},"industry":{"value":null,"isVisable":null,"list":[{"id":2,"name":"行业名称222"},{"id":2,"name":"行业名称222"}]},"area":{"value":null,"isVisable":null,"list":[{"id":2,"name":"ddd"},{"id":2,"name":"ddd"}]},"amount":{"value":null,"isVisable":true,"beginAmount":1000,"endAmount":2000,"unitId":111},"contact":{"value":"13111111111","isVisable":false},"phone":{"value":"1311111111","isVisable":true},"createrId":1000000,"createrName":"haiyan","createTime":null,"demandType":1,"tags":"标签1,标签2,标签3","commentCount":null,"customList":null,"permIds":{"dule":false,"xiaoles":[],"zhongles":[{"id":13556,"name":"张斌"},{"id":12454,"name":"林美霞"},{"id":13835,"name":"张桂珍"}],"dales":[{"id":13414,"name":"股市水晶球"},{"id":10089,"name":"杨楠"},{"id":13247,"name":"曾添_dataplayer"}]},"status":null},"vCode":"123"}';
    loginInfo_arr[213]='{"demandTemplate":{"id":null,"title":"标题","name":"名称"}}';

    loginInfo_arr[216]="{'type':'hy',id:''}";
    loginInfo_arr[217]="{'demandId':799}";
    loginInfo_arr[230]="{'industry': '2','type':'','area': '','beginAmount':'1000','endAmount':'1800','demandType':'1','index':'0','size':'20'}";
    loginInfo_arr[231]="{'demandId':835}";
    loginInfo_arr[232]="{'demandId':835}";
    loginInfo_arr[233]="{'demandId':835}";
    loginInfo_arr[234]="{ 'content':'举报内容','reason':'举报理由','contact':'18701682222','demandId':'835'}";
    loginInfo_arr[235]="{'demandId':835}";
    //风行添加begin
    loginInfo_arr[407]='{"people":{"modelType":0,"position":"333","peopleNameList":[{"firstname":"qwe","lastname":"123","parentType":0}],"company":"123","fromUserId":0,"telephone":"123","updateTime":0,"locationCity":"?","regionId":0,"peopleType":1,"gender":1,"email":"123","careerId":0,"typeId":0,"createTime":0,"createUserId":0,"address":"","taskId":0},"tid":[1,2,3]}';
    loginInfo_arr[408]='{"id":3,"personType":2}';
    loginInfo_arr[409]='{"id":20,"personType":2}';
    loginInfo_arr[410]='{"cid":1,"tid":1,"type":1,"fromTime":"2015-1-1 00:00:00"}';
    loginInfo_arr[411]='{"personId":3}';
    loginInfo_arr[412]='{"typeId":1,"regionId":1,"careerId":1,"index":1,"size":10}';
    loginInfo_arr[413]='{"basePeopleId":4,"personType":2}';
    loginInfo_arr[414]='{"otherPeopleId":85,"people":{"email":"44455@qq.com","id":83}}';
    loginInfo_arr[415]='{"meetDate":"2016-7-06 15:40:00","meetendDate":"2016-7-06 15:40:00","title":"今天下午去砍柴","address":"西山树林","remark":"今天下午和别人一起去砍柴，你们要一起吗","creatorTagId":1111,"creatorTagName":"commander","url":"lianjie","creatortype":1,"relations":[{"type":"p","isOnline":0,"picUrl":"连接","relateId":3333,"label":"nihaoa buyaoshanghaiwo","title":"不要","userOrType":"1"},{"type":"o","isOnline":0,"picUrl":"连接","relateId":3333,"label":"nihaoa buyaoshanghaiwo","title":"不要","userOrType":"1"},{"type":"k","isOnline":0,"picUrl":"连接","relateId":3333,"label":"nihaoa buyaoshanghaiwo","title":"不要","userOrType":"1"},{"type":"r","isOnline":0,"picUrl":"连接","relateId":3333,"label":"nihaoa buyaoshanghaiwo","title":"不要","userOrType":"1"},]}'
    loginInfo_arr[416]='{"creatortype":2,"creatorTagId":1213,"currentPage":1,"pageSize":5}';
    loginInfo_arr[417]='{"id":109666}';
    loginInfo_arr[418]='{"id":109666}';
    loginInfo_arr[419]='{"name":"你好"}';
    loginInfo_arr[420]='{"id":171}';
    loginInfo_arr[421]='{"id":185}';
    loginInfo_arr[422]='{"personId":18}';
    loginInfo_arr[423]='{"personId":18}';
    loginInfo_arr[424]='{"type":"1"}';
    loginInfo_arr[425]='{"userId":1,"isSelf":false}';
    loginInfo_arr[426]='{"comment":"nihoa","userId":1}';
    loginInfo_arr[427]='{"id":134,"feedback":false}';
    loginInfo_arr[428]='{"id":"134"}';
    loginInfo_arr[429]='{"userId":1}';
    loginInfo_arr[430]='{"id":109666,"meetDate":"meetDate","meetendDate":"meetendDate","title":"nihao","address":"beijingshihaidianqu","remark":"jintianyaoyaoxiuxinl miangtianzhunbeiquganshenm","creatortype":"2","tags":[{"tagId":"TagId","tagName":"TagName","type":"1"}],"associateList":[{"assoc_desc":"会议主持人","assoc_type_id":"1","associd":1,"assoc_title":"buzuowei"},{"assoc_desc":"会议主持人","assoc_type_id":"2","associd":1,"assoc_title":"buzuowei"},{"assoc_desc":"会议主持人","assoc_type_id":"3","associd":1,"assoc_title":"buzuowei"},{"assoc_desc":"会议主持人","assoc_type_id":"4","associd":1,"assoc_title":"buzuowei"}]}';
    loginInfo_arr[431]='{"tagName":"tg1,tg2,tg3"}';
    loginInfo_arr[432]='{"id":4}';
    loginInfo_arr[433]='{"index":0,"size":10,"type":1}';
    loginInfo_arr['personReport']='{"personId":"1","userId":"2","content":"举报类型(违法、欺诈等汉字，逗号分隔)", "reason":"举报理由"}';
    //风行添加end
    loginInfo_arr['myCountList']="{}";


    loginInfo_arr[210]='{"id":48,"investmentdemandList":[{"address":null,"id":0,"industryIds":[],"industryNames":[],"parentType":1,"personalLineList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"typeIds":[],"typeNames":[]}]}';
    loginInfo_arr[211]='{"orgType":"1","isListing":"1","stockNum":"12345678","name":"北京金桐网","shotName":"金桐","licensePic":"/pic/url","linkIdPic":"/pic/url2","linkName":{"relation":"张三","type":"3"},"linkMobile":"18500000000"}';
    loginInfo_arr[212]='{}';
    loginInfo_arr[213]='{"templateId":1,"orgId":57}';
    loginInfo_arr[214]='{"columnIds":[2,3,4],"orgId":57}';
    loginInfo_arr[215]='{"id":57,"area":null,"auth":-1,"banner":"","comeId":"","createById":1,"ctime":"2015-03-18 11:24:28","discribe":"","email":"","fileIndex":[],"group":[],"industryIds":[],"industrys":[],"isListing":"1","lableList":[],"licenseNo":"","licensePic":"/pic/url","linkEmail":"","linkIdPic":"/pic/url2","linkMobile":"18500000000","linkName":{"id":0,"relation":"张三","relationId":"","type":3},"name":"北京金桐网","nameFirst":"B","nameIndex":"bjjtw","nameIndexAll":"beijingjintongwang","phoneList":[],"picLogo":"","profile":{"accountingFirm":null,"braceIndustry":null,"branchList":[{"address":"","email":"wfl@gintong.com","fax":"","id":"","leader":null,"phone":"ddfasfafafaf","propertyList":[],"relation":{"id":0,"relation":"关联名称","relationId":"","type":3},"sponsor":null,"website":""}],"businessList":[],"columnOrder":null,"enterpriseList":[],"fileIndex":[],"id":57,"incomeFeature":null,"info":null,"lawFirm":null,"linkMans":[],"listing":null,"partnerList":[],"personalPlateMap":null,"practiceList":[],"prodectList":[],"publication":null,"selectCoumnIds":[2,3,4,28],"sponsorBank":null,"sponsorBranchList":[],"sponsorCustomerList":[],"taskId":"","teamBankList":[],"teamList":[]},"propertyList":[],"shotName":"金桐"}';

    loginInfo_arr[217]='{"gczlList":[{"birth":"","eduational":"大学","id":"","job":"开发主管","relation":{"id":0,"relation":"马云","relationId":"","type":0},"sex":"","shares":"","type":"高层信息"}],"id":57,"jshList":[],"personalLineList":[{"content":"自定义内容","name":"自定义名称","type":"1"}]}';

    loginInfo_arr[220]='{"tagId":"","groupId":"0","index":"0","size":"20"}';
    loginInfo_arr[221]='{"area":"北京","type":"1","industry":"金融","index":"0","size":"20"}';

    loginInfo_arr[222]='{"customerId":"2"}';
    loginInfo_arr[223]='{"customerId":"2"}';
    loginInfo_arr[224]='{}';
    loginInfo_arr[225]='{}';
    loginInfo_arr[226]='{ "orgId":"1","index":"0","size":"20"}';


    loginInfo_arr[230]='{"customerId":"1"}';
    loginInfo_arr[231]='{"customerId":"1"}';


    loginInfo_arr[210]='{"id":48,"investmentdemandList":[{"address":null,"id":0,"industryIds":[],"industryNames":[],"parentType":1,"personalLineList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"typeIds":[],"typeNames":[]}]}';
    loginInfo_arr[211]='{"orgType":"1","isListing":"1","stockNum":"12345678","name":"北京金桐网","shotName":"金桐","licensePic":"/pic/url","linkIdPic":"/pic/url2","linkName":{"relation":"张三","type":"3"},"linkMobile":"18500000000"}';
    loginInfo_arr[212]='{}';
    loginInfo_arr[213]='{"templateId":1,"orgId":57}';
    loginInfo_arr[214]='{"columnIds":[2,3,4],"orgId":57}';
    loginInfo_arr[215]='{"id":57,"area":null,"banner":"","comeId":"","createById":1,"ctime":"2015-03-18 11:24:28","discribe":"","email":"","fileIndex":[],"group":[],"industryIds":[],"industrys":[],"isListing":"1","lableList":[],"licenseNo":"","licensePic":"/pic/url","linkEmail":"","linkIdPic":"/pic/url2","linkMobile":"18500000000","linkName":{"id":0,"relation":"张三","relationId":"","type":3},"name":"北京金桐网","nameFirst":"B","nameIndex":"bjjtw","nameIndexAll":"beijingjintongwang","phoneList":[],"picLogo":"","profile":{"accountingFirm":null,"braceIndustry":null,"branchList":[{"address":"","email":"wfl@gintong.com","fax":"","id":"","leader":null,"phone":"ddfasfafafaf","propertyList":[],"relation":{"id":0,"relation":"关联名称","relationId":"","type":3},"sponsor":null,"website":""}],"businessList":[],"columnOrder":null,"enterpriseList":[],"fileIndex":[],"id":57,"incomeFeature":null,"info":null,"lawFirm":null,"linkMans":[],"listing":null,"partnerList":[],"personalPlateMap":null,"practiceList":[],"prodectList":[],"publication":null,"selectCoumnIds":[2,3,4,28],"sponsorBank":null,"sponsorBranchList":[],"sponsorCustomerList":[],"taskId":"","teamBankList":[],"teamList":[]},"propertyList":[],"shotName":"金桐"}';
    loginInfo_arr[216]='{"id":57,"area":null,"banner":"","comeId":"","createById":1,"ctime":"2015-03-18 11:24:28","discribe":"","email":"","fileIndex":[],"group":[],"industryIds":[],"industrys":[],"isListing":"1","lableList":[],"licenseNo":"","licensePic":"/pic/url","linkEmail":"","linkIdPic":"/pic/url2","linkMobile":"18500000000","linkName":{"id":0,"relation":"张三","relationId":"","type":3},"name":"北京金桐网","nameFirst":"B","nameIndex":"bjjtw","nameIndexAll":"beijingjintongwang","phoneList":[],"picLogo":"","profile":{"accountingFirm":null,"braceIndustry":null,"branchList":[{"address":"","email":"wfl@gintong.com","fax":"","id":"","leader":null,"phone":"ddfasfafafaf","propertyList":[],"relation":{"id":0,"relation":"关联名称","relationId":"","type":3},"sponsor":null,"website":""}],"businessList":[],"columnOrder":null,"enterpriseList":[],"fileIndex":[],"id":57,"incomeFeature":null,"info":null,"lawFirm":null,"linkMans":[],"listing":null,"partnerList":[],"personalPlateMap":null,"practiceList":[],"prodectList":[],"publication":null,"selectCoumnIds":[2,3,4,28],"sponsorBank":null,"sponsorBranchList":[],"sponsorCustomerList":[],"taskId":"","teamBankList":[],"teamList":[]},"propertyList":[],"shotName":"金桐"}';
    loginInfo_arr[217]='{"gczlList":[{"birth":"","eduational":"大学","id":"","job":"开发主管","relation":{"id":0,"relation":"马云","relationId":"","type":0},"sex":"","shares":"","type":"高层信息"}],"id":57,"jshList":[],"personalLineList":[{"content":"自定义内容","name":"自定义名称","type":"1"}]}';
    loginInfo_arr[218]='{"cShareholder":"wangfeiliang","cStockPercent":"60%","fShareholder":"bb","fStockPercent":"60%","id":57,"personalLineList":[{"content":"自定义内容","name":"自定义名称","type":"1"}],"rShareholder":"xyb","rStockPercent":"60%"}';
    loginInfo_arr[219]='{"id":57,"tagNames":["金融","行业","巨头"],"personalLineList":[{"content":"自定义内容","name":"自定义名称","type":"1"}]}';

    loginInfo_arr[220]='{"tagId":"","groupId":"0","index":"0","size":"20"}';
    loginInfo_arr[221]='{"area":"北京","type":"1","industry":"金融","index":"0","size":"20"}';
    loginInfo_arr[222]='{"customerId":"2"}';
    loginInfo_arr[223]='{"customerId":"2"}';
    loginInfo_arr[224]='{}';
    loginInfo_arr[225]='{}';
    loginInfo_arr[226]='{ "orgId":"1","index":"0","size":"20"}';
    loginInfo_arr[230]='{"customerId":"1"}';
    loginInfo_arr[231]='{"customerId":"1"}';
    loginInfo_arr[240]='{"id":57,"peerList":[{"id":0,"inc":{"id":0,"relation":"中国电力","relationId":"","type":0},"tagNames":["金融","行业","大拿"]}],"personalLineList":[{"content":"自定义内容","name":"自定义名称","type":"1"}]}';

    loginInfo_arr[312]="{'orgid':1,'commentcontent':'这个组织不错啊','anonymous':0,'type':1}";
    loginInfo_arr[313]="{'id':1}";
    loginInfo_arr[314]="{'orgid':1,'currentPage':1,'pageSize':4,'type':1}";
    loginInfo_arr[315]="{'commentid':1}";
    loginInfo_arr[316]="{'commentid':1}";
    loginInfo_arr[317]="{'commentid':1,'replycontent':'你怎么说话的呢','bereplyusername':'张三'}";
    loginInfo_arr[318]="{'id':2}";

    loginInfo_arr[310]="{'orgId':1}";
    loginInfo_arr[311]="{'orgId':1}";
    loginInfo_arr[434]="{'page':1,'rows':6,'type':4,'hot':1}";
    loginInfo_arr[435]="{'page':1,'rows':6,'targetId':1898330,'targetType':1,'scope':3}";
    loginInfo_arr[500]="{'columnid':1,'title':'测试','columnType':1,'asso':'{\"r\":[],\"p\":[],\"o\":[],\"k\":[]}','selectedIds':'{\"dule\":true,\"xiaoles\":[],\"zhongles\":[],\"dales\":[]}'}";
    loginInfo_arr[502]="{'columnType':1,'columnid':'173'}";
    loginInfo_arr[503]="{'type':0,'keyword':'','index':0,'size':20}";
    loginInfo_arr[504]="{'ids':[5738]}";
    loginInfo_arr[505]="{'page':1,'size':'6','type':1}";
    loginInfo_arr[506]="{'page':1,'size':'5','targetId':'1898330','targetType':'1','scope':'3'}";
    loginInfo_arr[507]="{\"knowledge2\":{\"id\":\"1893076\",\"type\":\"1\",\"title\":\"fdsa\",\"uid\":\"126565\",\"uname\":\"顺丰集团\",\"cid\":\"126565\",\"cname\":\"顺丰集团\",\"source\":\"\",\"s_addr\":\"\",\"cpathid\":\"资讯\",\"pic\":\"\",\"desc\":\"<p>fdsa</p>\",\"content\":\"<!DOCTYPE html><html><head><meta charset='utf-8' /><style>.gtrelated img{margin-top:10px;max-width:96%;margin-left:2%;height:auto;}.gtrelated{word-break: break-all;word-wrap: break-word; overflow-x: hidden; overflow-y:auto; } body { letter-spacing: 0.1em; line-height: 1.5em;} table{ width:100%; border-top: #bbb solid 1px;border-left: #bbb solid 1px; text-align: center;}table td{ border-right: #bbb solid 1px; border-bottom: #bbb solid 1px;} </style></head><body><div class='gtrelated'><p>fdsa</p></div></body></html>\",\"hcontent\":\"\",\"essence\":\"0\",\"createtime\":\"1442384640000\",\"modifytime\":null,\"status\":\"4\",\"report_status\":\"0\",\"listTag\":[],\"ish\":\"0\",\"knowledgeUrl\":null,\"knowledgeStatics\":{\"knowledgeId\":\"1893076\",\"commentcount\":\"0\",\"sharecount\":\"0\",\"collectioncount\":\"0\",\"clickcount\":\"0\",\"source\":\"1\",\"type\":\"1\"},\"column\":{\"id\":\"1\",\"columnname\":\"资讯\",\"userId\":null,\"parentId\":\"1\",\"createtime\":null,\"pathName\":null,\"columnLevelPath\":null,\"delStatus\":null,\"updateTime\":null,\"subscribeCount\":null,\"type\":null},\"listUserCategory\":[\"5780\"],\"isConnectionAble\":null,\"isShareAble\":null,\"isSaved\":true,\"listRelatedConnectionsNode\":[],\"listRelatedOrganizationNode\":[],\"listRelatedKnowledgeNode\":[],\"listRelatedAffairNode\":[],\"listHightPermission\":null,\"listMiddlePermission\":null,\"listLowPermission\":null,\"taskId\":\"1265652857213818933815\",\"listJtFile\":[],\"collected\":false,\"listImageUrl\":null,\"authorType\":\"2\",\"submitTime\":\"2015-09-16\",\"performTime\":\"2015-09-16\",\"postUnit\":\"\",\"titanic\":\"\",\"synonyms\":\"\",\"fileType\":null,\"tranStatus\":\"0\"},\"columnName\":\"资讯\"} ";
    loginInfo_arr[508]="{'columnid':1}";
    loginInfo_arr[509]="{'columnid':1,'page':1,'size':20}";
    loginInfo_arr[510]="{'columnid':1,'page':2,'size':20}";
    loginInfo_arr[512]="{'listKnowledgeType':['5755750','1892870'],'listKnowledgeId':['5755750','1892870'],'source':[3,3]}";
    loginInfo_arr[513]="{'kid':5755750,'type':1,'desc':'dddd','title':'测试','columnType':4}";
    loginInfo_arr[514]="{'type':1}";
    loginInfo_arr[515]="{'type':1}";
    loginInfo_arr[516]="{'type':1}";
    loginInfo_arr[517]="{'type':1,'page':1,'size':5}";
    loginInfo_arr[518]="{}";
    loginInfo_arr[527]="{'organId':112790}";
    loginInfo_arr[528]="{'type':'2','custermIds':[222,223,224]}";
    loginInfo_arr[529]="{'type':'2','custermIds':[222,223,224]}";
    loginInfo_arr[530]="{'custormId':'22','directoryIds':[222,223,224]}";
    loginInfo_arr[531]="{'custormId':'22','taglist':[222,223,224]}";
    loginInfo_arr[532]="{'type':'2','custermIds':[222,223,224]}";
    $("#joint").hide();
    $("#bigdata").hide();
    var url = "";
    switch (selectValue) {
        case '0':
            url="";
            loginInfo="";
            $("#paramsDiv").val("");
            break;
        case '1':
            url = "/login/loginConfiguration.json";
            loginInfo = '{"clientID":"131321321321","clientPassword":"","imei":"yss-3434-dsf55-22256","version":"1.6.0.0609","platform":"iPhone","model":"iPhone 3G","resolution":"480x320","systemName":"iOS","systemVersion":"1.5.7","channelID":"10086111445441","loginString":"yinxing","password":"27c878bcb21bf1b26375c059923a5d48dd3f5e75578769b52632a0faaff564d3"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '2':
            url = "/mobileApp/addRecord.json";
            loginInfo = '{"listRecord": [{"name":"记录名称","listTag": "[标签字符, 标签字符]","date":"时间"},{"name":"记录名称","listTag": "[标签字符, 标签字符]","date":"时间"}]}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '3':
            url = "/register/register.json";
            loginInfo = '{"mobile":"","email":"11@163.com","password":"MTExMTEx","code":"GT2014","userType":1,"mobileAreaCode":"+86","orgName":"银河证券"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '4':
            url = "/mobileApp/fullPersonMemberInfo.json";
            loginInfo = "{'name':'张君','jobTitle':'总经理','company':'某某企业','mobile':'16478885525', 'email':'sdflkjdsflds@163.com','image':'http://www.baidu.com/1.jpg'}";
            $("#paramsDiv").val(loginInfo);
            break;
        case '5':
            url = "/orginazition/fullContactInfo.json";
            loginInfo = '{"contactCardImage":"/upload/123.jpg","mobile":"18610555080","jtContactID":"11150"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '6':
            url = "/orginazition/fullOrganizationAuth.json";
            loginInfo = '{"logoImage":"/upload/123.jpg","fullName":"罗纳尔多","shortName":"肥罗","occImage":"/upload/123.jpg","tcImage":"/upload/123.jpg","legalPersonIDCardImage":"/upload/123.jpg","jtContactID":"11150"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '7':
            url = "/register/getVCodeForPassword.json";
            loginInfo = "{'mobile':'16478885525','type':0,'mobileAreaCode':'86'}";
            $("#paramsDiv").val(loginInfo);
            break;
        case '8':
            url = "/register/setNewPassword.json";
            loginInfo = "{'vcode':'294861','oldPassword': '','newPassword': '555555','mobile':'16478885525'}";
            $("#paramsDiv").val(loginInfo);
            break;
        case '9':
            url = "/connections/addJTContact.json";
            loginInfo = "{'JTContact':{'d':1, 'name':'名','listMobilePhone':[{'name':'哈哈','mobile':'18701682234'},{'name':'呵呵 ','mobile':'1870212334'}]}}";
            $("#paramsDiv").val(loginInfo);
            break;
        case '10':
            url = "/orginazition/addOrginazitionGuest.json";
            loginInfo = '{"orginazition": {"logo": "机构logo图片url","fullname":"username","outInvestKeyword": {},"inInvestKeyword": {},"shortName": "客户简称","guestType": "1","fromDes": "com.ginkgocap.ywxt.model.mobile.Organization","joinState": "0","isOnline": "","friendState": "0","isOffline": "","listJTFile": [{}]}}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '11':
            url = "/requirement/addRequirement.json";
            var myTrue=new Boolean(true);
            var myFalse=new Boolean(false);
            //loginInfo = '{"requirement":{"title":"一证券公司增资扩股","listJTFile":[],"name":"支松锋","userId":"13","area":"山东","publishTime":"2014-04-21 11:05:49","type":"0","moneyRange":"1800万","moneyType":"CNY","investTypeCode":["1-2-39","1-2-40"],"investTypeName":["私募股权","测试"],"trade":["化学及生物制药","中成药及植物药","化学原料药及中间体"],"tradeId":["15-565-566","15-565-567","15-565-568"]}}';
            loginInfo = '{"requirement": {"userid": "13", "listMatchRequirementMini":[],"listMatchConnectionsMini":[],"listMatchKnowledgeMini":[],"name": "支松锋","publishTime": "2014-04-21 11:05:49","title": "一证券公司增资扩股","type": "0","publishRange": "1","listConnectionsMini": [],"comment": "备注信息","listJTFile": [],"investKeyword": {"moneyType": "cur","moneyRange": "1800万","listInvestType": [{"investType": {"id": "30-33-737","title": "印度教"}},{"investType": {"id": "739-851","title": "行为金融学"}}],"listTrade": [{"trade": {"id": "15-483-486","title": "食品"}},{"trade": {"id": "15-379-381","title": "饮料"}}],"area": {"id": "","name": "山东","listArea": []}}}		}';

            $("#paramsDiv").val(loginInfo);
            break;
        case '12':
            url = "/requirement/getRequirementByID.json";
            loginInfo = '{"id":"1267"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '13':
            url = "/mobileApp/createMUC.json";
            loginInfo = '{"subject":"会议主题","orderTime":"会议召开时间，为空表示当前立刻开始","listJTContactID":"与会好友id列表","content":"会议内容","listJTFile":"会议文件列表"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '14':
            url = "/businessRequirement/addBusinessRequirement.json";
            loginInfo = '{"businessRequirement":{"area":{"id":0,"listArea":[],"name":"北京"},"content":"content","deadline":"","id":0,"keyword":"","listConnections":[{"id":0,"jtContactMini":{"company":"金桐网","friendState":1,"id":0,"isOffline":false,"isOnline":false,"lastName":"江","listOrganizationID":[],"name":"月恒","workmate":false},"organizationMini":{"financingList":[],"friendState":0,"fullname":"机构名称","guestType":0,"id":0,"investTagList":[],"joinState":0,"logo":"","online":false,"shortName":"机构"},"sourceFrom":"SourceFrom","type":"1"},{"id":0,"jtContactMini":null,"organizationMini":null,"sourceFrom":"SourceFrom","type":"2"}],"listInvestType":[{"id":"","title":"融资1"},{"id":"","title":"融资2"}],"listJTContact":[{"address":"address","comment":"comment","company":"company","fax":"","friendState":0,"fromDes":"fromDes","id":0,"inInvestKeyword":null,"isOffline":false,"isOnline":true,"lastName":"超","listBasePersonInfo":[],"listConnections":[],"listEduExperience":[],"listEmail":["123@123.com"],"listJTFile":[],"listKnowledgeMini":[],"listMobilePhone":[],"listOrganizationID":[],"listPersonInfo":[],"listRequirementMini":[],"listSns":[],"listWorkExperience":[],"name":"老江","outInvestKeyword":null,"url":"url","workmate":false}],"listJoinJTContact":[],"listTrade":[],"moneyRange":"100亿以上","moneyType":"USD","name":"1业务需求byJC","principal":"","publishTime":"","title":"1测试数据byJC","type":"0","userid":0}}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '15':
            url = "/task/addTask.json";
            loginInfo = '{"task":{"company":"","content":"content","deadline":"2014-04-30","id":0,"image":"","inInvestKeyword":null,"jobTitle":"","keyword":"keyword","listJTContact":[],"listJoinJTContact":[],"listRelationJTContact":[],"name":"name","outInvestKeyword":null,"principal":"联系人","progress":0,"publishTime":"","title":"title","userid":0}}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '16':
            url = "/connections/getConnections.json";
            loginInfo = '';
            $("#paramsDiv").val(loginInfo);
            break;
        case 'getFriends':
            url = "/connections/getFriends.json";
            loginInfo = '';
            $("#paramsDiv").val(loginInfo);
            break;
        case 'getAllRelations':
            url = "/connections/getAllRelations.json";
            loginInfo = "{'organizationFriend':true, 'personFriend':true, 'customer':false, 'person':false}";
            $("#paramsDiv").val(loginInfo);
            break;
        case '17':
            url = "/mobileApp/getListRequirementByOrganizationID.json";
            loginInfo = '{"id":"机构id"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '18':
            url = "/mobileApp/inviteJoinOrganization.json";
            loginInfo = '{"orginazitionID":"机构id","userID":"被邀请的用户id"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '19':
            url = "/friend/getWorkmate.json";
            loginInfo = '{"type":"1","listOrganiationID":["1","35","36","37"]}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '20':
            url = "/requirement/editRequirement.json";
            loginInfo = '{"requirement":{"listMatchRequirementMini":[],"id":1408,"listMatchKnowledgeMini":[{"id":0,"time":"","title":"","url":""}],"title":"here comes a requirement for you","listMatchConnectionsMini":[],"publishTime":"2014-04-25 10:58:15.0","investKeyword":{"moneyRange":"10亿以上","area":{"id":"29","listArea":[],"name":"新疆"},"listTrade":[{"id":"15-523-525-532","title":"办公设备"},{"id":"15-523-524-530","title":"机械制造"},{"id":"15-483-485-497","title":"其他酒类"}],"listInvestType":[{"id":"8-9-113","title":"私募股权"}],"moneyType":{"tag":"CNY","name":"人民币"}},"name":"设计部","userid":10278,"publishRange":0,"comment":"：","type":0,"listConnectionsMini":[]}} ';
            $("#paramsDiv").val(loginInfo);
            break;
        case '21':
            url = "/requirement/closeRequirement.json";
            loginInfo = '{"id":"1266"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '22':
            url = "/businessRequirement/editBusinessRequirement.json";
            loginInfo = '{"BusinessRequirement":{}}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '23':
            url = "/businessRequirement/closeBusinessRequirement.json";
            loginInfo = '{"businessRequirementID":""}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '24':
            url = "/task/editTask.json";
            loginInfo = '{"Task":{}}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '25':
            url = "/task/closeTask.json";
            loginInfo = '{"taskID":""}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '26':
            url = "/project/addProject.json";
            loginInfo = '{"Project":{}}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '27':
            url = "/project/editProject.json";
            loginInfo = '{"Project":{}}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '28':
            url = "/project/closeProject.json";
            loginInfo = '{"projectID":""}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '29':
            url = "/connections/upPhoneBook.json";
            loginInfo = '{"listPhoneBookItem":[{"lastName":"Jac","listPersonInfo":[],"firstName":"Mi","listMobilePhone":[{"name":"电话0","mobile":"225466886"},{"name":"电话1","mobile":"1992266688"}],"listEmail":["ghhf@hsdg.vjk"]}]}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '30':
            url = "/friend/recommend2Friend.json";
            loginInfo = '{"JTContactID":"被推荐人脉的金桐网id","destJTContactID":"接受推荐人的金桐网id"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '31':
            url = "/mobileApp/invite2Conference.json";
            loginInfo = '{"JTContactID":"好友id","listConferenceID":["会议id",""]}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '32':
            url = "/mobileApp/setJTContactAccessControl.json";
            loginInfo = '{"JTContactID":"对方的会员id","isOpenFlow":"是够让对方看到我的投融资动�?","isOpenDoc":"对方是否看到我的商业文档","isOpenJTContact":"对方是否看到我的金桐人脉"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '33':
            url = "/mobileApp/add2BlackList.json";
            loginInfo = ' {"id":"对方的会员id"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '34':
            url = "/friend/addFriend.json";
            loginInfo = "{'id':'5','userType':1}";
            $("#paramsDiv").val(loginInfo);
            break;
        case '35':
            url = "/mobileApp/sendMessage.json";
            loginInfo = '{"JTContactID":"好友id","MUCID":"会议id","text":"发送的文本内容","JTFile":{}}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '36':
            url = "/mobileApp/invite2MUC.json";
            loginInfo = '{{"JTContactID":"好友id","listMUCID":["会议id",""]}}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '37':
            url = "/mobileApp/getListMUC.json";
            loginInfo = '';
            $("#paramsDiv").val(loginInfo);
            break;
        case '38':
            url = "/connections/getNewConnections.json";
            loginInfo = '';
            $("#paramsDiv").val(loginInfo);
            break;
        case '39':
            url = "/friend/deleteFriend.json";
            loginInfo = "{'id':'5','userType':1}";
            $("#paramsDiv").val(loginInfo);
            break;
        case '40':
            url = "/mobileApp/exitOrganization.json";
            loginInfo = '{"id":"对方的会员id，JTContactId或者OrganizationID"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '41':
            url = "/mobileApp/getFlow.json";
            loginInfo = '{"index":"0","size":"20"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '42':
            url = "/search/getSearchList.json";
            loginInfo = '{"keyword":"投","index":0,"size":20,"type":9}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '43':
            url = "/requirement/getListRequirement.json";
            loginInfo = '{ "keyword":"关键字","index":"0","size":"20"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '44':
            url = "/mobileApp/getListConnections.json";
            loginInfo = '{"type":"0-全部；1-机构客户；2-人脉；3-机构；4-用户"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '45':
            url = "/register/sendValidateEmail.json";
            loginInfo = "{'email':'liuyang@gintong.com'}";
            $("#paramsDiv").val(loginInfo);
            break;
        case '46':
            url = "/friend/allowConnectionsRequest.json";
            loginInfo = '{"id":"人脉id或者机构id", "type":"0-通过加好友请求（包括个人好友和机构好友）、1-通过加入机构"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '47':
            url = "/login/userLogin.json";
            loginInfo = '{"loginString":"zsf@ginkgocap.cn","password":"27c878bcb21bf1b26375c059923a5d48dd3f5e75578769b52632a0faaff564d3"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '48':
            url = "/connections/getJTContactDetail.json";
            loginInfo = '{"jtContactID":1,"isOnline":true}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '49':
            url = "/requirement/getListRequirementByUserID.json";
            loginInfo = '{"userType":"1","userID":"13","index":"0","size":"10"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '50':
            url = "/requirement/focusRequirement.json";
            var flag=new Boolean;
            loginInfo = '{"requirementID":"1266","isFocus":'+flag+'}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '51':
            url = "/reply/getListComment.json";
            loginInfo = '{ "type":"1", "id":"15", "index":"0", "size":"20"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '52':
            url = "/reply/addComment.json";
            loginInfo = '{"type":"1-需求；2-业务需求；3-任务；4-项目；","id":"评论对象id", "content":"评论内容"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '53':
            url = "/login/userLoginOut.json";
            loginInfo = '';
            $("#paramsDiv").val(loginInfo);
            break;
        case '54':
            url = "/orginazition/getOrganizationDetail.json";
            var flag=new Boolean(true);
            loginInfo = '{"isOnline":'+flag+',"organizationID":"11141"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '55':
            url = "/connections/getListConnectionsMini.json";
            loginInfo = '{"listUserID":["2","3","5"]}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '56':
            url = "/businessRequirement/requirementToBusinessRequirement.json";
            loginInfo = '{"businessRequirement":{"area":{"id":93,"listArea":[],"name":"北京"},"content":"content","deadline":"","id":0,"keyword":"","listConnections":[{"id":0,"jtContactMini":{"company":"金桐网","friendState":1,"id":0,"isOffline":false,"isOnline":false,"lastName":"江","listOrganizationID":[],"name":"月恒","workmate":false},"organizationMini":{"financingList":[],"friendState":0,"fullname":"机构名称","guestType":0,"id":0,"investTagList":[],"joinState":0,"logo":"","online":false,"shortName":"机构"},"sourceFrom":"SourceFrom","type":"1"},{"id":0,"jtContactMini":null,"organizationMini":null,"sourceFrom":"SourceFrom","type":"2"}],"listInvestType":[{"id":"","title":"融资1"},{"id":"","title":"融资2"}],"listJTContact":[{"address":"address","comment":"comment","company":"company","fax":"","friendState":0,"fromDes":"fromDes","id":0,"inInvestKeyword":null,"isOffline":false,"isOnline":true,"lastName":"超","listBasePersonInfo":[],"listConnections":[],"listEduExperience":[],"listEmail":["123@123.com"],"listJTFile":[],"listKnowledgeMini":[],"listMobilePhone":[],"listOrganizationID":[],"listPersonInfo":[],"listRequirementMini":[],"listSns":[],"listWorkExperience":[],"name":"老江","outInvestKeyword":null,"url":"url","workmate":false}],"listJoinJTContact":[],"listTrade":[],"moneyRange":"100亿以上","moneyType":"USD","name":"1业务需求byJC","principal":"","publishTime":"","title":"1测试数据byJC","type":"0","userid":0},"id":"1256"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '57':
            url = "/project/requirementToProject.json";
            loginInfo = '{"project":{"area":{"id":0,"listArea":[],"name":"北京"},"content":"","deadline":"","id":0,"keyword":"","listConnections":[{"id":0,"jtContactMini":{"company":"金桐网","friendState":1,"id":0,"isOffline":false,"isOnline":false,"lastName":"江","listOrganizationID":[],"name":"月恒","workmate":false},"organizationMini":{"financingList":[],"friendState":0,"fullname":"机构名称","guestType":0,"id":0,"investTagList":[],"joinState":0,"logo":"","online":false,"shortName":"机构"},"sourceFrom":"SourceFrom","type":"1"},{"id":0,"jtContactMini":null,"organizationMini":null,"sourceFrom":"SourceFrom","type":"2"}],"listInvestType":[{"id":"","title":"融资1"},{"id":"","title":"融资2"}],"listJTContact":[],"listJoinJTContact":[],"listMemberConnections":[],"listTrade":"","moneyRange":"100亿以上","moneyType":"USD","name":"","principal":"","publishTime":"","title":"1测试数据byJC","type":"0","userid":0},"id":"90","type":"1"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '58':
            url = "/register/inviteJoinGinTong.json";
            loginInfo = "{'listEmail':['zhangwei@gintong.com','vvaivv@163.com']}";
            $("#paramsDiv").val(loginInfo);
            break;
        case '60':
            url = "/feed/testAddFeed.json";
            loginInfo = '';
            $("#paramsDiv").val(loginInfo);
            break;
        case '61':
            url = "/feed/testSelectFeed.json";
            loginInfo = '{"zhangwei@gintong.com","vvaivv@163.com"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '62':
            url = "/feed/getFlow.json";
            loginInfo = '{"index":0,"size":20,"type":0}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '63':
            url = "/businessRequirement/getBusinessRequirementDetailByID.json";
            loginInfo = '{"id":"100"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '64':
            url = "/project/getProjectDetailByID.json";
            loginInfo = '{"id":"100"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '65':
            url = "/affair/getMyAffair.json";
            loginInfo = '{"index":"0","size":"20"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '66':
            url = "/knowledge/testAddKnowledge.json";
            loginInfo = '{"title":"","url":""}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '67':
            url = "/knowledge/testSelectKnowledge.json";
            loginInfo = '';
            $("#paramsDiv").val(loginInfo);
            break;
        case '68':
            url = "/knowledge/testGetDetail.json";
            loginInfo = '{"id":}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '69':
            url = "/match/getMatchRequirementMini.json";
            loginInfo = '{"id":"100","type":"1"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '70':
            url = "/match/getMatchKnowledgeMini.json";
            loginInfo = '{"id":"100","type":"1"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '71':
            url = "/match/getKnowledgeDetailByID.json";
            loginInfo = '{"id":"100"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '72':
            url = "/match/getMatchConnectionsMini.json";
            loginInfo = '{"id":"100"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '99':
            url = "/mobileApp/getUserByName.json";
            loginInfo = '{"keyword":"","index":0,"size":20}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '100':
            url = "/affair/getListAffair.json";
            loginInfo = '{"keyword":"","type":"1","index":0,"size":20}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '98':
            url = "/connections/delJtContact.json";
            loginInfo = '{"id":}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '97':
            url = "/connections/testGetAllReq.json";
            loginInfo = '{"id":}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '101':
            url = "/project/operateProject.json";
            loginInfo = '{"operateType":"2-批准立项 3-启动立项 4-取消立项 5-申请结项 6-批准结项 7-终止项目 8-驳回结项","id":""}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '102':
            url = "/orginazition/removeCustomer.json";
            loginInfo = '{"id":"613"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '103':
            url = "/friend/groupAndCustomer.json";
            loginInfo = '';
            $("#paramsDiv").val(loginInfo);
            break;
        case '104':
            url = "/friend/groupAndPeople.json";
            loginInfo = '';
            $("#paramsDiv").val(loginInfo);
            break;
        case '105':
            url = "/friend/relatedPeopleAndCustomer.json";
            loginInfo = '';
            $("#paramsDiv").val(loginInfo);
            break;
        case '106':
            url = "/feed/getNewDynamicCount.json";
            loginInfo = '';
            $("#paramsDiv").val(loginInfo);
            break;
        case '107':
            url = "/feed/deleteDynamic.json";
            loginInfo = '';
            $("#paramsDiv").val(loginInfo);
            break;
        case '108':
            url = "/knowledge/getMyKnowledge.json";
            loginInfo = '{"index":0,"size":20}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '109':
            url = "/knowledge/addMyKnowledge.json";
            loginInfo = '{"url":"www.baidu.com","title":"呵呵呵呵"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '110':
            url = "/connections/initAllNewconnection.json";
            loginInfo = '';
            $("#paramsDiv").val(loginInfo);
            break;
        case '111':
            url = "/knowledge/addArticle.json";
            loginInfo = '{"article":{"content":"111","uid":"1","columnIds":"29,30","title":"6月9号测试","keywords":"","name":"银杏","userName":"yinxing","deptId":"1","incId":"10001"}}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '112':
            url = "/connections/getJTContactTemplet.json";
            loginInfo = '';
            $("#paramsDiv").val(loginInfo);
            break;
        case '113':
            url = "/connections/getActionList.json";
            loginInfo = '{"id": 6}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '114':
            url = "/connections/getPeopleRelatedResources.json";
            loginInfo = '{"id": 14359,"forwhat":"user","range":"me"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '115':
            url = "/connections/getVisible.json";
            loginInfo = '';
            $("#paramsDiv").val(loginInfo);
            break;

        //云知识版开始
        //千万不要手贱将url前方的"/"去掉
        case '116':
            url = "/webknowledge/getKnowledgeByColumnAndSource.json";
            loginInfo = '{"columnId":1,"index":"1","size":"20"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '117':
            url = "/knowledge/columnManager/getSubscribedColumnByUserId.json";
            loginInfo = '{"userId":1}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '118':
            url = "/knowledge/columnManager/getColumnByUserId.json";
            loginInfo = '{"userId":14359}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '119':
            url = "/knowledge/columnManager/editSubscribedColumn.json";
            loginInfo = '{"type":0,"columnId":1}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '120':
            url = "/knowledge/createKnowledge.json";
            loginInfo = '{"knowledge2":{"id": "id","title": "标题","uid": "作者","uname": "作者名称","cid": "创建人id","cname": "创建人名称","source": "来源","s_addr": "来源地址","cpathid": "栏目路径","pic": "封面地址","desc": "描述","content": "原内容","hcontent": "高亮处理后的内容","essence": "是否加精","createtime": "创建时间","modifytime": "最后修改时间","status": "1","report_status": "1","taskid": "附件ID","tag": "标签","ish": "0","knowledgeStatics": {"knowledgeId": "知识id","commentcount": "45","sharecount": "56","collectioncount": "65","clickcount": "35","title": "标题","source": "知识来源","type": "0"},"column":{"id": "栏目id","columnname": "栏目名称","userId": "用户Id","parentId": "父级id","createtime": "创建时间","pathName": "路径名称","columnLevelPath": "分类层级路径","delStatus": "删除状态","updateTime": "更新时间","type": "1","listSubColumn": [1,2,3,4]} ,"userCategory":{"id": "23","categoryname": "目录名称","sortid": "路径Id","createtime": "创建时间","pathName": "路径名称","parentId": "父级id","listSubUserCategory":[1,2,3,4,5],"level":"3"} ,"isConnectionAble":"当前用户对接权限" ,"isShareAble":"当前用户分享权限"}}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '121':
            url = "/knowledge/getUserCategory.json";
            loginInfo = '{"userId":14359}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '122':
            url = "/knowledge/addUserCategory.json";
            loginInfo = '{"parentId":154,"categoryName":"目录名"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '123':
            url = "/knowledge/deleteUserCategory.json";
            loginInfo = '{"categoryId":"目录id"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '124':
            url = "/knowledge/getKnowledgeComment.json";
            loginInfo = '{"knowledgeId":48416,"parentId": 0,"index":0,"size":20}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '125':
            url = "/knowledge/addKnowledgeComment.json";
            loginInfo = '{"knowledgeId":48416, "parentId":501,"comment":"评论内容", "index": 0, "size": 20}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '126':
            url = "/knowledge/getKnowledgeByUserCategoryAndKeyword.json";
            loginInfo = '{"userId":1,"categoryId":1,"keyword":"金桐","index":"0","size":"20"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '127':
            $("#bigdata").show();
            url = "/knowledge/getKnowledgeByTagAndKeyword.json";
            loginInfo = '{"userId":1,"tag":"金桐","keyword":"金桐","index":"0","size":"20"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '128':
            $("#bigdata").show();
            url = "/knowledge/getKnowledgeByTypeAndKeyword.json";
            loginInfo = '{"userId":1,"type":-1,"keyword":"","index":"0","size":"20"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '129':
            url = "/knowledge/getKnowledgeTagList.json";
            loginInfo = '{"userId": "1","listTag": "标签1;标签2"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '130':
            url = "/knowledge/getKnowledgeTagList.json";
            loginInfo = '{"userId":"1"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '131':
            url = "/knowledge/editKnowledgeTagById.json";
            loginInfo = '{"userId":"1","listKnowledgeId":"1/2/3/4/5","listTag":"q/w/e/r/t/y"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '132':
            url = "/knowledge/deleteKnowledgeById.json";
            loginInfo = '{"userId":"1","listKnowledgeId":"2/3/4/5"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '133':
            url = "/knowledge/getKnowledgeRelatedResources.json";
            loginInfo = '{"keyword": "","type":4}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '134':
            $("#joint").show();
            url = "/knowledge/getJointResources.json";
            loginInfo = '{"id":585,"type":1,"range":3,"segment":1}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '135':
            url = "/knowledge/collectKnowledge.json";
            loginInfo = '{"id":1294,"type":1}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '136':
            url = "/knowledge/columnManager/updateSubscribedColumn.json";
            loginInfo = '{"listColumnId": [12,32,56,76]}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '137':
            $("#bigdata").show();
            url = "/knowledge/getKnowledgByKeyword.json";
            loginInfo = '{"userId":10132,"keyword":"","index":"1", "size":"10"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '138':
            url = "/knowledge/getKnowledgeDetails.json";
            loginInfo = '{"knowledgeId": 1,"knowledgeType":1}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '139':
            url = "/knowledge/correctJointResult.json";
            loginInfo = '{"listJointResourceColumn":[{"type":1,"column":"tt","listItemId":[214,22,6565],"listItemType":[11,555,333]},{"type":1,"column":"tt","listItemId":[11,555,333],"listItemType":[214,22,6565]}],"targetType":1,"targetId":2}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '140':
            url = "/knowledge/editUserCategory.json";
            loginInfo = '{"categoryId":141,"name":"我的观点子集目录"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '141':
            url = "/knowledge/fetchExternalKnowledgeUrl.json";
            loginInfo = '{"externalUrl":"http://house.qq.com/a/20140815/010886.htm","isCreate":true}';
            $("#paramsDiv").val(loginInfo);
            break;
        //云知识版结束

        case '142':
            break;
        case '143':
            url = arr_url[143];
            $("#paramsDiv").val(loginInfo_arr[143]);
            break;
        case '144':
            url = arr_url[144];
            $("#paramsDiv").val(loginInfo_arr[144]);
            break;
        case '145':
            url = arr_url[145];
            $("#paramsDiv").val(loginInfo_arr[145]);
            break;
        case '146':
            url = arr_url[146];
            $("#paramsDiv").val(loginInfo_arr[146]);
            break;
        case '147':
            url = arr_url[147];
            $("#paramsDiv").val(loginInfo_arr[147]);
            break;
        case '148':
            url = arr_url[148];
            $("#paramsDiv").val(loginInfo_arr[148]);
            break;
// 	case '149':
// 		url = arr_url[149];
// 		$("#paramsDiv").val(loginInfo_arr[149]);
// 		break;
        case '149':
            url = arr_url[149];
            $("#paramsDiv").val(loginInfo_arr[149]);
            break;
        case '150':
            url = arr_url[150];
            $("#paramsDiv").val(loginInfo_arr[150]);
            break;
        case '151':
            url = arr_url[151];
            $("#paramsDiv").val(loginInfo_arr[151]);
            break;
        case '152':
            url = arr_url[152];
            $("#paramsDiv").val(loginInfo_arr[152]);
            break;
        case '153':
            url = arr_url[153];
            $("#paramsDiv").val(loginInfo_arr[153]);
            break;
        case '154':
            url = arr_url[154];
            $("#paramsDiv").val(loginInfo_arr[154]);
            break;
        case '155':
            url = arr_url[155];
            $("#paramsDiv").val(loginInfo_arr[155]);
            break;
        case '156':
            url = arr_url[156];
            $("#paramsDiv").val(loginInfo_arr[156]);
            break;
        case '157':
            url = arr_url[157];
            $("#paramsDiv").val(loginInfo_arr[157]);
            break;
        case '158':
            url = arr_url[158];
            $("#paramsDiv").val(loginInfo_arr[158]);
            break;
        case '159':
            url = arr_url[159];
            $("#paramsDiv").val(loginInfo_arr[159]);
            break;
        case '160':
            url = arr_url[160];
            $("#paramsDiv").val(loginInfo_arr[160]);
            break;
        case '161':
            url = arr_url[161];
            $("#paramsDiv").val(loginInfo_arr[161]);
            break;
        case '162':
            url = arr_url[162];
            $("#paramsDiv").val(loginInfo_arr[162]);
            break;
        case '163':
            url = arr_url[163];
            $("#paramsDiv").val(loginInfo_arr[163]);
            break;
        case '164':
            url = arr_url[164];
            $("#paramsDiv").val(loginInfo_arr[164]);
            break;
        case '165':
            url = arr_url[165];
            $("#paramsDiv").val(loginInfo_arr[165]);
            break;
        case '166':
            url = arr_url[166];
            $("#paramsDiv").val(loginInfo_arr[166]);
            break;
        case '167':
            url = arr_url[167];
            $("#paramsDiv").val(loginInfo_arr[167]);
            break;
        case '168':
            url = arr_url[168];
            $("#paramsDiv").val(loginInfo_arr[168]);
            break;
        case '169':
            url = arr_url[169];
            $("#paramsDiv").val(loginInfo_arr[169]);
            break;
        case '170':
            url = arr_url[170];
            $("#paramsDiv").val(loginInfo_arr[170]);
            break;
        case '171':
            url = arr_url[171];
            $("#paramsDiv").val(loginInfo_arr[171]);
            break;
        case '172':
            url = arr_url[172];
            $("#paramsDiv").val(loginInfo_arr[172]);
            break;
        case '173':
            url = arr_url[173];
            $("#paramsDiv").val(loginInfo_arr[173]);
            break;
        case '174':
            url = arr_url[174];
            $("#paramsDiv").val(loginInfo_arr[174]);
            break;
        case '175':
            url = arr_url[175];
            $("#paramsDiv").val(loginInfo_arr[175]);
            break;
        case '176':
            url = arr_url[176];
            $("#paramsDiv").val(loginInfo_arr[176]);
            break;
        case '177':
            url = arr_url[177];
            $("#paramsDiv").val(loginInfo_arr[177]);
            break;
        case '178':
            url = arr_url[178];
            $("#paramsDiv").val(loginInfo_arr[178]);
            break;
        case '179':
            url = arr_url[179];
            $("#paramsDiv").val(loginInfo_arr[179]);
            break;
        case '180':
            url = arr_url[180];
            $("#paramsDiv").val(loginInfo_arr[180]);
            break;
        case '181':
            url = arr_url[181];
            $("#paramsDiv").val(loginInfo_arr[181]);
            break;
        case '182':
            url = arr_url[182];
            $("#paramsDiv").val(loginInfo_arr[182]);
            break;
        case '183':
            url = arr_url[183];
            $("#paramsDiv").val(loginInfo_arr[183]);
            break;
        case '185':
            url = arr_url[185];
            $("#paramsDiv").val(loginInfo_arr[185]);
            break;
        case '186':
            url = arr_url[186];
            $("#paramsDiv").val(loginInfo_arr[186]);
            break;
        case '187':
            url = arr_url[187];
            $("#paramsDiv").val(loginInfo_arr[187]);
            break;
        case '188':
            url = arr_url[188];
            $("#paramsDiv").val(loginInfo_arr[188]);
            break;
        case '191':
            url = arr_url[191];
            $("#paramsDiv").val(loginInfo_arr[191]);
            break;
        case '192':
            url = arr_url[192];
            $("#paramsDiv").val(loginInfo_arr[192]);
            break;
        case '193':
            url = arr_url[193];
            $("#paramsDiv").val(loginInfo_arr[193]);
            break;
        case '194':
            url = arr_url[194];
            $("#paramsDiv").val(loginInfo_arr[194]);
            break;
        case '200':
            url = arr_url[200];
            $("#paramsDiv").val(loginInfo_arr[200]);
            break;
        case '201':
            url = arr_url[201];
            $("#paramsDiv").val(loginInfo_arr[201]);
            break;
        case '202':
            url = arr_url[202];
            $("#paramsDiv").val(loginInfo_arr[202]);
            break;
        case '203':
            url = arr_url[203];
            $("#paramsDiv").val(loginInfo_arr[203]);
            break;
        case '204':
            url = arr_url[204];
            $("#paramsDiv").val(loginInfo_arr[204]);
            break;
        case '205':
            url = arr_url[205];
            $("#paramsDiv").val(loginInfo_arr[205]);
            break;
        case 'myCountList':
            url = arr_url['myCountList'];
            $("#paramsDiv").val(loginInfo_arr['myCountList']);
            break;
        case '206':
            url = arr_url[206];
            $("#paramsDiv").val(loginInfo_arr[206]);
            break;
        case '207':
            url = arr_url[207];
            $("#paramsDiv").val(loginInfo_arr[207]);
            break;
        case '208':
            url = arr_url[208];
            $("#paramsDiv").val(loginInfo_arr[208]);
            break;
        case '209':
            url = arr_url[209];
            $("#paramsDiv").val(loginInfo_arr[209]);
            break;
        case '210':
            url = arr_url[210];
            $("#paramsDiv").val(loginInfo_arr[210]);
            break;
        case '211':
            url = arr_url[211];
            $("#paramsDiv").val(loginInfo_arr[211]);
            break;
        case '212':
            url = arr_url[212];
            $("#paramsDiv").val(loginInfo_arr[212]);
            break;
        case '213':
            url = arr_url[213];
            $("#paramsDiv").val(loginInfo_arr[213]);
            break;
        case '216':
            url = arr_url[216];
            $("#paramsDiv").val(loginInfo_arr[216]);
            break;
        case '217':
            url = arr_url[217];
            $("#paramsDiv").val(loginInfo_arr[217]);
            break;
        case '230':
            url = arr_url[230];
            $("#paramsDiv").val(loginInfo_arr[230]);
            break;
        case '231':
            url = arr_url[231];
            $("#paramsDiv").val(loginInfo_arr[231]);
            break;
        case '232':
            url = arr_url[232];
            $("#paramsDiv").val(loginInfo_arr[232]);
            break;
        case '233':
            url = arr_url[233];
            $("#paramsDiv").val(loginInfo_arr[233]);
            break;
        case '234':
            url = arr_url[234];
            $("#paramsDiv").val(loginInfo_arr[234]);
            break;
        case '235':
            url = arr_url[235];
            $("#paramsDiv").val(loginInfo_arr[235]);
            break;
        case '310':
            url = arr_url[310];
            $("#paramsDiv").val(loginInfo_arr[310]);
            break;
        case '311':
            url = arr_url[311];
            $("#paramsDiv").val(loginInfo_arr[311]);
            break;
        case '312':
            url = arr_url[312];
            $("#paramsDiv").val(loginInfo_arr[312]);
            break;
        case '313':
            url = arr_url[313];
            $("#paramsDiv").val(loginInfo_arr[313]);
            break;
        case '314':
            url = arr_url[314];
            $("#paramsDiv").val(loginInfo_arr[314]);
            break;
        case '315':
            url = arr_url[315];
            $("#paramsDiv").val(loginInfo_arr[315]);
            break;
        case '316':
            url = arr_url[316];
            $("#paramsDiv").val(loginInfo_arr[316]);
            break;
        case '317':
            url = arr_url[317];
            $("#paramsDiv").val(loginInfo_arr[317]);
            break;
        case '318':
            url = arr_url[318];
            $("#paramsDiv").val(loginInfo_arr[318]);
            break;
        case '407':
            url = arr_url[407];
            $("#paramsDiv").val(loginInfo_arr[407]);
            break;
        case '408':
            url = arr_url[408];
            $("#paramsDiv").val(loginInfo_arr[408]);
            break;
        case '409':
            url = arr_url[409];
            $("#paramsDiv").val(loginInfo_arr[409]);
            break;
        case '410':
            url = arr_url[410];
            $("#paramsDiv").val(loginInfo_arr[410]);
            break;
        case '411':
            url = arr_url[411];
            $("#paramsDiv").val(loginInfo_arr[411]);
            break;
        case '412':
            url = arr_url[412];
            $("#paramsDiv").val(loginInfo_arr[412]);
            break;
        case '413':
            url = arr_url[413];
            $("#paramsDiv").val(loginInfo_arr[413]);
            break;
        case '414':
            url = arr_url[414];
            $("#paramsDiv").val(loginInfo_arr[414]);
            break;
        case '415':
            url = arr_url[415];
            $("#paramsDiv").val(loginInfo_arr[415]);
            break;
        case '416':
            url = arr_url[416];
            $("#paramsDiv").val(loginInfo_arr[416]);
            break;
        case '417':
            url = arr_url[417];
            $("#paramsDiv").val(loginInfo_arr[417]);
            break;
        case '418':
            url = arr_url[418];
            $("#paramsDiv").val(loginInfo_arr[418]);
            break;
        case '419':
            url = arr_url[419];
            $("#paramsDiv").val(loginInfo_arr[419]);
            break;
        case '420':
            url = arr_url[420];
            $("#paramsDiv").val(loginInfo_arr[420]);
            break;
        case '421':
            url = arr_url[421];
            $("#paramsDiv").val(loginInfo_arr[421]);
            break;
        case '422':
            url = arr_url[422];
            $("#paramsDiv").val(loginInfo_arr[422]);
            break;
        case '423':
            url = arr_url[423];
            $("#paramsDiv").val(loginInfo_arr[423]);
            break;
        case '424':
            url = arr_url[424];
            $("#paramsDiv").val(loginInfo_arr[424]);
            break;
        case '425':
            url = arr_url[425];
            $("#paramsDiv").val(loginInfo_arr[425]);
            break;
        case '426':
            url = arr_url[426];
            $("#paramsDiv").val(loginInfo_arr[426]);
            break;
        case '427':
            url = arr_url[427];
            $("#paramsDiv").val(loginInfo_arr[427]);
            break;
        case '428':
            url = arr_url[428];
            $("#paramsDiv").val(loginInfo_arr[428]);
            break;
        case '429':
            url = arr_url[429];
            $("#paramsDiv").val(loginInfo_arr[429]);
            break;
        case '430':
            url = arr_url[430];
            $("#paramsDiv").val(loginInfo_arr[430]);
            break;
        case '431':
            url = arr_url[431];
            $("#paramsDiv").val(loginInfo_arr[431]);
            break;
        case '432':
            url = arr_url[432];
            $("#paramsDiv").val(loginInfo_arr[432]);
            break;
        case '433':
            url = arr_url[433];
            $("#paramsDiv").val(loginInfo_arr[433]);
            break;
        case '434':
            url = arr_url[434];
            $("#paramsDiv").val(loginInfo_arr[434]);
            break;
        case '500':
            url = arr_url[500];
            $("#paramsDiv").val(loginInfo_arr[500]);
            break;
        case '501':
            url = arr_url[501];
            $("#paramsDiv").val(loginInfo_arr[501]);
            break;
        case '502':
            url = arr_url[502];
            $("#paramsDiv").val(loginInfo_arr[502]);
            break;
        case '503':
            url = arr_url[503];
            $("#paramsDiv").val(loginInfo_arr[503]);
            break;
        case '504':
            url = arr_url[504];
            $("#paramsDiv").val(loginInfo_arr[504]);
            break;
        case '505':
            url = arr_url[505];
            $("#paramsDiv").val(loginInfo_arr[505]);
            break;
        case '507':
            url = arr_url[507];
            $("#paramsDiv").val(loginInfo_arr[507]);
            break;
        case '508':
            url = arr_url[508];
            $("#paramsDiv").val(loginInfo_arr[508]);
            break;
        case '509':
            url = arr_url[509];
            $("#paramsDiv").val(loginInfo_arr[509]);
            break;
        case '510':
            url = arr_url[510];
            $("#paramsDiv").val(loginInfo_arr[510]);
            break;
        case '511':
            url = arr_url[511];
            $("#paramsDiv").val(loginInfo_arr[511]);
            break;
        case '513':
            url = arr_url[513];
            $("#paramsDiv").val(loginInfo_arr[513]);
            break;
        case '514':
            url = arr_url[514];
            $("#paramsDiv").val(loginInfo_arr[514]);
            break;
        case '515':
            url = arr_url[515];
            $("#paramsDiv").val(loginInfo_arr[515]);
            break;
        case '516':
            url = arr_url[516];
            $("#paramsDiv").val(loginInfo_arr[516]);
            break;
        case '517':
            url = arr_url[517];
            $("#paramsDiv").val(loginInfo_arr[517]);
            break;
        case '518':
            url = arr_url[518];
            $("#paramsDiv").val(loginInfo_arr[518]);
            break;
        case '519':
            url = "/webknowledge/addColumn.json";
            loginInfo = '{"columnname":"测试","pid":1,"pathName":"资讯/测试","type":1,"tags":"不知道,fhw"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '520':
            url = "/webknowledge/delColumn.json";
            loginInfo = '{"id":5172,"verify":true}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '521':
            url = "/webknowledge//home/queryColumnsById.json";
            loginInfo = '{"pid":0}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '522':
            url = "/webknowledge/home/orderColumn/showColumn.json";
            loginInfo = '{"pid":1}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '523':
            url = "/webknowledge/home/setOrderColumn.json";
            loginInfo = '{"pcid":0,"columnid":"4,5,7"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '524':
            url = "/webknowledge//home/column/list.json";
            loginInfo = '{"pid":0}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '525':
            url = "/friend/myCountList.json";
            loginInfo = '{"type":4,"web":4}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '526':
            url = "/file/download.json";
            loginInfo = '{"fileIds":"1592133163241"}';
            $("#paramsDiv").val(loginInfo);
            break;
        case '527':
            url = "/organ/announcement/list.json";
            $("#paramsDiv").val(loginInfo_arr[527]);
            break;
        case '528':
            url = arr_url[528];
            $("#paramsDiv").val(loginInfo_arr[528]);
            break;
        case '529':
            url = arr_url[529];
            $("#paramsDiv").val(loginInfo_arr[529]);
            break;
        case '530':
            url = arr_url[530];
            $("#paramsDiv").val(loginInfo_arr[530]);
            break;
        case '531':
            url = arr_url[531];
            $("#paramsDiv").val(loginInfo_arr[531]);
            break;
        case '532':
            url = arr_url[532];
            $("#paramsDiv").val(loginInfo_arr[532]);
            break;
        case 'personReport':
            url = arr_url['personReport'];
            $("#paramsDiv").val(loginInfo_arr['personReport']);
            break;
        default:
            url = arr_url[selectValue];
            $("#paramsDiv").val(loginInfo_arr[selectValue]);
    }

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
    var aaaa=document.getElementById("aaaa");
    aaaa.value=sessionId;
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
    loginInfo = '{"clientID":"131321321321","clientPassword":"","imei":"yss-3434-dsf55-22256","version":"1.6.0.0609","platform":"iPhone","model":"iPhone 3G","resolution":"480x320","systemName":"iOS","systemVersion":"1.5.7","channelID":"10086111445441","loginString":"yinxing","password":"27c878bcb21bf1b26375c059923a5d48dd3f5e75578769b52632a0faaff564d3"}';
    $.ajax({
        url : url,
        data :  loginInfo,
        beforeSend: function(xhr){xhr.setRequestHeader('sessionID', sessionId);},
        async : false,
        type : 'POST',
        dataType : "text",
        cache : false,
        success : function(data,statusText, jqXHR) {
         // sessionId=jqXHR.getResponseHeader("sessionID");
           sessionId="ea469c36-bba6-4572-a336-b41001a3e689";
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

