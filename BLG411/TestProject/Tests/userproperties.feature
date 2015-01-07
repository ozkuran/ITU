Feature: User and Restaurant Properties

  Scenario: User could change his properties
    Given I am at "UserProperties" page
    And I have changed "UserInformation"
    And I press "SaveChanges" Button
    Then "UserInformation" should be changed in Database

  Scenario: Admin user could change restaurant properties
    Given I am at "RestaurantProperties" page
    And I am "Admin" of the "Restaurant"
    And I have changed "RestaurantInformation"
    And I press "SaveChanges" Button
    Then "RestaurantInformation" should be changed in Database

  Scenario: Normal user could not change restaurant properties
    Given I am at "RestaurantProperties" page
    And I am not "Admin" of the "Restaurant"
    And I have changed "RestaurantInformation"
    And I press "SaveChanges" Button
    Then  I should get error page tells "You have no rights to change that information"