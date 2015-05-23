package com.socket;
import common.*;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Download implements Runnable {

	public ServerSocket server;
	public Socket socket;
	public int port;
	public String saveTo = "";
	public ObjectInputStream In;
	public FileOutputStream Out;
	public gui_client_chat_sendfile ui;
	private ArrayList<byte[]> receiveListInBytes;
	private byte[] dataFile;

	public Download(String saveTo, gui_client_chat_sendfile ui) {
		try {
			server = new ServerSocket(0);
			port = server.getLocalPort();
			this.saveTo = saveTo;
			this.ui = ui;
		} catch (IOException ex) {
			System.out.println("Exception [Download : Download(...)]");
		}
	}

	
	public void run() {
		try {
			receiveListInBytes = new ArrayList<byte[]>();
			socket = server.accept();
			System.out.println("Download : " + socket.getRemoteSocketAddress());

			
			Out = new FileOutputStream(saveTo);
			DataInputStream in = new DataInputStream(
					socket.getInputStream());
			String startMessage = in.readUTF();
			
			;
			if (startMessage.equals(Tags.FILE_DATA_BEGIN)){
				In =new  ObjectInputStream(socket.getInputStream());
				byte[] b = (byte[])  In.readObject();
			int length = b.length;
			System.out.println("hahaha"+b.toString()+"  "+b.length);
			ProtocolFileData metaData = Communicate.getFileDataInBinary(b, length);
			while (!metaData.isEnd()) {
				receiveListInBytes.add(metaData.getContent());
				b = (byte[]) In.readObject();
				metaData = Communicate.getFileDataInBinary(b, b.length);
			}

			length = (receiveListInBytes.size() - 1)
					* 1024
					+ receiveListInBytes.get(receiveListInBytes.size() - 1).length;
			byte[] lego = new byte[length];
			int offset = 0;
			for (byte[] bytes : receiveListInBytes) {
				System.arraycopy(bytes, 0, lego, offset, bytes.length);
				offset += bytes.length;
			}
			dataFile = lego;
			}
			Out.write(dataFile);
			Out.close();
			
		
			ui.tf_show.append("[Application > Me] : Download complete\n");
			
		

			if (Out != null) {
				Out.close();
			}
			if (In != null) {
				In.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (Exception ex) {
			System.out.println("Exception [Download : run(...)]");
			ex.printStackTrace();
		}
	}
}