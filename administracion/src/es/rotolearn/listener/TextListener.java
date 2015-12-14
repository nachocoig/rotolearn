package es.rotolearn.listener;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Conciliacion;
import entities.Usuario;
/*
import jhc.tema5.ejbtidw.ValesPedidoOperacionBeanRemote;*/

public class TextListener implements MessageListener {
/*
	@EJB(lookup = "ejb/ValesPedidoOperacionBean")
	private ValesPedidoOperacionBeanRemote _vales;*/
	/*****PROBAR CON CUPONES****/
	public void onMessage(Message message) {

		TextMessage msg = null;
		String mensaje = null;
		String [] mensajes;
		String codop, codpe, valepromo=null;
		int profid, preciofinal, precioorig,descadmin=0;
		int dineroprofe, dineroempresa;
		float aux;
	 	Date dNow = new Date( );
	    SimpleDateFormat an = new SimpleDateFormat ("yyyy");
	    SimpleDateFormat me = new SimpleDateFormat ("mm");
	    String anio = an.format(dNow);
	    String mes = me.format(dNow);

		try {
			Conciliacion conc = new Conciliacion();
			Conciliacion conc2 = new Conciliacion();
			if (message instanceof TextMessage) {
				msg = (TextMessage) message;
				System.out.println("Lectura AsÃ­ncrona-->: " + msg.getText());
				mensaje = msg.getText();
				mensajes = mensaje.split("-");
				//String mensaje = codPed+ "-" +codOP+ "-" +request.getParameter("totalprecio")+ "-" +request.getParameter("valePromocional")+ "-" +request.getParameter("valeAdmin")+ "-" +request.getParameter("precioOriginal")+ "-" +request.getParameter("profesor");
				System.out.println("Copiando parametros");
				
				//Sacando datos
				codpe = mensajes[0];
				codop = mensajes[1];
				aux = Float.valueOf(mensajes[2]);
				preciofinal = (int) aux;
				if(!mensajes[3].equals("null")){
					valepromo = mensajes[3];
				}
				if(!mensajes[4].equals("null")){
					descadmin = Integer.parseInt(mensajes[4]);
				}
				precioorig= Integer.parseInt(mensajes[5]);
				profid= Integer.parseInt(mensajes[6]);
				
				//AÑADIMOS DATOS PARA PROFESOR
				conc.setCodOp(codop);
				conc.setCodPed(codpe);
				Usuario cobrador = new Usuario();
				cobrador.setId(profid);
				conc.setUsuario(cobrador);
				if(valepromo!=null){
					conc.setDescuento(valepromo);
					System.out.println(Integer.parseInt(valepromo.substring(Math.max(valepromo.length() - 2, 0))));
					dineroprofe = (int) ((int) precioorig*0.7*(1-Integer.parseInt(valepromo.substring(Math.max(valepromo.length() - 2, 0)))));
				}else{
					dineroprofe = (int) (precioorig*0.7);
				}
				conc.setImporte(dineroprofe);
				conc.setPagado("NO");
				conc.setAnio(anio);
				conc.setMes(mes);
				
				
				//AÑADIMOS DATOS PARA EMPRESA
				conc2.setCodOp(codop);
				conc2.setCodPed(codpe);
				Usuario empresa = new Usuario();
				empresa.setId(1);/****OJO EMPRESA ID 0***/
				conc2.setUsuario(empresa);
				if(descadmin!=0){
					dineroempresa = (int) (0.3*precioorig*(1-(descadmin/100)));
					conc2.setPromocion(descadmin);
					
				}else{
					dineroempresa = (int) (0.3*precioorig);
					
				}
				conc2.setImporte(dineroempresa);
				conc2.setPagado("NO");
				conc2.setAnio(anio);
				conc2.setMes(mes);
				
				//INSERTAR EN LA BASE DE DATOS CADA CAMPO
				System.out.println("ENtro a insertar");
				EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");
				EntityManager em = factory.createEntityManager();
				em.getTransaction().begin();
				em.persist(conc);
				em.persist(conc2);
				em.getTransaction().commit();
				System.out.println("Salgo persist");
				em.close();
				//92668751	
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
