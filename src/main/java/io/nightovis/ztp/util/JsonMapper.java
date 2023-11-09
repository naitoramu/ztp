package io.nightovis.ztp.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import io.nightovis.ztp.problem.ProblemOccurredException;
import io.nightovis.ztp.problem.Problems;

public class JsonMapper {

	private static final ObjectMapper MAPPER = createObjectMapper();

	private static ObjectMapper createObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new ParameterNamesModule());
		return objectMapper;
	}

	public static String serialize(Object object) {
		try {
			return MAPPER.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T deserialize(String object, Class<T> clazz) {
		try {
			return MAPPER.readValue(object, clazz);
		} catch (JsonProcessingException e) {
			throw new ProblemOccurredException(Problems.badRequest(e.getMessage()));
		}
	}
}
