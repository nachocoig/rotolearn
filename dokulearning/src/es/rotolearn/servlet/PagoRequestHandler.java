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
		
		//comprobar tarjeta
		if(request.getParameter("tarjeta").length()==20 && (request.getParameter("tarjeta").charAt(0)=='A' || request.getParameter("tarjeta").charAt(0)=='B') ){
			//correcto
		}else{
				
				
				return "pagos.jsp";
			  }
		
		RegistroBean user = new RegistroBean();
		HttpSession session;
		session = ((HttpServletRequest) request).getSession();
		user = (RegistroBean) session.getAttribute("perfil");
		int id_curso = Integer.parseInt(request.getParameter("cursoCompra"));
		//Conexion con JPA
				EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");
				EntityManager em = factory.createEntityManager();
				em.getTransaction().begin();
				/*try{
					CursoAlumno pet = new CursoAlumno();
					pet = em.createQuery("SELECT i FROM CursoAlumno i WHERE i. = ?1").setParameter(1, nick).getSingleResult();
				pet.
				}catch(Exception e3){
					
					System.out.println("HE CASCAO");
				}*/
				Curso verCurso;
				Usuario userio;
				CursoAlumno curAl;
				//Obtenemos los datos del curso
				try{
					System.out.println("Entro en el try");
					verCurso = em.find(Curso.class, id_curso);
					userio = em.find(Usuario.class, user.getId());
					CursoAlumnoPK prueba = new CursoAlumnoPK();
					prueba.setID_c(id_curso);
					prueba.setID_u(user.getId());
					System.out.println("Entro el find");
					curAl = em.find(CursoAlumno.class, prueba);
					System.out.println("Salgo del find");
					request.setAttribute("curso", verCurso);
					if(curAl!=null){
						System.out.println("YA ESTA INSCRITO");
						return "catalogo.form";
					}
				}catch(javax.persistence.NoResultException e){
					System.out.println("Descripcion: " + e.getMessage()); 
					return "pagos.jsp";

				}
		
		//1 . Pedir codigo de pedido
			//String codigoPedido
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
		System.out.println("Remote EJB: "+ codPed);
		
		//2 . Pedir codigo de operacion
		//System.out.println("ENTRO A PEDIR EL CODIGO");

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		
		WebTarget wt = client.target("http://localhost:8080/Banco/ws");
		
		
		String result = wt.path("codigo").path("operacion").path("300").path("A57575757").path(codPed).request().accept(MediaType.TEXT_PLAIN).get(String.class);
		System.out.println(result);
		//descomponer result OPERACION201512110409590902PM;300;ORDER777;201509110459902+0100
		String mensajes[] = result.split("-");
		
		//String codigoOp
		Curso aux = (Curso) request.getAttribute("curso");
		//3 . Escribir en la cola
		//String mensaje = codigpPedido + "-" + codigoOp + "-" + request.getAtributte("precio") + "-" + request.getAtributte("numbreCurso");
		String mensaje = codPed+mensajes[1]+"400"+verCurso.getTitulo();
		InteraccionJMS mq=new InteraccionJMS();
		/* CUANDO ESTE INTEGRADO EL CHAT HAY QUE PASARLE A ESCRITURA POR PARAMETRO EL ID DEL CURSO EN VEZ DE AAAAAAAA*/
		System.out.println("Me escriben esto:"+mensaje);
		mq.escrituraPago(mensaje);
		//4 . Insertar usuario en base de datos curso
		CursoAlumno nuevoalumno = new	CursoAlumno();
		CursoAlumnoPK pk = new	CursoAlumnoPK();
		pk.setID_c(id_curso);
		pk.setID_u(userio.getId());
		nuevoalumno.setCurso(verCurso);
		nuevoalumno.setUsuario(userio);
		nuevoalumno.setEstado("INCOMPLETO");//YOKSE
		nuevoalumno.setId(pk);
		//System.out.println("CURSOID "+id_curso +"USERID "+verCurso.getId());
		try{
		System.out.println("Intento persist");
		em.persist(nuevoalumno);
		em.getTransaction().commit();
		System.out.println("Salgo persist");
		em.close();
		}catch(Exception e){
			System.out.println("YA EXISTE EL USUARIO INSCRIBICIONADO");
			// AVISAR AL MENDIGO QUE YA ESTA INSCRITO
			return "catalogo.jsp";
		}
		return "index.jsp";
	}

}
