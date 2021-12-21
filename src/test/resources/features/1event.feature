@event
Feature: Event

  Scenario: Create account with initial balance
    Given a new event "deposit" type for destination "100" with an amount of 10
    When the client calls event request
    Then the client receives a HTTP event status code of 201
    And the follow json values response, destination id '100' and balance of 10

  Scenario: Deposit into existing account
    Given a new event "deposit" type for destination "100" with an amount of 10
    When the client calls event request
    Then the client receives a HTTP event status code of 201
    And the follow json values response, destination id '100' and balance of 20


#  --
## Withdraw from non-existing account
#
#  POST /event {"type":"withdraw", "origin":"200", "amount":10}
#
#  404 0
#
#  --
## Withdraw from existing account
#
#  POST /event {"type":"withdraw", "origin":"100", "amount":5}
#
#  201 {"origin": {"id":"100", "balance":15}}
#
#  --
## Transfer from existing account
#
#  POST /event {"type":"transfer", "origin":"100", "amount":15, "destination":"300"}
#
#  201 {"origin": {"id":"100", "balance":0}, "destination": {"id":"300", "balance":15}}
#
#  --
## Transfer from non-existing account
#
#  POST /event {"type":"transfer", "origin":"200", "amount":15, "destination":"300"}
#
#  404 0
