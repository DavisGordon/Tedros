package org.tedros.chat.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.tedros.chat.entity.ChatMessage;
import org.tedros.chat.entity.ChatUser;
import org.tedros.chat.entity.TStatus;

public class ServerConnHandler extends Thread {

	private ChatUser owner;
    private ChatServer server;
    private Socket socket;
    private ObjectOutputStream dout;

    public ServerConnHandler(ChatServer server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        dout = new ObjectOutputStream(socket.getOutputStream());
        
        start();
    }

    @Override
    public void run() {
    	ObjectInputStream ois = null;
        try {
        	ois = new ObjectInputStream(socket.getInputStream());
            while (true) {
            	Object obj = ois.readObject();
            	if(obj instanceof ChatMessage) {
	            	ChatMessage msg = (ChatMessage) obj;
	                server.replyMessage(msg);
            	}else if(obj instanceof ChatUser)
            		owner = (ChatUser) obj;
            }
        } catch (EOFException ex) {
            //DO NOTHING
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            server.removeClient(this);
            try {
            	if(ois!=null)
            		ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }

    public void send(ChatMessage msg) {
        try {
        	msg.setStatus(TStatus.RECEIVED);
        	dout.writeObject(msg);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void close() throws IOException {
        socket.close();
    }

	/**
	 * @return the owner
	 */
	public ChatUser getOwner() {
		return owner;
	}
}
