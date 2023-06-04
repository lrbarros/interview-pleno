FROM postgres

EXPOSE 5432
COPY init_schema.sql /docker-entrypoint-initdb.d/

#docker run --name some-postgres -e POSTGRES_PASSWORD=mysecretpassword -d postgres
#docker run --name interview-db --network host -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d interview-db/db-postgres:1.0
