package io.nightovis.ztp.bdd;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import io.nightovis.ztp.Application;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = Application.class)
@CucumberOptions(features = "src/test/resources/bdd", glue = "io.nightovis.ztp.bdd")
public class CucumberIntegrationTest {

	@Autowired
	protected TestRestTemplate restTemplate;

	@Autowired
	protected MongoTemplate mongoTemplate;

	@Autowired
	protected ObjectMapper objectMapper;

}

