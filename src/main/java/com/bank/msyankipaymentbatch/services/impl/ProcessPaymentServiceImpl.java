package com.bank.msyankipaymentbatch.services.impl;

import com.bank.msyankipaymentbatch.constants.Constant;
import com.bank.msyankipaymentbatch.consumer.KafkaConsumer;
import com.bank.msyankipaymentbatch.models.documents.Payment;
import com.bank.msyankipaymentbatch.models.utils.DataEvent;
import com.bank.msyankipaymentbatch.services.PaymentService;
import com.bank.msyankipaymentbatch.services.ProcessPaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProcessPaymentServiceImpl implements ProcessPaymentService {

    @Autowired
    private PaymentService paymentService;

    private static final Logger log = LoggerFactory.getLogger(ProcessPaymentServiceImpl.class);

    @Override
    public void process(DataEvent<Payment> dataEvent) {
        log.info("[INI] Process {}", dataEvent.getId());
        if(dataEvent.getProcess().equals(Constant.PROCESS_PAYMENT_CREATE)){
            log.info("save Payment");
            paymentService.create(dataEvent.getData());
        }else if(dataEvent.getProcess().equals(Constant.PROCESS_PAYMENT_UPDATE)){
            log.info("update Payment");
            paymentService.update(dataEvent.getData());
        }else{
            log.info("Procces Invalid {}", dataEvent.getProcess());
        }

        log.info("[END] Process {}", dataEvent.getId());
    }

}
