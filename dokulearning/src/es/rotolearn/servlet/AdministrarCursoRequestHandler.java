package es.rotolearn.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Curso;
import entities.CursoAlumno;
import entities.Leccion;
import entities.Material;
import entities.ProfesorAsociado;
import entities.ProfesorAsociadoPK;
import entities.Seccion;
import entities.Usuario;
import es.rotolearn.controlImagenes.MultipartRequest;
import es.rotolearn.controlImagenes.UploadedFile;

@MultipartConfig(location="/tmp")
public class AdministrarCursoRequestHandler implements RequestHandler {

	public int cargarFichero(byte []fichero, HttpServletRequest request, int idMaterial, String tipo) throws IOException{
		ServletContext context = request.getServletContext();
	    final String path = context.getRealPath("/materiales/");
	    
	    String rutaCompleta = path + File.separator + idMaterial + "_mat."+tipo;
		//File fichero = new File(rutaCompleta);
		//if(!fichero.exists()){
			//fichero.delete();
		    try{
			    FileOutputStream fos = new FileOutputStream(rutaCompleta);
			    fos.write(fichero);
			    fos.close();
			    return 0;
		    }catch (Exception e){
		    	System.out.println("Error al cargar la imagen de usuario");
		    	e.printStackTrace();
		    }
		//}else{
		//	fichero.delete();
		//	System.out.println("Ya existe? WTF?");
		//	System.out.println("Se supone que existe '"+idCurso+"_curso.jpg' en "+rutaCompleta);
		//}
		System.out.println("termino de cargar la imagen, por donde no debo");
		return -1;
	}
	
public byte []obtenerFicheroBytes(HttpServletRequest request, UploadedFile material, int leccionId) throws ServletException, IOException {
		
		ServletContext context = request.getServletContext();
	    
		final String path = context.getRealPath("/materiales/");
	    byte ficheroTotal[] = null;
	    
	    if(material == null)
	    	System.out.println("NULLLLLLLLLLLLLLLLLLLLLLL");

	    //final String fileName = foto.getFileName();

	    OutputStream out = null;
	    InputStream filecontent = null;
	    File file = null;
        FileInputStream fis = null;
        
	    try {
	        
	    	out = new FileOutputStream(new File(path + File.separator + leccionId + "_mat."+material.getFileExt()));
	        out.write(material.getData());

			file = new File(path + File.separator + leccionId + "_mat."+material.getFileExt());
	        fis = new FileInputStream(file);
			
	        
	        ficheroTotal = new byte[(int)file.length()];
			fis.read(ficheroTotal);
			file.delete();
			
	    } catch (Exception e) {
	        System.out.println("Error al crear el material en el servidor. Motivo: ");
	        e.printStackTrace();
	    } finally {
	        if (out != null) 
	            out.close();
	        if (filecontent != null) 
	            filecontent.close();	
			if(fis != null)
				fis.close();
	    }
	    return ficheroTotal;
	}
	
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
		List <Seccion> secciones = null;
		List <Leccion> lecciones = null;
		List <Material> materiales = null;

		
		ArrayList <Usuario> lasociados = new ArrayList <Usuario> ();
		ArrayList <Usuario> lasociadosSinValidar = new ArrayList <Usuario> ();
		
