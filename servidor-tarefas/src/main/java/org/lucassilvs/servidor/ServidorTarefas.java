package org.lucassilvs.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTarefas {

    public static void main(String[] args) throws IOException, InterruptedException {

        ServerSocket servidor = new ServerSocket(5000);

        System.out.println("-------- Iniciando servidor manual ----------------");

        while (true) {
            Socket socket = servidor.accept();
            System.out.println("Aceitando novo cliente " + socket.getPort());

            DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket);
            Thread threadCliente = new Thread(distribuirTarefas);
            threadCliente.start();
        }


    }
}
