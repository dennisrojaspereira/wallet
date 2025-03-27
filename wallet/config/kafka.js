// config/kafka.js
const { Kafka } = require('kafkajs');

const kafka = new Kafka({
    clientId: 'wallet-service',
    brokers: ['localhost:9092'],
});

const producer = kafka.producer();
const consumer = kafka.consumer({ groupId: 'wallet-group' });

module.exports = { producer, consumer };
