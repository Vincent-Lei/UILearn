package com.demo.ui.uiapplication.niotest;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.Pipe;

/**
 * Created by Vincent.Lei on 2019/3/4.
 * Title：
 * Note：
 */
public class NIOTest {

    private static final String FILE_PATH = "C:\\Users\\Android\\Desktop\\test\\11.txt";
    private static final String TO_FILE_PATH = "C:\\Users\\Android\\Desktop\\test\\22.txt";
    private static final String MODE_RW = "rw";

    private static void print(String msg) {
        System.out.println(msg);
    }

    public static void main(String[] args) {
        pipe();
    }

    private static void transferFrom() {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(FILE_PATH, MODE_RW);
            FileChannel fileChannel = randomAccessFile.getChannel();

            RandomAccessFile toFile = new RandomAccessFile(TO_FILE_PATH, MODE_RW);
            FileChannel toChannel = toFile.getChannel();
            toChannel.transferFrom(fileChannel, 0, fileChannel.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void fileRead() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            RandomAccessFile randomAccessFile = new RandomAccessFile(FILE_PATH, "rw");
            FileChannel fileChannel = randomAccessFile.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(128);
            int len;
            while ((len = fileChannel.read(byteBuffer)) > 0) {
                byteBuffer.flip();
                byteArrayOutputStream.write(byteBuffer.array(), 0, len);
                byteBuffer.clear();
            }
            fileChannel.close();
            System.out.println(new String(byteArrayOutputStream.toByteArray(), "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void scattering() {
        ByteBuffer[] byteBuffers = {ByteBuffer.allocate(48), ByteBuffer.allocate(128)};
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(FILE_PATH, "rw");
            FileChannel fileChannel = randomAccessFile.getChannel();
            fileChannel.read(byteBuffers);
            fileChannel.close();
            byteBuffers[0].flip();
            byteBuffers[1].flip();
            System.out.println(new String(byteBuffers[0].array(), "utf-8"));
            System.out.println(new String(byteBuffers[1].array(), "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void gathering() {
        ByteBuffer[] byteBuffers = {ByteBuffer.allocate(128), ByteBuffer.allocate(128)};
        RandomAccessFile randomAccessFile;
        try {
            byteBuffers[0].put("$$$$$$$$$$$".getBytes());
            byteBuffers[1].put("@@@@@@@@@%%%%%%%%%%%%%%%%%%%%%".getBytes());
            byteBuffers[0].flip();
            byteBuffers[1].flip();
            randomAccessFile = new RandomAccessFile(FILE_PATH, "rw");
            randomAccessFile.seek(randomAccessFile.length());
            FileChannel fileChannel = randomAccessFile.getChannel();
            fileChannel.write(byteBuffers);
            fileChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void copy() {
        FileInputStream fis;
        FileOutputStream fos;
        try {
            fis = new FileInputStream(FILE_PATH);
            fos = new FileOutputStream("C:\\Users\\Android\\Desktop\\test\\33.txt");
            FileChannel fileChannel = fis.getChannel();
            FileChannel outChannel = fos.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(128);
            int len;
            while ((len = fileChannel.read(byteBuffer)) > 0) {
                byteBuffer.flip();
                outChannel.write(byteBuffer);
                byteBuffer.clear();
            }
            outChannel.force(true);
            fileChannel.close();
            outChannel.close();


//            byteBuffer.clear();
//            FileChannel fc = fis.getChannel();
//            fc.read(byteBuffer);
//            byteBuffer.flip();
//            System.out.println("limit = " + byteBuffer.limit());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void pipe() {
        try {
            Pipe pipe = Pipe.open();
            Pipe.SinkChannel sinkChannel = pipe.sink();
            ByteBuffer buf = ByteBuffer.allocate(48);
            buf.clear();
            buf.put("this is test data".getBytes());
            buf.flip();
            sinkChannel.write(buf);
            sinkChannel.close();
            buf.clear();
            Pipe.SourceChannel sourceChannel = pipe.source();
            int len = sourceChannel.read(buf);
            System.out.println("len = " + len);
            buf.flip();
            sourceChannel.close();
            System.out.println(new String(buf.array()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
