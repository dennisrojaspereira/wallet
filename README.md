# Wallet

The system offers the following main features for users:

Create Wallet: Allows the creation of wallets for users.

Retrieve Balance: Allows users to view their wallet balance.

Retrieve Historical Balance: Retrieves the balance of a user's wallet at a specific point in the past.

Deposit Funds: Allows users to deposit money into their wallets.

Withdraw Funds: Allows users to withdraw money from their wallets.

Transfer Funds: Facilitates the transfer of money between user wallets.

# Architecture
![image](https://github.com/user-attachments/assets/2e3a3208-49cb-4ce5-b8fb-52e0d3fa3476)



![Wallet](https://github.com/user-attachments/assets/57c3848b-b180-433e-b9b5-480fe7af83ac)

Main Components:
API Gateway: Handles incoming requests and routes them to the appropriate services.

Auth Rate Limit: Rate limit for authentication requests.

Auth User Service: User authentication service (uses Keycloak for identity management).

Wallet Service: Manages user wallets (Node.js and MySQL).

Balance Service: Manages and calculates wallet balances (Redis).

Transaction Service: Manages money transfers between wallets (Java and Spring).

Audit Service: Records all transactions and critical events for auditing (Go and MongoDB).

Kafka: Used for messaging and event handling between services.

Jaeger: Distributed tracing tool for monitoring performance.

# Sequence Diagram
API Gateway receives user requests and forwards them to the appropriate services.

Auth Rate Limit controls the number of authentication requests, preventing system overload.

Auth User Service authenticates users using Keycloak.

The Wallet Service allows the creation and management of user wallets, using MySQL to store the information.

The Balance Service manages wallet balances and uses Redis for real-time balance caching.

The Transaction Service manages money transfers between wallets and uses PostgreSQL to persist transactions.

The Audit Service records all transactions and critical events using MongoDB.

Kafka ensures asynchronous communication and event exchange between services.

Jaeger is used for tracking and monitoring the performance of each service in real-time.

# Technologies
Node.js (for Wallet Service)

Java + Spring (for Transaction Service)

Go (for Audit Service)

MySQL (for wallet data storage)

PostgreSQL (for transactions)

Redis (for balance caching)

MongoDB (for auditing)

Keycloak (for user authentication)

Kafka (for messaging)

Jaeger (for distributed tracing)


# Running the Project
## Prerequisites

Docker

Java 11 or later

Node.js

Go

Kafka

# To run 
Clone the repository.


1 ) docker-compose up

2 ) docker exec -it kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic audit-events --from-beginning

3) In folder transaction "execute gradle bootRun".

4 ) curl -X POST "http://localhost:8081/transactions"      -H "Content-Type: application/x-www-form-urlencoded"      -d "walletId=12345"      -d "type=deposit"      -d "amount=100.50"

5) In folder audit execute "go run cmd/main.go"

6) In folder balance execute "gradle quarkusDev" 

5) In folder wallet execute "node app.js" 





Running Tests To run unit and integration tests:

Wallet Service (Node.js):

npm test
Transaction Service (Java/Spring):


mvn test
Audit Service (Go):

go test
Monitoring and Tracing Access the Jaeger dashboard to view service traces in real-time.

# Contributing
Fork the repository.

Create a new branch for each new feature or bug fix.

Make changes and submit a pull request with a description of the changes.

License
This project is licensed under the MIT License - see the LICENSE file for details.
