package engine;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.Socket;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.socket.Message;
import com.socket.gui_client;

public class Socketthreadchat extends Thread {

	public SocketSeverchat server = null;
	public Socket socket = null;
	public String username = "";
	public DataInputStream streamIn = null;
	public DataOutputStream streamOut = null;
	public gui_client ui;

	public int ID = -1;

	public Socketthreadchat(SocketSeverchat _server, Socket _socket) {
		super();
		server = _server;
		socket = _socket;
		ID = socket.getPort();
		ui = _server.ui;

	}

	public void send(Message msg) {
		try {
			
			streamOut.writeUTF(msg.toString());
			streamOut.flush();
			System.out.println("Out:" + msg.toString());
		} catch (IOException ex) {
			System.out.println("Exception [SocketClient : send(...)]");
		}
	}

	public int getID() {
		return ID;
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
			
			content = ".bye";

			
		}else if(a.equals("CHAT_CLOSE")) 
		{
			content =".bye";
		}
		else if (a.equals("CHAT_REQ")) {
			type = "connect_peer";
			sender = rootNode.getChildText("PEER_NAME");

		} else if (a.equals("SESSION")) {
			type = "test";
			sender = rootNode.getChildText("PEER_NAME");
			content =rootNode.getChildText("PORT");
		} else if (a.equals("FILE_DATA_END")) {
			type = "download_complete";
		} else if (a.equals("SESSION_ACCEPT")) {
			type = "login";
			

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

	@SuppressWarnings("deprecation")
	public void run() {
		while (true) {
			try {
				
				
				String temp = streamIn.readUTF();
				String Str = temp;

				
				System.out.println("Incoming : " + "\n" + Str);
				Message msg = handle_protocol(Str);
				System.out.println("Incoming : " + "\n" + msg.toString());
				try {
					server.handle(ID, msg);
				} catch (Exception io) {
					System.out.println("loi ngay cho nay handle"); // TODO
					io.getStackTrace();
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