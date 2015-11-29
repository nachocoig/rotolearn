package es.rotolearn.servlet;

import java.io.IOException;

import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DescuentoRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ruta = "mostrarDescuentos.form";
		
		String aux = request.getParameter("cursoDesc");
		String desc = request.getParameter("descuento");
		
		Curso curso = new Curso();
		curso.setId(Integer.parseInt(aux));
		Promocion promo = new Promocion();
		promo.setCurso(curso);
		promo.setDescuento(Integer.parseInt(desc));
		
		// 1 Create the factory of Entity Manager
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");

		// 2 Create the Entity Manager
		EntityManager em = factory.createEntityManager();

		// 3 Get one EntityTransaction and start it
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try{
			Promocion existe = (Promocion) em.createQuery("SELECT i FROM Promocion i WHERE i.curso= ?1").setParameter(1, curso).getSingleResult();
			request.setAttribute("desc", "no");
		}catch(Exception e){
			try{
				em.persist(promo);
				// 4 Commmit the transaction
				tx.commit();
				request.setAttribute("desc", "si");
			}catch(Exception e1){
				request.setAttribute("desc", "no");
				e1.printStackTrace();
			}
		}
		// 5 Close the manager
		em.close();
		
		return ruta;
	}

}
