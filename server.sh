#!/bin/bash

java -cp runlibs/mysql-connector-java-5.1.27.jar:api/target/api-1.0.1-SNAPSHOT.jar:server/target/server-1.0.1-SNAPSHOT.jar \
     org.chat.server.ServerProcess
