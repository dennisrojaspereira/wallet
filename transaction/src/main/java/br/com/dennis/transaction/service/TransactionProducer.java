package br.com.dennis.transaction.service;


import br.com.dennis.transaction.model.TransactionEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final TransactionRepository transactionRepository;


    public TransactionProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper, TransactionRepository transactionRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.transactionRepository = transactionRepository;
    }

    public void publishTransaction(TransactionEvent event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("audit-events", json);
            System.out.println("Event sent: " + json);
            transactionRepository.save(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}