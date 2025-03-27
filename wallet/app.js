// app.js
const express = require('express');
const { sendMessageToKafka, consumeMessagesFromKafka, getWalletBalance, cacheWalletBalance } = require('./services/walletService');

const app = express();
const port = 3000;


app.get('/send-message', async (req, res) => {
    const message = 'Teste de envio de mensagem para Kafka';
    await sendMessageToKafka(message);
    res.send('Mensagem enviada para o Kafka');
});


app.get('/get-balance/:userId', (req, res) => {
    const userId = req.params.userId;
    getWalletBalance(userId, (err, balance) => {
        if (err) {
            return res.status(500).send('Erro ao consultar o saldo');
        }
        cacheWalletBalance(userId, balance);
        res.send(`Saldo do usuário ${userId}: ${balance}`);
    });
});


consumeMessagesFromKafka().catch(console.error);

app.listen(port, () => {
    console.log(`Wallet Service rodando na porta ${port}`);
});
