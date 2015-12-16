package es.rotolearn.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;

import entities.Conciliacion;
import entities.Curso;
import entities.Usuario;

public class ConciliacionProfesorRequestHandler implements RequestHandler{

	@Override
	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		Conciliacion concil  = new Conciliacion();
		int recuento = 0;
		int profeactual, primeravez=0;
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		List <Conciliacion> lista = em.createQuery("SELECT i FROM Conciliacion i WHERE i.usuario.id NOT IN (1) AND i.pagado='NO' ORDER BY i.usuario.id ASC").getResultList();// 1 es el id del usuario empresa
		Date dNow = new Date( );
	    SimpleDateFormat an = new SimpleDateFormat ("yyyy");
	    SimpleDateFormat me = new SimpleDateFormat ("MM");
	    String anio = an.format(dNow);
	    String mes = me.format(dNow);
		Iterator<Conciliacion> d = lista.iterator();
		while(d.hasNext()){
			if(primeravez==0){
				concil=d.next();
				primeravez=1;
			}
			profeactual = concil.getUsuario().getId();
			while(profeactual == concil.getUsuario().getId() && d.hasNext()){
					recuento = recuento + concil.getImporte();					
					concil=d.next();
			}
			WebTarget wt = client.target("http://localhost:8080/Banco/ws");
			int result = Integer.parseInt(wt.path("codigo").path("conciliacionProfesor").path(Integer.toString(recuento)).path(anio).path(mes).request().accept(MediaType.TEXT_PLAIN).get(String.class));
			em.createQuery("UPDATE Conciliacion i SET i.pagado='SI' WHERE i.usuario.id="+profeactual+" AND i.pagado='NO'").executeUpdate();
		}
		
		em.getTransaction().commit();
		em.close();
		return "admin_index.jsp";
	}

}
