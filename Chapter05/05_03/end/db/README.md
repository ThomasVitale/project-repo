# PostgreSQL

## Lifecycle tasks

### Run PostgreSQL as a Docker container

docker run --name polardb -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=polardb -p 5432:5432 -d postgres:13

### Stop PostgreSQL container

docker stop polardb

### Start PostgreSQL container

docker start polardb

### Remove PostgreSQL container

docker remove polardb

## Managing tasks

### Starting an interactive psql console

docker exec -it polardb psql -U admin -d polardb

### List all databases

\list

### Connect to specific database

\connect polardb

### Quit interactive psql console

\quit
