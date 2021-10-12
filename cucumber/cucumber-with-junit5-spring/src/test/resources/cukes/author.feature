@author
Feature: Test all author rest endpoints

  Background:
    Given The application is available at "http://localhost:8080"
    And Health check is fine at "/actuator/health"

  Scenario: List all available authors
    When I fetch authors at "/v1/author"
    Then I should find 2 authors

  Scenario Outline: Get authors by name
    When I search for author by name at "/v1/author/<name>"
    Then I should find <count> authors

    Examples: 
      | name  | count |
      | BALAI |     1 |
      | sara |     1 |
      | a |     2 |
      | A |     2 |      
      