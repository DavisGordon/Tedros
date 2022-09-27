package org.tedros.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {

    private ServerSocket servidor;
    //guardar os clientes
    private final ArrayList<ServerConnHandler> clientes;

    public ChatServer(int port) throws IOException {
        clientes = new ArrayList<ServerConnHandler>();
        listen(port);
    }

    private void listen(int port) throws IOException {

        //criar o socket servidor
        servidor = new ServerSocket(port);
        System.out.println("À escuta em " + servidor);

        //aceitar ligações para sempre
        while (true) {

            Socket cliente = servidor.accept();
            System.out.println("Ligação aceite de " + cliente);

            //criar um novo handler para este cliente
            clientes.add(new ServerConnHandler(this, cliente));
        }
    }

    public void replicarMensagem(String mensagem) {
        //vamos sincronizar o nosso acesso à lista para evitar problemas se alguma
        //outra thread estiver a tentar adicionar ou remover elementos
        synchronized (clientes) {
            for (ServerConnHandler cl : clientes) {
                cl.enviarMensagem(mensagem);
            }
        }
    }

    public void removerCliente(ServerConnHandler cliente) {
        synchronized (clientes) {
            System.out.println("A remover a ligação de " + cliente);
            clientes.remove(cliente);
            System.out.println("Ligações restantes: " + clientes.size());
            try {
                cliente.fechar();
            } catch (IOException ex) {
                System.out.println("Erro ao desligar o contacto com " + cliente);
                System.out.println(ex.getMessage());
            }
        }
    }

    public void removeConnection(Socket cliente) {
    }

    public static void main(String args[]) {
        try {
            if (args.length == 0) {
                new ChatServer(5000);
            } else {
                new ChatServer(Integer.parseInt(args[0]));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
