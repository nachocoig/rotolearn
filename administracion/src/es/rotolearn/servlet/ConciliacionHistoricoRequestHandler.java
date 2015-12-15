package es.rotolearn.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Conciliacion;
import entities.Usuario;

public class ConciliacionHistoricoRequestHandler implements RequestHandler{

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String ruta = "historico_conciliacion.jsp";
		
		String mes = request.getParameter("mes");
		String ano = request.getParameter("ano");
		String profesor = request.getParameter("profesor");
		
		List<Conciliacion> con = null;
		ArrayList<Conciliacion> lcon = new ArrayList<Conciliacion>();
		
		Usuario profe = null;
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");//ESTO ES CLAVE
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		if(mes != null || ano != null || profesor != null){	
			if(mes != null){
				con = em.createQuery("SELECT i FROM Conciliacion i WHERE i.mes=?1").setParameter(1, mes).getResultList();
				if(con != null)
				for(int i = 0 ; i < con.size(); i++)
					lcon.add(con.get(i));
			}
			if(ano != null){
				con = em.createQuery("SELECT i FROM Conciliacion i WHERE i.anio= ?1").setParameter(1, ano).getResultList();
				if(con != null)
				for(int i = 0 ; i < con.size(); i++)
					if(!lcon.contains(con.get(i)))
						lcon.add(con.get(i));
			}
			if(profesor != null && !profesor.equals("")){
				profe = (Usuario) em.createQuery("SELECT i FROM Usuario i WHERE i.tipo= 'profe' AND i.nickname = ?1").setParameter(1, profesor).getSingleResult(); //buscamos los profesores que cumplan
				
				if(profe != null){
					con = em.createQuery("SELECT i FROM Conciliacion i WHERE i.usuario = ?1").setParameter(1, profe).getResultList();
					if(con != null)
					for(int i = 0 ; i < con.size(); i++)
						if(!lcon.contains(con.get(i)))
							lcon.add(con.get(i));
				}
			}
		}else{
			con = em.createQuery("SELECT i FROM Conciliacion i").getResultList();
			if(con != null)
			for(int i = 0 ; i < con.size(); i++)
				if(!lcon.contains(con.get(i)))
					lcon.add(con.get(i));
		}
		
		request.setAttribute("conciliaciones", lcon);
		return ruta;
	}

}
