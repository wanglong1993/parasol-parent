<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>云研究-500页面</title>
<link rel="stylesheet" type="text/css" href="${stylesRoot}/jquery.alertNew.css"/>
<link rel="stylesheet" type="text/css" href="${stylesRoot}/jquery.tipsy.css"/>
<link rel="stylesheet" type="text/css" href="${stylesRoot}/layoutCloud.css"/>
<link rel="stylesheet" type="text/css" href="${stylesRoot}/case.css"/>
</head>
<body class="case_2">
<div id="global">
<!--top-->		
<div id="caseHeader">
	<div class="case_error_top"><h1 class="logo"><img src="${imagesRoot}/logo-2.png"/></h1></div>
</div>
<!-- 头部导航结束 -->
	<div id="subNav" style="border-bottom:0px;"><a href="javascript:void(0);">首页</a>&gt;<a href="/cloud/case/index">经典案例</a>&gt;<span class="hy">错误信息</span></div>	
     
<!-- 中间部分开始 -->
<div class="content clearfix" id="bd">
				
			<div class="caseBorder_1 clearfix" style="margin-top:-1px;">
				<div class="error_img"><p>您可以稍后再试，或联系客服</p><p>将在<span id="dd">10</span>秒后自动转入<a href="/cloud/case/index">经典案例首页</a>，请稍候...</p></div>
			</div>
	
</div>

<!-- 中间部分结束 -->
</div>

</body>
</html>
<script>
function run(){
	var s = document.getElementById("dd");
	if(s.innerHTML == 0){
		window.location.href='${cloudWebIndex}';
		return false;
	}
	s.innerHTML = s.innerHTML * 1 - 1;
}
window.setInterval("run();", 1000);  

</script>
