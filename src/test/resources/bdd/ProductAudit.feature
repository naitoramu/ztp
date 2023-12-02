Feature: The Product audit operations

  Scenario: audit log is being added, when the product is being created
    Given exists 1 available products
    When the client make GET request to "/v1/products/{productId}/audit"
    Then the client receives status code of 200
    And the client receives 1 "CREATE" "PRODUCT" audit logs

  Scenario: audit log is being added, when the product is being updated
    Given exists 1 available products
    When the client make PUT request to "/v1/products/{productId}"
    And the client make PUT request to "/v1/products/{productId}"
    And the client make PUT request to "/v1/products/{productId}"
    And the client make GET request to "/v1/products/{productId}/audit"
    Then the client receives status code of 200
    And the client receives 1 "CREATE" "PRODUCT" audit logs
    And the client receives 3 "UPDATE" "PRODUCT" audit logs

  Scenario: audit log is being added, when the product is being deleted
    Given exists 1 available products
    When the client make DELETE request to "/v1/products/{productId}"
    And the client make GET request to "/v1/products/{productId}/audit"
    Then the client receives status code of 200
    And the client receives 1 "CREATE" "PRODUCT" audit logs
    And the client receives 1 "DELETE" "PRODUCT" audit logs
