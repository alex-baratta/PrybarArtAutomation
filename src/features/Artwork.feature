Feature: I want to test the functionality of the Artwork work portion of the website. 

  @NoLoginTest @00001
  Scenario Outline: verify the functionality of the artwork header links as a user who is not logged in.
   Given I am a user who is not logged in on the home page
   When I cursor over artwork from the header
   And I select the <Artwork Sub Page> provided 
   Then I am shown the <Artwork Sub Page> Artwork Sub page.
   
   Examples:
   | Artwork Sub Page 				  |
   | ARTWORK										|
   | Artist Statement						|
   | Linocut   									|
   | Photography Work						|
   | Uke and Tuba Discography 	|
   
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
  
  @NoLoginTest @00003
 Scenario: Verify the presence of a George A Walker quote on the Artist Statement page  as a user who is not logged in.  
 Given I am a user who is not logged in on the home page  
 When I access the Artist statement page
 Then I am shown the artists statment including a quote from George A Walker
 
  @NoLoginTest @00004
  Scenario: Verify the presence of the material list and gallery of images on the Linocut page as a user who is not logged in.
   Given I am a user who is not logged in on the home page
   When I access the Linocut artwork page from the header 
   Then I am shown a Materials list 
	 And I am shown a gallery of images
	
  @NoLoginTest @00005	
  Scenario Outline: Verify the functionality of the gallerys Next and Previous image functionality on the Linocut page  as a user who is not logged in.
   Given I am a user who is not logged in on the home page
   When I access the Linocut page from the header
   And I select an image from the Linocut Pages the gallery of image
   And select the <Direction> key
   Then the user is taken to the image in corrisponding <Direction>
   
   Examples:
   | Direction    |
   | Left					|
   | Right				|
   