package org.lucassilvs.servidor;

import org.lucassilvs.servidor.comandos.ComandoC1;
import org.lucassilvs.servidor.comandos.ComandoC2AcessaBancoDeDados;
import org.lucassilvs.servidor.comandos.ComandoC2ChamaWebService;
import org.lucassilvs.servidor.comandos.JuntaResultadosFutureComandoC2;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class DistribuirTarefas implements Runnable{


    private Socket socket;

    private BlockingQueue<String> filaComandos;
    private ServidorTarefas servidorTarefas;

    private ExecutorService threadPool;

    public DistribuirTarefas(Socket socket, BlockingQueue<String> filaComandos, ServidorTarefas servidorTarefas, ExecutorService threadPool) {
        this.socket = socket;
        this.filaComandos = filaComandos;
        this.servidorTarefas = servidorTarefas;
        this.threadPool = threadPool;
        iniciarConsumidores();

    }

    private void iniciarConsumidores() {
        int quantidadeConsumidores = 2;
        for (int i = 0; i < quantidadeConsumidores; i++) {
            TarefaConsumir tarefaConsumir = new TarefaConsumir(filaComandos);
            this.threadPool.execute(tarefaConsumir);
        }
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
                        ComandoC2ChamaWebService comandoC2ChamaWebService = new ComandoC2ChamaWebService(saidaCliente);
                        ComandoC2AcessaBancoDeDados comandoC2AcessaBancoDeDados = new ComandoC2AcessaBancoDeDados(saidaCliente);
                        Future<String> stringFutureC2WebServer = this.threadPool.submit(comandoC2ChamaWebService);
                        Future<String> stringFutureC2BancoDeDados = this.threadPool.submit(comandoC2AcessaBancoDeDados);

//                        Implementação utilizando FutureTask, que permite um Callabe ser executado em uma Thread

//                        FutureTask<String> futureTaskC2ChamaWebService = new FutureTask<>(comandoC2ChamaWebService);
//                        new Thread(futureTaskC2ChamaWebService).start();
//                        String stringFutureC2WebServer = futureTaskC2ChamaWebService.get();


                        threadPool.submit(new JuntaResultadosFutureComandoC2(stringFutureC2WebServer, stringFutureC2BancoDeDados, saidaCliente));

                        break;
                    }
                    case "c3":{
                        this.filaComandos.put(comando); // Bloqueia caso chegue no limite total da fila
                        saidaCliente.println("Adicionando comando na fila");

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
