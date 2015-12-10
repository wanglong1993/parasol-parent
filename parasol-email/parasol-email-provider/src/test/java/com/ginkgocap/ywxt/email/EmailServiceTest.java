package com.ginkgocap.ywxt.email;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ginkgocap.parasol.email.service.EmailService;
import com.ginkgocap.parasol.email.service.impl.EmailServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * 
 * @author liu
 *
 */
public class EmailServiceTest extends BaseTest {

    @Autowired
    private EmailService service =  new EmailServiceImpl();

    @Test
    public void testSendHtml() {
        String path = System.getProperty("user.dir");
        path = path.replaceAll("\\\\", "/");
        String to = "fengdezhen@gintong.com";
        String from = "fengdezhen@gintong.com";
        String subject = "简单测试";
        String content = "<TABLE border=3 cellSpacing=0 borderColor=#e6e6e6 cellPadding=0 width=800 align=center>";
        content += "<TBODY><TR>";
        content += "<TD style=\"BORDER-BOTTOM: #a3c2e0 2px solid\" height=60>&nbsp;</TD></TR><TR><TD>";
        content += "<P align=center><SPAN style=\"FONT-SIZE: 14px\"><BR>邀请函</SPAN></P>";
        content += "<P align=left><SPAN style=\"FONT-SIZE: 14px\"><STRONG>&nbsp;尊敬的[xm]：</STRONG></SPAN></P>";
        content += "<P align=left><SPAN style=\"FONT-SIZE: 14px\"><SPAN style=\"FONT-SIZE: 14px\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ";
        content += "</SPAN>您好!</SPAN></P>";
        content += "<P align=left><SPAN style=\"FONT-SIZE: 14px\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        content += " 银杏资本管理有限公司诚挚邀请您加入我们的网络平台。</SPAN><SPAN style=\"FONT-SIZE: 14px\">&nbsp;</SPAN></P>";
        content += "<P></P><A style=\"TEXT-DECORATION: none\" href=\"http://pt.ginkgocap.cn\">";
        content += "<DIV style=\"BORDER-BOTTOM: 1px; BORDER-LEFT: 1px; PADDING-BOTTOM: 3px; ";
        content += "BACKGROUND-COLOR: #c9e5b5; PADDING-LEFT: 3px; WIDTH: 130px; PADDING-RIGHT: 3px;";
        content += " HEIGHT: 20px; MARGIN-LEFT: 40px; FONT-SIZE: 14px; BORDER-TOP: 1px; BORDER-RIGHT: 1px;";
        content += " PADDING-TOP: 3px\">&nbsp;立即登录网络平台</DIV></A>";
        content += "<P></P>";
        content += "<P align=left><SPAN style=\"FONT-SIZE: 14px\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ";
        content += "请您登陆以上地址进行会员注册，我们将会在24小时内审核，并回复您的注册信息。</SPAN></P><SPAN style=\"FONT-SIZE: 14px\">";
        content += "<P><SPAN style=\"FONT-SIZE: 14px\">此致!</SPAN></P>";
        content += "<P><SPAN style=\"FONT-SIZE: 14px\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        content += "&nbsp;&nbsp;&nbsp;&nbsp; 敬礼!</SPAN></P></SPAN></TD></TR><TR>";
        content += "<TD align=right><SPAN style=\"FONT-SIZE: 14px\"><P align=right><BR>银杏资本管理团队<BR>";
        content += "</SPAN><SPAN style=\"FONT-SIZE: 14px\" align=\"right\">[rq]<BR>&nbsp; &nbsp;</SPAN></P>";
        content += "</TD></TR></TBODY></TABLE><P>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </P>";
        String attachment = path + "/pom.xml";

        boolean isSuccess = service.sendEmailSync(to, from, subject, content, attachment);
        assertEquals(isSuccess,true);
    }
}
