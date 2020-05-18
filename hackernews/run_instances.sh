#!/bin/bash

SERVER_PORT=9001 USR_DB=root PWD_DB= java -jar target/hackernews-0.0.1-SNAPSHOT.jar  &
echo "Done 1";
SERVER_PORT=9002 USR_DB=root PWD_DB= java -jar target/hackernews-0.0.1-SNAPSHOT.jar  &
echo "Done 2";
SERVER_PORT=9003 USR_DB=root PWD_DB= java -jar target/hackernews-0.0.1-SNAPSHOT.jar  &
echo "Done 3";
SERVER_PORT=9004 USR_DB=root PWD_DB= java -jar target/hackernews-0.0.1-SNAPSHOT.jar  &
echo "Done 4";
SERVER_PORT=9005 USR_DB=root PWD_DB= java -jar target/hackernews-0.0.1-SNAPSHOT.jar  &
echo "Done 5";
