#!/bin/sh
set -e

MAVEN_PROJECTBASEDIR="$(cd "$(dirname "$0")" && pwd)"
MAVEN_HOME="$MAVEN_PROJECTBASEDIR/.mvn/maven-3.9.6"
MAVEN_BIN="$MAVEN_HOME/bin/mvn"

if [ ! -f "$MAVEN_BIN" ]; then
  echo "Downloading Maven 3.9.6..."
  mkdir -p "$MAVEN_HOME"
  curl -fsSL "https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.6/apache-maven-3.9.6-bin.tar.gz" | tar xz -C "$MAVEN_HOME" --strip-components=1
fi

exec "$MAVEN_BIN" "$@"
