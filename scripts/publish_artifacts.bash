#!/usr/bin/env bash

set -eu

cd $(command git rev-parse --show-toplevel)

main() {
    ./gradlew clean build

    ./gradlew library:bintrayUpload \
        -PbintrayUser="$BINTRAY_USER_NAME" \
        -PbintrayKey="$BINTRAY_API_KEY" \
        -PdryRun=${DRY_RUN:-true}

    ./gradlew processor:bintrayUpload \
        -PbintrayUser="$BINTRAY_USER_NAME" \
        -PbintrayKey="$BINTRAY_API_KEY" \
        -PdryRun=${DRY_RUN:-true}
}

main