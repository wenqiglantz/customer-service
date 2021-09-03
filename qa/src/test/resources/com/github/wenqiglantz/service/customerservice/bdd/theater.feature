Feature: Customer functionalities

  Scenario: Creating a new customer

    When CustomerInfo with the following inputs is passed into createCustomer endpoint:
      | firstName   | lastName   |
      | first name  | last name  |

    Then A new customer is created
