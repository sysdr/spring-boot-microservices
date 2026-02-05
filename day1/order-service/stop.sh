#!/bin/bash
# Stop script for order-service

DOCKER_CONTAINER_NAME="order-service-container"
DOCKER_IMAGE_NAME="order-service-app"
PROJECT_DIR="./order-service"

echo "--- Stopping order-service ---"

# Stop Docker container if running
if docker ps -a --format '{{.Names}}' | grep -q "^order-service-container$"; then
    echo "Stopping Docker container: order-service-container"
    docker stop "order-service-container" > /dev/null 2>&1 || true
    docker rm "order-service-container" > /dev/null 2>&1 || true
    echo "Docker container stopped and removed."
else
    echo "Docker container not found."
fi

# Remove Docker image if exists
if docker images --format '{{.Repository}}' | grep -q "^order-service-app$"; then
    echo "Removing Docker image: order-service-app"
    docker rmi "order-service-app" > /dev/null 2>&1 || true
    echo "Docker image removed."
fi

# Kill any running Java processes for order-service
if [ -f "/home/systemdr/git/spring-boot-microservices/day1/order-service/target/order-service-0.0.1-SNAPSHOT.jar" ]; then
    echo "Checking for running order-service processes..."
    pkill -f "order-service-0.0.1-SNAPSHOT.jar" > /dev/null 2>&1 || true
    echo "Order service processes stopped."
fi

echo "--- Cleanup complete ---"
