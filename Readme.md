# Hibernate

## Docker Databases

### Postgres empty database

shell:
```
docker run --detach \
	--name pg-dbmovietu \
	--env POSTGRES_DB=dbmovietu \
	--env POSTGRES_USER=movie \
	--env POSTGRES_PASSWORD=password \
	-v ${PWD}/docker/sql-emptydb:/docker-entrypoint-initdb.d \
	-p 5432:5432 \
	postgres:17
```

powershell
```
docker run --detach `
		--name pg-dbmovietu `
		--env POSTGRES_DB=dbmovietu `
		--env POSTGRES_USER=movie `
		--env POSTGRES_PASSWORD=password `
		-v ${PWD}\docker\sql-emptydb:/docker-entrypoint-initdb.d `
		-p 5432:5432 `
		postgres:17
```

#### connect to db in container
```
docker exec -it pg-dbmovietu psql -U movie -d dbmovietu
\l
\d
\d movie
show SEARCH_PATH;
```

### composition docker

Start composition:
```
cd docker
docker compose up -d
```

PgAdmin4 can be accessed with a browser at localhost:80

#### connect to db in container
```
docker compose exec -it db psql -U movie -d dbmoviedata
\d
```



