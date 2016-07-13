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
	<div class="edmTop">为了确保能收到会员活动提醒，请将金桐加为您的邮件联系人</div>
	<div class="edmMain">
		<div class="edmM">
			<div class="edmMainTop">                                            
				<div class="edmLogo">
					<img src="${imageRoot}/emailImg/emailLogo.png" width="280"/>
					<span class="topHref"><a href="http://www.gintong.com/" target="_blank">金桐首页</a>|<a  href="http://www.gintong.com/about/about.html" target="_blank">关于我们</a></span>
				</div>
			</div>
				<div class="edmText">
					<h2>亲爱的金桐会员<span class="redText">${acceptor}：</span></h2>
					<p>您在${time}申请了修改密码，为确保您的帐户信息正确且安全，一旦密码变更，我们会立即通知您。</p>
					<p>若上述变更正确无误，请点击以下链接，进行修改：<a href='${email}' target="_blank">${email}</a></p>
					<p>如果您本人未进行过激活操作，则您无需理会本邮件。<br/>如有任何疑问，请联系金桐客服。</p>
					<p>客服邮箱：<a href="" target="_blank">kefu@gintong.com</a><br/>客服电话：400-070-0011</p>
					<p>此信由金桐系统自动发出，系统不接收回信，请勿直接回复。</p>
				</div>
		</div>
	</div>
	<div class="edmBottom">Copyright © 2010-2014 版权所有gintong.com</div>

</div>
</body>
</html>