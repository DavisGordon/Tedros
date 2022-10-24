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
import java.util.List;
import java.util.UUID;

import javax.naming.NamingException;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.chat.ejb.controller.IChatUserController;
import org.tedros.chat.entity.ChatMessage;
import org.tedros.chat.entity.ChatUser;
import org.tedros.chat.entity.TStatus;
import org.tedros.chat.module.client.model.ChatUserMV;
import org.tedros.chat.module.client.model.TChatMV;
import org.tedros.common.model.TFileEntity;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.control.PopOver;
import org.tedros.core.control.PopOver.ArrowLocation;
import org.tedros.core.repository.TRepository;
import org.tedros.core.security.model.TUser;
import org.tedros.core.service.remote.ServiceLocator;
import org.tedros.fx.control.TButton;
import org.tedros.fx.control.TLabel;
import org.tedros.fx.form.TSetting;
import org.tedros.fx.util.TFileBaseUtil;
import org.tedros.server.entity.ITFileEntity;
import org.tedros.server.model.ITFileModel;
import org.tedros.server.model.TFileModel;
import org.tedros.server.result.TResult;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.WeakListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;


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
    private TRepository repo;
    
    private boolean receive = true;
    private boolean scrollFlag = false;
    
    private ChatUser owner;

	private int titleLength = 80;
    
	/**
	 * @param descriptor
	 */
	public ChatSetting(ITComponentDescriptor descriptor) {
		super(descriptor);
		messages = new SimpleObjectProperty<>();
		util = new ChatUtil();
		repo = new TRepository();
		TUser u = TedrosContext.getLoggedUser();
		try {
			owner = util.findUser(u.getAccessToken(), u.getId(), null);
			owner.setToken(u.getAccessToken());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.form.TSetting#run()
	 */
	@Override
	public void run() {
		
		TChatMV mv = (TChatMV) super.getModelView();
		// Listen new messages to show
		final ObservableList<ChatMessage> msgs = mv.getMessages();
		Collections.sort(msgs);
		ListChangeListener<ChatMessage> chl0 = ch0 ->{
			if(ch0.next() && ch0.wasAdded()) {
				ch0.getAddedSubList().forEach(m->{
					boolean left = !m.getFrom().getUserId().equals(TedrosContext.getLoggedUser().getId());
					showMsg(m, left);
				});
			}
		};
		repo.add("chl0", chl0);
		msgs.addListener(new WeakListChangeListener<ChatMessage>(chl0));
		
		// Listen for received message
		ChangeListener<ChatMessage> chl1 = (a,o,n) -> {
			if(n!=null && !n.getFrom().getUserId().equals(TedrosContext.getLoggedUser().getId())) {
				msgs.add(n);
				messages.setValue(null);
			}
		};
		repo.add("chl1", chl1);
		messages.addListener(new WeakChangeListener<>(chl1));
		
		// Send event
		EventHandler<ActionEvent> ev0 = e -> {

			ObservableList<ChatUserMV> dest = mv.getParticipants();
			if(dest.size()==0) {
				Node fbx = (Node) super.getFieldBox("participants");
				PopOver ppo = new PopOver();
	        	//ppo.setHeaderAlwaysVisible(true);
	        	ppo.setAutoFix(true);
	        	ppo.setArrowLocation(ArrowLocation.RIGHT_CENTER);
	        	ppo.setContentNode(new TLabel("Select the users"));
	        	ppo.show(fbx);
	        	return;
			}
			
			String msg = mv.getMessage().getValue();
			ITFileModel fm = mv.getSendFile().getValue();
			 try {
		            ChatMessage m = new ChatMessage();
					m.setContent(msg);
					m.setInsertDate(new Date());
					m.setFrom(owner);
					m.setStatus(TStatus.SENT);
					
					if(fm!=null && fm.getFile()!=null) {
						ITFileEntity fe = TFileBaseUtil.convert((TFileModel) fm);
						m.setFile((TFileEntity) fe);
					}
					
					for(ChatUserMV c : dest){
						m.setTo(c.getEntity());
						dout.writeObject(m);
					}
					
					showMsg(m, false);
					
					mv.getMessage().setValue(null);
					mv.getSendFile().setValue(null);
					
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
		};
		repo.add("ev0", ev0);
		TButton sendBtn = (TButton) super.getDescriptor().getFieldDescriptor("sendBtn").getComponent();
		sendBtn.setOnAction(new WeakEventHandler<>(ev0));
		
		// Clear event
		EventHandler<ActionEvent> ev1 = e->{
			mv.getMessage().setValue(null);
		};
		repo.add("ev1", ev1);
		TButton clearBtn = (TButton) super.getDescriptor().getFieldDescriptor("clearBtn").getComponent();
		clearBtn.setOnAction(new WeakEventHandler<>(ev1));
		
		
		TabPane tp = super.getLayout("owner");
		// Tab title
		Tab t = tp.getTabs().get(0);
		t.textProperty().bind(mv.getTitle());
		
		ListChangeListener<ChatUserMV> chl2 = ch -> {
			List<? extends ChatUserMV> l = ch.getList();
			buildTitle(l);
		};
		repo.add("chl2", chl2);
		ObservableList<ChatUserMV> users = mv.getParticipants();
		users.addListener(new WeakListChangeListener<ChatUserMV>(chl2));
		buildTitle(users);
		
		// auto scroll
		ScrollPane sp = (ScrollPane) t.getContent();
		sp.setVvalue(1.0);
		sp.vvalueProperty().addListener((a,o,n)->{
			if(scrollFlag) {
				sp.setVvalue(sp.getVmax());
				if(n.doubleValue()==sp.getVmax())
					scrollFlag = false;
			}
		});
		
		connect();
		
		if(mv.getModel().isNew()) {
			mv.getModel().setCode(UUID.randomUUID().toString());
			mv.getOwner().setValue(owner);
			util.saveChat(mv);
		}
		
		try {
			dout.writeObject(mv.getModel());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}

	/**
	 * @param ch
	 */
	private void buildTitle(List<? extends ChatUserMV> ch) {
		StringBuilder sb = new StringBuilder(owner.getName());
		ch.forEach(u->{
			if(sb.toString().equals(owner.getName())) {
				sb.append(" >> " + u.getName().getValue());
			} else 
				sb.append(", " + u.getName().getValue());
		});
		TChatMV mv = (TChatMV) super.getModelView();
		String tt = sb.toString().length()>titleLength 
				? sb.toString().substring(0, titleLength-1) +"..." 
						: sb.toString();
		mv.getTitle().setValue(tt);
	}

	/**
	 * @param m
	 */
	private void showMsg(ChatMessage m, boolean left){
		
		scrollFlag = true;
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
