package common;

public class ProtocolSignUp {

	private String mUserName;
	private int mPort;
	public ProtocolSignUp(String username, int port) {
		mUserName = username;
		mPort = port;
	}
	
	public void setUserName(String username) {
		mUserName = username;
	}
	
	public void setPort(int port) {
		mPort = port;
	}
	
	public String getUserName( ) {
		return mUserName;
	}
	
	public int getPort() {
		return mPort;
	}
}
