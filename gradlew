#!/bin/sh
set -eu

GRADLE_VERSION="8.11.1"
CACHE_DIR="${HOME}/.gradle/swaadgo-gradle/${GRADLE_VERSION}"
GRADLE_HOME="${CACHE_DIR}/gradle-${GRADLE_VERSION}"
ZIP_FILE="${CACHE_DIR}/gradle-${GRADLE_VERSION}-bin.zip"

if [ ! -x "${GRADLE_HOME}/bin/gradle" ]; then
  mkdir -p "${CACHE_DIR}"
  if [ ! -f "${ZIP_FILE}" ]; then
    curl -L --fail --retry 3 \
      "https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip" \
      -o "${ZIP_FILE}"
  fi
  rm -rf "${GRADLE_HOME}"
  unzip -q "${ZIP_FILE}" -d "${CACHE_DIR}"
fi

exec "${GRADLE_HOME}/bin/gradle" "$@"
