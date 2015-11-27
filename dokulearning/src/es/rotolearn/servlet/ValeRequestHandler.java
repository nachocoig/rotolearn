package es.rotolearn.servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.*;

public class ValeRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ruta = "profes_listadodescuentos.jsp";
		
		//Pillamos los datos del vale
		String curso = request.getParameter("cursoVale");
		int descuento = Integer.parseInt(request.getParameter("descuento"));
		int minCursos = Integer.parseInt(request.getParameter("minCursos"));
		String validez = request.getParameter("validez");
		String tipo = request.getParameter("tipo");
		
		Date date = new Date();
		DateFormat dia = new SimpleDateFormat("yyyyMMdd");
		DateFormat hora = new SimpleDateFormat("hhmmssSSSa");
		
		String vale = tipo+dia.format(date)+hora.format(date)+String.format("%02d", descuento);;
		System.out.println("El vale de descuento es: "+vale);
		
		//Creamos el Descuento a guardar
		Descuento valeProfe = new Descuento();
		valeProfe.setTipo(tipo);
		valeProfe.setCupon(vale);
		Curso aux = new Curso();
		aux.setId(Integer.parseInt(curso));
		valeProfe.setCurso(aux);
		
		// 1 Create the factory of Entity Manager
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");

		// 2 Create the Entity Manager
		EntityManager em = factory.createEntityManager();

		// 3 Get one EntityTransaction and start it
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try{
			em.persist(valeProfe);
			// 4 Commmit the transaction
			tx.commit();
			request.setAttribute("cupon", "si");
		}catch(Exception e){
			request.setAttribute("cupon", "no");
			e.printStackTrace();
		}

		// 5 Close the manager
		em.close();
		
		
		/*request.getParameter("curso");
		request.getParameter("tipodescuento");
	    request.getParameter("cupon");
	    request.getParameter("descuento");
	    
	    request.setAttribute("cupon", "si");*/
	    
		return ruta;
	}

}
