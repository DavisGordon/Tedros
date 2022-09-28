package org.tedros.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class ServerConnHandler extends Thread {

	private String userId;
    private ChatServer servidor;
    private Socket socket;
    private DataOutputStream dout;

    public ServerConnHandler(ChatServer servidor, Socket socket) throws IOException {
        this.servidor = servidor;
        this.socket = socket;
        dout = new DataOutputStream(socket.getOutputStream());
        //somos uma thread, vamos começar...
        start();
    }

    @Override
    public void run() {
        try {
            //como noutras situações, obter as streams de leitura e escrita.
            DataInputStream din = new DataInputStream(socket.getInputStream());
            String mensagem;
            while (true) {
                mensagem = din.readUTF();
                System.err.println("SR LIDO: " + mensagem);
                //se não foi enviada a mensagem de saida então enviamos o
                //texto para todos
                servidor.replicarMensagem(mensagem);
            }
        } catch (EOFException ex) {
            //DO NOTHING
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            servidor.removeConnection(socket);
        }
    }

    public void enviarMensagem(String mensagem) {
        try {
            dout.writeUTF(mensagem);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void fechar() throws IOException {
        socket.close();
    }
}
