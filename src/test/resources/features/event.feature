@event
Feature: Event

  Scenario: Reset state before starting tests
    When the client calls reset system
    Then the client receives status code of 200
    And the client receives response body as 'OK'

  Scenario: Create account with initial balance
    Given a new event "deposit" type for destination "100" with an amount of 10
    When the client calls event request
    Then the client receives a HTTP event status code of 201
    And the follow json values response, "destination" id '100' and balance of 10

  Scenario: Deposit into existing account
    Given a new event "deposit" type for destination "100" with an amount of 10
    When the client calls event request
    Then the client receives a HTTP event status code of 201
    And the follow json values response, "destination" id '100' and balance of 20
    And lack of "origin" value

  Scenario: Get balance for existing account
    When the client calls balance for a client '100'
    Then the client receives a balance status code of 200
    And the client receives a balance response body as '20'

  Scenario: Withdraw from non-existing account
    Given a new event "withdraw" type from origin account "200" with an amount of 10
    When the client calls event request
    Then the client receives a HTTP event status code of 404

  Scenario: Withdraw from existing account
    Given a new event "withdraw" type from origin account "100" with an amount of 5
    When the client calls event request
    Then the client receives a HTTP event status code of 201
    And the follow json values response, "origin" id '100' and balance of 15
    And lack of "destination" value

  Scenario: Transfer from existing account
    Given a new event "transfer" type from origin account "100" with an amount of 15 to destination "300"
    When the client calls event request
    Then the client receives a HTTP event status code of 201
    And the follow json values response, "origin" id '100' and balance of 0
    And the follow json values response, "destination" id '300' and balance of 15

  Scenario: Transfer from non-existing account
    Given a new event "transfer" type from origin account "200" with an amount of 15 to destination "300"
    When the client calls event request
    Then the client receives a HTTP event status code of 404
