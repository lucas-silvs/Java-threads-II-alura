package org.lucassilvs.servidor.comandos;

import java.io.PrintStream;
import java.util.concurrent.*;

public class JuntaResultadosFutureComandoC2 implements Callable<Void> {
    private Future<String> stringFutureC2WebServer;
    private Future<String> stringFutureC2BancoDeDados;
    private PrintStream saidaCliente;

    public JuntaResultadosFutureComandoC2(Future<String> stringFutureC2WebServer, Future<String> stringFutureC2BancoDeDados, PrintStream saidaCliente) {

        this.stringFutureC2WebServer = stringFutureC2WebServer;
        this.stringFutureC2BancoDeDados = stringFutureC2BancoDeDados;
        this.saidaCliente = saidaCliente;
    }

    @Override
    public Void call(){

        System.out.println("iniciando executação para juntar resultados Comando C2");

        try {
            String numeroWebServer = this.stringFutureC2WebServer.get(20, TimeUnit.SECONDS);
            String numeroBancoDeDados = this.stringFutureC2BancoDeDados.get(20, TimeUnit.SECONDS);

            this.saidaCliente.println("Comando executado com sucesso, numeros gerados -- Web Server: " + numeroWebServer + " -- Banco de dados: " + numeroBancoDeDados);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            this.saidaCliente.println("Execução do Comando C2 ocorreu um erro - TimeOut: " + e.getMessage());
            System.out.println("Cancelando Execução do comando C2");
            this.stringFutureC2BancoDeDados.cancel(true);
            this.stringFutureC2WebServer.cancel(true);
        }


        return null;
    }
}
