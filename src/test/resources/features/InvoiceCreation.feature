Feature: Invoice Creation
  Scenario: Verify user can create invoice successfully
    Given User is logged into the dashboard for invoice creation
    When User creates a new invoice
    Then Invoice should be created successfully
