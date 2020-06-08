package com.hy.nio;

public class NIOTimeCLient {
    public static void main(String[] args) {
        new Thread(new TimeClientHandle("127.0.0.1", 8080)).start();
    }
}
