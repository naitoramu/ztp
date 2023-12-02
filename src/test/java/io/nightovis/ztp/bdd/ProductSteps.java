package io.nightovis.ztp.bdd;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.nightovis.ztp.model.AuditOperation;
import io.nightovis.ztp.model.ResourceType;
import io.nightovis.ztp.model.domain.AuditLog;
import io.nightovis.ztp.model.dto.ProductDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ProductSteps extends CucumberIntegrationTest {
	private ProductList responseProducts;
	private int responseStatusCode;
	private Map<String, Object> requestBody = mockRequestBody();
	private ProductDto responseProduct;
	private List<AuditLog> responseAuditLogs;
	private List<ProductDto> existingProducts;

	@Before
	@After
	public void clearCollection() {
		mongoTemplate.getCollection("products").drop();
		mongoTemplate.getCollection("audit_logs").drop();
	}

	@Given("the request body with data:")
	public void the_request_body_with_data(Map<String, Object> data) {
		requestBody = data;
	}

	@Given("exists {int} available products")
	public void existsAvailableProducts(int productsCount) {
		existingProducts = mockProducts(productsCount);
	}

	@When("the client make GET request to {string}")
	public void the_client_make_get_request_to_v1_products(String endpoint) {
		ResponseEntity<?> response;
		if (endpoint.contains("{productId}/audit")) {
			response = restTemplate.getForEntity(endpoint, AuditLogList.class, existingProducts.getFirst().id());
			responseAuditLogs = (AuditLogList) response.getBody();
		} else if (endpoint.contains("{productId}")) {
			response = restTemplate.getForEntity(endpoint, ProductDto.class, existingProducts.getFirst().id());
			responseProduct = (ProductDto) response.getBody();
		} else {
			response = restTemplate.getForEntity(endpoint, ProductList.class);
			responseProducts = (ProductList) response.getBody();
		}
		responseStatusCode = response.getStatusCode().value();
	}

	@When("the client make POST request to {string}")
	public void the_client_make_post_request_to(String endpoint) {
		ResponseEntity<ProductDto> response = restTemplate.postForEntity(endpoint, requestBody, ProductDto.class);
		responseProduct = response.getBody();
		responseStatusCode = response.getStatusCode().value();
	}

	@When("the client make PUT request to {string}")
	public void theClientMakePUTRequestTo(String endpoint) {
		ResponseEntity<ProductDto> response = restTemplate.exchange(
			endpoint,
			HttpMethod.PUT,
			new HttpEntity<>(requestBody),
			ProductDto.class,
			existingProducts.getFirst().id()
		);
		responseProduct = response.getBody();
		responseStatusCode = response.getStatusCode().value();
	}

	@When("the client make DELETE request to {string}")
	public void the_client_make_delete_request_to(String endpoint) {
		ResponseEntity<ProductDto> response = restTemplate.exchange(
			endpoint,
			HttpMethod.DELETE,
			null,
			ProductDto.class,
			existingProducts.getFirst().id()
		);
		responseProduct = response.getBody();
		responseStatusCode = response.getStatusCode().value();
	}

	@Then("the client receives status code of {int}")
	public void the_client_receives_status_code_of(Integer statusCode) {
		Assertions.assertThat(responseStatusCode).isEqualTo(statusCode);
	}

	@Then("the client receives empty body")
	public void the_client_receives_empty_body() {
		Assertions.assertThat(responseProduct).isNull();
	}

	@And("the client receives product with given productId")
	public void theClientReceivesProductWithGivenProductId() {
		Assertions.assertThat(responseProduct).isEqualTo(existingProducts.getFirst());
	}

	@And("the client receives list of available products")
	public void theClientReceivesListOfAvailableProducts() {
		Assertions.assertThat(responseProducts).hasSameSizeAs(existingProducts);
		Assertions.assertThat(responseProducts).containsExactlyInAnyOrderElementsOf(existingProducts);
	}

	@And("the client receives {int} {string} {string} audit logs")
	public void theClientReceivesAuditLogs(int logsCount, String operation, String resourceType) {
		List<AuditLog> filteredAuditLogs = responseAuditLogs.stream()
			.filter(l -> l.operation().equals(AuditOperation.valueOf(operation)))
			.toList();

		Assertions.assertThat(filteredAuditLogs).hasSize(logsCount);
		Assertions.assertThat(filteredAuditLogs)
			.extracting(AuditLog::resourceType)
			.containsOnly(ResourceType.valueOf(resourceType));
	}

	private List<ProductDto> mockProducts(int productsCount) {
		return IntStream.range(0, productsCount)
			.mapToObj((i) -> new ProductDto(
				          String.valueOf(i),
				          RandomStringUtils.random(256),
				          RandomStringUtils.random(1024),
				          2137.69,
				          420L
			          )
			).map(p -> restTemplate.postForEntity("/v1/products", p, ProductDto.class).getBody())
			.toList();
	}

	private Map<String, Object> mockRequestBody() {
		return Map.of("name", RandomStringUtils.random(256),
		              "description", RandomStringUtils.random(1024),
		              "price", 42.0,
		              "availableQuantity", 213);
	}

	private static class ProductList extends ArrayList<ProductDto> {}
	private static class AuditLogList extends ArrayList<AuditLog> {}
}
