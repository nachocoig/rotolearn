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
	    	System.out.println("Error " + e.getMessage());
	    }
		return rutaCompleta;
	}
	
	
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		HttpSession session;
		
	
		String ruta = null;
		String nick = request.getParameter("Nickname");
		String pass = String.valueOf(request.getParameter("Password").hashCode());

		//Creamos el usuario a buscar en la BBDD
		Usuario aux = new Usuario();
		aux.setNickname(nick);
		aux.setPass(pass);
		Usuario resultado;
				
		//Conexion con JPA
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try{
			resultado = (Usuario) em.createQuery("SELECT i FROM Usuario i WHERE i.nickname = ?1").setParameter(1, nick).getSingleResult();		
			if(resultado.getPass().equals(pass)){
				
					RegistroBean regbean = new RegistroBean();
					
					regbean.setId(resultado.getId());
					regbean.setNickName(resultado.getNickname());
					regbean.setApellido1(resultado.getApellido1());
					regbean.setApellido2(resultado.getApellido2());
					regbean.setTipo(resultado.getTipo());
					regbean.setEmail(resultado.getEmail());
					regbean.setDescripcion(resultado.getDescripcion());
					regbean.setIntereses(resultado.getIntereses());
					regbean.setTelefono(resultado.getTelefono());
					regbean.setNacimiento(resultado.getFecha_nac());
				    regbean.setDireccion(resultado.getDireccion());
				    regbean.setNombre(resultado.getNombre());
				    if(resultado.getImagen()==null){
				    	regbean.setImagen(false);
				    }
				    else{
				    	regbean.setImagen(true);
				    }
				    cargarImagen(resultado.getImagen(), request, resultado.getNickname());
				    
					session = ((HttpServletRequest) request).getSession();
					session.setAttribute("logueado", "true");
					session.setAttribute("usuario",nick);
					session.setAttribute("perfil",regbean);	
					request.setAttribute("tipo", "index");
					ruta = "Notificacion.form";
					
				}else{
					
					request.setAttribute("error", "pass");
					request.setAttribute("tipo", "index");
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