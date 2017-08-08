package jsuop;

import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		Get get = new Get();
		Save save = new Save();
		Send send = new Send();
		
		ArrayList<ArrayList<String>> con = get.contacts();
		
		save.saveNowArray(get.nowArray());
		save.saveContacts(con);
		send.sendEmail(con);
	}
}
