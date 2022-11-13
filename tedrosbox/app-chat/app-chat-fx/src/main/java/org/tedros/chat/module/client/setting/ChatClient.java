/**
 * 
 */
package org.tedros.chat.module.client.setting;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.tedros.chat.CHATKey;
import org.tedros.chat.entity.ChatUser;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.security.model.TUser;
import org.tedros.server.security.TAccessToken;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

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
    private SimpleBooleanProperty connected;
    private SimpleStringProperty log; 
    private boolean receive = true;
    private ChatUtil util;
    private ChatUser owner;

	private ChatClient() {
		connected = new SimpleBooleanProperty(false);
		message = new SimpleObjectProperty<>();
		log = new SimpleStringProperty();
		util = new ChatUtil();
		findOwner();
	}

	/**
	 * 
	 */
	private void findOwner() {
		TUser u = TedrosContext.getLoggedUser();
		try {
			owner = util.findUser(u.getAccessToken(), u.getId(), null);
			owner.setToken(u.getAccessToken());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Connect to the Server
	 * */
	public void connect() {
		try {
			log.setValue(null);
			if(owner==null) 
				findOwner();
			if(owner==null) 
				throw new Exception(TLanguage.getInstance().getString(CHATKey.ERROR_USER_NOT_FOUND));
			
            TAccessToken token = TedrosContext.getLoggedUser().getAccessToken();
            String host = util.getServerIp(token);
            Integer port = util.getServerPort(token);
            if(host==null || port==null)
            	throw new Exception(TLanguage.getInstance().getString(CHATKey.ERROR_SERVER_PREFERENCE));

            System.out.println("<-client->: Connecting...\n");
           
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
         new Thread(()-> {
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
    			Platform.runLater(()->{
    				log.setValue(TLanguage.getInstance().getFormatedString(CHATKey.MSG_ERROR, ex.getMessage()));
    				connected.setValue(false); 
    			});
            }
         }).start();
          
         send(owner);
         connected.setValue(true);
        } catch (UnknownHostException ex) {
        	String reason = TLanguage.getInstance().getString(CHATKey.ERROR_SERVER_OUT);
			log.setValue(TLanguage.getInstance().getFormatedString(CHATKey.MSG_ERROR, reason));
	        connected.setValue(false);
        	System.out.println("<-client->: " + ex.getMessage());
        } catch (IOException e) {
			log.setValue(TLanguage.getInstance().getFormatedString(CHATKey.MSG_ERROR, e.getMessage()));
			connected.setValue(false);
        	e.printStackTrace();
		} catch (Exception e) {
			log.setValue(TLanguage.getInstance().getFormatedString(CHATKey.MSG_ERROR, e.getMessage()));
			connected.setValue(false);
        	e.printStackTrace();
		}
	}
	
	/**
	 * Send an object as message
	 * */
	public void send(Object obj) throws IOException {
		dout.writeObject(obj);
	}

	/**
	 * Close the connection with the server
	 * */
	public void close() {
		receive = false; 
		if (socket != null) {
            try {
                socket.close();
                connected.setValue(false);
            } catch (IOException ex) {
            	ex.printStackTrace();;
            }
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
	 * True if connected to the server
	 * */
	public boolean isConnected() {
		return connected.getValue();
	}
	
	/**
	 * Return the log message
	 * */
	public String getLog() {
		return log.getValue();
	}

	/**
	 * Return the log message
	 * */
	public ReadOnlyStringProperty logProperty() {
		return log;
	}
	
	/**
	 * The connected property
	 * */
	public ReadOnlyBooleanProperty connectedProperty() {
		return connected;
	}
	
	/**
	 * The received message property
	 * */
	public ReadOnlyObjectProperty<Object> messageProperty() {
		return message;
	}

	public static ChatClient getInstance() {
		if(instance==null)
			instance = new ChatClient();
		return instance;
	}

}
