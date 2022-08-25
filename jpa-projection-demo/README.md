# About

## H2 DB

### Accessing H2 Console

  http://localhost:8080/h2-console
  
  UserName: sa
  Password: sa
  Jdbc Url: jdbc:h2:file:./target/h2-db

### Liquibase

To export schema:

    mvn -P db-export liquibase:generateChangeLog

