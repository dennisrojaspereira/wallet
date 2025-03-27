package br.com.dennis.transaction;


import br.com.dennis.transaction.model.TransactionEvent;
import br.com.dennis.transaction.service.TransactionProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;
import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import static org.mockito.Mockito.*;


public class TransactionProducerTest {

    @Test
    public void testPublishTransaction_sendsMessageToKafka() throws Exception {
        KafkaTemplate<String, String> kafkaTemplate = Mockito.mock(KafkaTemplate.class);
        TransactionProducer producer = new TransactionProducer(kafkaTemplate,new ObjectMapper().registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule()));

        TransactionEvent event = new TransactionEvent(
                "tx123", "wallet1", "deposit", new BigDecimal("100.00"), Instant.now()
        );

        producer.publishTransaction(event);

        verify(kafkaTemplate, times(1)).send(eq("transaction.executed"), any(String.class));
    }
}