		ArrayList <Usuario> lalumnos = new ArrayList <Usuario> ();
		ArrayList <Seccion> lsecciones = new ArrayList <Seccion> ();
		ArrayList <Leccion> llecciones = new ArrayList <Leccion> ();
		ArrayList <Material> lmateriales = new ArrayList <Material> ();

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
		}else if(tipo.equals("eliminarAsociado") || tipo.equals("denegarAsociado")){ //para eliminar un profesor asociado
			int idProfesor = Integer.parseInt(mr.getParameterValues("asociado")[0]);
			em.createQuery("DELETE FROM ProfesorAsociado i WHERE i.id.ID_c = ?1 AND i.id.ID_p = ?2").setParameter(1, idCurso).setParameter(2, idProfesor).executeUpdate();
			em.getTransaction().commit();
			request.setAttribute("aviso","SI/Profesor asociado eliminado correctamente");
			request.setAttribute("actual", "profes");
			if(tipo.equals("denegarAsociado")){ ruta = "/perfil.form";}
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
			ruta = "/perfil.form";
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
		}else if(tipo.equals("crearSeccion")){
			String nombreSeccion = mr.getParameterValues("nombre")[0];
			Seccion nuevaSeccion = new Seccion();
			nuevaSeccion.setCurso(editar);
			nuevaSeccion.setNombre(nombreSeccion);
			em.persist(nuevaSeccion);
			em.getTransaction().commit();
			request.setAttribute("aviso","SI/Secci&oacute;n creada correctamente.");
			request.setAttribute("actual", "material");
		}else if(tipo.equals("crearLeccion")){
			
			String nombreLeccion = mr.getParameterValues("nombre")[0];
			String descripcion = mr.getParameterValues("descripcion")[0];
			int seccionId = Integer.parseInt(mr.getParameterValues("seccion")[0]);
			Leccion nuevaLeccion = new Leccion();
			
			Seccion aux = new Seccion();
			
			aux.setId(seccionId);
			
			nuevaLeccion.setSeccion(aux);
			nuevaLeccion.setNombre(nombreLeccion);
			nuevaLeccion.setDescripcion(descripcion);
			
			em.persist(nuevaLeccion);
			em.getTransaction().commit();
			request.setAttribute("aviso","SI/Lecci&oacute;n creada correctamente.");
			request.setAttribute("actual", "material");
		}else if(tipo.equals("crearMaterial")){
			Collator comparaExt = Collator.getInstance();
			comparaExt.setStrength(Collator.SECONDARY); // para que tenga en cuenta que a = A.
			
			String nombreMaterial = mr.getParameterValues("nombre")[0];
			int leccionId = Integer.parseInt(mr.getParameterValues("leccion")[0]);
			UploadedFile fichero = (UploadedFile) mr.getUploadedFile("fichero");
			
			String ext = fichero.getFileExt();
			
			if( comparaExt.equals(ext, "pdf") || comparaExt.equals(ext, "txt") || comparaExt.equals(ext, "doc")
			 || comparaExt.equals(ext, "docx") || comparaExt.equals(ext, "xl") || comparaExt.equals(ext, "xls")
			 || comparaExt.equals(ext, "xlsx") || comparaExt.equals(ext, "xlsm") || comparaExt.equals(ext, "txt")
			 || comparaExt.equals(ext, "mp4") || comparaExt.equals(ext, "m4v") || comparaExt.equals(ext, "mp3")){
			
				Leccion leccion = new Leccion();
				Material nuevoMaterial = new Material();
				
				leccion.setId(leccionId);
				
				nuevoMaterial.setLeccion(leccion);
				nuevoMaterial.setNombre(nombreMaterial);
				nuevoMaterial.setContenido(obtenerFicheroBytes(request, fichero, leccionId));
				nuevoMaterial.setTipo(fichero.getFileExt());
				em.persist(nuevoMaterial);
				em.getTransaction().commit();
				request.setAttribute("aviso","SI/Material subido correctamente.");
			}else{
				request.setAttribute("aviso", "NO/El tipo de fichero que has intentado subir no es v&aacute;lido.");
			}
			request.setAttribute("actual", "material");
		}
		
		
		asociados = em.createQuery("SELECT i FROM ProfesorAsociado i WHERE i.id.ID_c = ?1 AND i.validado='SI'").setParameter(1, idCurso).getResultList();	
		asociadosSinValidar = em.createQuery("SELECT i FROM ProfesorAsociado i WHERE i.id.ID_c = ?1 AND i.validado='NO'").setParameter(1, idCurso).getResultList();	
		alumnos = em.createQuery("SELECT i FROM CursoAlumno i WHERE i.id.ID_c = ?1").setParameter(1, idCurso).getResultList();	
		secciones = em.createQuery("SELECT i FROM Seccion i WHERE i.curso.id = ?1").setParameter(1, idCurso).getResultList();

		for(int i = 0; i < asociados.size(); i++){
			lasociados.add(asociados.get(i).getUsuario());
		}		
		for(int i = 0; i < asociadosSinValidar.size(); i++){
			lasociadosSinValidar.add(asociadosSinValidar.get(i).getUsuario());
		}		
		for(int i = 0; i < alumnos.size(); i++){
			lalumnos.add(alumnos.get(i).getUsuario());
		}
		for(int i = 0; i < secciones.size(); i++){
			lsecciones.add(secciones.get(i));
			lecciones = em.createQuery("SELECT i FROM Leccion i WHERE i.seccion.id = ?1").setParameter(1, secciones.get(i).getId()).getResultList();
			for(int j = 0; j < lecciones.size(); j++){
				llecciones.add(lecciones.get(j));
				materiales = em.createQuery("SELECT i FROM Material i WHERE i.leccion.id = ?1").setParameter(1, lecciones.get(j).getId()).getResultList();
				for(int k = 0; k < materiales.size();k++){
					lmateriales.add(materiales.get(k));
					cargarFichero(materiales.get(k).getContenido(), request, materiales.get(k).getId(), materiales.get(k).getTipo());
				}
			}

		}
		
		Curso a = em.find(Curso.class, idCurso);
		
		request.setAttribute("alumnos", lalumnos);
		request.setAttribute("curso",a);
		request.setAttribute("asociados", lasociados);
		request.setAttribute("asociadosSinValidar", lasociadosSinValidar);
		request.setAttribute("secciones", lsecciones);
		request.setAttribute("lecciones", llecciones);
		request.setAttribute("materiales", lmateriales);

		em.close();
	 return ruta;
	}

}
