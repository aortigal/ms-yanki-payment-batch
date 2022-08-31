package com.bank.msyankipaymentbatch.services;

import com.bank.msyankipaymentbatch.models.documents.Payment;
import com.bank.msyankipaymentbatch.models.utils.DataEvent;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ProcessPaymentService {

    void process(String message) throws JsonProcessingException;
}
