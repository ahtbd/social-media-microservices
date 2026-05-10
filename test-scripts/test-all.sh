#!/bin/bash

GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m'

BASE_URL="http://localhost:8080"
PASS=0
FAIL=0
RANDOM_SUFFIX=$RANDOM

echo "========================================="
echo "  Social Media API - Semi-Manual Tests"
echo "========================================="
echo ""

# Test 1: Register User
echo "Test 1: Register User"
RESPONSE=$(curl -s -X POST $BASE_URL/api/users/register \
  -H "Content-Type: application/json" \
  -d "{\"username\":\"testuser${RANDOM_SUFFIX}\",\"email\":\"test${RANDOM_SUFFIX}@test.com\",\"password\":\"pass123\",\"fullName\":\"Test User\"}")
if echo "$RESPONSE" | grep -q '"id"'; then
    echo -e "${GREEN}PASS${NC}: User registered"
    USER_ID=$(echo "$RESPONSE" | grep -o '"id":"[^"]*"' | head -1 | cut -d'"' -f4)
    ((PASS++))
else
    echo -e "${RED}FAIL${NC}: $RESPONSE"
    ((FAIL++))
fi

# Test 2: Get User
echo ""
echo "Test 2: Get User by ID"
RESPONSE=$(curl -s $BASE_URL/api/users/$USER_ID)
if echo "$RESPONSE" | grep -q "testuser"; then
    echo -e "${GREEN}PASS${NC}: User found"
    ((PASS++))
else
    echo -e "${RED}FAIL${NC}: $RESPONSE"
    ((FAIL++))
fi

# Test 3: Create Post
echo ""
echo "Test 3: Create Post"
RESPONSE=$(curl -s -X POST $BASE_URL/api/posts \
  -H "Content-Type: application/json" \
  -d "{\"userId\":\"$USER_ID\",\"content\":\"Script test post ${RANDOM_SUFFIX}\"}")
if echo "$RESPONSE" | grep -q '"id"'; then
    echo -e "${GREEN}PASS${NC}: Post created"
    POST_ID=$(echo "$RESPONSE" | grep -o '"id":"[^"]*"' | head -1 | cut -d'"' -f4)
    ((PASS++))
else
    echo -e "${RED}FAIL${NC}: $RESPONSE"
    ((FAIL++))
fi

# Test 4: Get All Posts
echo ""
echo "Test 4: Get All Posts"
RESPONSE=$(curl -s $BASE_URL/api/posts)
if echo "$RESPONSE" | grep -q "Script test post"; then
    echo -e "${GREEN}PASS${NC}: Posts retrieved"
    ((PASS++))
else
    echo -e "${RED}FAIL${NC}: $RESPONSE"
    ((FAIL++))
fi

# Test 5: Add Comment
echo ""
echo "Test 5: Add Comment"
RESPONSE=$(curl -s -X POST $BASE_URL/api/comments \
  -H "Content-Type: application/json" \
  -d "{\"postId\":\"$POST_ID\",\"userId\":\"$USER_ID\",\"content\":\"Script test comment\"}")
if echo "$RESPONSE" | grep -q '_id'; then
    echo -e "${GREEN}PASS${NC}: Comment added"
    ((PASS++))
else
    echo -e "${RED}FAIL${NC}: $RESPONSE"
    ((FAIL++))
fi

# Test 6: Toggle Like
echo ""
echo "Test 6: Toggle Like (Add)"
RESPONSE=$(curl -s -X POST "$BASE_URL/api/likes/toggle?userId=$USER_ID&postId=$POST_ID")
if echo "$RESPONSE" | grep -q '"liked":true'; then
    echo -e "${GREEN}PASS${NC}: Like added"
    ((PASS++))
else
    echo -e "${RED}FAIL${NC}: $RESPONSE"
    ((FAIL++))
fi

# Test 7: Like Count
echo ""
echo "Test 7: Get Like Count"
RESPONSE=$(curl -s $BASE_URL/api/likes/count/$POST_ID)
if echo "$RESPONSE" | grep -q '"likeCount"'; then
    echo -e "${GREEN}PASS${NC}: Like count retrieved"
    ((PASS++))
else
    echo -e "${RED}FAIL${NC}: $RESPONSE"
    ((FAIL++))
fi

# Summary
echo ""
echo "========================================="
echo -e "Results: ${GREEN}$PASS Passed${NC}, ${RED}$FAIL Failed${NC}"
echo "Total: $((PASS + FAIL)) tests"
echo "========================================="