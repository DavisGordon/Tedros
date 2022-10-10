/**
 * 
 */
package org.tedros.chat.module.client.behaviour;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.tedros.chat.ejb.controller.IFindChatUserController;
import org.tedros.chat.entity.ChatMessage;
import org.tedros.chat.entity.ChatUser;
import org.tedros.chat.entity.TStatus;
import org.tedros.chat.module.client.decorator.TChatDecorator;
import org.tedros.chat.module.client.model.TChatMV;
import org.tedros.chat.module.client.model.TChatModel;
import org.tedros.common.model.TFileEntity;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.message.TMessage;
import org.tedros.core.message.TMessageType;
import org.tedros.core.security.model.TUser;
import org.tedros.fx.TFxKey;
import org.tedros.fx.control.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.domain.TImageExtension;
import org.tedros.fx.exception.TException;
import org.tedros.fx.presenter.dynamic.behavior.TDynaViewSimpleBaseBehavior;
import org.tedros.fx.presenter.paginator.TPagination;
import org.tedros.fx.process.TPaginationProcess;
import org.tedros.fx.property.TBytesLoader;
import org.tedros.server.entity.ITFileEntity;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Davis Gordon
 *
 */
public class TChatBehaviour extends TDynaViewSimpleBaseBehavior<TChatMV, TChatModel> {

	private TChatDecorator deco;
	
	 //Socket usado para a ligação
    private Socket socket;
    //Streams de leitura e escrita. A de leitura é usada para receber os dados do
    //servidor, enviados pelos outros clientes. A de escrita para enviar os dados
    //para o servidor.
    private ObjectInputStream din;
    private ObjectOutputStream dout;
    
    private SimpleObjectProperty<ChatMessage> messages;
    
    private boolean receive = true;
    private int row = 0;
	
