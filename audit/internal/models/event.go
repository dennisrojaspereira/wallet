package models

type Event struct {
	Message   string `bson:"message"`
	Topic     string `bson:"topic"`
	Partition int    `bson:"partition"`
	Offset    int64  `bson:"offset"`
}
