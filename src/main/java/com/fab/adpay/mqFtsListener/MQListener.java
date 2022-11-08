package com.fab.adpay.mqFtsListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MQListener {
    private static final Logger logger = LoggerFactory.getLogger(MQListener.class);


    @JmsListener(destination = "FTS.ADIPIPAYMENT.STATUS.OUT.LQ.AE")
    public void receive(Message message) throws JMSException {

        if (message instanceof TextMessage) {
            final String queueMsg = ((TextMessage) message).getText();
            final String jmsCorrelationId = message.getJMSCorrelationID();
            logger.info("AdPayListening messaging queue : {} and its correlation id : {}",
                    queueMsg, jmsCorrelationId);

        }
    }
}

