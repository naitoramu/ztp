package io.nightovis.ztp.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class OrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/orders")) {
            // Handle GET request for "/orders"
            response.getWriter().write("GET request for /orders");
        } else if (pathInfo.startsWith("/orders/")) {
            // Handle GET request for "/orders/{orderId}"
            String orderId = pathInfo.substring(8); // Extract orderId from the URL
            response.getWriter().write("GET request for /orders/" + orderId);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo.equals("/orders")) {
            // Handle POST request for "/orders"
            response.getWriter().write("POST request for /orders");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo.startsWith("/orders/")) {
            // Handle PUT request for "/orders/{orderId}"
            String orderId = pathInfo.substring(8); // Extract orderId from the URL
            response.getWriter().write("PUT request for /orders/" + orderId);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo.startsWith("/orders/")) {
            // Handle DELETE request for "/orders/{orderId}"
            String orderId = pathInfo.substring(8); // Extract orderId from the URL
            response.getWriter().write("DELETE request for /orders/" + orderId);
        }
    }
}
