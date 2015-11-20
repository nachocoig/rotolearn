package es.rotolearn.servlet;

import java.io.IOException;
import java.sql.Connection;
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

import entities.*;
import es.rotolearn.javaBean.RegistroBean;

public class CursoRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session;
		session = ((HttpServletRequest) request).getSession();
		RegistroBean user = (RegistroBean) session.getAttribute("perfil");
		
		String ruta = "profes_crearcurso.jsp";
		Usuario uAux = new Usuario();
		Curso aux = new Curso();
		uAux.setNickname(user.getNickName());
		
		aux.setUsuario(uAux);
		aux.setTitulo(request.getParameter("titulo"));
		aux.setPrecio(Integer.parseInt(request.getParameter("precio")));
		aux.setHoras(Integer.parseInt(request.getParameter("horas")));
		aux.setDescripcion(request.getParameter("descripcion"));
		aux.setDificultad(request.getParameter("dificultad"));
		aux.setDestacado("NO");
		aux.setValidado("NO");
		aux.setImagen(request.getParameter("imagen"));
		aux.setCategoria(request.getParameter("categoria"));
		aux.setEmail_paypal(request.getParameter("paypal"));
		

		// 1 Create the factory of Entity Manager
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");

		// 2 Create the Entity Manager
		EntityManager em = factory.createEntityManager();

		// 3 Get one EntityTransaction and start it
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		em.persist(aux);

		// 4 Commmit the transaction
		tx.commit();

		// 5 Close the manager
		em.close();

		/*
		HttpSession session;
		session = ((HttpServletRequest) request).getSession();
		RegistroBean user = (RegistroBean) session.getAttribute("perfil");
		
		//Insercion a BBDD con DataSource
		System.out.println("Vamos a probar a hacer la insercion por DATASOURCE");
		
		InitialContext miInitialContext;
		DataSource miDS;
		try{
			miInitialContext = new InitialContext();

			miDS = (DataSource) miInitialContext.lookup("RotolearnJNDI");

			Connection conexion = miDS.getConnection();

			Statement myStatement = conexion.createStatement();
			//FALTA HACER QUE EL PROFESOR QUE CREA EL CURSO SEA EL DEL BEAN
			myStatement.executeUpdate("INSERT INTO CURSO VALUES ('"+request.getParameter("titulo")+"','"+user.getNickName()+"','"+request.getParameter("precio")+"','"+request.getParameter("horas")+"','"+request.getParameter("paypal")+"','"+request.getParameter("dificultad")+"','"+request.getParameter("descripcion")+"','"+"NO"+"','"+"NO"+"','"+request.getParameter("categoria")+"', '"+request.getParameter("imagen")+"')");

			myStatement.close();
			conexion.close();
			request.setAttribute("curso", "si");
			
		}catch (NamingException e) {
			e.printStackTrace();
			request.setAttribute("curso", "no");

		} catch (SQLWarning sqlWarning) {
			while (sqlWarning != null) {
				System.out.println("Error: " + sqlWarning.getErrorCode());
				System.out.println("Descripcion: " + sqlWarning.getMessage());
				System.out.println("SQLstate: " + sqlWarning.getSQLState());
				sqlWarning = sqlWarning.getNextWarning();
				request.setAttribute("curso", "no");
			}
		} catch (SQLException sqlException) {
			while (sqlException != null) {
				System.out.println("Error: " + sqlException.getErrorCode());
				System.out.println("Descripcion: " + sqlException.getMessage());
				System.out.println("SQLstate: " + sqlException.getSQLState());
				sqlException = sqlException.getNextException();
				request.setAttribute("curso", "no");
			}
		}
		*/
		return ruta;
	}

}
