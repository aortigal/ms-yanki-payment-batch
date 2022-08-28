package com.bank.msyankipaymentbatch.models.dao;

import com.bank.msyankipaymentbatch.models.documents.Payment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PaymentDao extends ReactiveMongoRepository<Payment, String> {
}
