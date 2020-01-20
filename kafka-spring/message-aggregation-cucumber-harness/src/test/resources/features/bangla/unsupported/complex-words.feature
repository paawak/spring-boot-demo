 Feature: Complex words
 	
Scenario: Typing a complex word ঠাত্
  Given I am on the Bhasha editor
  When I type "tthaT"
  Then Bhasha editor should display "ঠাত্"
  
Scenario: Typing a complex word ঠাট্
  Given I am on the Bhasha editor
  When I type "tthaTT"
  Then Bhasha editor should display "ঠাট্"  
 
        