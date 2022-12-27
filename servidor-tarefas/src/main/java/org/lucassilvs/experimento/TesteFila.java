package org.lucassilvs.experimento;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TesteFila {


    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> filaString = new ArrayBlockingQueue<>(3);

        filaString.put("c1");
        filaString.put("c2");
        filaString.put("c3");

        System.out.println(filaString.poll());
        System.out.println(filaString.take());
        System.out.println(filaString.size());
    }
}
