#!C:/Program\ Files/Git/usr/bin/sh.exe

echo "Running static analysis."

./gradlew lintKotlin
./gradlew detekt
echo "Running static analysis completed"