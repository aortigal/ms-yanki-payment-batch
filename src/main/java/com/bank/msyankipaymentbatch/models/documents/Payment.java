package com.bank.msyankipaymentbatch.models.documents;

import com.bank.msyankipaymentbatch.models.utils.Audit;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document(collection = "payments")
public class Payment extends Audit {

    @Id
    private String id;

    private String recipientName;

    private String recipientPhone;

    private String comissionAmount;

    private BigDecimal amount;

    private String customerPhone;

}
