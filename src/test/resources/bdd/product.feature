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
    Given exists 3 available products
    When the client make GET request to "/v1/products"
    Then the client receives status code of 200
    And the client receives list of available products

  Scenario: client fetches existing product, by making GET request to "/v1/products/{productId}"
    Given exists 1 available products
    When the client make GET request to "/v1/products/{productId}"
    Then the client receives status code of 200
    And the client receives product with given productId

  Scenario Outline: client updates existing product, by making PUT request to "/v1/products/{productId}"
    Given exists 1 available products
    And  the request body with data:
      | Property          | Value                |
      | name              | <ProductName>        |
      | description       | <ProductDescription> |
      | price             | <ProductPrice>       |
      | availableQuantity | <AvailableQuantity>  |
    When the client make PUT request to "/v1/products/{productId}"
    Then the client receives status code of <StatusCode>
    Examples:
      | ProductName     | ProductDescription              | ProductPrice | AvailableQuantity | StatusCode |
      | ExampleProduct1 | Description for ExampleProduct1 | 20.99        | 100               | 200        |
      |                 | Product with missing name       | 10.99        | 50                | 400        |


  Scenario: client deletes existing product, by making DELETE request to "/v1/products/{productId}"
    Given exists 1 available products
    When the client make DELETE request to "/v1/products/{productId}"
    Then the client receives status code of 204
    And the client receives empty body
