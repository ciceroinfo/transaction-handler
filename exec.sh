#!/bin/bash

if [ "run" = $1 ]; then
  mvn clean package;
  docker build -t transaction-handler .;
  docker run --rm -p8080:8080 transaction-handler
elif [ "cucumber-report" = $1 ]; then
  mvn clean test verify -Dtest=com.ciceroinfo.transactionhandler.bdd.CucumberTest
  echo "See the report at the link below"
  echo "file://$PWD/target/cucumber-html-reports/overview-features.html"
elif [ "--help" = $1 ]; then
  echo "Usage: $ ./exec.sh [OPTION]"
  echo -e ' \t  build \t\t Execute docker command to create transaction-handler image and run using 8080 port'
  echo -e ' \t  cucumber-report \t Generate a Cucumber report from features files'
else
  echo "Invalid command"
  echo "Try 'exec.sh --help' for more information."
fi