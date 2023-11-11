package io.nightovis.ztp.api.servlet;

import io.nightovis.ztp.api.controller.OrderController;
import io.nightovis.ztp.api.servlet.util.HttpMethod;
import io.nightovis.ztp.api.servlet.util.ResponseEntity;
import io.nightovis.ztp.model.dto.OrderDto;
import io.nightovis.ztp.problem.ProblemOccurredException;
import io.nightovis.ztp.problem.Problems;
import io.nightovis.ztp.util.JsonMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Collectors;

public class OrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleRequest(request, response, HttpMethod.GET);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleRequest(request, response, HttpMethod.POST);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response, HttpMethod method)
        throws IOException {
        String pathInfo = request.getPathInfo();

        try {
            ResponseEntity<?> responseEntity = switch (method) {
                case GET -> handleGetRequest(pathInfo);
                case POST -> handlePostRequest(request, pathInfo);
                default -> throw new ProblemOccurredException(Problems.badMethod(method));
            };

            response.setContentType(responseEntity.contentType().toString());
            response.setStatus(responseEntity.statusCode());
            response.getWriter().write(responseEntity.serialize());

        } catch (ProblemOccurredException e) {
            handleException(response, e);
        }
    }

    private ResponseEntity<?> handleGetRequest(String pathInfo) throws ProblemOccurredException {
        if (pathInfo == null || pathInfo.equals("/")) {
            return OrderController.fetchAll();
        } else if (pathInfo.startsWith("/")) {
            return OrderController.fetchById(extractId(pathInfo));
        } else {
            throw pathNotFoundException(pathInfo);
        }
    }

    private ResponseEntity<OrderDto> handlePostRequest(HttpServletRequest request, String pathInfo) throws ProblemOccurredException {
        if (pathInfo == null || pathInfo.equals("/")) {
            return OrderController.create(deserializeBody(request));
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
            throw new ProblemOccurredException(Problems.badRequest("Invalid order ID '" + substring + "'"));
        }
    }

    private static OrderDto deserializeBody(HttpServletRequest request) throws ProblemOccurredException {
        String stringBody;
        try {
            stringBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new ProblemOccurredException(Problems.badRequest(e.getMessage()));
        }
        return JsonMapper.deserialize(stringBody, OrderDto.class);
    }

    private static ProblemOccurredException pathNotFoundException(String pathInfo) {
        return new ProblemOccurredException(Problems.pathNotFound("/orders" + pathInfo));
    }

}
