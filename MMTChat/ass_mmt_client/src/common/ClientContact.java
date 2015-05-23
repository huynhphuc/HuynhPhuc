package common;
import common.*;

public class ClientContact {
	private String clientName;
	private String clientIP;
	private int clientPort;
	
	public ClientContact() {
		clientName = "abc";
		clientIP = "localhost";
		clientPort = -1;
	}
	
	public ClientContact(String name, String ip, int port) {
		clientName = name;
		clientIP = ip;
		clientPort = port;
	}
	
	public void setName(String name) {
		clientName = name;	
	}
	
	public void setIP(String ip) {
		clientIP = ip;
	}
	
	public void setPort(int port) {
		clientPort = port;
	}
	
	public String getName() {
		return clientName;
	}
	
	public String getIP() {
		return clientIP;
	}
	
	public int getPort() {
		return clientPort;
	}
	
}
