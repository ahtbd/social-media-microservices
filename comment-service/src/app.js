const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const commentRoutes = require('./routes/commentRoutes');

const app = express();
const PORT = process.env.PORT || 8083;

app.use(cors());
app.use(express.json());

const MONGO_URI = process.env.MONGO_URI || 'mongodb://admin:admin123@mongo-comment:27017/comments_db?authSource=admin';

// Connect with retry
const connectWithRetry = () => {
    mongoose.connect(MONGO_URI)
        .then(() => console.log('Connected to MongoDB'))
        .catch(err => {
            console.error('MongoDB connection error:', err.message);
            setTimeout(connectWithRetry, 5000);
        });
};

connectWithRetry();

app.use('/api/comments', commentRoutes);

app.get('/health', (req, res) => {
    res.json({ status: 'UP', service: 'Comment Service', mongo: mongoose.connection.readyState === 1 ? 'connected' : 'disconnected' });
});

app.listen(PORT, () => {
    console.log(`Comment Service running on port ${PORT}`);
});