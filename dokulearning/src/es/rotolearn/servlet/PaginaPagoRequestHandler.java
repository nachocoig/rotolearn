package es.rotolearn.servlet;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Curso;
import entities.Descuento;
import entities.Promocion;

public class PaginaPagoRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ruta = "pagos.jsp";
		
		//Recogemos los datos que nos pueden pasar cuando nos llamen
		int id_curso = Integer.parseInt(request.getParameter("cursoCompra"));
		String tipo = request.getParameter("tipo");
		
		//Conexion con JPA
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		Curso verCurso;
		//Obtenemos los datos del curso
		try{
			verCurso = em.find(Curso.class, id_curso);
			request.setAttribute("curso", verCurso);
			
			double total = verCurso.getPrecio();
			
			List<Promocion> desc = verCurso.getPromocions();
			if(!desc.isEmpty()){//Quiere decir que existe una rebaja del admin
				int precio = verCurso.getPrecio();
				double descuento = desc.get(0).getDescuento();//Puede ser [0,30]
				
				double rebaja = Math.rint((precio*(descuento/100))*100)/100;
				total = Math.rint((precio-rebaja)*100)/100;		
								
				request.setAttribute("descuento", ""+desc.get(0).getDescuento());
				request.setAttribute("rebaja", String.valueOf(rebaja));
			}
			
			if(tipo != null){
				if(tipo.equals("valeDesc")){
					String cuponDesc = request.getParameter("Cuponazo");
					
					try{
						Descuento cuponDescuento = (Descuento) em.createQuery("SELECT i FROM Descuento i WHERE i.curso = ?1").setParameter(1, verCurso).getSingleResult();
						
						if(cuponDescuento!=null){
							
							if(cuponDescuento.getCupon().equals(cuponDesc)){
								int precio = verCurso.getPrecio();
								String cuponazo = cuponDesc.substring(cuponDesc.length()-2);
							
								double cupo = (double) Integer.parseInt(cuponazo);
								double des = Math.rint((precio*(cupo/100))*100)/100;
								
								total = Math.rint((total-des)*100)/100;	
								
								request.setAttribute("valePromocional", cuponDesc);
								request.setAttribute("cuponVale", cuponazo);
								request.setAttribute("precioCupon", String.valueOf(des));
							
								
							}else{
							
							}
						}
						
					}catch(Exception e1){
						System.out.println("¡Error!" + e1.getMessage());
						
					}
				}
			}
			
			request.setAttribute("total", total);
			
		}catch(javax.persistence.NoResultException e){
			System.out.println("Descripcion: " + e.getMessage());  
		}
		
		
		em.close();
		return ruta;
	}

}
