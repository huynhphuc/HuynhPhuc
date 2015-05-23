package engine;

import com.socket.*;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import engine.*;

public class SocketSeverchat implements Runnable {
	public Socketthreadchat clients[];
	public ServerSocket server = null;
	public Thread thread = null;
	public int clientCount = 0;
	public gui_client ui;

	public String username;

	public SocketSeverchat(gui_client frame, int port_peer) {

		clients = new Socketthreadchat[50];
		ui = frame;

		try {
			server = new ServerSocket(port_peer);
			ui.tf_show.append("Server startet. IP : "
					+ InetAddress.getLocalHost() + ", Port : "
					+ server.getLocalPort());
			ui.tf_show.append("\n");

			start();
		} catch (IOException ioe) {

		}
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	public void run() {
		while (thread != null) {
			try {
				ui.tf_show.append("\nWaiting for a client ...");
				addThread(server.accept());

			} catch (Exception ioe) {
			}
		}

	}

	private void addThread(Socket socket) {
		int a = socket.getPort();

		clients[clientCount] = new Socketthreadchat(this, socket);
		try {
			clients[clientCount].open();
			clients[clientCount].start();
			clientCount++;
		} catch (IOException ioe) {
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

	public synchronized void remove(int ID) {

		int pos = findClient(ID);

		if (pos >= 0) {
			Socketthreadchat toTerminate = clients[pos];
			ui.tf_show.append("\nRemoving client thread " + ID + " at " + pos);

			if (pos < clientCount - 1) {
				for (int i = pos + 1; i < clientCount; i++) {

					clients[i - 1] = clients[i];
				}
			}
			clientCount--;
			try {
				toTerminate.close();
			} catch (IOException ioe) {

			}
			toTerminate.stop();
		}
	}

	public void stop() {
		if (thread != null) {
			thread.stop();
			thread = null;
		}
	}

	public Socketthreadchat findUserThread(String usr) {
		for (int i = 0; i < clientCount; i++) {
			if (clients[i].username.equals(usr)) {
				return clients[i];
			}
		}
		return null;
	}

	public synchronized void handle(int ID, Message msg)
			throws NumberFormatException, UnknownHostException, InterruptedException {
		if (msg.content.equals(".bye")) {
			remove(ID);
			
		} else if (msg.content.equals("complete")) {

		} else {

			if (msg.type.equals("message")) {
				
				int pos = ui.findip(ui.listuser,clients[findClient(ID)].username);
				System.out.println("ngay cho nay ...................................."+pos);
				ui.ui_chat[pos].tf_show.append("["
						+ ui.ui_chat[pos].lbl_name.getText() + msg.sender
						+ " > Me] : " + msg.content + "\n");

			} else if (msg.type.equals("connect_peer")) {
				if (JOptionPane.showConfirmDialog(ui, ("chat request from "
						+ msg.sender + " ?")) == 0) {
					ui.connect_peer_res(msg.sender);
					clients[findClient(ID)].username=msg.sender;
					clients[findClient(ID)].send(new Message("connect_peer",
							ui.username, "accept", msg.sender));
					

				} else {

					ui.tf_show.append("reject connect from " + msg.sender);
					clients[findClient(ID)].send(new Message("connect_peer",
							ui.username, "reject", msg.recipient));

					remove(ID);
				}
			} else if (msg.type.equals("upload")) {
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
						int pos = ui.findip(ui.listuser,
								clients[findClient(ID)].username);
						Download dwn = new Download(saveTo, ui.ui_chat[pos]);
						Thread t = new Thread(dwn);
						t.start();
						clients[findClient(ID)]
								.send(new Message("upload_res", server
										.getInetAddress().getHostAddress(),
										("" + dwn.port), jf.getSelectedFile()
												.getName()));
					} else {
						clients[findClient(ID)].send(new Message("upload_res",
								ui.username, "NO", msg.sender));
					}
				} else {
					clients[findClient(ID)].send(new Message("upload_res",
							ui.username, "NO", msg.sender));
				}
			} else if (msg.type.equals("download_complete")) {
				int pos = ui.findip(ui.listuser, clients[findClient(ID)].username);
				ui.ui_chat[pos].tf_show.append(ui.ui_chat[pos].lbl_name
						.getText() + ": Download complete\n");
			} else if (msg.type.equals("Loading")) {
				int pos = ui.findip(ui.listuser, clients[findClient(ID)].username);
				ui.ui_chat[pos].tf_show.append(ui.ui_chat[pos].lbl_name
						.getText() + ": Uploading file\n");
			}
			
			else if (msg.type.equals("upload_res")) { // TODO
				if (!msg.content.equals("NO")) {
					int port = Integer.parseInt(msg.content);
					String addr = clients[findClient(ID)].socket.getInetAddress().getHostAddress();
					
					int pos = ui.findip(ui.listuser,clients[findClient(ID)].username);
					Upload upl = new Upload(addr, port, ui.ui_chat[pos].file, ui.ui_chat[pos]);
					Thread t = new Thread(upl);
					t.start();
					
				} else {
					ui.tf_show.append("[SERVER > Me] : " + msg.sender
							+ " rejected file request\n");
				}
			}

		}
	}

}
