package io.nightovis.ztp.api.servlet.util;

import io.nightovis.ztp.util.JsonMapper;

import java.net.HttpURLConnection;

public record ResponseEntity<T>(
	T entity,
	int statusCode,
	ContentType contentType,
	String location
) {

	public static <T> ResponseEntity<T> ok(T entity) {
		return new ResponseEntity<>(entity, HttpURLConnection.HTTP_OK, ContentType.JSON, null);
	}

	public static <T> ResponseEntity<T> created(T entity, String location) {
		return new ResponseEntity<>(entity, HttpURLConnection.HTTP_CREATED, ContentType.JSON, location);
	}

	public static <T> ResponseEntity<T> noContent() {
		return new ResponseEntity<>(null, HttpURLConnection.HTTP_NO_CONTENT, ContentType.JSON, null);
	}

	public String serialize() {
		if (entity == null || statusCode == HttpURLConnection.HTTP_NO_CONTENT) {
			return "";
		}

		return JsonMapper.serialize(this.entity);
	}


	enum ContentType {
		JSON("application/json"), JSON_PROBLEM("application/problem+json");

		final String contentType;

		ContentType(String contentType) {
			this.contentType = contentType;
		}

		@Override
		public String toString() {
			return contentType;
		}
	}
}
