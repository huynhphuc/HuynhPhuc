package common;

import java.io.IOException;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import common.*;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class Communicate {

	public static final byte[] fileDataStart = { 60, 70, 73, 76, 69, 95, 68,
			65, 84, 65, 62 };
	public static final byte[] fileDataEnd = { 60, 47, 70, 73, 76, 69, 95, 68,
			65, 84, 65, 62 };
	public static final byte[] endOfTransfer = { 60, 70, 73, 76, 69, 95, 68,
			65, 84, 65, 95, 69, 78, 68, 47, 62 };

	public Communicate() {
		// TODO Auto-generated constructor stub
	}


	

	


	
	

	public static ProtocolFileData getFileDataInBinary(byte[] fileInBytes,
			int length) throws TagFormatException {

		byte[] subStart = Arrays.copyOfRange(fileInBytes, 0, 11);
		byte[] subEnd = Arrays.copyOfRange(fileInBytes, length - 12, length);
		byte[] endOfTransfers = Arrays.copyOfRange(fileInBytes, 0, 16);

		byte[] content;
		boolean isEnd = false;
		if (Arrays.equals(endOfTransfers, endOfTransfer)) {
			isEnd = true;
			content = null;
		} else if (Arrays.equals(subStart, fileDataStart)
				&& Arrays.equals(subEnd, fileDataEnd)) {
			content = Arrays.copyOfRange(fileInBytes, 11, length - 12);
			isEnd = false;
		} else {
			TagFormatException tfe = new TagFormatException(
					"It's not a acknowledgement file protocol");
			throw tfe;
		}
		return new ProtocolFileData(content, isEnd);
	}

	public static ServerSocket ramdomAvailableSocket() {
		Random ran = new Random();
		boolean accept = false;
		ServerSocket socket = null;
		while (!accept) {
			int x = ran.nextInt(9999);

			try {
				socket = new ServerSocket(x);
				accept = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				accept = false;
			}
		}

		return socket;
	}
}
