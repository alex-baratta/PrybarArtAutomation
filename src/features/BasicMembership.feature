Feature: I want to test the functionality of the basic Free signup memembership.

	@LoginTest @00002
  Scenario Outline: verify the functionality of the artwork header links as a user who is logged in.
   Given I am a user who is logged in on the home page
   When I cursor over artwork from the header
   And I select the <Artwork Sub Page> provided 
   Then I am shown the <Artwork Sub Page> Artwork Sub page.
   
   Examples:
   | Artwork Sub Page 				  |
   | Artwork										|
   | Artist Statement						|
   | Linocut   									|
   | Photography Work						|
   | Uke and Tuba Discography 	|   
   
   