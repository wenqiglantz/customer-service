Feature: Customer functionalities
  This feature contains a list of functionalities related to customer

  Scenario: get a customer detail by customerId

    Given the collection of customers:
      | customerId                       | firstName   | lastName   |
      | ABCDEFG12345678910HIJKLMNOP12345 | John        | Smith      |
      | ABCDEFG12345678910HIJKLMNOP12346 | Jane        | Smith      |

    When customerId ABCDEFG12345678910HIJKLMNOP12345 is passed in to retrieve the customer details

    Then The customer detail is retrieved
      | customerId                       | firstName   | lastName   |
      | ABCDEFG12345678910HIJKLMNOP12345 | John        | Smith      |
