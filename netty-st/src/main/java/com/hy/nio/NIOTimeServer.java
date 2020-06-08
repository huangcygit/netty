package com.hy.nio;

public class NIOTimeServer {
    public static void main(String[] args) {
        int port = 8080;
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer( port);
        new Thread(timeServer).start();
    }
}
