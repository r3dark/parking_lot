#!/bin/bash

## This file will run the test suite and build an executable jar with dependencies

echo "Running test suite..."
gradle clean test
echo "Test suite execution complete. To view report, open: parking_lot/build/reports/tests/test/index.html"
echo "Building application..."
gradle clean build --refresh-dependencies

