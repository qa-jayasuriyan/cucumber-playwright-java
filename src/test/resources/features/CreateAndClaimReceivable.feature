Feature: Create and Claim Receivable

  Scenario: User creates and claims a receivable successfully
    Given User is logged into the dashboard
    When User creates and claims a receivable
    Then Receivable should be created and claimed successfully
