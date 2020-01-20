 Feature: Complex vyanjan varnas that need 2 or more letters to type
 	
Scenario: Typing a single vyanjan varna ট
  Given I am on the Bhasha editor
  When I type "tt"
  Then Bhasha editor should display "ট"
  
Scenario: Typing a single vyanjan varna ঠ
  Given I am on the Bhasha editor
  When I type "tth"
  Then Bhasha editor should display "ঠ"  
  
Scenario: Typing a single vyanjan varna ড
  Given I am on the Bhasha editor
  When I type "dd"
  Then Bhasha editor should display "ড"  
  
Scenario: Typing a single vyanjan varna ঢ
  Given I am on the Bhasha editor
  When I type "ddh"
  Then Bhasha editor should display "ঢ"  
 
        