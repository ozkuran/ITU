Feature: Login

  Scenario: Go to Register New User Page
    Given I am on the "Login" page
    And I press "Register"
    Then I should go to the "Register" page

  Scenario: Register New User Complete
    Given I am on the "Register" page
    And I fill "Username" with a nonexistent username
    And I fill "Password" length more than 8
    And I press "Register"
    Then I should get registered

  Scenario: Register New User Existent Username
    Given I am on the "Register" page
    And I fill "Username" with a existent username
    And I fill "Password" length more than 8
    And I press "Register"
    Then I should get error page tells "User Already Exists"

  Scenario: Register New User Without Password
    Given I am on the "Register" page
    And I fill "Username" with a nonexistent username
    And I did not fill "Password" field
    And I press "Register"
    Then I should get error page tells "Password could not be empty"

  Scenario: Register New User With Password shorter than 8 characters
    Given I am on the "Register" page
    And I fill "Username" with a nonexistent username
    And I fill "Password" length less than 8
    And I press "Register"
    Then I should get error page tells "Password could not be less then 8 characters long"


  Scenario: Login Existing User
    Given I am on the "Login" page
    And I fill "Username" correctly
    And I fill "Password" correctly
    And I press "LoginButton"
    Then I should get "LoggedIn"

  Scenario: Trying to Login Nonexistent User
    Given I am on the "Login" page
    And I fill "Username" wrong
    And I fill "Password" correctly
    And I press "LoginButton"
    Then I should get not "LoggedIn"

  Scenario: Trying to Login with wrong password
    Given I am on the "Login" page
    And I fill "Username" correctly
    And I fill "Password" wrong
    And I press "LoginButton"
    Then I should get not "LoggedIn"

  Scenario: Trying to Login with wrong username and password
    Given I am on the "Login" page
    And I fill "Username" wrong
    And I fill "Password" wrong
    And I press "LoginButton"
    Then I should get not "LoggedIn"

  Scenario: Trying to Login with empty username
    Given I am on the "Login" page
    And I did not fill "Username"
    And I fill "Password" wrong
    And I press "LoginButton"
    Then I should get not "LoggedIn"

  Scenario: Trying to Login with empty password
    Given I am on the "Login" page
    And I fill "Username" correctly
    And I did not fill "Password"
    And I press "LoginButton"
    Then I should get not "LoggedIn"

