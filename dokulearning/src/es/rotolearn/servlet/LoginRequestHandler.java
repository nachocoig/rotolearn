package es.rotolearn.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import entities.Usuario;
import es.rotolearn.javaBean.RegistroBean;

public class LoginRequestHandler implements RequestHandler {

	public String cargarImagen(byte []img, HttpServletRequest request, String nick) throws IOException{
		ServletContext context = request.getServletContext();
	    final String path = context.getRealPath("/images/im_usuarios");
	    String rutaCompleta = path + File.separator + nick + "_perfil.jpg";
	    try{
		    FileOutputStream fos = new FileOutputStream(rutaCompleta);
		    fos.write(img);
		    fos.close();
	    }catch (Exception e){
	    	System.out.println("Error al cargar la imagen de usuario");
	    }
		return rutaCompleta;
	}
	
	
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session;
		System.out.println("Handler login received the request");
		//HAY QUE AÑADIR/MODIFICAR PARA METERLE LA BBDD
		String ruta = null;
		String nick = request.getParameter("Nickname");
		String pass = String.valueOf(request.getParameter("Password").hashCode());

		// 1 Create the factory of Entity Manager
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");//ESTO ES CLAVE

		// 2 Create the Entity Manager
		EntityManager em = factory.createEntityManager();

		// 3 Get one EntityTransaction
		EntityTransaction tx = em.getTransaction();

		//Creamos el usuario a buscar en la BBDD
		Usuario aux = new Usuario();
		aux.setNickname(nick);
		aux.setPass(pass);
		Usuario resultado;
		//PARA BUSCAR EL USUARIO QUE HEMOS RECIBIDO POR PARAMETROS, si devuelve null no existe si devuelve algo es que existe
		tx.begin();//Comenzamos la transaccion

		try{
			resultado = (Usuario) em.createQuery("SELECT i FROM Usuario i WHERE i.nickname = ?1").setParameter(1, nick).getSingleResult();
			System.out.println("Comparo users " + resultado.getNickname() + " " + nick);
			System.out.println("Comparo pass " + resultado.getPass() + " " + pass);					
			if(resultado.getPass().equals(pass)){
				
					RegistroBean regbean = new RegistroBean();
					
					regbean.setId(resultado.getId());
					regbean.setNickName(resultado.getNickname());
					regbean.setApellido1(resultado.getApellido1());
					regbean.setApellido2(resultado.getApellido2());
					regbean.setTipo(resultado.getTipo());
					regbean.setEmail(resultado.getEmail());
					regbean.setDescripcion(resultado.getDescripcion());
					regbean.setIntereses(resultado.getIntereses());//por si no te has dado cuenta, esrto hay que cambiarlo.
					regbean.setTelefono(resultado.getTelefono());
					regbean.setNacimiento(resultado.getFecha_nac());
				    regbean.setDireccion(resultado.getDireccion());
				    regbean.setNombre(resultado.getNombre());
				    
				    cargarImagen(resultado.getImagen(), request, resultado.getNickname());
				    
					session = ((HttpServletRequest) request).getSession();
					session.setAttribute("logueado", "true");
					session.setAttribute("usuario",nick);
					session.setAttribute("perfil",regbean);						
					ruta = "index.jsp";
					
				}else{
					System.out.println("Pass incorrecta, no puede entrar");
					request.setAttribute("error", "pass");
					ruta = "login.jsp";
				 }
				
			
			em.close();
		}catch(javax.persistence.NoResultException e){//no existe el usuario
			em.close();
			request.setAttribute("error", "true");
			ruta = "login.jsp";
			
		}
		// 5 Close the manager

		
	
		
	
return ruta;
	}
}