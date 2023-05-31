FROM postgres

EXPOSE 5432
COPY init_schema.sql /docker-entrypoint-initdb.d/
#docker run -p 5432:5432 --name container-postgres -e POSTGRES_PASSWORD=postgres -d postgres