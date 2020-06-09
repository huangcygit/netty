package com.hy.buffer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferDemo {
    public static void main(String[] args) throws IOException {
        FileInputStream fin = new FileInputStream("D://test.txt");
        //创建文件的操作通道
        FileChannel channel = fin.getChannel();

        //分配一个10个大小的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(10);
        output("初始化",buffer);

        channel.read(buffer);
        output("调用read()",buffer);

        buffer.flip();
        output("flip()",buffer);

        while (buffer.remaining() > 0){
            byte b = buffer.get();
        }
        output("调用 get()", buffer);

        //可以理解为解锁
        buffer.clear();
        output("调用 clear()", buffer);

        fin.close();
    }

    private static void output(String step, ByteBuffer buffer) {
        System.out.println(step + " : ");
        System.out.print("capacity : " + buffer.capacity() + ",");
        System.out.print("position : " + buffer.position() + ",");
        System.out.println("limit : " + buffer.limit());

    }
}
