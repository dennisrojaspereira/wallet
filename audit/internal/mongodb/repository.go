package mongodb

import (
	"audit/internal/models"
	"context"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
	"log"
)

func SaveEvent(client *mongo.Client, dbName, collectionName string, event models.Event) error {
	collection := client.Database(dbName).Collection(collectionName)

	_, err := collection.InsertOne(context.Background(), bson.M{
		"message":   event.Message,
		"topic":     event.Topic,
		"partition": event.Partition,
		"offset":    event.Offset,
	})

	if err != nil {
		log.Printf("Error inserting event into MongoDB: %v", err)
		return err
	}
	return nil
}
