package com.bank.msyankipaymentbatch.consumer;

import com.bank.msyankipaymentbatch.models.documents.Payment;
import com.bank.msyankipaymentbatch.models.utils.DataEvent;
import com.bank.msyankipaymentbatch.services.ProcessPaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    private ProcessPaymentService processPaymentService;
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "${kafka.topic.payment}")
    public void consume(String message) {
        log.info("[INI] Consume");
        log.info("Consuming Message {}", message);
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            DataEvent<Payment> dataEvent = objectMapper.readValue(message, new TypeReference<DataEvent<Payment>>() {});
            processPaymentService.process(dataEvent);
        }catch (JsonProcessingException e){
            log.error("JsonProcessingException {}", e.getMessage());
        }

        log.info("[END] Consume");
    }

}