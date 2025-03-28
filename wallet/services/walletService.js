// services/walletService.js
const { producer, consumer } = require('../config/kafka');
const { mysqlConnection, redisClient } = require('../config/database');




const consumeMessagesFromKafka = async () => {
    await consumer.connect();
    await consumer.subscribe({ topic: 'audit-events', fromBeginning: true });

    await consumer.run({
        eachMessage: async ({ topic, partition, message }) => {
            console.log(`Mensagem recebida: ${message.value.toString()}`);

            cacheWalletBalance(userId, balance);

        },
    });
};


const getWalletBalance = (userId, callback) => {
    mysqlConnection.execute(
        'SELECT balance FROM wallets WHERE user_id = ?',
        [userId],
        (err, results) => {
            if (err) {
                console.error('Erro ao consultar o saldo:', err);
                return callback(err);
            }
            callback(null, results[0]?.balance || 0);
        }
    );
};


const cacheWalletBalance = (userId, balance) => {
    redisClient.setex(`wallet_balance_${userId}`, 3600, balance, (err, reply) => {
        if (err) {
            console.error('Erro ao salvar no Redis:', err);
        } else {
            console.log('Saldo armazenado em cache no Redis:', reply);
        }
    });
};

module.exports = { sendMessageToKafka, consumeMessagesFromKafka, getWalletBalance, cacheWalletBalance };
