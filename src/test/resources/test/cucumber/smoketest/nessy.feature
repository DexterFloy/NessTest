#Search input should be visible on main page.
#• First page with searching result should have at most 10 elements.
#• Clicking on Next button should display next page with 10 more result.
#• Third searched element should redirect to the proper article.
Feature: Log in/out - Smoke Test

Scenario: Startup
	Given I open the browser
	
Scenario Outline: Search Functionality
	Given I Navigate To "targetSite" Page
	When I Insert "<searchTerm>" In The "search" Area
	And I Hit The "enter" Keyboard Key On "search" Area
	Then I Verify That "resultsList" Shows Up To Result Number "<resultsPerPage>"

	Examples:
	|searchTerm|resultsPerPage|
	|mere|9| 
	|testing|10|
	|test|10|

Scenario: Navigate Results
	Given I Navigate To "bottomOfThe" Page
	When I Click The "nextPage" Button
	Then I Verify That "resultsList" Shows Up To Result Number "20"
	
Scenario: Open Results
	When I Click The "firstResult" Button
	Then I Verify That "searchResult" Page Is Loaded

Scenario: Teardown
	Given I close the browser