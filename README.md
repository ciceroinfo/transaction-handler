<h1 align="center">Home assignment: Transaction Handler</h1>

<p align="center">
<img src="https://img.shields.io/badge/made%20by-cicero-blue.svg" >
<img src="https://img.shields.io/badge/java-11-red.svg" >
<img src="https://img.shields.io/badge/spring boot-2.6.1-important.svg" >
<img src="https://img.shields.io/badge/guava-31.0.1_jre-yellowgreen.svg" >
<img src="https://img.shields.io/badge/springfox-3.0.0-green.svg" >
<img src="https://img.shields.io/badge/cucumber-7.1.0-brightgreen.svg" >
<img src="https://img.shields.io/badge/cucumber%20reporting-5.6.1-ff6.svg" >
<img src="https://img.shields.io/badge/lombok-1.18.22-blueviolet.svg" >
<img src="https://img.shields.io/badge/docker-20.10.10-yellow.svg" >
<img src="https://img.shields.io/badge/intellij%20%28CE%29-11.0.12-red.svg" >
</p>

<p align="center">
Tests<br>
<img src="https://img.shields.io/badge/coverage-0%25-red.svg" >
<img src="https://img.shields.io/badge/it-100%25-brightgreen.svg" >
<img src="https://img.shields.io/badge/e2e-100%25-brightgreen.svg" >
</p>

_The API consists of three endpoints, **POST** /reset, **GET** /balance, and **POST** /event_

---

Basically the solution involve a _REST_ API to perform bank transactions

# Info

### Endpoints

| request methods | endpoint                           | input type   | example                                             | description                                                  |
|-----------------|------------------------------------|--------------|-----------------------------------------------------|--------------------------------------------------------------|
| `POST`          | `/transaction-handler/reset`       | `void`       |                                                     | reset repository                                             |
| `GET`           | `/transaction-handler/balance`     | Query Params | `?account_id=1234`                                  | retrieve a balance account                                   |
| `POST`          | `/transaction-handler/event`       | JSON         | `{"type":"deposit", "destination":"1", "amount":1}` | perform events such as: `deposit`, `withdraw` and `transfer` |
| `GET`           | `/transaction-handler/v2/api-docs` | `void`       |                                                     | `Swagger` api documentation                                    |

# Installation

## Requirements
- Java 11 or newer
- Maven 3.3.1 or newer

### Run by maven

```bash
user@pc:~/transaction-handler$ mvn clean package
user@pc:~/transaction-handler/target$ java -jar transaction-handler.jar
```

### Run by exec.sh
To facilitate executions I created a `exec.sh` file with the boilerplate commands.<br />
To execute given `sh` file, do the following `chmod +x exec.sh` command, after that it's ready to run, just pass the 
proper parameters, any doubts just run the command `./exec.sh --help`.

#### more details

Uso:`user@pc:~/transaction-handler$$ ./exec.sh [OPTION]`
```bash
user@pc:~/transaction-handler$ ./exec.sh run               Execute docker command to create transaction-handler image and run using 8080 port
user@pc:~/transaction-handler$ ./exec.sh cucumber-report   Generate a Cucumber report from de features files
```

#### manual commands

- `docker build -t transaction-handler .` to create the docker image `transaction-handler`
- `docker run --rm transaction-handler` run the docker image
- `mvn clean test verify -Dtest=CucumberTest` generate _Cucumber Report_


## Sample of `feature` cucumber file

```gherkin
@balance
Feature: Balance

  Scenario: Get balance for non-existing account
    When the client calls balance for a client '1234'
    Then the client receives a balance status code of 404
    And the client receives a balance response body as '0'
```

```gherkin
@event
Feature: Event

  Scenario: Create account with initial balance
    Given a new event "deposit" type for destination "100" with an amount of 10
    When the client calls event request
    Then the client receives a HTTP event status code of 201
    And the follow json values response, "destination" id '100' and balance of 10
```
