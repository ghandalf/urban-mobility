#!/bin/bash

root=../urban-mobility
ws=../urban-mobility/ws-api

# see: https://www.mojohaus.org/versions-maven-plugin/usage.html
# mvn versions:display-plugin-updates
# mvn versions:display-dependency-updates
# mvn versions:display-dependency-updates
# mvn versions:display-property-updates

mvn clean install

mvn enforcer:display-info

# see: https://www.mojohaus.org/versions-maven-plugin/usage.html
# mvn version:set

cd ${ws}
mvn spring-boot:run

cd -
