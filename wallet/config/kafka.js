// config/kafka.js
const { Kafka } = require('kafkajs');

const kafka = new Kafka({
    clientId: 'wallet-service',
    brokers: ['localhost:9092'],
});

const producer = kafka.producer();
const consumer = kafka.consumer({ groupId: 'audit-event' });

module.exports = { producer, consumer };
