const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const commentRoutes = require('./routes/commentRoutes');

const app = express();
const PORT = process.env.PORT || 8083;

// Middleware
app.use(cors());
app.use(express.json());

// MongoDB Connection
const MONGO_URI = 'mongodb://admin:admin123@localhost:27017/comments_db?authSource=admin';

mongoose.connect(MONGO_URI)
    .then(() => console.log('Connected to MongoDB'))
    .catch(err => console.error('MongoDB connection error:', err));

// Routes
app.use('/api/comments', commentRoutes);

// Health Check
app.get('/health', (req, res) => {
    res.json({ status: 'UP', service: 'Comment Service' });
});

app.listen(PORT, () => {
    console.log(`Comment Service running on port ${PORT}`);
});