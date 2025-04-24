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
	-v ${PWD}\docker\sql-emptydb:/docker-entrypoint-initdb.d \
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

### connect to db in container
```
docker exec -it pg-dbmovietu psql -U movie -d dbmovietu
\l
\d
```