@event
Feature: Event

  Scenario: Create account with initial balance
    Given a new event "deposit" type for destination "100" with an amount of 10
    When the client calls event request
    Then the client receives a HTTP event status code of 201
    And the follow json values response, destination id '100' and balance of 10
