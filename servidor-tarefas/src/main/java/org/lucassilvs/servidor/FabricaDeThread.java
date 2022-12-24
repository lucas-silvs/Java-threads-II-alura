package org.lucassilvs.servidor;

import org.lucassilvs.experimento.TratadorDeError;

import java.util.concurrent.ThreadFactory;

public class FabricaDeThread implements ThreadFactory {

    private static int numeroThread = 1;
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, "Thread do servidor numero - " + numeroThread);
        thread.setUncaughtExceptionHandler(new TratadorDeError());
        thread.setDaemon(true);
        numeroThread++;

        return thread;
    }
}
