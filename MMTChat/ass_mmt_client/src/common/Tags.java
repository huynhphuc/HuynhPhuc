package common;

import java.io.BufferedReader;
import java.io.IOException;

public class Tags {
	
	public static final String SESSION = "SESSION";
	public static final String SESSION_DENY_S = "SESSION_DENY";
	public static final String SESSION_ACCEPT = "SESSION_ACCEPT";
	public static final String SESSION_KEEP_ALIVE = "SESSION_KEEP_ALIVE";
	public static final String PEER = "PEER";
	public static final String PEER_NAME = "PEER_NAME";
	public static final String PORT = "PORT";
	public static final String IP = "IP";
	





	// file transfer

	public static final String FILE_REQ_S = "<FILE_REQ>";
	public static final String FILE_REQ_E = "</FILE_REQ>";
	public static final String FILE_REQ_NOACK = "<FILE_REQ_NOACK/>";
	public static final String FILE_REQ_ACK_S = "<FILE_REQ_ACK>";
	public static final String FILE_REQ_ACK_E = "</FILE_REQ_ACK>";
	public static final String FILE_DATA_BEGIN = "<FILE_DATA_BEGIN/>";
	public static final String FILE_DATA_S = "<FILE_DATA>";
	public static final String FILE_DATA_E = "</FILE_DATA>";
	public static final String FILE_DATA_STOP = "<FILE_DATA_END/>";
	
	
}
