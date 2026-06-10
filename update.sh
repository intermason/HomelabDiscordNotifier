#!/usr/bin/bash

export JAVA_HOME=/usr/lib/jvm/java-26-openjdk

DIR="$(cd "$(dirname "$0")" && pwd)"

cd "${DIR}" || exit 1
echo "Fetching latest git commits..."
git fetch

doNewCommitsExist=false
[ -n "$(git log HEAD..origin/main --oneline)" ] && doNewCommitsExist=true || doNewCommitsExist=false

if [ "$doNewCommitsExist" = true ]; then

  git pull
  mvn clean package
fi