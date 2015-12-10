package com.ginkgocap.parasol.email.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.email.model.Email;
import com.ginkgocap.parasol.email.service.EmailService;
import com.ginkgocap.parasol.email.util.SendHtmlMail;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

    private Logger log =Logger.getLogger(EmailServiceImpl.class);


    /**
     * send email immediately, this method will block because the method is synchronized.
     * @param mailTo
     * @param mailFrom
     * @param mailTitle  the mail title
     * @param mailContext the mail context
     * @param attachment the mail attachment 
     * @return true if success.
     */
    @Override
    public boolean sendEmailSync(String mailTo, String mailFrom, String mailTitle, String mailContext, String attachment) {
        Email email = new Email(mailTo, mailFrom, mailTitle, mailContext, attachment);
        return sendEmailNoThread(email);
    }

    /**
     * send email immediately, this method non-block because the method is asynchronized, we could not get the status.
     * @param mailTo
     * @param mailFrom
     * @param mailTitle  the mail title
     * @param mailContext the mail context
     * @param attachment the mail attachment, if no attachment, set value to "".
     */
    @Override
    public void sendEmailAsync(String mailTo, String mailFrom, String mailTitle, String mailContext, String attachment) {
        Email email = new Email(mailTo, mailFrom, mailTitle, mailContext, attachment);
        sendEmail(email);
    }

    private boolean sendEmail(Email email) {
        SendHtmlMail sender;
        try {
            sender = new SendHtmlMail(email);
            sender.setAttach(email.getAttachment());
            new Thread(sender).run();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("sendEmail error", e);
            return false;
        }
    }

    private boolean sendEmailNoThread(Email email) {
        SendHtmlMail sender;
        try {
            sender = new SendHtmlMail(email);
            sender.setAttach(email.getAttachment());
            boolean result = sender.sendMail();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("sendEmailNoThread error", e);
            return false;
        }
    }

}
