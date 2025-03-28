// services/walletService.js
const { producer, consumer } = require('../config/kafka');
const { mysqlConnection, redisClient } = require('../config/database');




const consumeMessagesFromKafka = async () => {
    await consumer.connect();
    await consumer.subscribe({ topic: 'audit-events', fromBeginning: true });

    await consumer.run({
        eachMessage: async ({ topic, partition, message }) => {
            console.log(`Message received: ${message.value.toString()}`);


        },
    });
};




module.exports = {  consumeMessagesFromKafka };
