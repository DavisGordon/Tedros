/**
 * 
 */
package org.tedros.chat.module.client.setting;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.Date;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.api.form.ITForm;
import org.tedros.chat.entity.ChatMessage;
import org.tedros.chat.entity.ChatUser;
import org.tedros.chat.entity.TStatus;
import org.tedros.chat.module.client.model.TChatMV;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.security.model.TUser;
import org.tedros.fx.control.TButton;
import org.tedros.fx.form.TSetting;

import javafx.collections.ListChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.ObservableList;


/**
 * @author Davis Gordon
 *
 */
public class ChatSetting extends TSetting {

	private ChatUtil util;
	
	//Socket usado para a ligação
    private Socket socket;
    //Streams de leitura e escrita. A de leitura é usada para receber os dados do
    //servidor, enviados pelos outros clientes. A de escrita para enviar os dados
    //para o servidor.
    private ObjectInputStream din;
    private ObjectOutputStream dout;
    
    private SimpleObjectProperty<ChatMessage> messages;
    
    private boolean receive = true;
    
	/**
	 * @param descriptor
	 */
	public ChatSetting(ITComponentDescriptor descriptor) {
		super(descriptor);
		messages = new SimpleObjectProperty<>();
		util = new ChatUtil();
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.form.TSetting#run()
	 */
	@Override
	public void run() {
		
		TChatMV mv = (TChatMV) super.getModelView();
	
		final ObservableList<ChatMessage> msgs = mv.getMessages();
		Collections.sort(msgs);
		
		msgs.addListener((Change<? extends ChatMessage> c)->{
			if(c.next() && c.wasAdded()) {
				c.getAddedSubList().forEach(m->{
					boolean left = !m.getFrom().getUserId().equals(TedrosContext.getLoggedUser().getId());
					showMsg(m, left);
				});
			}
		});
		
		ChangeListener<ChatMessage> chl1 = (a,o,n) -> {
			if(n!=null && !n.getFrom().getUserId().equals(TedrosContext.getLoggedUser().getId())) {
				msgs.add(n);
				messages.setValue(null);
			}
		};
		messages.addListener(chl1);
		

		EventHandler<ActionEvent> ev = e -> {
			String msg = mv.getMessage().getValue();
			
			TUser u = TedrosContext.getLoggedUser();
			ChatUser from = new ChatUser(); 
			from.setName(u.getName());
			from.setProfiles(u.getProfilesText());
			from.setUserId(u.getId());
			 try {
		            //enviar a mensagem para o servidor.
		            //anexamos o nickname deste utilizador apenas para identificação
		            //dout.writeUTF(u.getName()+"|$&|" + msg);
		            ChatMessage m = new ChatMessage();
					m.setContent(msg);
					m.setInsertDate(new Date());
					m.setFrom(from);
					ChatUser to = new ChatUser();
					to.setId(99L);
					to.setUserId(99L);
					to.setName("Fulano de tal");
					m.setTo(to);
					m.setStatus(TStatus.SENT);
					
					dout.writeObject(m);
					showMsg(m, false);
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
		};
		TButton sendBtn = (TButton) super.getDescriptor().getFieldDescriptor("sendBtn").getComponent();
		sendBtn.setOnAction(ev);
		
		connect();
	}

	/**
	 * @param m
	 */
	private void showMsg(ChatMessage m, boolean left){
		StackPane p1 = util.buildTextPane(m, left);
		GridPane.setVgrow(p1, Priority.ALWAYS);
		GridPane gp = super.getLayout("messages");
		int row = gp.getChildren().size();
		gp.add(p1, left ? 0 : 1, row);
	}
	
	private void connect() {
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
                //estamos a usar uma classe anónima...

                public void run() {
                    try {
                        while (receive) {
                        	ChatMessage msg = (ChatMessage) din.readObject();
							Platform.runLater(()->{
                        		messages.setValue(msg);
                        	});
                            
                        	
                        }
                    } catch (Exception ex) {
                    	System.out.println("<-client->: " + ex.getMessage());
                    }
                }
            }).start();
          
         // MessageBuilder.writeAuthentication(TedrosContext.getLoggedUser(), dout);
			
        } catch (Exception ex) {
        	System.out.println("<-client->: " + ex.getMessage());
        }
	}

	@Override
	public void dispose() {
		receive = false; 
		if (socket != null) {
            try {
                socket.close();
            } catch (IOException ex) {
            	ex.getMessage();
            }
        }
	}

}
