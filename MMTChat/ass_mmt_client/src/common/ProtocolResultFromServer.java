package common;

import java.util.ArrayList;

import common.*;

public class ProtocolResultFromServer {
	private ArrayList<ClientContact> mClientContacts;
	private boolean isAccept = false;
	
	public ProtocolResultFromServer() {
		isAccept = false;
		mClientContacts = null;
	}
	
	public void setClientContacts(ArrayList<ClientContact> clients) {
		mClientContacts = clients;
	}
	
	public void setStatus(boolean accept) {
		isAccept = accept;
	}
	
	public ArrayList<ClientContact> getClientContacts() {
		return mClientContacts;
	}
	
	public boolean isAccept() {
		return isAccept;
	}
	
}