package org.lucassilvs.servidor.comandos;

import java.io.PrintStream;

public class ComandoC2  implements Runnable{

    private PrintStream saidaCliente;
    public ComandoC2(PrintStream saidaCliente) {
        this.saidaCliente = saidaCliente;
    }

    @Override
    public void run() {
        System.out.println("Executando Comando C2");
        try {
            Thread.sleep(40000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        saidaCliente.println("Comando C2 executado");

    }
}
