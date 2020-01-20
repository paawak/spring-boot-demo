Feature: Bangla typing works manually but fails in Selenium
 	
Scenario: Typing a juktakkhor word লক্ষ্মী 1
  Given I am on the Bhasha editor
  When I type "lXmii"
  Then Bhasha editor should display "লক্ষ্মী"  
    
Scenario: Typing a juktakkhor word লক্ষ্মী 2
  Given I am on the Bhasha editor
  When I type "lXmee"
  Then Bhasha editor should display "লক্ষ্মী"  
