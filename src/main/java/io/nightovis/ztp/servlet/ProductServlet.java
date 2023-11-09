package io.nightovis.ztp.servlet;

import io.nightovis.ztp.JsonMapper;
import io.nightovis.ztp.ProblemOccurredException;
import io.nightovis.ztp.Problems;
import io.nightovis.ztp.controller.ProductController;
import io.nightovis.ztp.dto.ProductDto;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.stream.Collectors;


public class ProductServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String pathInfo = request.getPathInfo();

		try {
			response.setContentType(ResponseEntity.ContentType.JSON.toString());
			response.setStatus(HttpURLConnection.HTTP_OK);
			if (pathInfo == null || pathInfo.equals("/")) {
				response.getWriter().write(ProductController.fetchAllProducts().serialize());
			} else if (pathInfo.startsWith("/")) {
				ResponseEntity<ProductDto> responseEntity = ProductController.fetchById(
					extractId(pathInfo)
				);

				response.getWriter().write(responseEntity.serialize());
			} else {
				throw new ProblemOccurredException(Problems.pathNotFound("/products" + pathInfo));
			}
		} catch (ProblemOccurredException e) {
			response.setStatus(e.problem().statusCode());
			response.setContentType(ResponseEntity.ContentType.JSON_PROBLEM.toString());
			response.getWriter().write(e.problem().serialize());
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String pathInfo = request.getPathInfo();

		try {
			if (pathInfo == null || pathInfo.equals("/")) {
				response.setContentType(ResponseEntity.ContentType.JSON.toString());
				response.setStatus(HttpURLConnection.HTTP_CREATED);
				response.getWriter().write(ProductController.create(deserializeBody(request)).serialize());
			} else {
				throw new ProblemOccurredException(Problems.pathNotFound("/products" + pathInfo));
			}
		} catch (ProblemOccurredException e) {
			response.setStatus(e.problem().statusCode());
			response.setContentType(ResponseEntity.ContentType.JSON_PROBLEM.toString());
			response.getWriter().write(e.problem().serialize());
		}
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String pathInfo = request.getPathInfo();

		try {
			if (pathInfo != null && pathInfo.startsWith("/")) {
				response.setContentType(ResponseEntity.ContentType.JSON.toString());
				response.setStatus(HttpURLConnection.HTTP_OK);
				ResponseEntity<ProductDto> responseEntity = ProductController.update(
					extractId(pathInfo),
					deserializeBody(request)
				);

				response.getWriter().write(responseEntity.serialize());
			} else {
				throw new ProblemOccurredException(Problems.pathNotFound("/products" + pathInfo));
			}
		} catch (ProblemOccurredException e) {
			response.setStatus(e.problem().statusCode());
			response.setContentType(ResponseEntity.ContentType.JSON_PROBLEM.toString());
			response.getWriter().write(e.problem().serialize());
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String pathInfo = request.getPathInfo();
		response.setStatus(206);

		try {
			if (pathInfo != null && pathInfo.startsWith("/")) {
				response.setContentType(ResponseEntity.ContentType.JSON.toString());
				response.setStatus(HttpURLConnection.HTTP_NO_CONTENT);
				ProductController.delete(extractId(pathInfo));
			} else {
				throw new ProblemOccurredException(Problems.pathNotFound("/products" + pathInfo));
			}
		} catch (ProblemOccurredException e) {
			response.setStatus(e.problem().statusCode());
			response.setContentType(ResponseEntity.ContentType.JSON_PROBLEM.toString());
			response.getWriter().write(e.problem().serialize());
		}
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
}

