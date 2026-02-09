#!/bin/bash
PROJECT_NAME="virtual-threads-demo"
PORT=8080

echo "Stopping $PROJECT_NAME..."

# Stop native Java process
if [ -f $PROJECT_NAME/logs/application.log ]; then
    PID=$(ps aux | grep "[j]ava.*$PROJECT_NAME.*jar" | awk '{print $2}')
    if [ ! -z "$PID" ]; then
        echo "Stopping native Java process (PID: $PID)..."
        kill $PID 2>/dev/null
        sleep 2
    fi
fi

# Stop Docker container
if docker ps -a | grep -q "$PROJECT_NAME-container"; then
    echo "Stopping Docker container..."
    docker stop $PROJECT_NAME-container > /dev/null 2>&1
    docker rm $PROJECT_NAME-container > /dev/null 2>&1
fi

# Remove Docker image
if docker images | grep -q "$PROJECT_NAME-image"; then
    echo "Removing Docker image..."
    docker rmi $PROJECT_NAME-image > /dev/null 2>&1
fi

# Check for processes on port
LSOF=$(lsof -ti:$PORT 2>/dev/null)
if [ ! -z "$LSOF" ]; then
    echo "Killing processes on port $PORT..."
    kill -9 $LSOF 2>/dev/null
fi

echo "Cleanup complete."
