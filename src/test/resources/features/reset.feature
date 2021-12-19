@reset
Feature: Reset state before starting tests

  Scenario: client makes call to POST /reset
    When the client calls reset system
    Then the client receives status code of 200
    And the client receives response body as 'OK'
