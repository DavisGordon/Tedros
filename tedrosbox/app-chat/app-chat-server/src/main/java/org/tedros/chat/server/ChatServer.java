package org.tedros.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;
import org.tedros.chat.domain.ChatPropertie;
import org.tedros.chat.entity.ChatMessage;
import org.tedros.chat.model.ChatInfo;
import org.tedros.core.controller.ITLoginController;
import org.tedros.core.controller.TPropertieController;
import org.tedros.core.security.model.TProfile;
import org.tedros.core.security.model.TUser;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.service.TServiceLocator;
import org.tedros.util.TEncriptUtil;

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
    
    public void replyMessage(ChatInfo msg) {
        synchronized (clients) {
            clients.parallelStream()
            .filter(h->{
            	return msg.getRecipients().stream().filter(p->{
            		return h.getOwner().equals(p) && !msg.getUser().equals(p);
            	}).findFirst().isPresent();
            }).forEach(c->{
            	c.send(msg);
            });
        }
    }

    public void replyMessage(ChatMessage msg) {
        synchronized (clients) {
            clients.parallelStream()
            .filter(h->{
            	return msg.getSent().stream().filter(p->{
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

    private static int  port = 0;

    public static void main(String args[]) {
        try {
            if (args.length == 0) {
            	lookupPort();
            	if(port>0)
            		new ChatServer(port);
            } else {
                new ChatServer(Integer.parseInt(args[0]));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private static void lookupPort() {
    	
    	try {
			try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
				String ip, url, user, pass;
				System.out.println("Tedros Chat Server");
				System.out.println("-----------------+");
				System.out.println("Application Server IP (Enter for localhost): ");
				ip = br.readLine();
				if(StringUtils.isBlank(ip)) 
					ip = null;
				System.out.println("The remote ejb url (Enter for http://{0}:8080/tomee/ejb): ");
				url = br.readLine();
				if(StringUtils.isBlank(url)) 
					url = null;
				
				System.out.println("Tedros user: ");
				user = br.readLine();
				
				if(StringUtils.isBlank(user)) {
					System.out.println("A non empty value is required! ");
					return;
				}
				System.out.println("Tedros user password: ");
				pass = br.readLine();
				
				if(StringUtils.isBlank(pass)) {
					System.out.println("A non empty value is required! ");
					return;
				}

				System.out.println("Preparing the Service locator: ");
				TServiceLocator serv = TServiceLocator.getInstance(url, ip);
				System.out.println("Lookup login service: ");
				TResult<TUser> res;
				try {
					ITLoginController lServ = serv.lookup(ITLoginController.JNDI_NAME);
					System.out.println("Logon at server... ");
					res = lServ.login(user, TEncriptUtil.encript(pass));
					if(res.getState().equals(TState.SUCCESS)) {
						System.out.println("Lookup for properties service: ");
						TUser u = res.getValue();
						if(u.getActiveProfile()==null && u.getProfiles()!=null && !u.getProfiles().isEmpty()) {
							Object[] arr = u.getProfiles().toArray();
							TProfile p = (TProfile) arr[0];
							res = lServ.saveActiveProfile(u.getAccessToken(), p, u.getId());
							u = res.getValue();
						}
						TPropertieController pServ = serv.lookup(TPropertieController.JNDI_NAME);
						System.out.println("Search for the propertie: "+ChatPropertie.CHAT_SERVER_PORT.name());
						TResult<String> res1 = pServ.getValue(u.getAccessToken(), ChatPropertie.CHAT_SERVER_PORT.getValue());
						if(res1.getState().equals(TState.SUCCESS) && StringUtils.isNotBlank(res1.getValue())) {
							port = Integer.parseInt(res1.getValue());
							System.out.println("Propertie "+ChatPropertie.CHAT_SERVER_PORT.name() +" = "+ res1.getValue());
						}else {
							System.out.println("The propertie "+ChatPropertie.CHAT_SERVER_PORT.name()+" not configured!");
						}
					}else {
						System.out.println("Logon failed!");
					}
				} catch (NamingException e) {
					e.printStackTrace();
				}finally{
					serv.close();
				}
				
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
}
