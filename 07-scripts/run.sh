#!/bin/bash

root=../urban-mobility
ws=../urban-mobility/05-ws-api

# see: https://www.mojohaus.org/versions-maven-plugin/usage.html
# mvn versions:display-plugin-updates
# mvn versions:display-dependency-updates
# mvn versions:display-dependency-updates
# mvn versions:display-property-updates

mvn clean install

cd ${ws}
mvn spring-boot:run

cd -
