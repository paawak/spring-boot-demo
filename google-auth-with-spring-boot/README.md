# About

Demo application for Google OAuth2 Integration with Spring Boot.

# Rest API
## Swagger
<http://localhost:8000/swagger-ui/>

## Author
### Add new Author

        curl -v -X POST "http://localhost:8000/author" -H  "accept: application/json" -H  "Content-Type: application/json" -H "Authorization: MySecretToken" -d @test/add_new_author.json

### Get all Authors

        curl -v -H "Authorization: MySecretToken" "http://localhost:8000/author" 

### Get Author by ID

        curl -v -H "Authorization: MySecretToken" "http://localhost:8000/author/1" 

### Search Author by Country

        curl -v -H "Authorization: MySecretToken" "http://localhost:8000/author/search?country=India" 

## Genre
### Add new Genre

        curl -v -X POST "http://localhost:8000/genre" -H "Authorization: MySecretToken" -H  "accept: application/json" -H  "Content-Type: application/json" -d @test/add_new_genre.json

### Get all Genres

        curl -v -H "Authorization: MySecretToken" "http://localhost:8000/genre" 

### Get Genre by ID

        curl -v -H "Authorization: MySecretToken" "http://localhost:8000/genre/1" 

## Book
### Add new Book

        curl -v -X POST "http://localhost:8000/book" -H "Authorization: MySecretToken" -H  "accept: application/json" -H  "Content-Type: application/json" -d @test/add_new_book.json

### Get all Books

        curl -v -H "Authorization: MySecretToken" "http://localhost:8000/book" 

### Get Book by ID

        curl -v -H "Authorization: MySecretToken" "http://localhost:8000/book/1" 

# Access H2 DB Console

<http://localhost:8000/h2-console/>

The JDBC Url is: *jdbc:h2:mem:library*. The user-name and password are both *sa*.
    
# Google Authentication

1.  Spring security: <https://docs.spring.io/spring-security/site/docs/current/reference/html5/>
1.  Spring Security Architecture: <https://spring.io/guides/topicals/spring-security-architecture>    
1.  Google Identity Integration: <https://developers.google.com/identity/sign-in/web/backend-auth>
1.  Spring security headers x-frame options: <https://docs.spring.io/spring-security/site/docs/5.0.x/reference/html/headers.html#headers-frame-options>
1.  x-frame options: <https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Frame-Options>

## Debugging Spring Security Filters

In the annotation:

    @EnableWebSecurity(debug = true)

In application.yml:
   
```yaml 
logging:
  level:    
    org.springframework.security.web.FilterChainProxy: DEBUG
```


