package org.lucassilvs.cliente;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTarefa {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 5000);

        System.out.println("Conexão estabelecida com o cliente");

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        socket.close();
    }
}
