package com.ginkgocap.parasol.sms.receive.impl;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.sms.receive.DealReceivedSmsTask;

/**
 * 
* <p>Title: MobileRegistServiceImpl.java<／p> 
* <p>Description: <／p> 

* @author fuliwen 
* @date 2015-11-11 
* @version 1.0
 */
@Service("mobileRegistService")
public class MobileRegistServiceImpl implements DealReceivedSmsTask {

    @Resource
    private JmsTemplate jmsTemplate;//jms模版
    @Resource(name="amqQueueRegistMsg")
    private Destination destination;//发送地址，在spring配置的
    private final String name = "mobileRegistService";//处理任务名字，用于与其他处理任务区分
    private final String regist_prefix = "GTJR";//注册标志前缀
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Override
    public boolean deal(String mobile, String msg, String source){
        logger.debug("begin deal msg:{} by {}",msg,name);
        boolean f = validate(msg);
        logger.debug("can deal it ? : {}",f);
        if(f){
            logger.debug("begin to send the msg to message bus");
            int i = storeRegistMsg2Bus(mobile, msg);
            logger.debug("send msg to message bus return {}",i);
        }
        return f;
    }
    @Override
    public String getName(){
        return name;
    }
    /**
     * 验证该任务是否能够处理该短信
     * @param msg
     * @return
     */
    private boolean validate(String msg){
        return msg.startsWith(regist_prefix);
    }
    /**把注册用户的指令存储到消息总线<br>
     * 该接口对外开放，目前在sms-provider以及web中调用
     * @param mobile 上行手机号码 eg.{18810693055}
     * @param msg 消息内容  eg.{GTJR12345}
     * @return 1:success or 0:fail
     */
    public int storeRegistMsg2Bus(final String mobile, final String msg) {
        logger.debug("send a regist msg:{},{}",mobile,msg);
        int flag = 1;
        try{
            String str = msg.substring(regist_prefix.length());
            if(str.length()>0 && StringUtils.isNumeric(str)){//是userId
                logger.debug("get invite userId:{}",str);
                final long friendId = Long.parseLong(str);
              //直接使用jms模版发送
                jmsTemplate.send(destination, new MessageCreator() {//匿名类创建消息，这里采用了map消息，另外有text  Object 等类型
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        logger.debug("create message of regist user");
                        MapMessage mm = session.createMapMessage();
                        mm.setString("mobile", mobile);
                        mm.setLong("friendId", friendId);
                        return mm;
                    }
                });
            }
        }catch (Exception e) {
            logger.error("something wrong when sending regist msg",e);
            flag = 0;
        }
        return flag;
    }

}
