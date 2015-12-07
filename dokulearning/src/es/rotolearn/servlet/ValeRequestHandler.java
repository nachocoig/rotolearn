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
import javax.servlet.http.HttpSession;

import entities.*;
import es.rotolearn.javaBean.RegistroBean;

public class ValeRequestHandler implements RequestHandler {

	/*
	 * Descripcion:
	 * Este Handler se encarga de recoger las peticiones que se realicen en la vista profes_listadodescuentos.jsp
	 * Y su funcion es crear vales de descuento si no existe un vale anterior para el curso y de eliminar los vales
	 * creados. En caso de no tener cursos creados o validados no se podra crear ningun vale.
	 */
	
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ruta = "profe_descuentos.form";
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		RegistroBean user = (RegistroBean) session.getAttribute("perfil");
		
		//Cogemos los atributos hidden para saber de que formulario nos llaman
		String ID = (String) request.getParameter("eliminar");
		String tipo = request.getParameter("tipo");
		
		//Conexion con JPA
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		if(tipo != null){//Quiere decir que me llaman del formulario de creacion del vale
			
			//Pillamos los datos del vale
			String curso = request.getParameter("cursoVale");
			int descuento = Integer.parseInt(request.getParameter("descuento"));
			int minCursos = Integer.parseInt(request.getParameter("minCursos"));//HAY QUE AGREGARLO A LA TABLA DE DESCUENTOS
			String validez = request.getParameter("validez");
			
			Date date = new Date();
			DateFormat dia = new SimpleDateFormat("yyyyMMdd");
			DateFormat hora = new SimpleDateFormat("hhmmssSSSa");
			
			String vale = tipo+dia.format(date)+hora.format(date)+String.format("%02d", descuento);;
			
			//Creamos el Descuento a guardar
			Descuento valeProfe = new Descuento();
			valeProfe.setTipo(tipo);
			valeProfe.setCupon(vale);
			Curso aux = new Curso();
			aux.setId(Integer.parseInt(curso));
			valeProfe.setCurso(aux);
			valeProfe.setValidez(validez);
			valeProfe.setProfesor(user.getNickName());
			
			try{
				Descuento existe = (Descuento) em.createQuery("SELECT i FROM Descuento i WHERE i.curso= ?1").setParameter(1, aux).getSingleResult();
				request.setAttribute("aviso", "NO/Tu vale no se ha podido crear porque el curso seleccionado ya tiene un vale de descuento");
			}catch(Exception e){
				try{
					em.persist(valeProfe);
					// 4 Commmit the transaction
					tx.commit();
					request.setAttribute("aviso", "SI/Tu vale descuento se ha creado correctamente");
				}catch(Exception e1){
					request.setAttribute("aviso", "NO/Tu vale no se ha podido crear");
					e1.printStackTrace();
				}
			}
		}else if(ID != null){//Quiere decir que nos llaman de eliminar vale
			
			int IDCurso = Integer.parseInt(ID);
			
			try{
				Descuento borrar = em.find(Descuento.class, IDCurso);
				try {
					em.remove(borrar);
					em.getTransaction().commit();
					request.setAttribute("aviso", "SI/Tu vale descuento se ha eliminado correctamente");
			    } catch (Exception e1) {
					System.out.println("Descripcion: " + e1.getMessage());
					request.setAttribute("aviso","NO/Tu vale no se ha podido eliminar");
				}
			}catch(javax.persistence.NoResultException e){ 
				request.setAttribute("aviso","NO/Tu vale no se ha podido eliminar");
				System.out.println("Descripcion: " + e.getMessage());  
			}	
		}else{
			System.out.println("Aqui no deberia de pasar nunca");
		}
		
		em.close();
	    
		return ruta;
	}

}
