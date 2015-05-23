package com.socket;

import java.io.*;
import java.util.Timer;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;




class ServerThread extends Thread {

	public SocketServer server = null;
	public Socket socket = null;
	public int ID = -1;
	public boolean Status=false;
	public String username = "";
	public boolean Status_sever_check=false;
	public DataInputStream streamIn = null;
	public DataOutputStream streamOut = null;
	public ServerFrame ui;
	public int time_count;

	public ServerThread(SocketServer _server, Socket _socket) {
		super();
		server = _server;
		socket = _socket;
		ID = socket.getPort();
		ui = _server.ui;
		Status=true;
		Status_sever_check=true;
		time_count=60;
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
		else if(a.equals("SESSION_KEEP_ALIVE"))
		{
			type="check_ok";
			sender=rootNode.getChildText("PEER_NAME");
			content=rootNode.getChildText("STATUS");
		}
		else if (a.equals("FILE_DATA_BEGIN")) {
			type = "Loading";
		} else if (a.equals("CHAT_DENY")) {
			type = "connect_peer";
			content = "reject";

		} else if (a.equals("CHAT_REQ")) {
			type = "connect_peer";
			sender = rootNode.getChildText("PEER_NAME");

		} else if (a.equals("FILE_DATA_END")) {
			type = "Loading";
		} else if (a.equals("SESSION")) {
			type = "login";
			sender=rootNode.getChildText("PEER_NAME");
			content=rootNode.getChildText("PORT");
			

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


	
	public void send(Message msg) {
		try {
			System.out.println(msg.toString());
			streamOut.writeUTF(msg.toString());
			
			streamOut.flush();
		} catch (IOException ex) {
			System.out.println("Exception [SocketClient : send(...)]");
		}
	}

	public int getID() {
		return ID;
	}

	public boolean handle_timerClient(){
		if(Status==false)
		return false;
		else
		{
			Status=false;
		}
		return true;
		
	}
	@SuppressWarnings("deprecation")
	public void run() {
		final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                        time_count--;
                      
                        if (time_count == 0) {
                        	if(handle_timerClient()==false)
                        	{
                                timer.cancel();
                                Status_sever_check=false;
                        	}
                        	else
                        	{time_count=60;}
                        }
                }
        }, 0, 1000);
		ui.jTextArea1.append("\nServer Thread " + ID + " running.");
		while (true) {
			
			try {
				String Str=streamIn.readUTF();
				System.out.println("Incomeing"+Str);
				
				Message msg = handle_protocol(Str);
				try{
				server.handle(ID, msg);
				}
				catch(Exception ioe) {
					System.out.println("loi cho handle"+ioe);
				}
			} catch (Exception ioe) {
				System.out.println(ID + " ERROR reading: " + ioe.getMessage());
				server.remove(ID);
				stop();
			}
		}
	}

	public void open() throws IOException {
		streamOut = new DataOutputStream(socket.getOutputStream());
		streamOut.flush();
		streamIn = new DataInputStream(socket.getInputStream());
	}

	public void close() throws IOException {
		if (socket != null)
			socket.close();
		if (streamIn != null)
			streamIn.close();
		if (streamOut != null)
			streamOut.close();
	}
}

public class SocketServer implements Runnable {

	public ServerThread clients[];
	public ServerSocket server = null;
	public Thread thread = null;
	public int clientCount = 0, port = 13000;
	public ServerFrame ui;
	public Database db;
	
	public String userinfo_name = "";
	public String userinfo_ip = "";
	public String userinfo_port = "";

	public Timer timer = new Timer();

	public SocketServer(ServerFrame frame) {

		clients = new ServerThread[50];
		ui = frame;
		db = new Database(ui.filePath);

		try {
			server = new ServerSocket(port);
			port = server.getLocalPort();
			ui.jTextArea1.append("Server startet. IP : "
					+ InetAddress.getLocalHost() + ", Port : "
					+ server.getLocalPort());
			start();
		} catch (IOException ioe) {
			ui.jTextArea1.append("Can not bind to port : " + port
					+ "\nRetrying");
			ui.RetryStart(0);
		}
	}

