package br.com.dennis.transaction.controller;


import br.com.dennis.transaction.model.TransactionEvent;
import br.com.dennis.transaction.service.TransactionProducer;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

//curl -X POST "http://localhost:8081/transactions"      -H "Content-Type: application/x-www-form-urlencoded"      -d "walletId=12345"      -d "type=deposit"      -d "amount=100.50"

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionProducer producer;

    public TransactionController(TransactionProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public String createTransaction(@RequestParam String walletId,
                                    @RequestParam String type,
                                    @RequestParam BigDecimal amount) {

        TransactionEvent event = new TransactionEvent(
                UUID.randomUUID().toString(),
                walletId,
                type,
                amount,
                Instant.now()
        );

        producer.publishTransaction(event);

        return "Transaction created and event published.";
    }
}