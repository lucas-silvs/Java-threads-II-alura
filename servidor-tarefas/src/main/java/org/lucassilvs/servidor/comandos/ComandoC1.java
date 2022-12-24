package org.lucassilvs.servidor.comandos;

import java.io.PrintStream;

public class ComandoC1 implements  Runnable{

    private PrintStream saidaCliente;
    public ComandoC1(PrintStream saidaCliente) {
        this.saidaCliente = saidaCliente;
    }

    @Override
    public void run() {
        System.out.println("Executando Comando C1");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Interrompendo Thread");
        //saidaCliente.println("Comando C1 executado");


    }
}
