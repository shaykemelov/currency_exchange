#!/bin/bash
set -e

POSTGRES="psql --username ${POSTGRES_USER} --password ${POSTGRES_PASSWORD}"

echo "CREATE DATABASE ${POSTGRES_DB};"
echo "\c ${POSTGRES_DB} ${POSTGRES_USER}"
echo "CREATE USER ${MIGRATIONS_USER} WITH PASSWORD '${MIGRATIONS_PASSWORD}';"
echo "GRANT ALL PRIVILEGES ON DATABASE ${POSTGRES_DB} TO ${MIGRATIONS_USER};"
echo "CREATE USER ${APP_USER} WITH PASSWORD '${APP_PASSWORD}';"
echo "ALTER DEFAULT PRIVILEGES FOR USER ${MIGRATIONS_USER} IN SCHEMA public GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO ${APP_USER};"

$POSTGRES <<-EOSQL
CREATE DATABASE ${POSTGRES_DB};
\c ${POSTGRES_DB} ${POSTGRES_USER}

CREATE USER ${MIGRATIONS_USER} WITH PASSWORD '${MIGRATIONS_PASSWORD}';
GRANT ALL PRIVILEGES ON DATABASE ${POSTGRES_DB} TO ${MIGRATIONS_USER};
GRANT CREATE, USAGE ON SCHEMA public to ${MIGRATIONS_USER};

CREATE USER ${APP_USER} WITH PASSWORD '${APP_PASSWORD}';
ALTER DEFAULT PRIVILEGES FOR USER ${MIGRATIONS_USER} IN SCHEMA public GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO ${APP_USER};

EOSQL
