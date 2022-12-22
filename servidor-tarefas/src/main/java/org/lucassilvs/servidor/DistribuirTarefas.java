package org.lucassilvs.servidor;

import java.net.Socket;

public class DistribuirTarefas implements Runnable{


    private Socket socket;

    public DistribuirTarefas(Socket socket) {

        this.socket = socket;
    }

    @Override
    public void run() {

        System.out.println("distribuindo tarefas para " + socket.getPort());

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
