package org.lucassilvs.cliente;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTarefa {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 5000);

        OutputStream outputStreamCliente = socket.getOutputStream();
        PrintStream saidaCliente = new PrintStream(outputStreamCliente);
        saidaCliente.println("c1");

        System.out.println("Conexão estabelecida com o cliente");

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        scanner.close();
        saidaCliente.close();
        socket.close();
    }
}
