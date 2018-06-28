#!/bin/bash

if [ $# -gt 0 ]; then
    exec "$@"
fi

exec java -cp "$ARTIFACT_TARGET_PATH" $JAVA_OPTS com.example.Service $CONFIG_PATH