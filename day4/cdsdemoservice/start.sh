#!/bin/bash
set -e

PROJECT_NAME="cdsdemoservice"
SPRING_BOOT_VERSION="3.2.5"
PORT=8080
JAR_PATH="./target/${PROJECT_NAME}-${SPRING_BOOT_VERSION}.jar"
CDS_ARCHIVE_NAME="app.jsa"

cd "$(dirname "$0")/${PROJECT_NAME}" || exit 1

if [ ! -f "${JAR_PATH}" ]; then
    echo "ERROR: JAR file not found at ${JAR_PATH}. Please run setup.sh first."
    exit 1
fi

if [ -f "${CDS_ARCHIVE_NAME}" ]; then
    echo "Starting application WITH CDS..."
    java -XX:SharedArchiveFile=${CDS_ARCHIVE_NAME} -jar ${JAR_PATH} > app.log 2>&1 &
    echo $! > app.pid
    echo "Application started with PID: $(cat app.pid)"
    echo "Using CDS archive: ${CDS_ARCHIVE_NAME}"
else
    echo "Starting application WITHOUT CDS..."
    java -jar ${JAR_PATH} > app.log 2>&1 &
    echo $! > app.pid
    echo "Application started with PID: $(cat app.pid)"
    echo "CDS archive not found, running without CDS"
fi

echo "Waiting for application to start..."
timeout 60 bash -c "until curl -s http://localhost:${PORT}/actuator/health > /dev/null; do sleep 1; done" || {
    echo "ERROR: Application failed to start in time!"
    if [ -f app.pid ]; then
        kill $(cat app.pid) 2>/dev/null || true
        rm -f app.pid
    fi
    exit 1
}

echo "Application is running!"
echo "Health check: $(curl -s http://localhost:${PORT}/actuator/health)"
echo "Metrics endpoint: http://localhost:${PORT}/actuator/metrics"
echo "Demo metrics: http://localhost:${PORT}/metrics/demo"
echo "Logs: tail -f app.log"
