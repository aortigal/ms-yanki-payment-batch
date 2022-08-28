package com.bank.msyankipaymentbatch.services;

import com.bank.msyankipaymentbatch.models.documents.Payment;
import com.bank.msyankipaymentbatch.models.utils.DataEvent;

public interface ProcessPaymentService {

    void process(DataEvent<Payment> dataEvent);
}