	@Override
	public void load() {
		super.load();
		this.deco = (TChatDecorator) super.getPresenter().getDecorator();
		
		ChangeListener<TPagination> chl = (a0, a1, a2) -> {
			try {
				paginate(a2);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		};
		this.deco.getPaginator().paginationProperty().addListener(chl);
	
		messages = new SimpleObjectProperty<>();
		ChangeListener<ChatMessage> chl1 = (a,o,n) -> {
			if(n!=null) {
				if(!n.getFrom().getId().equals(TedrosContext.getLoggedUser().getId())) {
					StackPane p1 = buildTextPane(n, true);
	        		GridPane.setVgrow(p1, Priority.ALWAYS);
	        		deco.getMsgPane().add(p1, 0, row);
	        		row++;
				}
	        	messages.setValue(null);
			}
		};
		super.getListenerRepository().add("receiveChl", chl1);
		messages.addListener(new WeakChangeListener<>(chl1));
		
		EventHandler<ActionEvent> ev = e -> {
			String msg = deco.getMsgArea().getText();
			
			TUser u = TedrosContext.getLoggedUser();
			 try {
		            //enviar a mensagem para o servidor.
		            //anexamos o nickname deste utilizador apenas para identificação
		            //dout.writeUTF(u.getName()+"|$&|" + msg);
		            ChatMessage m = new ChatMessage();
					m.setContent(msg);
					m.setInsertDate(new Date());
					m.setFrom(u);
					TUser to = new TUser();
					to.setId(99L);
					to.setName("Fulano de tal");
					m.setTo(to);
					m.setStatus(TStatus.SENT);
					
					dout.writeObject(m);
					//MessageBuilder.writeMessage(m, Step.SEND_MSG, dout);
					
					StackPane p1 = buildTextPane(m, false);
					GridPane.setVgrow(p1, Priority.ALWAYS);
					deco.getMsgPane().add(p1, 1, row);
					row++;
		            deco.getMsgArea().setText("");
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
		};
		super.getListenerRepository().add("sendEv", ev);
		this.deco.getSendBtn().setOnAction(new WeakEventHandler<>(ev));
		
		connect();
		
		/*
		String txt = "abc abc nhu hgtf gffuu yghh fdryu koojhg uvh u u tfg hnij bo abc nhu hgtf gffuu yghh fdryu koojhg uvh u u tfg hnij b";
		
		GridPane gp = this.deco.getMsgPane();
		int i = 0;
		for(int x=0; x<=2; x++) {
			ChatMessage m = new ChatMessage();
			m.setContent(txt);
			m.setInsertDate(new Date());
			TUser u;
			if(i==0) {
				u = new TUser();
				u.setName("Fulano de tal");
			}else {
				u = TedrosContext.getLoggedUser();
			}
		m.setFrom(u);
		StackPane p1 = buildTextPane(m, x%2==0);
		GridPane.setVgrow(p1, Priority.ALWAYS);
		gp.add(p1, i, x);
		
		i = i==0 ? 1 : 0;
		//this.deco.getMsgsVB().getChildren().add(p1);
		}
		
		try {
			TFileModel fm = new TFileModel(new File("C:\\Users\\Davis Gordon\\Documents\\Covid Sem Fome\\picpay.png"));
			ITFileEntity fe = TFileBaseUtil.convert(fm);
			ChatMessage m = new ChatMessage();
			m.setFile((TFileEntity) fe);
			m.setInsertDate(new Date());
			TUser u;
			u = TedrosContext.getLoggedUser();
			
		m.setFrom(u);
		StackPane p1 = buildTextPane(m, false);
		GridPane.setVgrow(p1, Priority.ALWAYS);
		gp.add(p1, 1, 4);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	
	private void connect() {
		try {
            String nick = TedrosContext.getLoggedUser().getName();
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
                            //sequencialmente, ler as mensagens uma a uma e acrescentar ao
                            //texto que já recebemos
                            //para o utilizador ver
                        	//String msg = din.readUTF();
                        	/*Document doc = MessageBuilder.parse(din);
                        	Step step = MessageBuilder.getStep(doc);
                        	switch(step) {
							case AUTHENTICATION_RESULT:
								break;
							case RECEIVE_MSG:
								ChatMessage msg = MessageBuilder.getMessage(doc);
								Platform.runLater(()->{
	                        		messages.setValue(msg);
	                        	});
								break;
							default:
								break;
                        	
                        	}*/
                        	
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

	/**
	 * @param txt
	 * @return
	 */
	private StackPane buildTextPane(ChatMessage msg, boolean left) {
		String user = msg.getFrom().getName();
		String txt = msg.getContent();
		final TFileEntity file = msg.getFile();
		Date dt =  msg.getInsertDate();
		String dtf = dt!=null 
				? DateFormat
				.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.DEFAULT, TLanguage.getLocale())
				.format(dt)
				: "";
		
		VBox gp = new VBox(8);
		HBox header = new HBox(4);
		header.setId("t-chat-msg-header");
		gp.getChildren().add(header);
		if(user!=null) {
			TText t2 = new TText(user);
			t2.settTextStyle(TTextStyle.MEDIUM);
			header.getChildren().add(t2);
		}
		HBox footer = new HBox(10);
		footer.setAlignment(Pos.CENTER_LEFT);
		footer.setId("t-chat-msg-footer");
		if(!"".equals(dtf)) {
			TText t2 = new TText(dtf);
			t2.settTextStyle(TTextStyle.SMALL);
			footer.getChildren().add(t2);
		}
		if(txt!=null) {
			TText t1 = new TText(txt);
			t1.settTextStyle(TTextStyle.MEDIUM);
			t1.setWrappingWidth(300);
			VBox.setMargin(t1, new Insets(8));
			gp.getChildren().add(t1);
			Hyperlink hl = new Hyperlink(super.iEngine.getString(TFxKey.BUTTON_COPY));
			hl.getStyleClass().add(TTextStyle.SMALL.getValue());
			hl.setUserData(txt);
			EventHandler<ActionEvent> ev = e -> {
				String text = (String) ((Node) e.getSource()).getUserData();
				final Clipboard clipboard = Clipboard.getSystemClipboard();
			    final ClipboardContent content = new ClipboardContent();
			    content.putString(text);
			    clipboard.setContent(content);
			};
			super.getListenerRepository().add(UUID.randomUUID().toString(), ev);
			hl.setOnAction(new WeakEventHandler<>(ev));
			footer.getChildren().add(hl);
			
		}else if(file!=null){
			TText t1 = new TText(file.toString());
			t1.settTextStyle(TTextStyle.MEDIUM);
			t1.setWrappingWidth(300);
			VBox.setMargin(t1, new Insets(8));
			gp.getChildren().add(t1);
			
			Hyperlink hl = new Hyperlink(super.iEngine.getString(TFxKey.BUTTON_OPEN));
			hl.getStyleClass().add(TTextStyle.SMALL.getValue());
			EventHandler<ActionEvent> ev = e -> {
				//TFileUtil..open(f)
			};
			super.getListenerRepository().add(UUID.randomUUID().toString(), ev);
			hl.setOnAction(new WeakEventHandler<>(ev));
			footer.getChildren().add(hl);
			
			String ext = file.getFileExtension();
			String[] exts = TImageExtension.ALL_IMAGES.getExtensionName();
			if(ArrayUtils.contains(exts, ext.toLowerCase())) {
				ImageView iv = new ImageView();
				iv.setFitWidth(300);
				iv.setPreserveRatio(true);
				if(!file.isNew()) {
					this.loadImage(iv, file);
				}else {
					this.setImage(iv, file.getByteEntity().getBytes());
				}

				VBox.setMargin(iv, new Insets(8));
				gp.getChildren().add(iv);
			}
		}
		gp.getChildren().add(footer);
		StackPane p1 = new StackPane();
		p1.setId("t-chat-msg-pane");
		p1.getChildren().add(gp);
		p1.setAlignment(left ? Pos.CENTER_LEFT : Pos.CENTER_RIGHT);
		return p1;
	}
	
	/**
	 * @param imgView
	 * @param fe
	 */
	private void loadImage(final ImageView imgView, ITFileEntity fe) {
		SimpleObjectProperty<byte[]> bp = new SimpleObjectProperty<>();
		bp.addListener(new ChangeListener<byte[]>() {
			@Override
			public void changed(ObservableValue<? extends byte[]> arg0, byte[] arg1,
					byte[] n) {
				try {
					fe.getByteEntity().setBytes(n);
					setImage(imgView, n);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				bp.removeListener(this);
			}
		});
		loadBytes(bp, fe);
	}
	
	private void loadBytes(SimpleObjectProperty<byte[]> bp, ITFileEntity m) {
		try {
			TBytesLoader.loadBytesFromTFileEntity(m.getByteEntity().getId(), bp);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param imgView
	 * @param bytes
	 * @throws IOException
	 */
	private void setImage(final ImageView imgView, byte[] bytes) {
		try(InputStream is = new ByteArrayInputStream(bytes)){
			Image img = new Image(is);
			imgView.setImage(img);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void processPagination(Long totalRows) {
		this.deco.getPaginator().reload(totalRows);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void paginate(TPagination pagination) throws TException {
		
		final String chlId = UUID.randomUUID().toString();
		TPaginationProcess process = new TPaginationProcess(TUser.class, IFindChatUserController.JNDI_NAME) {};
		ChangeListener<State> prcl = (arg0, arg1, arg2) -> {
			
			if(arg2.equals(State.SUCCEEDED)){
				
				TResult<Map<String, Object>> resultados = (TResult<Map<String, Object>>) process.getValue();
				
				if(resultados != null) {
					if(resultados.getState().equals(TState.SUCCESS)) {
						Map<String, Object> result =  resultados.getValue();
						ObservableList<ChatUser> models = deco.getUsrLV().getItems();
						models.clear();
						
						for(ChatUser e : (List<ChatUser>) result.get("list")){
							models.add(e);
						}
						processPagination((long)result.get("total"));
					}else {
						String msg = resultados.getMessage();
						System.out.println(msg);
						switch(resultados.getState()) {
							case ERROR:
								addMessage(new TMessage(TMessageType.ERROR, msg));
								break;
							default:
								addMessage(new TMessage(TMessageType.WARNING, msg));
								break;
						}
					}
					getListenerRepository().remove(chlId);
				}
			}
		};
		
		super.getListenerRepository().add(chlId, prcl);
		
		try {
			Class target = TUser.class;
			TUser entity = new TUser();;
			Method setter = null;
			do {
				try {
					Field f = target.getDeclaredField(pagination.getSearchFieldName());
					setter = target.getMethod("set"+StringUtils.capitalize(pagination.getSearchFieldName()), f.getType());
					break;
				} catch (NoSuchFieldException | SecurityException e) {
					target = target.getSuperclass();
				} catch (NoSuchMethodException e) {
					break;
				}
			}while(target != Object.class);
			
			if(setter==null)
				throw new TException("The setter method was not found for the field "+pagination.getSearchFieldName()+" declared in TPaginator annotation.");
			
			setter.invoke(entity, pagination.getSearch().toUpperCase());
			process.findAll(entity, pagination);
			
		} catch (Exception e) {
			throw new TException("An error occurred while trying paginate ", e);
		}
			
		this.deco.gettListViewProgressIndicator().bind(process.runningProperty());
		process.stateProperty().addListener(new WeakChangeListener(prcl));
		process.startProcess();
	}
	
	
	@Override
	public String canInvalidate() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean invalidate() {
		receive = false; 
		if (socket != null) {
            try {
                socket.close();
            } catch (IOException ex) {
            	ex.getMessage();
            }
        }
		return super.invalidate();
	}

}
