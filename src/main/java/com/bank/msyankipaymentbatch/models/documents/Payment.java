package com.bank.msyankipaymentbatch.models.documents;

import com.bank.msyankipaymentbatch.models.utils.Audit;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "payments")
public class Payment extends Audit {

    @Id
    private String id;

    private String recipientName;

    private String recipientPhone;

    private String comissionAmount;

    private float amount;

    private String customerId;

}
