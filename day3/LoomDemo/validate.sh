#!/bin/bash

echo "=== Validation Script ==="
echo ""

# Check if application is running
echo "1. Checking for running instances..."
RUNNING=$(ps aux | grep -E "java.*LoomDemo.*jar" | grep -v grep | wc -l)
echo "   Running instances: $RUNNING"
if [ "$RUNNING" -gt 1 ]; then
    echo "   WARNING: Multiple instances detected!"
    ps aux | grep -E "java.*LoomDemo.*jar" | grep -v grep
elif [ "$RUNNING" -eq 1 ]; then
    echo "   ✓ Single instance running"
else
    echo "   ✗ No instances running"
fi
echo ""

# Check port 8082
echo "2. Checking port 8082..."
if ss -tlnp 2>/dev/null | grep -q ":8082"; then
    echo "   ✓ Port 8082 is in use"
else
    echo "   ✗ Port 8082 is not in use"
fi
echo ""

# Test health endpoint
echo "3. Testing health endpoint..."
HEALTH=$(curl -s http://localhost:8082/actuator/health 2>&1)
if echo "$HEALTH" | grep -q "UP\|status"; then
    echo "   ✓ Health endpoint responding"
    echo "   Response: $HEALTH"
else
    echo "   ✗ Health endpoint not responding"
    echo "   Response: $HEALTH"
fi
echo ""

# Test API endpoint
echo "4. Testing API endpoint..."
API_RESPONSE=$(curl -s "http://localhost:8082/api/process?data=test" 2>&1)
if echo "$API_RESPONSE" | grep -q "Processed"; then
    echo "   ✓ API endpoint working"
    echo "   Response: $API_RESPONSE"
else
    echo "   ✗ API endpoint not working"
    echo "   Response: $API_RESPONSE"
fi
echo ""

# Test metrics endpoint
echo "5. Testing metrics endpoint..."
METRICS=$(curl -s "http://localhost:8082/actuator/metrics" 2>&1)
if echo "$METRICS" | grep -q "names\|metrics"; then
    echo "   ✓ Metrics endpoint responding"
    METRIC_COUNT=$(echo "$METRICS" | python3 -c "import sys, json; d=json.load(sys.stdin); print(len(d.get('names', [])))" 2>/dev/null || echo "N/A")
    echo "   Available metrics: $METRIC_COUNT"
else
    echo "   ✗ Metrics endpoint not responding"
fi
echo ""

# Test Prometheus endpoint
echo "6. Testing Prometheus endpoint..."
PROM=$(curl -s "http://localhost:8082/actuator/prometheus" 2>&1 | head -5)
if echo "$PROM" | grep -q "jvm\|http"; then
    echo "   ✓ Prometheus endpoint responding"
    echo "   Sample metrics:"
    echo "$PROM" | head -3 | sed 's/^/     /'
else
    echo "   ✗ Prometheus endpoint not responding"
fi
echo ""

echo "=== Validation Complete ==="


