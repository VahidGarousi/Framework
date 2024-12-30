#!/bin/bash

echo "Running static analysis."

./gradlew lintKotlin
echo "Running static analysis completed"