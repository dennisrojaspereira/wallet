package main

import (
	"audit/internal/kafka"
	"audit/internal/models"
	"audit/internal/mongodb"
	"github.com/joho/godotenv"
	"log"
	"os"
)

// docker exec -it kafka kafka-console-producer --broker-list localhost:9092 --topic audit-events

import (
	"context"
)

func main() {

	err := godotenv.Load()
	if err != nil {
		log.Fatalf("Error loading .env file")
	}

	kafkaBroker := os.Getenv("KAFKA_BROKER")
	topic := os.Getenv("KAFKA_TOPIC")
	mongoURI := os.Getenv("MONGO_URI")
	dbName := os.Getenv("MONGO_DB")
	collectionName := os.Getenv("MONGO_COLLECTION")

	if kafkaBroker == "" || topic == "" || mongoURI == "" || dbName == "" || collectionName == "" {
		log.Fatal("One or more environment variables are missing.")
	}

	mongoClient, err := mongodb.NewClient(mongoURI)
	if err != nil {
		log.Fatalf("Failed to connect to MongoDB: %v", err)
	}
	defer mongoClient.Disconnect(context.Background()) // Passando o contexto

	consumer, err := kafka.NewConsumer(kafkaBroker, topic)
	if err != nil {
		log.Fatalf("Failed to create Kafka consumer: %v", err)
	}

	for {
		msg, err := consumer.Consume()
		if err != nil {
			log.Fatalf("Error consuming message: %v", err)
		}

		event := models.Event{
			Message:   string(msg.Value),
			Topic:     msg.Topic,
			Partition: msg.Partition,
			Offset:    msg.Offset,
		}

		err = mongodb.SaveEvent(mongoClient, dbName, collectionName, event)
		if err != nil {
			log.Printf("Error saving event to MongoDB: %v", err)
		} else {
			log.Printf("Event saved: %s", event.Message)
		}
	}
}
