#!/bin/bash
cd "$(dirname "$0")" || exit
if [ "$1" == "--docker" ]; then
    CONTAINER_NAME="loomdemo-container"
    if docker ps -q --filter "name=${CONTAINER_NAME}" | grep -q .; then
        echo "Stopping Docker container: ${CONTAINER_NAME}"
        docker stop ${CONTAINER_NAME}
        docker rm ${CONTAINER_NAME}
        echo "Docker container stopped and removed."
    else
        echo "Docker container ${CONTAINER_NAME} is not running."
    fi
else
    PID_FILE="app.pid"
    if [ -f "${PID_FILE}" ]; then
        APP_PID=$(cat "${PID_FILE}")
        if ps -p ${APP_PID} > /dev/null; then
            echo "Stopping application with PID: ${APP_PID}"
            kill ${APP_PID}
            rm "${PID_FILE}"
            echo "Application stopped."
        else
            echo "Application with PID ${APP_PID} is not running."
            rm "${PID_FILE}"
        fi
    else
        echo "PID file not found. Checking for running Java processes..."
        PIDS=$(pgrep -f "java.*LoomDemo")
        if [ -n "${PIDS}" ]; then
            echo "Found running processes: ${PIDS}"
            echo "${PIDS}" | xargs kill
            echo "Application processes stopped."
        else
            echo "No running application found."
        fi
    fi
fi
