@author
Feature: Test all author rest endpoints

  Scenario: List all available authors
    Given The application health is fine at "http://localhost:8080/actuator/health"
    When I fetch authors at "http://localhost:8080/v1/author"
    Then I should find 2 authors
