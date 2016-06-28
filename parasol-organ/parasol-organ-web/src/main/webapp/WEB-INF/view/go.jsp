<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html>
<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
<meta content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;" name="viewport">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">

<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="generator" content="ywxt" />
<link rel="SHORTCUT ICON" href="/favicon.ico" type="image/x-icon"/>
<title>金桐网——邀请加入！</title>
</head>
<script type="text/javascript"  src="http://static.gintong.com/resources/js/lib/jquery-1.7.2.min.js"></script>
<body>
	<a id="openApp" style="display:none">APK客户端下载链接</a>
</body>
<script type="text/javascript">
var type = "${type}";//1表示跳转会议邀请函界面
var id = "${id}";
function open_or_download_app() {
	if (isNaN(parseInt(id)) || isNaN(parseInt(type))) {
		alert('param error:'+id+','+type);
		return;
	}
    if (navigator.userAgent.match(/(iPhone|iPod|iPad);?/i)) {// 判断useragent，当前设备为ios设备
        window.location = "gintong://"+type + "/" + id;// ios端URL Schema
// 设置时间阈值，在规定时间里面没有打开对应App的话，直接去App store进行下载。
         window.setTimeout(function() {
                	window.location = "http://static.gintong.com/resources/appweb/index.html";
        },
        1000);
    } else if (navigator.userAgent.match(/android/i)) {// 判断useragent，当前设备为Android设备
    	$('#openApp').live("click",function(){
    		var ifrSrc="gintong://" + type + "/" + id;
    		var ifr = document.createElement("iframe");
    		ifr.src=ifrSrc;
    		ifr.style.display="none";
    		document.body.appendChild(ifr);
    		 window.setTimeout(function(){
    			 document.body.removeChild(ifr);
                 window.location = "http://static.gintong.com/resources/appweb/index.html";
    		 },1000);
    		
    	});
    	if(document.all){
    		$('#openApp').click();
    	}else{
    		 var e = document.createEvent("MouseEvents");
    		 e.initEvent("click",true,true);
    		 document.getElementById("openApp").dispatchEvent(e);		
    	}
	} 
}
$(function(){
	open_or_download_app();
	
});
</script>
</html>