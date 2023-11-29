package io.nightovis.ztp.bdd;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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
	private Map<String, Object> requestBody;
	private ProductDto responseProduct;
	private List<ProductDto> existingProducts;

	@After
	 public void clearCollection() {
		mongoTemplate.getCollection("products").drop();
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
		if (endpoint.contains("{productId}")) {
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
//	@Then("the client receives created/modified products with correct properties")
//	public void the_client_receives_created_products_with_correct_properties() throws IOException {
//		ProductDto requestProduct = objectMapper.readValue((JsonParser) requestBody, ProductDto.class);
//		Assertions.assertThat(responseProduct).usingRecursiveComparison()
//			.ignoringFields("id")
//			.isEqualTo(requestProduct);
//	}

	private static class ProductList extends ArrayList<ProductDto> {}
}
