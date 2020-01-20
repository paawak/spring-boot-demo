 Feature: Typing a word that starts with a swar varna 
 	
Scenario: Typing আমি
  Given I am on the Bhasha editor
  When I type "aami"
  Then Bhasha editor should display "আমি"
  
Scenario: Typing একবার
  Given I am on the Bhasha editor
  When I type "ekbar"
  Then Bhasha editor should display "একবার"  
  
Scenario: Typing এই
  Given I am on the Bhasha editor
  When I type "ei"
  Then Bhasha editor should display "এই"    
 
        