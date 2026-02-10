#!/bin/bash
cd "$(dirname "$0")" || exit
JAR_FILE=$(find target -name "*.jar" | grep -v "sources.jar" | grep -v "javadoc.jar")
if [ -z "$JAR_FILE" ]; then
    echo "Error: JAR file not found. Please run setup.sh first."
    exit 1
fi
echo "Starting application with JAR: ${JAR_FILE}"
java -jar "${JAR_FILE}" > app.log 2>&1 &
APP_PID=$!
echo "${APP_PID}" > app.pid
echo "Application started with PID: ${APP_PID}"
echo "Logs are being written to app.log"
echo "Waiting for application to start..."
sleep 5
if ps -p ${APP_PID} > /dev/null; then
    echo "Application is running. PID: ${APP_PID}"
    echo "Service URL: http://localhost:8082/api/process?data=test"
    echo "Metrics: http://localhost:8082/actuator/metrics"
    echo "Health: http://localhost:8082/actuator/health"
else
    echo "Error: Application failed to start. Check app.log for details."
    exit 1
fi
