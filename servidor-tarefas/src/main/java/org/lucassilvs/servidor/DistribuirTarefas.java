package org.lucassilvs.servidor;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class DistribuirTarefas implements Runnable{


    private Socket socket;

    private ServidorTarefas servidorTarefas;

    public DistribuirTarefas(Socket socket, ServidorTarefas servidorTarefas) {
        this.socket = socket;
        this.servidorTarefas = servidorTarefas;
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
                        saidaCliente.println("Comando C1 executado");
                        break;
                    }
                    case "c2":{
                        saidaCliente.println("Comando C2 executado");
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
