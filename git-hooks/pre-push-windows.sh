#!C:/Program\ Files/Git/usr/bin/sh.exe

echo "Running static analysis."

./gradlew lintKotlin
./gradlew detektAll
echo "Running static analysis completed"