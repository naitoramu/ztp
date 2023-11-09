package io.nightovis.ztp.api.servlet;

import io.nightovis.ztp.api.controller.ProductController;
import io.nightovis.ztp.util.JsonMapper;
import io.nightovis.ztp.problem.ProblemOccurredException;
import io.nightovis.ztp.problem.Problems;
import io.nightovis.ztp.model.dto.ProductDto;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.stream.Collectors;

public class ProductServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		handleRequest(request, response, HttpMethod.GET);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		handleRequest(request, response, HttpMethod.POST);
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		handleRequest(request, response, HttpMethod.PUT);
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		handleRequest(request, response, HttpMethod.DELETE);
	}

	private void handleRequest(HttpServletRequest request, HttpServletResponse response, HttpMethod method)
		throws IOException {
		String pathInfo = request.getPathInfo();

		try {
			response.setContentType(ResponseEntity.ContentType.JSON.toString());
			setResponseStatus(response, method);

			ResponseEntity<?> responseEntity = switch (method) {
				case GET -> handleGetRequest(pathInfo);
				case POST -> handlePostRequest(request, pathInfo);
				case PUT -> handlePutRequest(request, pathInfo);
				case DELETE -> handleDeleteRequest(pathInfo);
			};

			response.setContentType(responseEntity.contentType().toString());
			response.setStatus(responseEntity.statusCode());
			response.getWriter().write(responseEntity.serialize());

		} catch (ProblemOccurredException e) {
			handleException(response, e);
		}
	}

	private void setResponseStatus(HttpServletResponse response, HttpMethod method) {
		if (method == HttpMethod.DELETE) {
			response.setStatus(HttpURLConnection.HTTP_NO_CONTENT);
		} else {
			response.setStatus(HttpURLConnection.HTTP_OK);
		}
	}

	private ResponseEntity<?> handleGetRequest(String pathInfo) throws ProblemOccurredException {
		if (pathInfo == null || pathInfo.equals("/")) {
			return ProductController.fetchAllProducts();
		} else if (pathInfo.startsWith("/")) {
			return ProductController.fetchById(extractId(pathInfo));
		} else {
			throw pathNotFoundException(pathInfo);
		}
	}

	private ResponseEntity<ProductDto> handlePostRequest(HttpServletRequest request, String pathInfo) throws ProblemOccurredException {
		if (pathInfo == null || pathInfo.equals("/")) {
			return ProductController.create(deserializeBody(request));
		} else {
			throw pathNotFoundException(pathInfo);
		}
	}

	private ResponseEntity<ProductDto> handlePutRequest(HttpServletRequest request, String pathInfo) throws ProblemOccurredException {
		if (pathInfo != null && pathInfo.startsWith("/")) {
			return ProductController.update(extractId(pathInfo), deserializeBody(request));
		} else {
			throw pathNotFoundException(pathInfo);
		}
	}

	private ResponseEntity<?> handleDeleteRequest(String pathInfo) throws ProblemOccurredException {
		if (pathInfo != null && pathInfo.startsWith("/")) {
			return ProductController.delete(extractId(pathInfo));
		} else {
			throw pathNotFoundException(pathInfo);
		}
	}


	private void handleException(HttpServletResponse response, ProblemOccurredException e) throws IOException {
		response.setStatus(e.problem().statusCode());
		response.setContentType(ResponseEntity.ContentType.JSON_PROBLEM.toString());
		response.getWriter().write(e.problem().serialize());
	}

	private Long extractId(String path) {
		String substring = path.substring(1);
		try {
			return Long.parseLong(substring);
		} catch (NumberFormatException exception) {
			throw new ProblemOccurredException(Problems.badRequest("Invalid product ID '" + substring + "'"));
		}
	}

	private static ProductDto deserializeBody(HttpServletRequest request) throws ProblemOccurredException {
		String stringBody;
		try {
			stringBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		} catch (IOException e) {
			throw new ProblemOccurredException(Problems.badRequest(e.getMessage()));
		}
		return JsonMapper.deserialize(stringBody, ProductDto.class);
	}

	private static ProblemOccurredException pathNotFoundException(String pathInfo) {
		return new ProblemOccurredException(Problems.pathNotFound("/products" + pathInfo));
	}

	private enum HttpMethod {
		GET, POST, PUT, DELETE
	}
}
