 Feature: Message aggregation
 	
Scenario: Message aggregation works for happy path
  Given Bank details messages are published on Kafka "192.168.1.4:9092"
  When I wait for 2 mins
  Then Aggregation results are published
        