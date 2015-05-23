package com.socket;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.DefaultListModel;

import engine.*;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JScrollPane;
import javax.swing.JList;

import com.socket.*;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.WindowAdapter;

import javax.swing.SpringLayout;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
	
public class gui_client extends javax.swing.JFrame {

	public Thread clientThread;
	public SocketClient client;
	public int port, peerport;

	public String listuser;

	public String serverAddr, username = "", password;

	public JFrame frame;
	public JTextField tf_username;
	public JTextField tf_serverip;
	public JTextArea tf_show;
	public JList list_onl;
	public SocketSeverchat server_peer;

	public DefaultListModel model;
	public gui_client_chat_sendfile[] ui_chat = new gui_client_chat_sendfile[50];
	public int count_ui = 0;
	public boolean Test=false;
	public JTextField port_server;
	public JButton btn_login = new JButton("Login");
	// kiem tra server co gui danh sach moi ko
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				
				try {
					gui_client window = new gui_client();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the application.
	 */
	public gui_client() {
		initialize();
		model.addElement("All");

	}

	/**
	 * Initialize the contents of the frame.
	 */
//kiem tra list onl
	
	private void initialize() {
		frame = new JFrame();
		frame.setForeground(new Color(153, 51, 255));
		frame.getContentPane().setBackground(new Color(153, 255, 255));
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(gui_client.class.getResource("/image/icon.png")));
		frame.setResizable(false);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				try {
					client.send(new Message("signout", username, ".bye", "SERVER"));
					clientThread.stop();
				} catch (Exception ex) {
				}

			}

		});
		frame.setBounds(100, 100, 838, 548);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("List Online");
		lblNewLabel.setBounds(473, 15, 111, 16);
		lblNewLabel.setForeground(new Color(153, 0, 204));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		frame.getContentPane().add(lblNewLabel);

		tf_username = new JTextField();
		tf_username.setBounds(666, 251, 141, 25);
		tf_username.addKeyListener(new KeyAdapter() {
			
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode()==KeyEvent.VK_ENTER)
					btn_loginActionPerformed();
			}
		});
		tf_username.setForeground(new Color(51, 0, 255));
		frame.getContentPane().add(tf_username);
		tf_username.setColumns(10);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(696, 216, 75, 16);
		lblUsername.setForeground(new Color(102, 0, 255));
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		frame.getContentPane().add(lblUsername);

		tf_serverip = new JTextField();
		tf_serverip.setBounds(666, 97, 141, 25);
		tf_serverip.setForeground(new Color(51, 0, 255));
		tf_serverip.setText("localhost");
		frame.getContentPane().add(tf_serverip);
		tf_serverip.setColumns(10);

		JLabel lblServerip = new JLabel("ServerIP");
		lblServerip.setBounds(696, 52, 75, 26);
		lblServerip.setForeground(new Color(255, 0, 153));
		lblServerip.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frame.getContentPane().add(lblServerip);
		btn_login.setBounds(696, 300, 75, 35);

		
		btn_login.setForeground(new Color(255, 51, 51));
		btn_login.setBackground(new Color(102, 255, 102));
		btn_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_loginActionPerformed();

			}
		});
		frame.getContentPane().add(btn_login);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(429, 37, 180, 422);
		frame.getContentPane().add(scrollPane);

		list_onl = new JList();
		list_onl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getClickCount()==2&&!arg0.isConsumed()){
					arg0.consume();
					String target = list_onl.getSelectedValue().toString();
					connect_peer(target);
				}
			}
		});
		list_onl.setForeground(new Color(102, 51, 255));
		scrollPane.setViewportView(list_onl);
		list_onl.setModel((model = new DefaultListModel()));

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(27, 33, 376, 432);
		frame.getContentPane().add(scrollPane_2);

		tf_show = new JTextArea();
		tf_show.setEditable(false);
		tf_show.setLineWrap(true);
		tf_show.setForeground(new Color(102, 0, 0));
		scrollPane_2.setViewportView(tf_show);
		
		port_server = new JTextField();
		port_server.setBounds(666, 169, 141, 25);
		port_server.setForeground(new Color(51, 0, 255));
		port_server.setText("13000");
		frame.getContentPane().add(port_server);
		port_server.setColumns(10);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(713, 139, 43, 17);
		lblPort.setForeground(new Color(255, 0, 153));
		lblPort.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(lblPort);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(gui_client.class.getResource("/image/back_ground.jpg")));
		lblNewLabel_1.setBounds(0, 0, 832, 519);
		frame.getContentPane().add(lblNewLabel_1);

	}

	private Boolean btn_connectActionPerformed() {
		serverAddr = tf_serverip.getText();
		port = Integer.parseInt(port_server.getText());

		if (!serverAddr.isEmpty()) {
			try {
				client = new SocketClient(this);
				clientThread = new Thread(client);
				clientThread.start();
				return true;
			} catch (Exception ex) {
				tf_show.append("[Application > Me] : Server not found\n");
				return false;
			}
		}
		return false;
	}

	private void btn_loginActionPerformed() {// GEN-FIRST:event_jButton1ActionPerformed
		if(Test==false)
		
		if(btn_connectActionPerformed())
		{
			tf_serverip.setEnabled(false);;
			port_server.setEnabled(false);;
		}
		username = tf_username.getText();
		
		Random ran = new Random();
		int x = ran.nextInt(55555) + 1000;
		
		peerport = x;
		if (!username.isEmpty() ) {
			client.send(new Message("login", username, Integer
					.toString(peerport), "SERVER"));
		}
		//tf_username.setEnabled(false);
		Test=true;
		//btn_login.setEnabled(false);
	}// GEN-LAST:event_jButton1ActionPerformed

	public void connect_peer(String target) {

		String user = finduser(listuser, target);
		ArrayList aList = new ArrayList(Arrays.asList(user.split(",")));
		String peer_name = aList.get(0).toString();
		String peer_ip = aList.get(1).toString();
		String peer_port = aList.get(2).toString();
		if (user == null)
			tf_show.append("[Application > Me] : user doesn't exist");
		else {
			ui_chat[count_ui] = new gui_client_chat_sendfile();
			ui_chat[count_ui].frame.setVisible(true);
			ui_chat[count_ui].lbl_name.setText(peer_name);
			ui_chat[count_ui].connect_chat(target, peer_ip,
					Integer.parseInt(peer_port), this, peerport);
			System.out.print(count_ui);
			count_ui++;

		}
	}

	public void connect_peer_res(String peer_name) {
		String user = finduser(listuser, peer_name);
		ArrayList aList = new ArrayList(Arrays.asList(user.split(",")));

		String peer_ip = aList.get(1).toString();
		String peer_port = aList.get(2).toString();
		ui_chat[count_ui] = new gui_client_chat_sendfile();
		ui_chat[count_ui].frame.setVisible(true);
		ui_chat[count_ui].lbl_name.setText(peer_name);
		ui_chat[count_ui].connect_chat_res(peer_name, peer_ip,
				peerport, this);
		System.out.print(count_ui);
		count_ui++;

	}

	

	private String finduser(String listuser, String username1) {
		ArrayList aList = new ArrayList(Arrays.asList(listuser.split(";")));
		String peer_name = aList.get(0).toString();
		String peer_ip = aList.get(1).toString();
		String peer_port = aList.get(2).toString();

		ArrayList aList_peername = new ArrayList(Arrays.asList(peer_name
				.split(",")));

		ArrayList aList_peerip = new ArrayList(
				Arrays.asList(peer_ip.split(",")));

		ArrayList aList_peerport = new ArrayList(Arrays.asList(peer_port
				.split(",")));
		int count = -1;

		for (int i = 0; i < aList_peerip.size(); i++) {
			String test = aList_peername.get(i).toString();
			if (test.equals(username1))
				count = i;

		}
		if (count == -1)
			return null;
		else
			return username1 + "," + aList_peerip.get(count) + ","
					+ aList_peerport.get(count);
	}

	public int findip(String listuser, String name) {
		ArrayList aList = new ArrayList(Arrays.asList(listuser.split(";")));
		String peer_name = aList.get(0).toString();
		String peer_ip = aList.get(1).toString();
		String peer_port = aList.get(2).toString();

		ArrayList aList_peername = new ArrayList(Arrays.asList(peer_name
				.split(",")));

		ArrayList aList_peerip = new ArrayList(
				Arrays.asList(peer_ip.split(",")));

		ArrayList aList_peerport = new ArrayList(Arrays.asList(peer_port
				.split(",")));
		int count = -1;

		for (int i = 0; i < aList_peerip.size(); i++) {
			String test = aList_peername.get(i).toString();
			
			if (test.equals(name)
					&& !username.equals(aList_peername.get(i).toString()))
				count = i;

		}

		for (int i = 0; i < count_ui; i++) {
			System.out.println("sxxxxxxxxxxxxxxx"+i);
			if (ui_chat[i].lbl_name.getText().equals(aList_peername.get(count))) {
				count = i;
				break;
			}
		}
		System.out.println(count);
		return count;

	}
	public void handel_list_onl(String list)
	{
		
		ArrayList aList = new ArrayList(Arrays.asList(list.split(";")));
		String peer_name = aList.get(0).toString();
		ArrayList aList_peername = new ArrayList(Arrays.asList(peer_name
				.split(",")));
		String temp=username;
		model.removeAllElements();
		username=temp;
		for (int i = 0; i < aList_peername.size(); i++)
		{	
			boolean exists = false;
			
        for(int j = 0; j< model.getSize(); j++){
        	
            if(model.getElementAt(j).equals(aList_peername.get(i))){
                exists = true; break;
            }
          
        }
        if(username.equals(aList_peername.get(i)))
		{
        	exists=true;
		}
        if(!exists){ model.addElement(aList_peername.get(i)); }
		}
	
		
	}
}
