package br.com.dennis.transaction.service;


import br.com.dennis.transaction.model.TransactionEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public TransactionProducer(KafkaTemplate<String, String> kafkaTemplate,ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishTransaction(TransactionEvent event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("transaction.executed", json);
            System.out.println("Event sent: " + json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}