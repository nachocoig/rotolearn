/*
 * Creado el 11-nov-14
 * https://docs.oracle.com/javaee/5/tutorial/doc/bncfa.html#bncfl
 */
package es.rotolearn.servlet;

import java.util.Enumeration;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.NamingException;


public class InteraccionJMS {
	private javax.jms.ConnectionFactory factory = null;
	private javax.naming.InitialContext contextoInicial = null;
	private javax.jms.Destination cola = null;
	private javax.jms.Connection Qcon = null;
	private javax.jms.Session QSes = null;
	private javax.jms.MessageProducer Mpro = null;
	private javax.jms.MessageConsumer Mcon = null;


	public void escrituraJMS(String mensaje,  String selector, String user) {
	    
		try {
			
			contextoInicial = new javax.naming.InitialContext();

				factory = (javax.jms.ConnectionFactory) contextoInicial
						.lookup("jms/cf1.1");
				cola = (javax.jms.Destination) contextoInicial
						.lookup("jms/queue1.1");
			Qcon = factory.createConnection();
			QSes = Qcon
					.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);

			Mpro = QSes.createProducer(cola);
			Mpro.setTimeToLive(60000);//60 segundos de vida los mensajes
			javax.jms.TextMessage men = QSes.createTextMessage();

			men.setText(user+": "+mensaje); 
			men.setJMSCorrelationID(selector);
			Qcon.start();
			Mpro.send(men);

			this.Mpro.close();
			this.QSes.close();
			this.Qcon.close();

		} catch (javax.jms.JMSException e) {
			System.out
					.println(".....JHC *************************************** Error de JMS: "
							+ e.getLinkedException().getMessage());
			System.out
					.println(".....JHC *************************************** Error de JMS: "
							+ e.getLinkedException().toString());
		} catch (Exception e) {
			System.out
					.println("JHC *************************************** Error Exception: "
							+ e.getMessage());
		}

	}
	public void escrituraPago(String mensaje) {
	    /* HAY QUE PONER QUE LEA DEL CORRELATIONID QUE SE PASA POR PARAMETRO*/
        /* HAY QUE CONCATENAR AL MENSAJE EL NICK DEL USUARIO OBTENIDO DEL BEAN*/
		
		try {

			contextoInicial = new javax.naming.InitialContext();

				factory = (javax.jms.ConnectionFactory) contextoInicial
						.lookup("jms/cf1.1");
				cola = (javax.jms.Destination) contextoInicial
						.lookup("jms/queuePagos");
			Qcon = factory.createConnection();
			QSes = Qcon
					.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);

			Mpro = QSes.createProducer(cola);
			javax.jms.TextMessage men = QSes.createTextMessage();

			men.setText(mensaje); //PONER EL NOMBRE DEL BEAN
			Qcon.start();
			Mpro.send(men);

			this.Mpro.close();
			this.QSes.close();
			this.Qcon.close();

		} catch (javax.jms.JMSException e) {
			System.out
					.println(".....JHC *************************************** Error de JMS: "
							+ e.getLinkedException().getMessage());
			System.out
					.println(".....JHC *************************************** Error de JMS: "
							+ e.getLinkedException().toString());
		} catch (Exception e) {
			System.out
					.println("JHC *************************************** Error Exception: "
							+ e.getMessage());
		}

	}

}