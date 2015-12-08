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

import entities.Curso;
import entities.CursoAlumno;
import entities.ProfesorAsociado;
import entities.ProfesorAsociadoPK;
import entities.Usuario;
import es.rotolearn.controlImagenes.MultipartRequest;

public class AdministrarCursoRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String ruta = "profes_administrarcurso.jsp";
		
		MultipartRequest mr = new MultipartRequest(request);

		String tipo = mr.getParameterValues("tipo")[0];
		int idCurso = Integer.parseInt(mr.getParameterValues("curso")[0]);
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");//ESTO ES CLAVE
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		/*
		 * Buscamos el curso 
		 */
		Curso editar = em.find(Curso.class, idCurso);
		
		/*
		 * Buscamos los profesores asociados al curso
		 */
		List <ProfesorAsociado> asociados = null;
		List <ProfesorAsociado> asociadosSinValidar = null;  
		List <CursoAlumno> alumnos = null;

		ArrayList <Usuario> lasociados = new ArrayList <Usuario> ();
		ArrayList <Usuario> lasociadosSinValidar = new ArrayList <Usuario> ();
		
		ArrayList <Usuario> lalumnos = new ArrayList <Usuario> ();

		if(tipo.equals("editarDescripcion")){ //Para modificar la descripcion del curso
			System.out.println("Modificar descripcion.");
			try{
				
				editar.setDescripcion(mr.getParameterValues("descripcion")[0]);
				em.getTransaction().commit();
				request.setAttribute("aviso","SI/Descripci&oacute;n modificada correctamente");
			}catch(Exception e){
				//e.printStackTrace();
				request.setAttribute("aviso","NO/La descripci&oacute;n no ha podido ser modificada");
			}
			request.setAttribute("actual", "info");
		}else if(tipo.equals("eliminarAsociado")){ //para eliminar un profesor asociado
			int idProfesor = Integer.parseInt(mr.getParameterValues("asociado")[0]);
			em.createQuery("DELETE FROM ProfesorAsociado i WHERE i.id.ID_c = ?1 AND i.id.ID_p = ?2").setParameter(1, idCurso).setParameter(2, idProfesor).executeUpdate();
			em.getTransaction().commit();
			request.setAttribute("aviso","SI/Profesor asociado eliminado correctamente");
			request.setAttribute("actual", "profes");
		}else if(tipo.equals("agregarAsociado")){ //agregar de la lista de tooodos los profesores 
			String profesor = mr.getParameterValues("asociado")[0];
			try{
				//buscamos ese profesor
				Usuario asociado = (Usuario) em.createQuery("SELECT i FROM Usuario i WHERE i.nickname = ?1 AND i.tipo='PROFE' ").setParameter(1, profesor).getSingleResult();
				if(asociado != null){
					ProfesorAsociado nuevo = new ProfesorAsociado();
					ProfesorAsociadoPK npk = new ProfesorAsociadoPK();
					System.out.println("El ID del curso es el siguiente:--________________--------____--__"+idCurso);
					npk.setID_c(idCurso);
					npk.setID_p(asociado.getId());
					nuevo.setCurso(editar);
					nuevo.setUsuario(asociado);
					nuevo.setId(npk);
					nuevo.setValidado("NO");
					em.persist(nuevo);
					em.getTransaction().commit();
					request.setAttribute("aviso","SI/El profesor "+profesor+" se ha asociado al curso");
				}else{
					request.setAttribute("aviso","NO/No se ha encontrado ning&uacute;n usuario con ese nick");
				}

			}catch(Exception e){
				e.printStackTrace();
			}
			request.setAttribute("actual", "profes");
		}else if(tipo.equals("validarAsociado")){ //validar un profesor no validado
			int idProfesor = Integer.parseInt(mr.getParameterValues("asociado")[0]);
			em.createQuery("UPDATE ProfesorAsociado i SET i.validado ='SI' WHERE i.id.ID_c = ?1 AND i.id.ID_p = ?2").setParameter(1, idCurso).setParameter(2, idProfesor).executeUpdate();
			em.getTransaction().commit();
			request.setAttribute("aviso","SI/Profesor asociado validado correctamente");
			request.setAttribute("actual", "profes");
		}else if(tipo.equals("eliminarAlumno")){
			int idAlumno = Integer.parseInt(mr.getParameterValues("alumno")[0]);
			em.createQuery("DELETE FROM CursoAlumno i WHERE i.id.ID_c = ?1 AND i.id.ID_u = ?2").setParameter(1, idCurso).setParameter(2, idAlumno).executeUpdate();
			em.getTransaction().commit();
			request.setAttribute("aviso","SI/Usuario eliminado correctamente");
			request.setAttribute("actual", "alumn");
		}else if(tipo.equals("eliminar")){
			em.createQuery("DELETE FROM Curso i WHERE i.id = ?1").setParameter(1, idCurso).executeUpdate();
			em.getTransaction().commit();
			//request.setAttribute("aviso","SI/Curso eliminado correctamente");
			return "verCursosProfe.form";
		}
		
		
		asociados = em.createQuery("SELECT i FROM ProfesorAsociado i WHERE i.id.ID_c = ?1 AND i.validado='SI'").setParameter(1, idCurso).getResultList();	
		asociadosSinValidar = em.createQuery("SELECT i FROM ProfesorAsociado i WHERE i.id.ID_c = ?1 AND i.validado='NO'").setParameter(1, idCurso).getResultList();	
		alumnos = em.createQuery("SELECT i FROM CursoAlumno i WHERE i.id.ID_c = ?1").setParameter(1, idCurso).getResultList();	
		
		for(int i = 0; i < asociados.size(); i++){
			lasociados.add(asociados.get(i).getUsuario());
		}		
		for(int i = 0; i < asociadosSinValidar.size(); i++){
			lasociadosSinValidar.add(asociadosSinValidar.get(i).getUsuario());
		}		
		for(int i = 0; i < alumnos.size(); i++){
			lalumnos.add(alumnos.get(i).getUsuario());
		}
		
		Curso a = em.find(Curso.class, idCurso);
		
		request.setAttribute("alumnos", lalumnos);
		request.setAttribute("curso",a);
		request.setAttribute("asociados", lasociados);
		request.setAttribute("asociadosSinValidar", lasociadosSinValidar);

		em.close();
	 return ruta;
	}

}
