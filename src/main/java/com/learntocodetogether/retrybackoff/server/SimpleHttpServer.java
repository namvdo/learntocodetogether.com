package com.learntocodetogether.retrybackoff.server;


import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author namvdo
 */
public class SimpleHttpServer {

    private final SimpleHttpHandler handler;
    public SimpleHttpServer(SimpleHttpHandler handler) {
        this.handler = handler;
    }
    public void run() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 8080), 0);
        server.createContext("/retry", handler);
        server.start();
    }
}
