package org.lucassilvs.experimento;

public class TratadorDeError implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("Deu erro na Thread "+ t.getName()+ " Erro: " + e.getMessage());

    }
}
