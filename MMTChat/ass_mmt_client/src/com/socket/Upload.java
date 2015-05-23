package com.socket;

import java.io.*;
import common.*;
import java.net.*;
import java.util.Arrays;

public class Upload implements Runnable {

	public String addr;
	public int port;
	public Socket socket;
	public ObjectOutputStream Out;	
	public File file;
	public gui_client_chat_sendfile ui;

	public Upload(String addr, int port1, File filepath,
			gui_client_chat_sendfile frame) {
		super();
		try {
			file = filepath;
			ui = frame;
			port=port1;
			socket = new Socket(InetAddress.getByName(addr), port);
			
		} catch (Exception ex) {
			System.out.println("Exception [Upload : Upload(...)]");
		}
	}
	public static byte[] getBytesFromFile(File file) throws IOException {        
		// Get the size of the file
		long length = file.length();


		if (length > Integer.MAX_VALUE) {
			// File is too large
			throw new IOException("File is too large!");
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int)length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;

		FileInputStream is = new FileInputStream(file);
		try {
			while (offset < bytes.length
					&& (numRead = is.read(bytes,offset ,bytes.length-offset)) >= 0) {
				offset += numRead;
			}
		} finally {
			is.close();
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "+file.getName());
		}
		return bytes;
	}




	public void run() {
		try {
			byte[] buffer = getBytesFromFile(file);
			int count;
			int offset = 0;
			int lenght = buffer.length;
			DataOutputStream writeFile = new DataOutputStream(socket.getOutputStream());
			writeFile.writeUTF("<FILE_DATA_BEGIN/>");
			writeFile.flush();
			Out =  new ObjectOutputStream(socket.getOutputStream());
			while( offset <= lenght ){
				byte[] byteToSend;
				if( (offset + 1024) >= lenght){
					byteToSend = Arrays.copyOfRange(buffer, offset, lenght);
				}else {
					byteToSend = Arrays.copyOfRange(buffer, offset, offset + 1024);
				}
				byte[] packData = new byte[11 + byteToSend.length + 12];
				System.arraycopy(Communicate.fileDataStart, 0, packData, 0, 11);
				System.arraycopy(byteToSend, 0, packData, 11, byteToSend.length);
				System.arraycopy(Communicate.fileDataEnd, 0, packData, 11 + byteToSend.length, 12);
				Out.writeObject(packData);
				Out.flush();
				offset += 1024;
				
			}	
			Out.writeObject(Communicate.endOfTransfer);
			Out.flush();
			ui.tf_show.append("[Applcation > Me] : File upload complete\n");

			writeFile.close();
			if (Out != null) {
				Out.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (Exception ex) {
			System.out.println("Exception [Upload : run()]");
			ex.printStackTrace();
		}
	}

}