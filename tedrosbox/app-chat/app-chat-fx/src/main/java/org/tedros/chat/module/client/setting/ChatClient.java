/**
 * 
 */
package org.tedros.chat.module.client.setting;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.tedros.chat.entity.ChatUser;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.security.model.TUser;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * @author Davis Gordon
 *
 */
public class ChatClient {
	
	private static ChatClient instance;
	
	//Socket establish the connection
    private Socket socket;
    //Read Stream
    private ObjectInputStream din;
    //Write Stream
    private ObjectOutputStream dout;
    //Message property
    private SimpleObjectProperty<Object> message;
    
    private boolean receive = true;
    private ChatUtil util;
    private ChatUser owner;

	private ChatClient() {

		message = new SimpleObjectProperty<>();
		util = new ChatUtil();
		TUser u = TedrosContext.getLoggedUser();
		try {
			owner = util.findUser(u.getAccessToken(), u.getId(), null);
			owner.setToken(u.getAccessToken());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Return the chat owner
	 * */
	public ChatUser getOwner() {
		return owner;
	}
	
	/**
	 * Get the last object received
	 * */
	public Object getMessage() {
		return message.getValue();
	}
	
	/**
	 * The received message property
	 * */
	public ReadOnlyObjectProperty<Object> messageProperty() {
		return message;
	}
	
	/**
	 * Send an object as message
	 * */
	public void send(Object obj) throws IOException {
		dout.writeObject(obj);
	}

	/**
	 * Connect to the Server
	 * */
	public void connect() {
		try {
            System.out.println("<-client->: Connecting...\n");
            String host = "localhost";
            int port = 5000;

            //criar o socket
            socket = new Socket(host, port);
            //como não ocorreu uma excepção temos um socket aberto
            System.out.println("<-client->: Connected...\n");

            //Vamos obter as streams de comunicação fornecidas pelo socket
            din = new ObjectInputStream(socket.getInputStream());
            dout = new ObjectOutputStream(socket.getOutputStream());

            //e iniciar a thread que vai estar constantemente à espera de novas
            //mensages. Se não usassemos uma thread, não conseguiamos receber
            //mensagens enquanto estivessemos a escrever e toda a parte gráfica
            //ficaria bloqueada.
          new Thread(new Runnable() {
                public void run() {
                    try {
                        while (receive) {
                        	Object obj = din.readObject();
							Platform.runLater(()->{
                        		message.setValue(obj);
                        		message.setValue(null);
                        	});
                        }
                    } catch (Exception ex) {
                    	System.out.println("<-client->: " + ex.getMessage());
                    }
                }
            }).start();
          
          send(owner);
          
        } catch (Exception ex) {
        	System.out.println("<-client->: " + ex.getMessage());
        }
	}
	
	/**
	 * Close the connection with the server
	 * */
	public void close() {
		receive = false; 
		if (socket != null) {
            try {
                socket.close();
            } catch (IOException ex) {
            	ex.getMessage();
            }
        }
	}
	
	public static ChatClient getInstance() {
		if(instance==null)
			instance = new ChatClient();
		return instance;
	}

}
