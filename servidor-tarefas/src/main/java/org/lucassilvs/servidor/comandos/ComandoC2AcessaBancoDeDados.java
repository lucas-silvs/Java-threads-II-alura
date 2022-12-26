package org.lucassilvs.servidor.comandos;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoC2AcessaBancoDeDados implements Callable<String> {

    private PrintStream saidaCliente;
    public ComandoC2AcessaBancoDeDados(PrintStream saidaCliente) {
        this.saidaCliente = saidaCliente;
    }

    @Override
    public String call() throws Exception{
        System.out.println("Executando Comando C2 - banco de dados");
        Thread.sleep(15000);

        int random = new Random().nextInt(100) + 1;
        saidaCliente.println("Comando C2 finalizado - Banco de dados");
        return Integer.toString(random);
    }
}
