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

/**
 * @author Jes�s Hernando Corrochano
 */

public class InteraccionJMS {
	private javax.jms.ConnectionFactory factory = null;
	private javax.naming.InitialContext contextoInicial = null;
	private javax.jms.Destination cola = null;
	private javax.jms.Connection Qcon = null;
	private javax.jms.Session QSes = null;
	private javax.jms.MessageProducer Mpro = null;
	private javax.jms.MessageConsumer Mcon = null;


	public void escrituraJMS(String mensaje,  String selector) {
		
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

			men.setText(mensaje); //PONER EL NOMBRE DEL BEAN
			men.setJMSCorrelationID("RUSH");
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

/*	public String lecturaJMS(int opcion, String strSelectorPasado) {

		StringBuffer mSB = new StringBuffer(64);
		try {
			contextoInicial = new javax.naming.InitialContext();

			switch (opcion) {
			case 1:
				factory = (javax.jms.ConnectionFactory) contextoInicial
						.lookup(InformacionProperties.getQCF());
				cola = (javax.jms.Destination) contextoInicial
						.lookup(InformacionProperties.getQueue());
				break;
			case 2:
				factory = (javax.jms.ConnectionFactory) contextoInicial
						.lookup("java:comp/env/"
								+ InformacionProperties.getQCF() + "Ref");
				cola = (javax.jms.Destination) contextoInicial
						.lookup("java:comp/env/"
								+ InformacionProperties.getQueue() + "Ref");
				break;
			default:
				factory = (javax.jms.ConnectionFactory) contextoInicial
						.lookup(InformacionProperties.getQCF());
				cola = (javax.jms.Destination) contextoInicial
						.lookup(InformacionProperties.getQueue());
				break;
			}

			Qcon = factory.createConnection();

			QSes = Qcon
					.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);

			String sSelector = "JMSCorrelationID = '"
					+ strSelectorPasado.trim() + "'";

			if (strSelectorPasado.equals("")) {
				Mcon = QSes.createConsumer(cola);
			} else {
				Mcon = QSes.createConsumer(cola, sSelector);

			}
			Qcon.start();
			StringBuffer _sB = new StringBuffer(32);
			_sB.append("<br>");
			QueueBrowser browser = QSes.createBrowser((Queue) cola, "AAAAAAAA");
			System.out.println("Try cuatro");

			@SuppressWarnings("rawtypes")
			Enumeration msgs = browser.getEnumeration();

			while (msgs.hasMoreElements()) {
				Message tempMsg = (Message) msgs.nextElement();
				_sB.append(tempMsg);
			}
			System.out.println("OJOJOJOJO"+_sB.toString());
			
			Message mensaje = null;

			while (true) {
				mensaje = Mcon.receive(100);
				if (mensaje != null) {
					if (mensaje instanceof TextMessage) {
						TextMessage m = (TextMessage) mensaje;
						mSB.append(m.getText() + " </br>");
					} else {
						// JHC ************ No es del tipo correcto
						break;
					}
				} else // NO existe mensaje, mensaje es null
				{
					break;
				}

			}
			this.Mcon.close();
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

		return mSB.toString();

	}*/

}
