package io.nightovis.ztp;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/products")) {
            // Handle GET request for "/products"
            response.getWriter().write("GET request for /products");
        } else if (pathInfo.startsWith("/products/")) {
            // Handle GET request for "/products/{productId}"
            String productId = pathInfo.substring(10); // Extract productId from the URL
            response.getWriter().write("GET request for /products/" + productId);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo.equals("/products")) {
            // Handle POST request for "/products"
            response.getWriter().write("POST request for /products");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo.startsWith("/products/")) {
            // Handle PUT request for "/products/{productId}"
            String productId = pathInfo.substring(10); // Extract productId from the URL
            response.getWriter().write("PUT request for /products/" + productId);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo.startsWith("/products/")) {
            // Handle DELETE request for "/products/{productId}"
            String productId = pathInfo.substring(10); // Extract productId from the URL
            response.getWriter().write("DELETE request for /products/" + productId);
        }
    }
}

