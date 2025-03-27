package mongodb

import (
	"audit/internal/models"
	"context"
	"github.com/stretchr/testify/assert"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo/integration/mtest"
	"testing"
)

func TestSaveEvent(t *testing.T) {

	mt := mtest.New(t, mtest.NewOptions().SetClusterSize(1))
	defer mt.Close()

	event := models.Event{
		Message:   "Test Event",
		Topic:     "audit-events",
		Partition: 0,
		Offset:    100,
	}

	collection := mt.CreateCollection(t, "auditEvents")

	err := SaveEvent(mt.Client, "auditDB", "auditEvents", event)
	assert.NoError(t, err)

	var result bson.M
	err = collection.FindOne(context.Background(), bson.M{"message": "Test Event"}).Decode(&result)
	assert.NoError(t, err)
	assert.Equal(t, "Test Event", result["message"])
	assert.Equal(t, "audit-events", result["topic"])
}
