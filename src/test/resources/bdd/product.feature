Feature: The Product CRUD operations

  Scenario Outline: client creates a new product, by making POST request to "/v1/products"
    Given  the request body with data:
      | Property          | Value                |
      | name              | <ProductName>        |
      | description       | <ProductDescription> |
      | price             | <ProductPrice>       |
      | availableQuantity | <AvailableQuantity>  |
    When the client make POST request to "/v1/products"
    Then the client receives status code of <StatusCode>
    Examples:
      | ProductName     | ProductDescription              | ProductPrice | AvailableQuantity | StatusCode |
      | ExampleProduct1 | Description for ExampleProduct1 | 20.99        | 100               | 201        |
      |                 | Product with missing name       | 10.99        | 50                | 400        |

  Scenario: client fetches existing products, by making GET request to "/v1/products"
    When the client make GET request to "/v1/products"
    Then the client receives status code of 200
    And the client receives list of available products

  Scenario: client deletes existing product, by making DELETE request to "/v1/products/{productId}"
    When the client make DELETE request to "/v1/products/{productId}"
    Then the client receives status code of 204
    And the client receives empty body
