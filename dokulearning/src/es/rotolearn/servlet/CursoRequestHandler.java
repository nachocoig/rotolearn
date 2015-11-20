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
		uAux.setId(1);
		
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
		try{
			em.persist(aux);
			// 4 Commmit the transaction
			tx.commit();
			request.setAttribute("curso", "si");
		}catch(Exception e){
			request.setAttribute("curso", "no");
			e.printStackTrace();
		}

		// 5 Close the manager
		em.close();

		return ruta;
	}

}