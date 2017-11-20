#!/usr/bin/env bash

set -eu

cd $(command git rev-parse --show-toplevel)

main() {
    ./gradlew library:test processor:test sample:test
}

main