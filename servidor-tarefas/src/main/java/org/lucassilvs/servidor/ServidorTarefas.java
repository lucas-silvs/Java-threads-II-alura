package org.lucassilvs.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorTarefas {

    public static void main(String[] args) throws IOException, InterruptedException {

        ServerSocket servidor = new ServerSocket(5000);
        //ExecutorService threadPool = Executors.newFixedThreadPool(2); numero fixo de Threads disponiveis para uso
        ExecutorService threadPool = Executors.newCachedThreadPool(); // Tamanho dinâmico


        System.out.println("-------- Iniciando servidor manual ----------------");

        while (true) {
            Socket socket = servidor.accept();
            System.out.println("Aceitando novo cliente " + socket.getPort());


            DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket);
            threadPool.execute(distribuirTarefas);
        }


    }
}
