Feature: TagSelectionAndListing

  Scenario: Tag Selected
    Given I am at "SearchPage"
    And I choose a "Tag"
    Then Restaurants with this tag listed

  Scenario: Tags Selected
    Given I am at "SearchPage"
    And I choose multiple "Tags"
    Then Restaurants with this tags listed

  Scenario: Select Restaurant From List
    Given I am at "SearchPage"
    And I select a "Restaurant" from list
    Then I should go to the selected restaurants page

  Scenario: Search a Keyword
    Given I am at "SearchPage"
    And I enter a keyword to "SearchTextBox"
    And I press "Search" button
    Then Restaurants with this keyword listed

  Scenario: Sort Search Results by Location
    Given I am at "SearchPage"
    And I press location criteria button
    Then Restaurants result list sort changes related to the location

  Scenario: Sort Search Results by Tag
    Given I am at "SearchPage"
    And I press tag criteria button
    Then Restaurants result list sort changes related to the tag


