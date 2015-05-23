package common;

public class ProtocolChatMessage {
	
	public static final int CHAT_MESSAGE = 1;
	public static final int FILE_MESSAGE = 2;
	public static final int FILE_ACK = 3;
	public static final int FILE_NOACK = 4;
	
	private int mType;
	private String mContent;
	
	public ProtocolChatMessage(int type, String content) {
		mType = type;
		mContent = content;
	}
	
	public void setType(int type) {
		mType = type;
	}
	
	public void setContent(String content) {
		mContent = content;
	}

	public int getType() {
		return mType;
	}
	
	public String getContent() {
		return mContent;
	}
}
