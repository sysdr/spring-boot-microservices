#!/bin/bash
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
