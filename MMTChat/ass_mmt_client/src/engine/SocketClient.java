package engine;

import com.socket.*;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import common.Tags;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class SocketClient implements Runnable {

	public int port;
	public int portpeer;
	public String serverAddr;
	public Socket socket;
	public gui_client ui;
	public Socket socket_peer;
	public DataInputStream In;
	public DataOutputStream Out;
	public gui_client_chat_sendfile ui_chat;
	public int countDown =15;
	public boolean test_connect=false;

	public SocketClient(gui_client frame) throws IOException {
		ui = frame;
		this.serverAddr = ui.serverAddr;
		this.port = ui.port;
		socket = new Socket(InetAddress.getByName(serverAddr), port);

		Out = new DataOutputStream(socket.getOutputStream());
		Out.flush();
		In = new DataInputStream(socket.getInputStream());

	}

	public Message handle_protocol(String msg) {
		String type = "";
		String sender = "";
		String content = "";
		String Receipt = "";
		
		
		
		SAXBuilder mSAXBuilder = new SAXBuilder();
		Document mDocument;
		try {
			mDocument = mSAXBuilder.build(new StringReader(msg));
		

		Element rootNode = mDocument.getRootElement();
		String protType = rootNode.getName();
		
		
		String a = protType;
		System.out.println(a);
		if (a.equals("CHAT_ACCEPT")) {
			type = "connect_peer";
			content = "accept";

		} else if (a.equals("FILE_DATA_BEGIN")) {
			type = "Loading";
		} else if (a.equals("CHAT_DENY")) {
			type = "connect_peer";
			content = "reject";

		} else if (a.equals("CHAT_REQ")) {
			type = "connect_peer";
			sender = rootNode.getChildText("PEER_NAME");

		} else if (a.equals("SESSION")) {
			type = "test";
			sender = rootNode.getChildText("PEER_NAME");
			content =rootNode.getChildText("PORT");
		} else if (a.equals("FILE_DATA_END")) {
			type = "Loading";
		} else if (a.equals("SESSION_ACCEPT")) {
			type = "login";
			List<Element> listOfPeers = rootNode.getChildren(Tags.PEER);
			
			String name1 = listOfPeers.get(0).getChildText(Tags.PEER_NAME);
			String ip1 = listOfPeers.get(0).getChildText(Tags.IP);
			String port1 = listOfPeers.get(0).getChildText(Tags.PORT);
			content=name1;
			for (int i = 1; i < listOfPeers.size(); i++) {

				Element node = listOfPeers.get(i);

				String name = node.getChildText(Tags.PEER_NAME);
				
				int port = Integer.parseInt(node.getChildText(Tags.PORT));

				content=content+","+name;
			}
			content=content+";"+ip1;
			for (int i = 1; i < listOfPeers.size(); i++) {

				Element node = listOfPeers.get(i);

				String ip = node.getChildText(Tags.IP);

				content=content+","+ip;
			}
			content=content+";"+port1;
			for (int i = 1; i < listOfPeers.size(); i++) {

				Element node = listOfPeers.get(i);

				String port = node.getChildText(Tags.PORT);

				content=content+","+port;
			}
			sender="TRUE";

		} else if (a.equals("SESSION_DENY")) {
			type = "login";
		
			sender = "FALSE";

		} else if (a.equals("FILE_REQ_NOACK")) {
			type = "upload_res";
		} else if (a.equals("FILE_REQ_ACK")) {
			type = "upload_res";
			content = rootNode.getChildText("PORT");

		} else if (a.equals("CHAT_MSG")) {
				type = "message";
				content=rootNode.getText();

			} else if (a.equals("FILE_REQ")) {
				type = "upload";
				content = rootNode.getText();
			}
		 else if (a.equals("FILE_DATA")) {
				type = "Loading";
			}
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("type" + type + "...sender" + sender + "....content"
				+ content + "..." + Receipt);
		Message msg1 = new Message(type, sender, content, Receipt);

		return msg1;
	}


	public void run() {
		boolean keepRunning = true;
		Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                        // TODO Auto-generated method stub
                        countDown--;
                        if(countDown == 0){
                                countDown = 15;
                             
                              	  Message msg2=new Message("alive",ui.username,"","SERVER");
                              	  ui.client.send(msg2);
                               
                                // TODO: read for result a list to update for main form
                              
                               
                               
                        }
                }
        }, 0, 1000);
		
		while (keepRunning) {
			try {
				String Str=In.readUTF();
				System.out.println("Incoming : " + "\n" +Str);
				Message msg = handle_protocol(Str) ;
				

			

				 if (msg.type.equals("login")&&(test_connect==false)) {
					if (msg.sender.equals("TRUE")) {
						test_connect=true;
						ui.listuser = msg.content;
						ui.handel_list_onl(ui.listuser);
					
						// tao socket de chat peer voi cac client khac
						ui.server_peer = new SocketSeverchat(ui, ui.peerport);
						ui.tf_show.append("[SERVER > Me] : Login Successful\n");
						ui.btn_login.setEnabled(false);
						ui.tf_username.setEnabled(false);
					} else {
						ui.tf_show.append("[SERVER > Me] : Login Failed\n");
					}
				}
				 else if (msg.type.equals("login")&&(test_connect==true)) 
				{
					ui.listuser = msg.content;
					ui.handel_list_onl(msg.content);
				}
				 else if (msg.type.equals("test")) {
					ui.tf_show.append("[SERVER > Me] : connect Successful\n");
				}
				 else if (msg.type.equals("signup")) {
					if (msg.content.equals("TRUE")) {

						ui.tf_show
								.append("[SERVER > Me] : Singup Successful\n");
					} else {
						ui.tf_show.append("[SERVER > Me] : Signup Failed\n");
					}
				} else if (msg.type.equals("signout")) {
					if (msg.content.equals(ui.username)) {
						ui.tf_show.append("[" + msg.sender + " > Me] : Bye\n");

						for (int i = 1; i < ui.model.size(); i++) {
							ui.model.removeElementAt(i);
						}

						ui.clientThread.stop();
					} else {
						ui.model.removeElement(msg.content);
						ui.tf_show.append("[" + msg.sender + " > All] : "
								+ msg.content + " has signed out\n");
					}
				}

				else {
					ui.tf_show.append("[SERVER > Me] : Unknown message type\n");
				}
			} catch (Exception ex) {
				
				ui.tf_show.append("[Application > Me] : Connection Failure\n");

				for (int i = 1; i < ui.model.size(); i++) {
					ui.model.removeElementAt(i);
				}

				ui.clientThread.stop();

				System.out.println("Exception SocketClient run()");
				ex.printStackTrace();
			}
			
		}
      }
		  
	

	public void send(Message msg) {
		try {
			Out.writeUTF(msg.toString());
			Out.flush();
			System.out.println("Outgoing : " + "\n" + msg.toString());

		} catch (IOException ex) {
			System.out.println("Exception SocketClient send()");
		}
	}

	
}
