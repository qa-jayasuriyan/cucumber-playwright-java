Feature: Receive Payments

  Scenario: Verify user can create a new receive payment with file upload
    Given User is logged into the dashboard for receive payments
    When User creates a new receive payment with invoice file
    Then Receive payment should be created successfully
