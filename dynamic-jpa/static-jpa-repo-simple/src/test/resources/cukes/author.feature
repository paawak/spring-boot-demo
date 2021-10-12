@author
Feature: Test all author rest endpoints

  Scenario: List all available authors
    Given The application is available at "http://localhost:8080"
    And Health check is fine at "/actuator/health"
    When I fetch authors at "/v1/author"
    Then I should find 2 authors

  Scenario Outline: Title of your scenario outline
    Given I want to write a step with <name>
    When I check for the <value> in step
    Then I verify the <status> in step

    Examples: 
      | name  | value | status  |
      | name1 |     5 | success |
      | name2 |     7 | Fail    |
      