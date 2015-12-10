<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>金桐-邮件</title>
<style type="text/css">
*{padding:0; margin:0; font-size:12px; line-height: 2em;color:#626262; border:none;}
.yinxingEdm{  margin: 20px auto;width: 612px;padding:6px; }
.edmTop{color: #a9a9a9; text-align: right; padding:0 6px;}
.edmBottom{color: #a9a9a9; text-align: center;  padding:0 6px; font-family: "Arial";}
.edmMain{ background: url(cid:img2) repeat-x;}
.edmM{ padding:15px 7px 0px;height:450px;}
.edmMainTop{}
.edmLogo{ height: 40px;border-bottom: 1px solid #ccc;}
.edmLogo img{float: left;}
.edmBanner{ height:136px; }
.topHref{ float: right; margin-top: 12px; margin-right: 5px;color:#636363;}
.topHref a{ color:#636363; margin: 0 5px;}
.topHref a:hover{ color:#636363;color:#dc9632;}
.edmText{ padding: 20px 50px 15px;word-break:break-all;word-wrap:break-word;color:#dc9632;}
.edmText h2{ font-size: 14px; font-weight: bold;}
.edmText p{ line-height: 18px; padding: 10px 0;}
.edmText p a{ color:#0070cd;}
.edmText p a:hover{ color:#dc9632;}
</style>
</head>
<body>
<div class="yinxingEdm">

		<div class="edmMain">
		<div class="edmT"></div>
		<div class="edmM">
			<div class="edmMainTop">
				<div class="edmLogo">
					<img src="${imageRoot}/emailImg/emailLogo.png" width="280"/>
				</div>
			</div>
			 
				<div class="edmText">
					<span>${acceptor},感谢您注册金桐！<span>
					<p>请您在48小时内点击下方链接完成验证:</p>
					<p></p>
					<p><a href='${email}' target="_blank">${email}</a></p>
					<p></p>
	
					<p>(如果链接无法直接点击，请复制链接到您的浏览器地址栏打开。)</p>
					
				</div>
		</div>
	</div>
	<div class="edmBottom">Copyright © 2010-2014 版权所有gintong.com</div>

</div>
</body>
</html>