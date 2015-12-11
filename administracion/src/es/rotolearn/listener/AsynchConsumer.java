package es.rotolearn.listener;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class AsynchConsumer {


	private InitialContext contextoInicial = null;
	private ConnectionFactory factory = null;
	private Destination cola = null;
	private Connection connection = null;
	private Session session = null;
	private MessageConsumer consumer = null;
	private TextListener listener = null;

	public void lecturaAsynch() {

		try {
			contextoInicial = new InitialContext();
			factory = (javax.jms.ConnectionFactory) contextoInicial
					.lookup("jms/cf1.1");
			cola = (javax.jms.Destination) contextoInicial
					.lookup("jms/queuePagos");

			connection = factory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			consumer = session.createConsumer(cola);
			listener = new TextListener();
			consumer.setMessageListener(listener);

			System.out.println("LecturaAsincronaServlet-->Listener levantado");
			connection.start();

		} catch (NamingException ne) {
			System.out
					.println("lecturaAsynch.NamingException....JHC *************************************** Error de JMS: "
							+ ne.getMessage());

		} catch (JMSException e) {
			System.out
					.println("lecturaAsynch.....JHC *************************************** Error de JMS: "
							+ e.getLinkedException().getMessage());
			System.out
					.println("lecturaAsynch.....JHC *************************************** Error de JMS: "
							+ e.getLinkedException().toString());
		}
	}
}
