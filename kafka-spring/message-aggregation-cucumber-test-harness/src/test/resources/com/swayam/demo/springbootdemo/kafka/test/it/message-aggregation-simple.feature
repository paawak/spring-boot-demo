 Feature: Message aggregation
 	
Scenario: Message aggregation works for happy path
  Given The kafka-camel-aggregation-simple application is available at "http://localhost:8080"
  When I publish bank details messages on Kafka by invoking "http://localhost:8090/kafka/publish"
  #And I wait for 50 second
  Then Aggregation results are published on the topic "bank-details-aggregated" of Kafka broker "192.168.1.4:9092" in 50 second 
        