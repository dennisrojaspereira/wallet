package balance;

import io.smallrye.reactive.messaging.annotations.Blocking;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BalanceConsumer {

    @Incoming("event-audits")
    @Blocking
    public void consumeBalanceEvent(String message) {
        System.out.println("Message Received: " + message);


        String[] parts = message.split(":");
        String transactionId = parts[0];
        Long userId = Long.parseLong(parts[1]);
        String type = parts[2];
        double amount = Double.parseDouble(parts[2]);

        Balance balanceEntity = Balance.find("userId", userId).firstResult();
        if (balanceEntity == null) {
            balanceEntity = new Balance();
            balanceEntity.userId = userId;
        }
        balanceEntity.balance = amount;
        balanceEntity.persist();
    }
}
