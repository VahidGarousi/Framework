#!/bin/bash

echo "Running static analysis."

./gradlew lintKotlin
./gradlew detektAll
echo "Running static analysis completed"