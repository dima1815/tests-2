Narrative:
In order to communicate effectively to the business some functionality
As a development team
I want to use Behaviour-Driven Development

Scenario: my test scenario 1
Given something
When I send the following request:
|action |value |currency |
|Buy |100 |USD |
|Sell |20 |GBP |
Then something should happen

Scenario: sad path
Given something
When I try a step that has not yet been implemented
Then an error should be reported in the test report

Scenario: scenario with failing step
Given something
When I send the following request:
|action |value  |currency   |
|Buy    |100    |USD        |
|Sell   |20     |GBP        |
Then the scenario should fail