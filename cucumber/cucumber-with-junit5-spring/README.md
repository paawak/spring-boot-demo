# About

Example of creating Cucumber based BDD tests using JUnit5 and Spring for Dependency Injection.

# Rest End Points

## Access H2 DB Console

<http://localhost:8080/h2-console/>

The JDBC Url is: *jdbc:h2:mem:bank_details*. The user-name and password are both *sa*.

## Swagger

<http://localhost:8080/swagger-ui/>

## Author
### List all Authors

<http://localhost:8080/v1/author/>

### Get Authors by name

<http://localhost:8080/v1/author/ban>

## Book
### List all Books

<http://localhost:8080/v1/book/>

### Get Books by name

<http://localhost:8080/v1/book/agn>

### Update an Author in a Book

    curl -X PUT "http://localhost:8080/v1/book/author?authorId=2&bookId=1" -H  "accept: */*"

# Reference
## Cucumber
Spring Cucumber Reference: <https://github.com/paawak/cucumber-jvm/tree/master/spring>    

JUnit 5 Example: <https://github.com/paawak/cucumber-jvm/tree/master/examples/calculator-java-junit5>

