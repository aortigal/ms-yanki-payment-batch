package com.bank.msyankipaymentbatch.models.dao;

import com.bank.msyankipaymentbatch.models.documents.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentDao extends MongoRepository<Payment, String> {
}
