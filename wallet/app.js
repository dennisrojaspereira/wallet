// app.js
const express = require('express');
const { sendMessageToKafka, consumeMessagesFromKafka, getWalletBalance, cacheWalletBalance } = require('./services/walletService');

const app = express();
const port = 3000;



app.get('/get-balance/:userId', (req, res) => {
    const userId = req.params.userId;
    getWalletBalance(userId, (err, balance) => {
        if (err) {
            return res.status(500).send('Error to get Balance');
        }
        cacheWalletBalance(userId, balance);
        res.send(`Balance user ${userId}: ${balance}`);
    });
});


consumeMessagesFromKafka().catch(console.error);

app.listen(port, () => {
    console.log(`Wallet Service running ${port}`);
});
