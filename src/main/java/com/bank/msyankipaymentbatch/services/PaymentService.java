package com.bank.msyankipaymentbatch.services;

import com.bank.msyankipaymentbatch.models.documents.Payment;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface PaymentService {
    Optional<Payment> create(Payment payment);

    Optional<Payment> update(Payment payment);

}
