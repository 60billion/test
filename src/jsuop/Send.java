package jsuop;

import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Send {
	
	public void sendEmail(ArrayList<ArrayList<String>> con){
		//ArrayList<ArrayList<String>> con=contacts();
		for(int i = 0; i<con.size(); i++){
			
			final String username = "60billion@mcasand.com";
			final String password = "!23public";
			
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.mailplug.co.kr");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username,password);
					}
				});

			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("60billion@mcasand.com"));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(con.get(i).get(2)));
				message.setSubject("We can upload your posting ("+con.get(i).get(0)+") at the blog");
				message.setContent("Dear "+con.get(i).get(1)+","+
						"\n\n If you want to upload your post at the blog, Plese click the 'yes' button bleow."
						+"\n\n And you dont need to join the blog and nothing to do for uploading your post. Just Click the button."
						+"\n\n It's easy."
						+ "\n\n It will make more efficient to advertise your posting. Thank you."
						+ "<a href='http://localhost:2000/email/"+con.get(i).get(5)+"'><input type='submit', value='Yes'></a>"
						,"text/html" 
						);

				Transport.send(message);

				System.out.println("sent emails.");

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}

	}

}
