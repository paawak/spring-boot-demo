 Feature: Message aggregation
 	
Scenario: Message aggregation works for happy path
  Given The kafka-camel-demo application is available at "http://localhost:8080"
  When I publish bank details messages on Kafka by invoking "http://localhost:8090/kafka/publish"
  And I wait for 50 second
  Then Aggregation results are published
        