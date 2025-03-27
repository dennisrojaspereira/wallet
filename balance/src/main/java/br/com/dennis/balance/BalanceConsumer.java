package br.com.dennis.balance;

import io.smallrye.reactive.messaging.annotations.Blocking;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BalanceConsumer {

    @Incoming("balance-events") // Nome do canal Kafka
    @Blocking
    public void consumeBalanceEvent(String message) {
        System.out.println("Mensagem recebida do Kafka: " + message);

        String[] parts = message.split(":");
        Long userId = Long.parseLong(parts[0]);
        double balance = Double.parseDouble(parts[1]);

        Balance balanceEntity = Balance.find("userId", userId).firstResult();
        if (balanceEntity == null) {
            balanceEntity = new Balance();
            balanceEntity.userId = userId;
        }
        balanceEntity.balance = balance;
        balanceEntity.persist();
    }
}
