package kafka

import (
	"context"
	"fmt"
	"github.com/segmentio/kafka-go"
	"log"
)

type Consumer struct {
	Reader *kafka.Reader
}

func NewConsumer(broker, topic string) (*Consumer, error) {
	if broker == "" || topic == "" {
		return nil, fmt.Errorf("broker or topic cannot be empty")
	}

	log.Println("Criando o consumidor Kafka...")
	reader := kafka.NewReader(kafka.ReaderConfig{
		Brokers:     []string{broker},
		Topic:       topic,
		StartOffset: kafka.FirstOffset, // Começa do primeiro offset
	})

	if reader == nil {
		log.Println("Falha ao criar o Kafka Reader!")
		return nil, fmt.Errorf("failed to create Kafka consumer: Reader is nil")
	}

	log.Println("Kafka Reader criado com sucesso!")

	return &Consumer{Reader: reader}, nil
}

func (c *Consumer) Consume() (kafka.Message, error) {
	if c.Reader == nil {
		log.Println("Reader do Kafka é nil")
		return kafka.Message{}, fmt.Errorf("kafka reader is nil")
	}

	log.Println("Lendo a mensagem do Kafka...")
	msg, err := c.Reader.ReadMessage(context.Background()) // Usando context.Background()
	if err != nil {
		log.Printf("Error reading message: %v", err)
		return kafka.Message{}, err
	}

	log.Printf("Mensagem consumida: %s", string(msg.Value))
	return msg, nil
}
