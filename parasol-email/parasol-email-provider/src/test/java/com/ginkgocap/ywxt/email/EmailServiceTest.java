package com.ginkgocap.ywxt.email;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ginkgocap.parasol.email.exception.EmailServiceException;
import com.ginkgocap.parasol.email.service.EmailService;
import com.ginkgocap.parasol.email.service.impl.EmailServiceImpl;
import com.ginkgocap.parasol.email.util.TemplateUtils;


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
        String to = "fuliwen@gintong.com";
        String from = "noreply@gintong.com";
        String subject = "【金桐】邮箱注册";
        String content = "<TABLE border=3 cellSpacing=0 borderColor=#e6e6e6 cellPadding=0 width=800 align=center>";
        String template ="reg-activate-emai-old.ftl";
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
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("email", "http://www.gintong.com#/verify?from=1&type=1&e=Y2Nra2t0dEAxMjYuY29t&email=MTUxMjEwMTUxMzE5MTAxPV89Y2Nra2t0dCU0MDEyNi5jb20=");
        map.put("acceptor",to);
        map.put("imageRoot", "http://static.gintong.com/resources/images/v3/");
        boolean isSuccess;
		try {
			isSuccess = service.sendEmailSync(to, from, subject,attachment,map, template);
			assertEquals(isSuccess,true);
		} catch (EmailServiceException e) {
			e.printStackTrace();
		}
        
        
    }
}
