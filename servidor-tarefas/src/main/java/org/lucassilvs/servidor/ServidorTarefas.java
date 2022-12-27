package org.lucassilvs.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorTarefas {

    private final ServerSocket servidor;
    private final ExecutorService threadPool;

    private AtomicBoolean rodando;

    private BlockingQueue<String> filaComandos;

    public ServidorTarefas() throws IOException {
        this.servidor = new ServerSocket(5000);
        this.threadPool = Executors.newCachedThreadPool(new FabricaDeThread());  // Tamanho dinâmico
        this.rodando = new AtomicBoolean(true);
        this.filaComandos = new ArrayBlockingQueue<>(2);

        System.out.println("-------- Iniciando servidor manual ----------------");
    }

    public void rodar() throws IOException {
        while (this.rodando.get()) {
            try {
                Socket socket = servidor.accept();
                System.out.println("Aceitando novo cliente " + socket.getPort());

                DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket, filaComandos, this, threadPool);
                threadPool.execute(distribuirTarefas);
            }catch (SocketException e){
                System.out.println("Conexão interrompida, saindo do loop");
            }
        }
    }

    public void parar() throws IOException {
        this.rodando.set(false);
        servidor.close();
        threadPool.shutdown();
    }





    public static void main(String[] args) throws IOException {

        ServidorTarefas servidorTarefas = new ServidorTarefas();
        servidorTarefas.rodar();




    }
}
