#!/usr/bin/env python3
"""Generate stop.sh and start.sh scripts"""

stop_sh = '''#!/bin/bash
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
'''

start_sh = '''#!/bin/bash
PROJECT_NAME="virtual-threads-demo"
PORT=8080
LOG_FILE="logs/application.log"

cd $PROJECT_NAME || exit 1

# Check if JAR exists
JAR_FILE=$(ls target/$PROJECT_NAME-*.jar 2>/dev/null | head -1)
if [ -z "$JAR_FILE" ]; then
    echo "JAR file not found. Building project..."
    mvn clean package -DskipTests
    JAR_FILE=$(ls target/$PROJECT_NAME-*.jar 2>/dev/null | head -1)
fi

if [ -z "$JAR_FILE" ]; then
    echo "Failed to find or build JAR file."
    exit 1
fi

# Check if already running
if lsof -ti:$PORT > /dev/null 2>&1; then
    echo "Port $PORT is already in use. Stopping existing process..."
    lsof -ti:$PORT | xargs kill -9 2>/dev/null
    sleep 2
fi

echo "Starting application..."
mkdir -p logs
java -jar $JAR_FILE > $LOG_FILE 2>&1 &
APP_PID=$!
echo "Application started with PID: $APP_PID"
echo "Logs: $PROJECT_NAME/$LOG_FILE"
echo "Health check: http://localhost:$PORT/actuator/health"
echo "Metrics: http://localhost:$PORT/actuator/metrics"
cd ..
'''

with open('stop.sh', 'w') as f:
    f.write(stop_sh)

with open('start.sh', 'w') as f:
    f.write(start_sh)

import os
os.chmod('stop.sh', 0o755)
os.chmod('start.sh', 0o755)

print("Generated stop.sh and start.sh")

