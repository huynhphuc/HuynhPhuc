package com.socket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	public String type, sender, content, recipient;

	public Message(String type, String sender, String content, String recipient) {
		this.type = type;
		this.sender = sender;
		this.content = content;
		this.recipient = recipient;
	}

	@Override
	public String toString() {
		if (this.type.equals("login") && this.content.equals("FALSE")
				&& this.sender.equals("SERVER"))
			return "<SESSION_DENY/>";
		else if (this.type.equals("signup") && this.content.equals("FALSE")
				&& this.sender.equals("SERVER"))
			return "<SESSION_DENY/>";
		else if (this.type.equals("login") && this.content.equals("TRUE"))
			return "<Login_Sucess>";
		else if (this.type.equals("test") && this.sender.equals("SERVER"))
			return "<SESSION_ACCEPT_CONECT>";
		else if (this.type.equals("userinfo") && this.sender.equals("SERVER")) {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			ArrayList aList = new ArrayList(Arrays.asList(this.content
					.split(";")));
			String peer_name = aList.get(0) + "";
			String peer_ip = aList.get(1) + "";
			String peer_port = aList.get(2) + "";

			return appendpeer(peer_name, peer_ip, peer_port);
		} 
		else if (this.type.equals("signup") && !this.sender.equals("SERVER"))
			return "<SESSION>"   + "<USER_NAME>" + sender + "<USER_NAME>"
					 + "<PASS_WORD>" + content + "<PASS_WORD>" + 
					 "</SESSION>";
		else if (this.type.equals("login") && !this.sender.equals("SERVER"))
			return "<SESSION>"   + "<USER_NAME>" + sender + "<USER_NAME>"
					 + "<PASS_WORD>" + content + "<PASS_WORD>" + 
					 "</SESSION>";
		else if (this.type.equals("signout") && this.sender.equals("SERVER"))
			return "<SESSION_KEEP_ALIVE>" +   "<USER_NAME>" + sender
					+ "<USER_NAME>"   + "<STATUS>" + content + "<STATUS>"
					  + "</SESSION_KEEP_ALIVE>";
		else if (this.type.equals("connect_peer")
				&& !this.content.equals("reject")
				&& !this.content.equals("accept")) {
			return "<CHAT_REQ>"   + "<PEER_NAME>" + this.sender
					+ "</PEER_NAME>"   + "</CHAT_REQ>";

		} else if (this.content.equals(".bye")) {
			return "<CHAT_DENY/>"  ;
		} else if (this.type.equals("connect_peer")
				&& this.content.equals("reject")) {
			return "<CHAT_DENY/>";
		} else if (this.type.equals("connect_peer")
				&& this.content.equals("accept")) {
			return "<CHAT_ACCEPT/>";
		} else if (this.type.equals("message")) {
			return "<CHAT_MSG>" + this.content + "</CHAT_MSG>"  ;
		} else if (this.type.equals("upload")) // TODO
		{
			return "<FILE_REQ>" + this.content + "</FILE_REQ>"  ;
		} else if (this.type.equals("upload_res") && this.content.equals("NO")) {
			return "<FILE_REQ_NOACK/>"  ;
		} else if (this.type.equals("upload_res") && !this.content.equals("NO")) {
			return "<FILE_REQ_ACK>" +   "<PORT>" + this.content
					+ "</PORT>" +   "</FILE_REQ_ACK>"  ;
		} else if (this.type.equals("Loading")) {
			return "<FILE_DATA_BEGIN/>" + "<FILE_DATA>" + "</FILE_DATA>"
					+ "<FILE_DATA_END/>"  ;
		} else if (this.type.equals("download_complete")) {
			return "</FILE_DATA>" + "<FILE_DATA_END/>"  ;
		} else
			return "{type='" + type + "', sender='" + sender + "', content='"
					+ content + "', recipient='" + recipient + "'}";
	}

	private String appendpeer(String peer_name, String peer_ip, String peer_port) {

		@SuppressWarnings({ "rawtypes", "unchecked" })
		ArrayList aList_peername = new ArrayList(Arrays.asList(peer_name
				.split(",")));

		@SuppressWarnings({ "rawtypes", "unchecked" })
		ArrayList aList_peerip = new ArrayList(
				Arrays.asList(peer_ip.split(",")));

		@SuppressWarnings({ "rawtypes", "unchecked" })
		ArrayList aList_peerport = new ArrayList(Arrays.asList(peer_port
				.split(",")));
		String str = "<SESSION_ACCEPT>"  ;
		for (int i = 0; i < aList_peerip.size(); i++) {
			str = str + "<PEER>" +  "<PEER_NAME>" + aList_peername.get(i)
					+ "</PEER_NAME>"   + "<IP>" + aList_peerip.get(i)
					+ "</IP>" +   "<PORT>" + aList_peerport.get(i)
					+ "</PORT>" +  "</PEER>"  ;

		}
		str = str + "</SESSION_ACCEPT>"  ;
		return str;
	}

}