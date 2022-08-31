package com.bank.msyankipaymentbatch.services.impl;

import com.bank.msyankipaymentbatch.constants.Constant;
import com.bank.msyankipaymentbatch.models.documents.Payment;
import com.bank.msyankipaymentbatch.models.documents.TransactionTransfer;
import com.bank.msyankipaymentbatch.models.utils.DataEvent;
import com.bank.msyankipaymentbatch.producer.KafkaProducer;
import com.bank.msyankipaymentbatch.services.PaymentService;
import com.bank.msyankipaymentbatch.services.ProcessPaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class ProcessPaymentServiceImpl implements ProcessPaymentService {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private KafkaProducer kafkaProducer;

    private static final Logger log = LoggerFactory.getLogger(ProcessPaymentServiceImpl.class);

    @Override
    public void process(String message) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        DataEvent<?> dataEvent = objectMapper.readValue(message, new TypeReference<DataEvent<?>>() {});
        log.info("[INI] Process {}", dataEvent.getId());
        switch (dataEvent.getProcess()) {
            case Constant.PROCESS_PAYMENT_CREATE:
                log.info("save Payment");
                Payment paymentCreate = objectMapper.readValue(message, new TypeReference<DataEvent<Payment>>() {})
                        .getData();
                Optional<Payment> payment =paymentService.create(paymentCreate);
                if(payment.isPresent()){
                    DataEvent<Payment> dataEventPayment = new DataEvent<>();
                    dataEventPayment.setId(dataEvent.getId());
                    dataEventPayment.setProcess(Constant.PROCESS_WALLET_PAYMENT);
                    dataEventPayment.setDateEvent(LocalDateTime.now());
                    dataEventPayment.setData(payment.get());
                    kafkaProducer.sendMessage(dataEventPayment);
                }
                break;
            case Constant.PROCESS_PAYMENT_UPDATE:
                log.info("update Payment");
                Payment paymentUpdate = objectMapper.readValue(message, new TypeReference<DataEvent<Payment>>() {})
                        .getData();
                paymentService.update(paymentUpdate);
                break;
            case Constant.PROCESS_BOOTCOIN_TRANSFER_YANKI_PAYMENT:
                log.info("bootCoin Payment");
                TransactionTransfer transactionTransfer = objectMapper.readValue(message, new TypeReference<DataEvent<TransactionTransfer>>() {})
                        .getData();

                Payment paymentBootcoin = new Payment();
                paymentBootcoin.setRecipientName(transactionTransfer.getRecipientName());
                paymentBootcoin.setRecipientPhone(transactionTransfer.getRecipientAccount());
                paymentBootcoin.setAmount(transactionTransfer.getAmount());
                paymentBootcoin.setComissionAmount(transactionTransfer.getRateAmount().toString());
                paymentBootcoin.setCustomerPhone(transactionTransfer.getSenderAccount());
                Optional<Payment> paymentOptional = paymentService.create(paymentBootcoin);
                if (paymentOptional.isPresent()){
                    DataEvent<TransactionTransfer> transactionTransferDataEvent = new DataEvent<>();
                    transactionTransferDataEvent.setId(dataEvent.getId());
                    transactionTransferDataEvent.setProcess(Constant.PROCESS_WALLET_PAYMENT_BOOTCOIN);
                    transactionTransferDataEvent.setDateEvent(LocalDateTime.now());
                    transactionTransferDataEvent.setData(transactionTransfer);
                    kafkaProducer.sendMessage(transactionTransferDataEvent);
                }
                break;
            default:
                log.info("Procces Invalid {}", dataEvent.getProcess());
                break;
        }
        log.info("[END] Process {}", dataEvent.getId());
    }

}
