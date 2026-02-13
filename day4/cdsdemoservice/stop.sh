#!/bin/bash

PROJECT_NAME="cdsdemoservice"

cd "$(dirname "$0")/${PROJECT_NAME}" || exit 1

if [ -f app.pid ]; then
    PID=$(cat app.pid)
    if ps -p ${PID} > /dev/null 2>&1; then
        echo "Stopping application (PID: ${PID})..."
        kill ${PID}
        wait ${PID} 2>/dev/null || true
        echo "Application stopped."
    else
        echo "Application process not found (PID: ${PID})."
    fi
    rm -f app.pid
else
    echo "No PID file found. Application may not be running."
fi

# Also check for any running Java processes with the JAR
JAR_PID=$(pgrep -f "cdsdemoservice-3.2.5.jar" || true)
if [ ! -z "${JAR_PID}" ]; then
    echo "Found additional Java process(es): ${JAR_PID}"
    echo "Killing additional processes..."
    pkill -f "cdsdemoservice-3.2.5.jar" || true
fi

# Stop Docker containers if they exist
if command -v docker &> /dev/null; then
    echo "Stopping Docker containers..."
    docker rm -f ${PROJECT_NAME}-no-cds &> /dev/null || true
    docker rm -f ${PROJECT_NAME}-with-cds &> /dev/null || true
    echo "Docker containers stopped."
fi

echo "Cleanup complete."
