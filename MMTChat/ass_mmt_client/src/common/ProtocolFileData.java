package common;

public class ProtocolFileData {

	private boolean isDataFileEnd;
	private byte[] mContent;
	
	public ProtocolFileData() {
		isDataFileEnd = false;
		mContent = null;
	}
	
	public ProtocolFileData(byte[] content, boolean isEnd) {
		mContent = content;
		isDataFileEnd = isEnd;
	}
	
	public void setIsDataFileEnd(boolean isEnd) {
		isDataFileEnd = isEnd;
	}
	
	public void setContent(byte[] content) {
		mContent = content;
	}
	
	public boolean isEnd() {
		return isDataFileEnd;
	}
	
	public byte[] getContent() {
		return mContent;
	}
}
