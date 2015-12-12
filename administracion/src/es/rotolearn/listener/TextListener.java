package es.rotolearn.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
/*
import jhc.tema5.ejbtidw.ValesPedidoOperacionBeanRemote;*/

public class TextListener implements MessageListener {
/*
	@EJB(lookup = "ejb/ValesPedidoOperacionBean")
	private ValesPedidoOperacionBeanRemote _vales;*/

	public void onMessage(Message message) {

		TextMessage msg = null;
		String mensaje = null;
		String [] mensajes;
		try {
		
			if (message instanceof TextMessage) {
				msg = (TextMessage) message;
				System.out.println("Lectura AsÃ­ncrona-->: " + msg.getText());
				mensaje = msg.getText();
				mensajes = mensaje.split("-");
				//INSERTAR EN LA BASE DE DATOS CADA CAMPO
				
				
			} else {
				System.err.println("Message is not a TextMessage");
			}
		} catch (JMSException e) {
			System.err.println("JMSException in onMessage(): " + e.toString());
		} catch (Throwable t) {
			System.err.println("Exception in onMessage():" + t.getMessage());
			t.printStackTrace();
		}

	}


}
