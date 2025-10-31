Feature: Dashboard login

  Scenario: Login to dashboard with valid OTP
    Given User is on login page
    When User enters email and submits OTP
    Then User should land on dashboard
