@balance
Feature: Balance

  Scenario: Get balance for non-existing account
    When the client calls balance for a client '1234'
    Then the client receives a balance status code of 404
    And the client receives a balance response body as '0'

  Scenario: Get balance for existing account
    When the client calls balance for a client '100'
    Then the client receives a balance status code of 200
    And the client receives a balance response body as '20'