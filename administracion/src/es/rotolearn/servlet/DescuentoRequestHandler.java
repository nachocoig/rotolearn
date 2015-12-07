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

	/*
	 * Descripcion:
	 * Este Handler se encarga de recoger las peticiones que se realicen en la vista admin_listadocupones.jsp
	 * Y su funcion es crear descuentos si no existe un descuento anterior para el curso y de eliminar los descuentos
	 * creados. En caso de no tener cursos creados o validados no se podra crear ningun descuento.
	 */
	
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ruta = "mostrarDescuentos.form";
		
		//Cogemos los atributos hidden para saber de que formulario nos llaman
		String ID = (String) request.getParameter("eliminar");
		String tipo = request.getParameter("tipo");
		
		//Conexion con JPA
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		if(tipo != null){//Quiere decir que me llaman del formulario de creacion del descuento
			String aux = request.getParameter("cursoDesc");
			String desc = request.getParameter("descuento");
			
			Curso curso = new Curso();
			curso.setId(Integer.parseInt(aux));
			Promocion promo = new Promocion();
			promo.setCurso(curso);
			promo.setDescuento(Integer.parseInt(desc));
			
			try{
				Promocion existe = (Promocion) em.createQuery("SELECT i FROM Promocion i WHERE i.curso= ?1").setParameter(1, curso).getSingleResult();
				request.setAttribute("aviso", "NO/El descuento no se ha podido crear porque el curso seleccionado ya tiene un descuento aplicado");
			}catch(Exception e){
				try{
					em.persist(promo);
					// 4 Commmit the transaction
					tx.commit();
					request.setAttribute("aviso", "SI/El descuento se ha creado correctamente");
				}catch(Exception e1){
					request.setAttribute("aviso", "NO/El desceunto no se ha podido crear");
					e1.printStackTrace();
				}
			}
		}else if(ID != null){//Quiere decir que nos llaman de eliminar descuento
		    
			int IDCurso = Integer.parseInt(ID);
		    
			try{
				Promocion borrar = em.find(Promocion.class, IDCurso);
				try {
					em.remove(borrar);
					em.getTransaction().commit();
					request.setAttribute("aviso", "SI/El descuento se ha eliminado correctamente");
			    } catch (Exception e1) {
					System.out.println("Descripcion: " + e1.getMessage());
					request.setAttribute("aviso","NO/El descuento no se ha podido eliminar");
				}
			}catch(javax.persistence.NoResultException e){ 
				request.setAttribute("aviso","NO/El descuento no se ha podido eliminar");
				System.out.println("Descripcion: " + e.getMessage());  
			}	
		}else{
			System.out.println("Aqui no deberia de pasar nunca");
		}
		
		
		// 5 Close the manager
		em.close();
		
		return ruta;
	}

}
