<!DOCTYPE html>
<html>
<head>
	<title></title>
	<meta charset="utf-8" />
	<style>
        .wrapper{
            width: 572px;
            padding-left: 90px;
            padding-top: 30px;
        }
        .wrapper img{
            display: block;
        }
        .wrapper a{
            color:#3996fd;
        } 

        .imgTxt{
            margin-top: 99px;
        }
        .wrapper p{
            color:#a5a5a5;
            margin-top: 45px;
        }
        .wrapper h3{
            font-size: 20px;
            font-family: '微软雅黑';
            color:#1f1f1f;
            font-weight: normal;
        }
    </style>
</head>
<body>
<div class="wrapper">
        <img src="${imageRoot}/emailImg/emailLogo.png" />  
        <img class="imgTxt" src="${imageRoot}/emailImg/emailText.png" /> 
        <h3>请您在48小时内点击下方链接完成验证：</h3>
        <a target="_blank" href="${email}">${email}</a>
        <p>（如果链接无法点击，请复制链接到您的浏览器地址打开。）</p>
 </div>
    
</body>
</html>