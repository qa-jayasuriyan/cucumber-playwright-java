Feature: Create Receivable

  Scenario: User creates a new receivable successfully
    Given User is logged into the dashboard
    When User creates a new receivable
    Then Receivable should be created successfully
