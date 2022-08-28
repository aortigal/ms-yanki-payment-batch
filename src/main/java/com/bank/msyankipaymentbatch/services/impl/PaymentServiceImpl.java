package com.bank.msyankipaymentbatch.services.impl;

import com.bank.msyankipaymentbatch.models.dao.PaymentDao;
import com.bank.msyankipaymentbatch.models.documents.Payment;
import com.bank.msyankipaymentbatch.services.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDao dao;

    private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Override
    public Optional<Payment> create(Payment payment) {
        payment.setDateRegister(LocalDateTime.now());
        return Optional.of(dao.save(payment));
    }

    @Override
    public Optional<Payment> update(Payment payment) {
        payment.setDateUpdate(LocalDateTime.now());
        return Optional.of(dao.save(payment));
    }

}
