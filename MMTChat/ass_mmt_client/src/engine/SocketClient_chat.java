package engine;

import com.socket.*;

import common.Tags;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class SocketClient_chat implements Runnable {

	public int portpeer;
	public String serverAddr;
	public Socket socket_peer = null;
	public DataInputStream In = null;
	public DataOutputStream Out = null;
	public gui_client_chat_sendfile ui;
	public gui_client ui_server;

	public SocketClient_chat(gui_client_chat_sendfile frame, gui_client frame1,
			String peer, int port) throws IOException {
		ui = frame;
		this.serverAddr = peer;
		this.portpeer = port;
		ui_server = frame1;
		socket_peer = new Socket(serverAddr, portpeer);

		Out = new DataOutputStream(socket_peer.getOutputStream());
		Out.flush();
		In = new DataInputStream(socket_peer.getInputStream());

	}

	
	public void run() {
		boolean keepRunning = true;
		while (keepRunning) {
			try {

				String Str = null;
				
				Str = In.readUTF();
				
				System.out.println(Str);
				Message msg;
				try {
					msg = handle_protocol(Str);
					if(msg.content.equals(".bye"))
					{
						
						ui.client.closeThread(ui.clientThread);
					
					}
					else if (msg.type.equals("message")) {
						
						
						ui.tf_show.append("["+
							  ui.lbl_name.getText()
								+ " > Me] : " + msg.content + "\n");
					}
					else if (msg.type.equals("upload")) {
						// TODO
						if (JOptionPane.showConfirmDialog(ui,
								("Do you accept transfer file: " + msg.content
										+ " from " + msg.sender + " ?")) == 0) {

							JFileChooser jf = new JFileChooser();
							jf.setSelectedFile(new File(msg.content));
							int returnVal = jf.showSaveDialog(ui);

							String saveTo = jf.getSelectedFile().getPath();
							if (saveTo != null
									&& returnVal == JFileChooser.APPROVE_OPTION) {
								
								Download dwn = new Download(saveTo, ui);
								Thread t = new Thread(dwn);
								t.start();
								ui.client.send(new Message("upload_res", socket_peer
												.getInetAddress().getHostAddress(),
												("" + dwn.port), jf.getSelectedFile()
														.getName()));
							} else {
								ui.client.send(new Message("upload_res",
										ui.username, "NO", msg.sender));
							}
						}
					}
					else if (msg.type.equals("download_complete")) {
					
						ui.tf_show.append(ui.lbl_name.getText() + ": Download complete\n");
					}
						else if (msg.type.equals("connect_peer")) {
						if (msg.content.equals("reject")) {
							ui_server.tf_show.append("error" + msg.sender
									+ " doesn't allow to connect");
							ui.windowclose();

						} else {
							
							
							ui_server.tf_show.append("user" + msg.sender
									+ " allow to connect");

						}
					}
				
					else if (msg.type.equals("upload_res")) { // TODO
						if (!msg.content.equals("NO")) {
							int port = Integer.parseInt(msg.content);
							String addr = ui.serverAddr;

							Upload upl = new Upload(addr, port, ui.file, ui);
							Thread t = new Thread(upl);
							t.start();
							
						} else {
							ui.tf_show.append("[SERVER > Me] : " + msg.sender
									+ " rejected file request\n");
						}
					}

				} catch (Exception ex) {
					System.out.println("loi ngay cho handle"  );
					ex.printStackTrace();
				}

			} catch (Exception ex) {
				keepRunning = false;
				ui.tf_show.append("[Application > Me] : Connection Failure\n");

				ui.clientThread.stop();

				System.out.println("Exception SocketClient run()");
				ex.printStackTrace();
			}
		}
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

		}
		else if(a.equals("CHAT_CLOSE")) 
		{
			content =".bye";
		}
		else if (a.equals("FILE_DATA_BEGIN")) {
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
			content=null;
			String name1 = listOfPeers.get(0).getChildText(Tags.PEER_NAME);
			String ip1 = listOfPeers.get(0).getChildText(Tags.IP);
			int port1 = Integer.parseInt(listOfPeers.get(0).getChildText(Tags.PORT));
			for (int i = 1; i < listOfPeers.size(); i++) {

				Element node = listOfPeers.get(i);

				String name = node.getChildText(Tags.PEER_NAME);
				
				

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

				int port = Integer.parseInt(node.getChildText(Tags.PORT));

				content=content+","+port;
			}
			

		} else if (a.equals("SESSION_DENY")) {
			type = "test";
			content = "FALSE";
			sender = "SERVER";

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
				type = "Downloading";
			}
		 else if (a.equals("FILE_DATA_END")) {
				type = "download_complete";
			}
		}
		
		
		catch (JDOMException e) {
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

	public void send(Message msg) {
		try {
			
			Out.writeUTF(msg.toString());

			Out.flush();
			System.out.println("Outgoing : " + "\n" + msg.toString());

		} catch (IOException ex) {
			System.out.println("Exception SocketClient send()");
		}
	}

	public void closeThread(Thread t) {
		t = null;
	}
}
