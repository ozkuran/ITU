Feature: User Restaurant Interaction

  Scenario: User Rates Restaurant as 0
    Given I am at "RestaurantPage"
    And I press "0" button
    Then My given rate as Restaurant should be added to restaurants rates as 0

  Scenario: User Rates Restaurant as 1
    Given I am at "RestaurantPage"
    And I press "1" button
    Then My given rate as Restaurant should be added to restaurants rates as 1

  Scenario: User Rates Restaurant as 2
    Given I am at "RestaurantPage"
    And I press "2" button
    Then My given rate as Restaurant should be added to restaurants rates as 2

  Scenario: User Rates Restaurant as 3
    Given I am at "RestaurantPage"
    And I press "3" button
    Then My given rate as Restaurant should be added to restaurants rates as 3

  Scenario: User Rates Restaurant as 4
    Given I am at "RestaurantPage"
    And I press "4" button
    Then My given rate as Restaurant should be added to restaurants rates as 4

  Scenario: User Rates Restaurant as 5
    Given I am at "RestaurantPage"
    And I press "5" button
    Then My given rate as Restaurant should be added to restaurants rates as 5

  Scenario: User Adds Photo of Restaurant
    Given I am at "AddPhoto" page
    And I press "AddPhotoButton"
    And I select a "Photo" a photo from my computer
    Then "Photo" should added to Restaurants "PhotoList"

  Scenario: User Adds Comment about Restaurant
    Given I am at "Restaurant" page
    And I have written comment to "CommentTextBox"
    And I press "AddComment" button
    Then "Comment" should added to Restaurants "CommentList"

  Scenario: User Adds Restaurant to Favorites
    Given I am at "Restaurant" page
    And I press "AddToFavorites" button
    Then "Restaurant" should added to Users "FavoritesList"

