package es.rotolearn.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import codped.ValesPedidoOperacionBeanRemote;
import entities.Curso;
import entities.CursoAlumno;
import entities.CursoAlumnoPK;
import entities.Promocion;
import entities.Usuario;
import es.rotolearn.javaBean.*;

import org.glassfish.jersey.client.*;

public class PagoRequestHandler implements RequestHandler {
	private ValesPedidoOperacionBeanRemote _vales;

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RegistroBean user = new RegistroBean();
		HttpSession session;
		session = ((HttpServletRequest) request).getSession();
		user = (RegistroBean) session.getAttribute("perfil");
		int id_curso = Integer.parseInt(request.getParameter("cursoCompra"));
		//Conexion con JPA
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		Curso verCurso;
		Usuario userio;
		CursoAlumno curAl;
		
		//Obtenemos los datos del curso
		try{
			
			verCurso = em.find(Curso.class, id_curso);
			userio = em.find(Usuario.class, user.getId());
			
			CursoAlumnoPK prueba = new CursoAlumnoPK();
			prueba.setID_c(id_curso);
			prueba.setID_u(user.getId());
		
			curAl = em.find(CursoAlumno.class, prueba);
		
			request.setAttribute("curso", verCurso);
			if(curAl!=null ){
				if(!curAl.getEstado().equals("lista deseos")){
					
					/****** AVISARLE****/
					return "catalogo.form";
					
				}
				
			}
		}catch(javax.persistence.NoResultException e){
			System.out.println("Descripcion: " + e.getMessage()); 
			return "pagos.jsp";
	
		}
		
		//1 . Pedir codigo de pedido
			
		try {
			_vales = (ValesPedidoOperacionBeanRemote) InitialContext.doLookup("ejb/ValesPedidoOperacionBean");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			System.out.println("Traza: "+ e.getMessage());
			System.out.println("Traza: "+ e.getRootCause().toString());
			System.out.println("Traza: "+ e.getCause().toString());
			return "pagos.jsp";
		}
		String codPed = _vales.generacionCodigoPedido();
		
		
		//2 . Pedir codigo de operacion
		

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		
		WebTarget wt = client.target("http://localhost:8080/Banco/ws");
		

		
		String result = wt.path("codigo").path("operacion").path(request.getParameter("totalprecio")).path(request.getParameter("tarjeta")).path(codPed).request().accept(MediaType.TEXT_PLAIN).get(String.class);
		
		String [] aux1 = result.split("-");
		
		request.setAttribute("codigOP", aux1[2]);
		request.setAttribute("valor", aux1[1]);
		request.setAttribute("bank", aux1[0]);
		
		
		if(result.equalsIgnoreCase("error")){
			/*****************REVISAR CUANDO HAY ERROR*********************/
			return "index.jsp";
			
		}
		String mensajes[] = result.split("-");
		
		
		Curso aux = (Curso) request.getAttribute("curso");
		
		//3 . Escribir en la cola
		String mensaje = codPed+ "-" +mensajes[0]+ "-" +request.getParameter("totalprecio")+ "-" +request.getParameter("valePromocional")+ "-" +request.getParameter("valeAdmin")+ "-" +request.getParameter("precioOriginal")+ "-" +request.getParameter("profesor");
		InteraccionJMS mq=new InteraccionJMS();
				
		mq.escrituraPago(mensaje);
		//4 . Insertar usuario en base de datos curso
		if(curAl!=null && curAl.getEstado().equals("lista deseos")){
			CursoAlumnoPK cpk = new CursoAlumnoPK();
			cpk.setID_u(user.getId());
			cpk.setID_c(id_curso);
			em.createQuery("UPDATE CursoAlumno i SET i.estado='INCOMPLETO' WHERE i.id=?1").setParameter(1,cpk).executeUpdate();
			em.getTransaction().commit();
			em.close();
			return "recibo_pago.jsp";		
		}
		CursoAlumno nuevoalumno = new	CursoAlumno();
		CursoAlumnoPK pk = new	CursoAlumnoPK();
		pk.setID_c(id_curso);
		pk.setID_u(userio.getId());
		nuevoalumno.setCurso(verCurso);
		nuevoalumno.setUsuario(userio);
		nuevoalumno.setEstado("INCOMPLETO");
		nuevoalumno.setId(pk);
		try{
		
			em.persist(nuevoalumno);
			em.getTransaction().commit();
		
			em.close();
		}catch(Exception e){
			
			/******* AVISAR QUE YA ESTA INSCRITO*******/
			return "catalogo.jsp";
		}
		
		return "recibo_pago.jsp";
	}

}
