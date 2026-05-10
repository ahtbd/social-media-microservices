const express = require('express');
const router = express.Router();
const commentController = require('../controllers/commentController');

// POST /api/comments - Create comment
router.post('/', commentController.createComment);

// GET /api/comments/post/:postId - Get post comments
router.get('/post/:postId', commentController.getCommentsByPost);

// DELETE /api/comments/:id - Delete comment
router.delete('/:id', commentController.deleteComment);

module.exports = router;