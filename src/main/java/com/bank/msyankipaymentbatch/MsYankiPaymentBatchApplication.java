package com.bank.msyankipaymentbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MsYankiPaymentBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsYankiPaymentBatchApplication.class, args);
	}

}
