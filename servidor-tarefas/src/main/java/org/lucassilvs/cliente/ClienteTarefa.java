package org.lucassilvs.cliente;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTarefa {

    public static void main(String[] args) throws IOException, InterruptedException {

        Socket socket = new Socket("localhost", 5000);

        OutputStream outputStreamCliente = socket.getOutputStream();


        Thread threadEnviaComando = new Thread(new Runnable() {
            @Override
            public void run() {
                PrintStream saidaCliente = new PrintStream(outputStreamCliente);
                System.out.println("Conexão estabelecida com o cliente");

                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNextLine()) {
                    String linha =  scanner.nextLine();

                    if(linha.trim().equals("")){
                        break;
                    }

                    saidaCliente.println(linha);
                }
                scanner.close();
                saidaCliente.close();
            }
        });

        Thread threadRecebeResposta = new Thread(new Runnable() {
            @Override
            public void run() {

                Scanner respostaServidor = null;
                System.out.println("Enviando comandos para os clientes");
                try {
                    respostaServidor = new Scanner(socket.getInputStream());

                    System.out.println("Recebendo dados do servidor");
                    while (respostaServidor.hasNextLine()){
                        String linha = respostaServidor.nextLine();
                        System.out.println(linha);
                    }
                    respostaServidor.close();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        threadEnviaComando.start();
        threadRecebeResposta.start();

        threadEnviaComando.join(); // informa para a thread main da classe ClienteTarefa aguardar o encerramentoda ThreadEnviaComando terminar antes de fechar o socket
        socket.close();
    }
}
