package org.lucassilvs.servidor.comandos;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoC2ChamaWebService  implements Callable<String> {

    private PrintStream saidaCliente;
    public ComandoC2ChamaWebService(PrintStream saidaCliente) {
        this.saidaCliente = saidaCliente;
    }

    @Override
    public String call() throws Exception{
        System.out.println("Executando Comando C2 - Web Server");
        Thread.sleep(25000);

        int random = new Random().nextInt(100) + 1;
        saidaCliente.println("Comando C2 finalizado - Web Server");
        return Integer.toString(random);
    }
}
