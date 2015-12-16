package es.rotolearn.servlet;

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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import entities.Admin;
import entities.Usuario;
import es.rotolearn.javabean.Registrobean;

public class LoginRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session;
		//HAY QUE AÑADIR/MODIFICAR PARA METERLE LA BBDD
		String ruta = "admin_index.jsp";
		String nick = request.getParameter("nick");
		String pass = String.valueOf(request.getParameter("pass").hashCode()); //hay que cambiar la contraseña de la base de datos para que se pueda entrar....
		System.out.println("Nick introducido: "+nick);
		System.out.println("Pass introducida: "+pass);
		
		
		//HAGO LA CONSULTA A LA BBDD
		// 1 Create the factory of Entity Manager
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");//ESTO ES CLAVE

		// 2 Create the Entity Manager
		EntityManager em = factory.createEntityManager();

		// 3 Get one EntityTransaction
		EntityTransaction tx = em.getTransaction();

		//Creamos el usuario a buscar en la BBDD
		Admin resultado;
		//PARA BUSCAR EL USUARIO QUE HEMOS RECIBIDO POR PARAMETROS, si devuelve null no existe si devuelve algo es que existe
		tx.begin();//Comenzamos la transaccion

		try{
			resultado = (Admin) em.createQuery("SELECT i FROM Admin i WHERE i.nickname = ?1").setParameter(1, nick).getSingleResult();
			System.out.println("Comparo users " + resultado.getNickname() + " " + nick);
			System.out.println("Comparo pass " + resultado.getPass() + " " + pass);					
			if(resultado.getPass().equals(pass)){
					Registrobean regbean = new Registrobean();
					regbean.setNickName(resultado.getNickname());
					regbean.setPass("FakePass");
					//System.out.println("Pass correcta, puede entrar");
					session = ((HttpServletRequest) request).getSession();
					session.setAttribute("logueado", "true");
					session.setAttribute("usuario",nick);
					session.setAttribute("perfil",regbean);											
				}else{
			System.out.println("Pass incorrecta, no puede entrar");
					request.setAttribute("error", "pass");
					ruta = "admin_login.jsp";
				 }
				
		}catch(javax.persistence.NoResultException e){//no existe el usuario
			request.setAttribute("error", "true");
			ruta = "admin_login.jsp";
			
		}
		em.close();
		
	
return ruta;
	}
}
