package com.bank.msyankipaymentbatch.consumer;

import com.bank.msyankipaymentbatch.models.utils.DataEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "${kafka.topic.payment}")
    public void consume(ConsumerRecord<String, DataEvent<?>> message) {
        log.info("Consuming Message {}", message.value());
    }

}