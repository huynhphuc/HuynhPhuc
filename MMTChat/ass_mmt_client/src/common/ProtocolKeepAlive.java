package common;

public class ProtocolKeepAlive {
	private String mUserName;
	private String mStatus;
	
	public ProtocolKeepAlive(String username, String status) {
		mUserName = username;
		mStatus = status;
	}
	
	public void setUserName(String username) {
		mUserName = username;
	}
	
	public void setStatus(String status) {
		mStatus = status;
	}
	
	public String getUserName() {
		return mUserName;
	}
	
	public String getStatus() {
		return mStatus;
	}

}
