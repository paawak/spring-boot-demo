@book
Feature: Test all book rest endpoints

  Background:
    Given The application is available at "http://localhost:8080"
    And Health check is fine at "/actuator/health"

  Scenario: List all available books
    When I fetch books at "/v1/book"
    Then I should find 6 books

  Scenario Outline: Get books by name
    When I search for books by name at "/v1/book/<name>"
    Then I should find <count> books

    Examples: 
      | name  | count |
      | TIRE |     1 |
      | dwi |     1 |
      | m |     2 |
      | tu |     2 |      

  Scenario: Update an Author in a Book
    When I update the author of a book at "/v1/book/author?authorId=2&bookId=3"
    Then 1 book should be affected
    When I search for books by name at "/v1/book/mrigaya"
    Then The author first name should be "Saradindu"    
      
  Scenario: Update an invalid Author in a invalid Book
    When I update the author of a book at "/v1/book/author?authorId=200&bookId=300"
    Then 0 book should be affected
          