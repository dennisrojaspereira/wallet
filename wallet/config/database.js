
const mysql = require('mysql2');
const redis = require('redis');


const mysqlConnection = mysql.createConnection({
    host: 'localhost',
    user: 'user',
    password: 'password',
    database: 'wallet_db',
});


const redisClient = redis.createClient({
    host: 'localhost',
    port: 6379,
});

module.exports = { mysqlConnection, redisClient };
