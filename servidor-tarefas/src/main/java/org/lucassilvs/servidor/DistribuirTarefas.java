package org.lucassilvs.servidor;

import org.lucassilvs.servidor.comandos.ComandoC1;
import org.lucassilvs.servidor.comandos.ComandoC2;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;

public class DistribuirTarefas implements Runnable{


    private Socket socket;

    private ServidorTarefas servidorTarefas;

    private ExecutorService threadPool;

    public DistribuirTarefas(Socket socket, ServidorTarefas servidorTarefas, ExecutorService threadPool) {
        this.socket = socket;
        this.servidorTarefas = servidorTarefas;
        this.threadPool = threadPool;

    }

    @Override
    public void run() {

        System.out.println("distribuindo tarefas para " + socket.getPort());

        Scanner entradaCliente;
        try {
            entradaCliente = new Scanner(socket.getInputStream());
            PrintStream saidaCliente = new PrintStream(socket.getOutputStream());

            while (entradaCliente.hasNextLine()){

                String comando = entradaCliente.nextLine();
                System.out.println("Comando Recebido: " + comando);


                switch (comando){
                    case "c1": {
                        ComandoC1 comandoC1 = new ComandoC1(saidaCliente);
                        this.threadPool.execute(comandoC1);
                        break;
                    }
                    case "c2":{
                        ComandoC2 comandoC2 = new ComandoC2(saidaCliente);
                        this.threadPool.execute(comandoC2);
                        break;
                    }
                    case "off":{
                        saidaCliente.println("Desligando Servidor");
                        servidorTarefas.parar();
                        break;
                    }
                    default:{
                        saidaCliente.println("Comando não encontrado. Comando: " + comando);
                        break;
                    }

                }
            }
            saidaCliente.close();
            entradaCliente.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
