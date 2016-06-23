<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>文件上传测试</title>
</head>
<script src="<%=request.getContextPath() %>/resources/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<body>
<%String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- 
<form action="<%=request.getContextPath() %>/file/goUpload.json" enctype="multipart/form-data" method="post">
	保存路径：<input type="text" name="path" style="width: 300px;"><br>
	<input type="file" id="file" name="fileBean"><input type="submit" value="上传">
	http://192.168.170.153:8080/ImServer/file/goUpload.json
	<%=request.getContextPath() %>/file/goUpload.json
</form>
 -->
 <!--  <form action="http://192.168.101.131:880/web/upload" name="upload" method="POST" enctype="multipart/form-data">
 -->
 <!-- <form action="http://file.online.gintong.com/mobile/upload" name="upload" method="POST" enctype="multipart/form-data"> -->
 <form action="http://file.online.gintong.com/mobile/idcard/avatar" name="upload" method="POST" enctype="multipart/form-data">
   <!-- <form action="http://file.online.gintong.com/mobile/people/avatar" name="upload" method="POST" enctype="multipart/form-data">
 <form action="http://file.online.gintong.com/mobile/user/avatar" name="upload" method="POST" enctype="multipart/form-data">--> 
	<input type="file" name="file"><br>
		<input type="submit" name="submit" value="nginx upload"> 
		taskId:<input type="text" name="taskId"  value="${taskId }">
		moduleType:<input type="text" name="moduleType"  value="${moduleType }">
		uid:<input type="text" name="uid"  value="${uid }">
		pid:<input type="text" name="pid"  value="${pid }">
		type:<input type="text" name="type"  value="${type }">
		fileWidth:<input type="text" name="fileWidth"  value="244">
</form>
http://file.online.gintong.com/mobile/download

<a href="http://file.online.gintong.com/mobile/download?id=${fileId}">下载</a>
<br>
<table>
<tr>
	<td>fileRealName</td>
	<td>${fileRealName }</td>
</tr>
<tr>
	<td>prefixPath</td>
	<td>${prefixPath }</td>
</tr>
<tr>
	<td>fullFilePath</td>
	<td>${fullFilePath }</td>
</tr>
<tr>
	<td>filePath</td>
	<td>${filePath }</td>
</tr>
<tr>
	<td>fileName</td>
	<td>${fileName }</td>
</tr>
<tr>
	<td>md5</td>
	<td>${md5 }</td>
</tr>
<tr>
	<td>size</td>
	<td>${size }</td>
</tr>
<tr>
	<td>crc32</td>
	<td>${crc32 }</td>
</tr>
<tr>
	<td>taskId</td>
	<td>${taskId }</td>
</tr>
</table>

<form action="<%=request.getContextPath() %>/file/testUpload.json" method="post">
<input type="submit" value="点击测试上传">
</form>
<!--  
<textarea rows="10" cols="20" id="fileJson">{"filePath":"d:/fileLoad/","fileName":"公司邮箱配置手册.docx"}</textarea><input type="button" value="下载" onclick="downLoad()">
--><div id="resultDiv"></div>
<script type="text/javascript">
function fileUpload(){
	$.ajax({
		url : '<%=request.getContextPath() %>/file/goUpload.json',
		async : false,
		type : 'POST',
		dataType : "text",
		cache : false,
		success : function(data) {
			$("#resultDiv").html(data);
		}
	});
}

function downLoad(){
	$.ajax({
		url : '<%=request.getContextPath() %>/file/goDownload.json',
		async : false,
		type : 'POST',
		data :  $("#fileJson").val(),
		dataType : "text",
		cache : false,
		success : function(data) {
			$("#resultDiv").html(data);
		}
	});
}
</script>
</body>
</html>

