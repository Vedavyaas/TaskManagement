#!/bin/bash

# Configuration
BASE_URL="http://localhost:8000/api"
EMAIL="testuser@amdox.com"
PASSWORD="password123"

echo "1. Registering User..."
curl -X POST "$BASE_URL/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "userName": "Test User",
    "userOfficialEmail": "'"$EMAIL"'",
    "password": "'"$PASSWORD"'",
    "role": "DEVELOPER"
  }'
echo -e "\n"

echo "2. Logging in..."
LOGIN_RESPONSE=$(curl -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "useOfficialEmail": "'"$EMAIL"'",
    "password": "'"$PASSWORD"'"
  }')
echo "Response: $LOGIN_RESPONSE"

TOKEN=$(echo $LOGIN_RESPONSE | grep -o '"token":"[^"]*' | cut -d'"' -f4)
echo "Token: $TOKEN"

if [ -z "$TOKEN" ]; then
  echo "Login failed, cannot proceed."
  exit 1
fi

AUTH_HEADER="Authorization: Bearer $TOKEN"

echo -e "\n3. Creating Board..."
BOARD_RESPONSE=$(curl -X POST "$BASE_URL/boards" \
  -H "Content-Type: application/json" \
  -H "$AUTH_HEADER" \
  -d '{
    "name": "Dev Board",
    "projectKey": "DEV",
    "boardType": "KANBAN"
  }')
echo "Response: $BOARD_RESPONSE"

echo -e "\n4. Creating Sprint..."
SPRINT_RESPONSE=$(curl -X POST "$BASE_URL/sprints/create" \
  -H "Content-Type: application/json" \
  -H "$AUTH_HEADER" \
  -d '{
    "sprintName": "Sprint 1",
    "projectId": 1
  }')
echo "Response: $SPRINT_RESPONSE"

echo -e "\n5. Creating Issue..."
ISSUE_RESPONSE=$(curl -X POST "$BASE_URL/issues/create" \
  -H "Content-Type: application/json" \
  -H "$AUTH_HEADER" \
  -d '{
    "issueKey": "DEV-1",
    "issueTitle": "Fix Login Bug",
    "desription": "Login fails randomly",
    "issueType": "BUG",
    "priority": "HIGH",
    "status": "OPEN",
    "assigneeEmail": "'"$EMAIL"'",
    "repoterEmail": "'"$EMAIL"'"
  }')
echo "Response: $ISSUE_RESPONSE"
ISSUE_ID=1 # Assumption for H2 DB starting fresh

echo -e "\n6. Assigning Issue to Sprint..."
curl -X PUT "$BASE_URL/sprints/assign/1/$ISSUE_ID" \
  -H "$AUTH_HEADER"
echo -e "\n"

echo "Test Complete."
