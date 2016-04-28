#!/bin/bash
# 25.4.2016
# just invokes hg6mrs-cli-app with given argument(s)

mvn exec:java -Dexec.args="$@"
