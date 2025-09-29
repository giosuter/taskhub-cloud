#!/usr/bin/env bash
set -euo pipefail

############################################
# CONFIG — EDIT THESE
############################################
HOST="zitatusi@zitatusi.myhostpoint.ch"
APP_DIR="app/taskhub-cloud"
############################################

BACKEND_DIR="backend/taskhub-backend"
POM="$BACKEND_DIR/pom.xml"
JAR_NAME="taskhub-backend-0.0.1-SNAPSHOT.jar"
LOCAL_JAR="$BACKEND_DIR/target/$JAR_NAME"

echo "[1/3] Build with Maven"
mvn -q -f "$POM" clean verify

echo "[2/3] Upload JAR"
rsync -avz "$LOCAL_JAR" "$HOST:~/$APP_DIR/taskhub-backend.jar"

echo "[2/3b] Restart app"
ssh "$HOST" "bash -lc '~/$APP_DIR/bin/restart.sh'"

echo "[3/3] Publish JaCoCo"
rsync -avz --delete "$BACKEND_DIR/target/site/jacoco/" \
  "$HOST:~/$APP_DIR/reports/jacoco/"

echo "==> Done."
echo "    App:    https://devprojects.ch/taskhub-cloud/"
echo "    Health: https://devprojects.ch/taskhub-cloud/actuator/health"
echo "    JaCoCo: https://devprojects.ch/taskhub-cloud/jacoco/index.html"
