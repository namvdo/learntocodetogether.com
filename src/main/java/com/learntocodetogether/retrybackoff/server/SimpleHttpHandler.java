package com.learntocodetogether.retrybackoff.server;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author namvdo
 */
public class SimpleHttpHandler implements HttpHandler {
    private final RequestHandler retryRequestHandler;

    public SimpleHttpHandler(RequestHandler retryRequestHandler) {
        this.retryRequestHandler = retryRequestHandler;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String requestMethod = httpExchange.getRequestMethod();
        if ("GET".equals(requestMethod)) {
            String[] params = httpExchange.getRequestURI()
                    .toString()
                    .split("\\?")[1]
                    .split("&");
            String uuid = params[0].split("=")[1];
            int succeededOn = Integer.parseInt(params[1].split("=")[1]);
            RetryRequest retryRequest = new RetryRequest(uuid, succeededOn);
            String response = retryRequestHandler.handle(retryRequest);
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}
