@reset
Feature: Reset

  Scenario: Reset state before starting tests
    When the client calls reset system
    Then the client receives status code of 200
    And the client receives response body as 'OK'
