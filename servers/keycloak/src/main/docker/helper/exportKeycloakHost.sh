#!/bin/sh

export KEYCLOAK_SERVICE_HOST=$(ping -c 1 keycloak | grep -Eo -m 1 '[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}';)
echo KEYCLOAK runs on: $KEYCLOAK_SERVICE_HOST

export KEYCLOAK_SERVICE_URL=http://$KEYCLOAK_SERVICE_HOST:8080/auth

sh /opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0
