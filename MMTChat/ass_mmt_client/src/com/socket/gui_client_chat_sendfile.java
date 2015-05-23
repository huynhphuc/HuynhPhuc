package com.socket;

import java.awt.event.WindowEvent;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import engine.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.awt.event.WindowAdapter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.Socket;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class gui_client_chat_sendfile extends javax.swing.JFrame {

	/**
	 * 
	 */
	public Socketthreadchat clientserver;
	private static final long serialVersionUID = 1L;
	public Thread clientThread;
	public SocketClient_chat client;
	public int port;
	public String serverAddr, username = "";
	public File file;

	public JFrame frame;
	public JTextField textField;
	public JButton btn_send;
	public JTextArea tf_show;
	public JButton btn_sendfile;
	public gui_client ui_server;
	public String Chat ="client";
	public final JLabel lbl_name = new JLabel("");
	private JTextField tf_file;
	private JLabel lblYouWantSend;
	private JLabel lblNewLabel;

	/**
	 * Create the application.
	 */
	// tao connect chat
	public void delete_ui_chat() {
		
		int pos = -1;
		for (int i = 0; i < ui_server.count_ui; i++) {
			if (ui_server.ui_chat[i].lbl_name.getText().equals(lbl_name))
				pos = i;
		}
		if (pos < ui_server.count_ui - 1 && pos >0) {
			for (int i = pos + 1; i < ui_server.count_ui; i++) {

				ui_server.ui_chat[i - 1] = ui_server.ui_chat[i];
			}
		}
		ui_server.count_ui--;
	}
	

	public void windowclose() {
		
		frame.setVisible(false);
		delete_ui_chat();
		System.out.println("number cua cuaso "+ui_server.count_ui);
		dispose();
		
	}

	public void connect_chat_res(String SERVER_PEER, String ip, int port,
			gui_client frame) {
		ui_server = frame;
		username = ui_server.username;
		Chat="server";
		try {
			int pos=0;
			for(int i=0;i<ui_server.server_peer.clientCount;i++)
			{
				if(ui_server.server_peer.clients[i].username==SERVER_PEER	)
					pos =i;
			};
			clientserver=ui_server.server_peer.clients[pos];

		} catch (Exception ex) {
		}
	}

	public void connect_chat(String SERVER_PEER, String ip, int port,
			gui_client frame, int mypeerport) {
		ui_server = frame;
		try {
			username = ui_server.username;
			serverAddr =ip;
			client = new SocketClient_chat(this, ui_server, ip, port);
			clientThread = new Thread(client);
			clientThread.start();
			client.send(new Message("connect_peer", ui_server.username, Integer
					.toString(mypeerport), SERVER_PEER));
		} catch (Exception ex) {
		}
	}

	public gui_client_chat_sendfile() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(204, 102, 255));
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(gui_client_chat_sendfile.class.getResource("/image/icon.png")));
		frame.setResizable(false);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				try {
					if(Chat.equals("client"))
					{
					client.send(new Message("message", username, ".bye",
							"SERVER"));
					clientThread.stop();
					}
					else
					{
						clientserver.send(new Message("message", username, ".bye",
								"SERVER"));
						ui_server.server_peer.remove(clientserver.ID);
					}
					windowclose();
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}

		});
		frame.setBounds(100, 100, 635, 519);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		btn_send = new JButton("Send");
		btn_send.setBounds(425, 416, 84, 31);
		btn_send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btn_sendActionPerformed();
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btn_send);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 31, 387, 329);
		frame.getContentPane().add(scrollPane);

		tf_show = new JTextArea();
		tf_show.setEditable(false);
		tf_show.setLineWrap(true);
		tf_show.setFont(new Font("Monospaced", Font.PLAIN, 13));
		tf_show.setForeground(new Color(102, 0, 102));
		scrollPane.setViewportView(tf_show);

		textField = new JTextField();
		textField.setBounds(24, 393, 387, 76);
		textField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode()==KeyEvent.VK_ENTER)
					btn_sendActionPerformed();
			}
		});
		textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField.setForeground(new Color(0, 102, 51));
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		btn_sendfile = new JButton("Send");
		btn_sendfile.setBounds(484, 147, 84, 25);
		btn_sendfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendfileActionPerformed();
			}
		});
		frame.getContentPane().add(btn_sendfile);
		lbl_name.setBounds(24, 359, 122, 31);
		frame.getContentPane().add(lbl_name);

		tf_file = new JTextField();
		tf_file.setBounds(446, 65, 156, 25);
		frame.getContentPane().add(tf_file);
		tf_file.setColumns(10);

		JButton btnChooseFile = new JButton("Choose");
		btnChooseFile.setBounds(484, 103, 84, 25);
		btnChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnChooseFileActionPerformed();
			}
		});
		frame.getContentPane().add(btnChooseFile);
		
		lblYouWantSend = new JLabel("You want send file?");
		lblYouWantSend.setBounds(462, 37, 122, 17);
		lblYouWantSend.setForeground(new Color(0, 0, 204));
		lblYouWantSend.setFont(new Font("Tahoma", Font.PLAIN, 13));
		frame.getContentPane().add(lblYouWantSend);
		
		lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(gui_client_chat_sendfile.class.getResource("/image/Beautiful-Background-64.jpg")));
		lblNewLabel.setBounds(0, 0, 629, 490);
		frame.getContentPane().add(lblNewLabel);
	}

	private void sendfileActionPerformed() { // TODO
		long size = file.length();
		if(Chat.equals("client")){
		if (size < 120 * 1024 * 1024) {
			client.send(new Message("upload", username, file.getName(),
					lbl_name.getText()));
		} else {
			tf_show.append("[Application > Me] : File is size too large\n");
		}
		}
		else
		{
			if (size < 120 * 1024 * 1024) {
				clientserver.send(new Message("upload", username, file.getName(),
						lbl_name.getText()));
			} else {
				tf_show.append("[Application > Me] : File is size too large\n");
			}
			
		}
	}// GEN-LAST:event_jButton6ActionPerformed
		

	private void btnChooseFileActionPerformed() {// TODO
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showDialog(this, "Select File");
		file = fileChooser.getSelectedFile();

		if (file != null) {
			if (!file.getName().isEmpty()) {
				String str;

				if (tf_file.getText().length() > 30) {
					String t = file.getPath();
					str = t.substring(0, 20) + " [...] "
							+ t.substring(t.length() - 20, t.length());
				} else {
					str = file.getPath();
				}
				tf_file.setText(str);
			}
		}
	}

	private void btn_sendActionPerformed() {// GEN-FIRST:event_jButton4ActionPerformed
		String msg = textField.getText();
		if(Chat.equals("client")){
		if (!msg.isEmpty()) {
			textField.setText("");
			tf_show.append("[me" + ">" + lbl_name.getText() + "] : " + msg
					+ "\n");
			client.send(new Message("message", username, msg, lbl_name
					.getText()));
		}
		}
		else
		{
			
			if (!msg.isEmpty()) {
				textField.setText("");
				tf_show.append("[me" + ">" + lbl_name.getText() + "] : " + msg
						+ "\n");
				clientserver.send(new Message("message", username, msg, lbl_name
						.getText()));
			}
		}
	}

}