	public SocketServer(ServerFrame frame, int Port) {

		clients = new ServerThread[50];
		ui = frame;
		port = Port;
		db = new Database(ui.filePath);

		try {
			server = new ServerSocket(port);
			port = server.getLocalPort();
			ui.jTextArea1.append("Server startet. IP : "
					+ InetAddress.getLocalHost() + ", Port : "
					+ server.getLocalPort());
			start();
		} catch (IOException ioe) {
			ui.jTextArea1.append("\nCan not bind to port " + port + ": "
					+ ioe.getMessage());
		}
	}
	
	public void run() {
		timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                   for(int i=0;i<clientCount;i++)
                   {
                	   if(clients[i].Status_sever_check==false)
                	   {
                		   remove(clients[i].ID);
                		   String str = userinfo_name + ";" + userinfo_ip + ";"
								+ userinfo_port;
							
							Announce("userinfo", "SERVER", str);
                	   }
                	   
                	   
                   }
            	}
			}, 0, 400);
		
		while (thread != null) {
			try {
				ui.jTextArea1.append("\nWaiting for a client ...");
				addThread(server.accept());
			} catch (Exception ioe) {
				ui.jTextArea1.append("\nServer accept error: \n");
				ui.RetryStart(0);
			}
		}
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	@SuppressWarnings("deprecation")
	public void stop() {
		if (thread != null) {
			thread.stop();
			thread = null;
		}
	}

	private int findClient(int ID) {
		for (int i = 0; i < clientCount; i++) {
			if (clients[i].getID() == ID) {
				return i;
			}
		}
		return -1;
	}
	
	public synchronized void handle(int ID, Message msg) {
		
			if (msg.type.equals("login")) {
				if (findUserThread(msg.sender) == null) {
					if(!db.userExists(msg.sender))
					{
						db.addUser(msg.sender);
					}
					if (db.checkLogin(msg.sender)) {
						clients[findClient(ID)].username = msg.sender;
						if (userinfo_ip == "")
							userinfo_ip =clients[findClient(ID)].socket.getInetAddress().getHostAddress();
						else
							userinfo_ip = userinfo_ip + ","
									+clients[findClient(ID)].socket.getInetAddress().getHostAddress();
						if (userinfo_port == "")
							userinfo_port =    msg.content ;
						else
							userinfo_port = userinfo_port + "," + msg.content;
						
						if(userinfo_name=="")
						{
						userinfo_name =clients[findClient(ID)].username;
						}
						
						else
						{	
							userinfo_name=userinfo_name+","+ msg.sender;
						
						}
						// Str gui thong diep cac user cho user vua dang nhap

						String str = userinfo_name + ";" + userinfo_ip + ";"
								+ userinfo_port;
						
						clients[findClient(ID)].send(new Message("userinfo","SERVER",str,"All"));
						ui.jTextArea1.append(str+"\n");
						Announce("userinfo", "SERVER", str);
					} else {
						clients[findClient(ID)].send(new Message("login",
								"SERVER", "FALSE", msg.sender));
					}
				} else {
					clients[findClient(ID)].send(new Message("login", "SERVER",
							"FALSE", msg.sender));
				}
			}
			else if(msg.type.equals("check_ok")&&(msg.content.equals("ALIVE")))
					{
				clients[findClient(ID)].Status=true;
				String str = userinfo_name + ";" + userinfo_ip + ";"
						+ userinfo_port;
				clients[findClient(ID)].send(new Message("userinfo", "SERVER", str,"All"));
					}
			else if(msg.type.equals("check_ok")&&(msg.content.equals("OOPS >>>WILL BE KILLED<<< ")))
			{
				remove(ID);
			}
			
		
	}

	public void Announce(String type, String sender, String content) {
		Message msg = new Message(type, sender, content, "All");
		for (int i = 0; i < clientCount; i++) {
			clients[i].send(msg);
		}
	}

	

	public ServerThread findUserThread(String usr) {
		for (int i = 0; i < clientCount; i++) {
			if (clients[i].username.equals(usr)) {
				return clients[i];
			}
		}
		return null;
	}
	

	@SuppressWarnings("deprecation")
	public synchronized void remove(int ID) {

	int pos = findClient(ID);
		
		if (!(userinfo_name==null)) {
			
			ArrayList aList_peername = new ArrayList(Arrays.asList(userinfo_name.split(",")));
			ArrayList aList_peerip = new ArrayList(Arrays.asList(userinfo_ip.split(",")));
			ArrayList aList_peerport = new ArrayList(Arrays.asList(userinfo_port.split(",")));
			
			if(pos<aList_peername.size())
			aList_peername.remove(pos);
			aList_peerip.remove(pos);
			aList_peerport.remove(pos);
			
			if(aList_peerip.isEmpty())
			{
				userinfo_ip="";
				userinfo_port="";
			}
			if (aList_peername.isEmpty())
			{
				userinfo_name="";
				
			}
			if (aList_peername.isEmpty()&& !aList_peerip.isEmpty())
			{
				userinfo_name=null;
				userinfo_ip = aList_peerip.get(0).toString();
				userinfo_port = aList_peerport.get(0).toString();
				for (int i = 1; i < clientCount - 1; i++) {
					
					userinfo_ip = userinfo_ip + "," + aList_peerip.get(i);
					userinfo_port = userinfo_port + "," + aList_peerport.get(i);
				}
			}
			
			if(!aList_peername.isEmpty()&& !aList_peerport.isEmpty() &&!aList_peerip.isEmpty())  {
				
				userinfo_name = aList_peername.get(0).toString();
				userinfo_ip = aList_peerip.get(0).toString();
				userinfo_port = aList_peerport.get(0).toString();
				for (int i = 1; i < clientCount - 1; i++) {
					userinfo_name = userinfo_name + "," + aList_peername.get(i);
					userinfo_ip = userinfo_ip + "," + aList_peerip.get(i);
					userinfo_port = userinfo_port + "," + aList_peerport.get(i);
				}
			}
			
		}
		else
		{
			ArrayList aList_peerip = new ArrayList(Arrays.asList(userinfo_ip.split(",")));
			ArrayList aList_peerport = new ArrayList(Arrays.asList(userinfo_port.split(",")));
			
			aList_peerip.remove(pos);
			aList_peerport.remove(pos);
			if(aList_peerip.isEmpty() )
				userinfo_ip=null;
				
			
			if(aList_peerport.isEmpty() )
				userinfo_port=null;
			else if(!aList_peerip.isEmpty()) {

				userinfo_ip = aList_peerip.get(0).toString();
				userinfo_port = aList_peerport.get(0).toString();
				for (int i = 1; i < clientCount - 1; i++) {

					userinfo_ip = userinfo_ip + "," + aList_peerip.get(i);
					userinfo_port = userinfo_port + "," + aList_peerport.get(i);
				}
			}
		}
		if (pos >= 0) {
			ServerThread toTerminate = clients[pos];
			ui.jTextArea1.append("\nRemoving client thread " + ID + " at "
					+ pos);
			if (pos < clientCount - 1) {
				for (int i = pos + 1; i < clientCount; i++) {

					clients[i - 1] = clients[i];
				}
			}
			clientCount--;
			try {
				toTerminate.close();
			} catch (IOException ioe) {
				ui.jTextArea1.append("\nError closing thread: " + ioe);
			}
			toTerminate.stop();
		}
	}

	private void addThread(Socket socket) {
		int a;
		a=socket.getPort();
		if (clientCount < clients.length) {
			
			ui.jTextArea1.append("\nClient accepted: " + socket);

			clients[clientCount] = new ServerThread(this, socket);
			try {
				clients[clientCount].open();
				clients[clientCount].start();
				clientCount++;
			} catch (IOException ioe) {
				ui.jTextArea1.append("\nError opening thread: " + ioe);
			}
		} else {
			ui.jTextArea1.append("\nClient refused: maximum " + clients.length
					+ " reached.");
		}
	}
}
