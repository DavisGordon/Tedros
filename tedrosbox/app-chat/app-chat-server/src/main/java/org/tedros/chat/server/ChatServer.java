package org.tedros.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.tedros.chat.entity.ChatMessage;

public class ChatServer {

    private ServerSocket server;
    //hold clients
    private final ArrayList<ServerConnHandler> clients;

    public ChatServer(int port) throws IOException {
        clients = new ArrayList<ServerConnHandler>();
        listen(port);
    }

    private void listen(int port) throws IOException {

        //socket server
        server = new ServerSocket(port);
        System.out.println("Listening at " + server);

        //Establish a connection
        while (true) {

            Socket client = server.accept();
            System.out.println("Connection established: " + client);

            //client handler
            clients.add(new ServerConnHandler(this, client));
        }
    }

    public void replyMessage(ChatMessage msg) {
        synchronized (clients) {
            clients.parallelStream()
            .filter(h->{
            	return msg.getTo().stream().filter(p->{
            		return h.getOwner().equals(p) && !msg.getFrom().equals(p);
            	}).findFirst().isPresent();
            }).forEach(c->{
            	c.send(msg);
            });
        }
    }

    public void removeClient(ServerConnHandler client) {
        synchronized (clients) {
            System.out.println("Removing " + client);
            clients.remove(client);
            System.out.println("Remaining Clients : " + clients.size());
            try {
                client.close();
            } catch (IOException ex) {
                System.out.println("Error while removing client " + client);
                System.out.println(ex.getMessage());
            }
        }
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
