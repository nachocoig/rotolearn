package es.rotolearn.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ReadMessageQueueBrowserServlet  {
	private static final long serialVersionUID = 1L;

	
	public String leerbw(String correlation)  {

		StringBuffer _sB = new StringBuffer(32);
		

		try {

			javax.naming.InitialContext jndiContext = new javax.naming.InitialContext(); 					
			ConnectionFactory connectionFactory = (ConnectionFactory) jndiContext.lookup("jms/cf1.1"); 
			Queue queue = (Queue) jndiContext.lookup("jms/queue1.1");
			
			//Create the connection
			Connection connection = connectionFactory.createConnection();

			boolean bTransacted = false;

			int iAcknowledgeMode = javax.jms.Session.CLIENT_ACKNOWLEDGE;

			//Create the session
			Session session =
				connection.createSession(bTransacted, iAcknowledgeMode);
		 			   
			javax.jms.QueueBrowser browser;
			
			String correlationn = "JMSCorrelationID = '"+correlation+"'";
			browser = session.createBrowser(queue, correlationn);

			//Start the connection
			connection.start();

			java.util.Enumeration enum1 = browser.getEnumeration();

			java.util.Vector vctMessage = new java.util.Vector();
			
			while (enum1.hasMoreElements()) {
				
				javax.jms.Message message =(javax.jms.Message) enum1.nextElement();
				javax.jms.Message message2 = message;
				if (message2 != null) {
					
					if (message2 instanceof javax.jms.TextMessage) {
						
						javax.jms.TextMessage Tmensaje =(javax.jms.TextMessage) message2;						
						_sB.append(Tmensaje.getText()+" </br>"); 
					}
				}
			}
			
			// Close browser
			browser.close();
			
			// Close session
			session.close();
			
			// Close connection
			connection.close();
			
		} catch (Exception e) {
			System.out.println(	"JHC *************************************** Error in doPost: "	+ e);
		}

		return _sB.toString();
	}
}
