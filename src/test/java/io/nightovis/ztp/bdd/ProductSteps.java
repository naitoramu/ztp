package io.nightovis.ztp.bdd;

import com.fasterxml.jackson.core.JsonParser;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.nightovis.ztp.model.dto.ProductDto;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ProductSteps extends CucumberIntegrationTest {

	private static final List<String> EXISTING_PRODUCT_IDS = new ArrayList<>();
	private ResponseEntity<ProductList> responseWithListOfProducts;
	private int responseStatusCode;
	private Map<String, Object> requestBody;
	private ResponseEntity<ProductDto> responseWithSingeProduct;

	@When("the client make GET request to {string}")
	public void the_client_make_get_request_to_v1_products(String endpoint) {
		responseWithListOfProducts = restTemplate.getForEntity(endpoint, ProductList.class);
		responseStatusCode = responseWithListOfProducts.getStatusCode().value();
	}
	@Then("the client receives status code of {int}")
	public void the_client_receives_status_code_of(Integer statusCode) {
		Assertions.assertThat(responseStatusCode).isEqualTo(statusCode);
	}
	@Then("the client receives list of available products")
	public void the_client_receives_list_of_available_products() {
		List<ProductDto> products = responseWithListOfProducts.getBody();
		Assertions.assertThat(products).hasSameSizeAs(EXISTING_PRODUCT_IDS);
		Assertions.assertThat(products.stream().map(ProductDto::id).collect(Collectors.toSet()))
			.containsExactlyInAnyOrderElementsOf(EXISTING_PRODUCT_IDS);
	}

	@Given("the request body with data:")
	public void the_request_body_with_data(Map<String, Object> data) {
		requestBody = data;
	}

	@When("the client make POST request to {string}")
	public void the_client_make_post_request_to(String endpoint) {
		responseWithSingeProduct = restTemplate.postForEntity(endpoint, requestBody, ProductDto.class);
		responseStatusCode = responseWithSingeProduct.getStatusCode().value();
		Optional.ofNullable(responseWithSingeProduct.getBody())
			.map(ProductDto::id)
			.ifPresent(EXISTING_PRODUCT_IDS::add);
	}
	@Then("the client receives created products with correct properties")
	public void the_client_receives_created_products_with_correct_properties() throws IOException {
		ProductDto requestProduct = objectMapper.readValue((JsonParser) requestBody, ProductDto.class);
		Assertions.assertThat(responseWithSingeProduct.getBody()).usingRecursiveComparison()
			.ignoringFields("id")
			.isEqualTo(requestProduct);
	}

	@When("the client make DELETE request to {string}")
	public void the_client_make_delete_request_to(String endpoint) {
		responseWithSingeProduct = restTemplate.exchange(
			endpoint,
			HttpMethod.DELETE,
			null,
			ProductDto.class,
			EXISTING_PRODUCT_IDS.getFirst()
		);
		responseStatusCode = responseWithSingeProduct.getStatusCode().value();
	}
	@Then("the client receives empty body")
	public void the_client_receives_empty_body() {
		Assertions.assertThat(responseWithSingeProduct.getBody()).isNull();
	}

	private static class ProductList extends ArrayList<ProductDto> {}
}
