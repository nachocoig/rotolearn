//Aqui se realiza tanto la comprobacion de notificaciones como la actualizacion al verlas.

package es.rotolearn.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.*;
import es.rotolearn.controlImagenes.MultipartRequest;
import es.rotolearn.javaBean.RegistroBean;

public class NotificacionRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO no se si hacer la insercion tambien aqui o en las cosas?
		String ruta=request.getServletPath();
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		RegistroBean user = (RegistroBean) session.getAttribute("perfil");
		
		/*Consulta a la BBDD con JPA*/
		
		// 1 Create the factory of Entity Manager
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");

		// 2 Create the Entity Manager
		EntityManager em = factory.createEntityManager();
				
		// 3 Get one EntityTransaction
		em.getTransaction().begin();
		
		// Variables
		
		List<Notificacion> notificaciones= null;
		List<Notificacion> noLeidas= null;
		ArrayList<Notificacion> listaNoLeidas = new ArrayList<Notificacion>();
		ArrayList<Notificacion> listaLeidas = new ArrayList<Notificacion>();
		
		//******************************************************************************************************************************
		//***************************************insertar notificaciones****************************************************************
		//******************************************************************************************************************************
		
		if(ruta.equals("/enviarAlerta.form")){
			MultipartRequest mr = new MultipartRequest(request);
			
			int ID = Integer.parseInt(mr.getParameterValues("curso")[0]);
			ruta="profes_administarCurso.form?id="+ID;
			List<CursoAlumno> alumnos = em.createQuery("SELECT i FROM CursoAlumno i WHERE i.id.ID_c = ?1 AND i.estado='inscrito'").setParameter(1, ID).getResultList();
			List<ProfesorAsociado> profes = em.createQuery("SELECT i FROM ProfesorAsociado i WHERE i.id.ID_c = ?1 AND i.validado='SI'").setParameter(1, ID).getResultList();
			Iterator<CursoAlumno> it1 = alumnos.iterator();
			Iterator<ProfesorAsociado> it2 = profes.iterator();
			while(it1.hasNext()){			
				Notificacion nueva = new Notificacion();
				nueva.setDescripcion(mr.getParameterValues("descripcion")[0]);
				nueva.setLeido(0);
				nueva.setUsuario(em.find(Usuario.class,it1.next().getId().getID_u()));
				em.persist(nueva);
			}
			while(it2.hasNext()){		
				Notificacion nueva = new Notificacion();
				nueva.setDescripcion(mr.getParameterValues("descripcion")[0]);
				nueva.setLeido(0);
				nueva.setUsuario(em.find(Usuario.class,it2.next().getId().getID_p()));
				em.persist(nueva);
			}
			em.getTransaction().commit();
			em.close();
		}
		
		else{
			//******************************************************************************************************************************
			//******************************************leer notificaciones*****************************************************************
			//******************************************************************************************************************************
			//if(ruta.equals("/marcarLeido.form")){
			//	ruta="perfil.form";
			MultipartRequest mr = new MultipartRequest(request);
			
			String leido = mr.getParameter("leido");
		if(leido != null){
			System.out.println("Entro en leido con valor" + leido);
			if (leido.equals("SI")){
			
				List<Notificacion> leidos = null;
				try{
					leidos =em.createQuery("SELECT i FROM Notificacion i WHERE i.usuario.id = ?1 AND i.leido=2").setParameter(1, user.getId()).getResultList();	
					
				}catch(Exception E){
					
				}
				System.out.println("Entro aqui");
				if(leidos != null){
					Iterator<Notificacion> it= leidos.iterator();
					int size = 0;
					while(it.hasNext()){
						Notificacion aux = em.find(Notificacion.class, it.next().getId());
						aux.setLeido(1);
						//em.getTransaction().commit();
					}			
				}
			}
		//}
	}
	System.out.println("Aqui no llego");
			if(ruta.equals("/getNotificacionPerfil.form") || ruta.equals("/marcarLeido.form")){
				System.out.println("Aqui tampoco"); 				
				try{
				notificaciones = em.createQuery("SELECT i FROM Notificacion i WHERE i.usuario.id = ?1 AND i.leido=1").setParameter(1, user.getId()).getResultList();	
				System.out.println("Notificaciones size: " + notificaciones.size());
				}catch(Exception E){
					System.out.println("WEJEJEJE YOLO1");
					ruta = "perfil.jsp";
				}	
				try{
				noLeidas = em.createQuery("SELECT i FROM Notificacion i WHERE i.usuario.id = ?1 AND i.leido<>1").setParameter(1, user.getId()).getResultList();	
				System.out.println("Notificaciones size: " + noLeidas.size());
				}catch(Exception E){
					System.out.println("WEJEJEJE YOLO2");
					ruta = "perfil.jsp";
				}
				if(notificaciones.size() == 0 && noLeidas.size() == 0){
					request.setAttribute("Notificaciones", "no");
				}
				else {
					if(notificaciones.size() != 0 && noLeidas.size() == 0){				
						request.setAttribute("Notificaciones", "0");
					}
					else{ //Listamos las notificaciones no leidas y establecemos el numero.
						Iterator<Notificacion> it= noLeidas.iterator();
						int size = 0;
						while(it.hasNext()){
							Notificacion aux = it.next();
							listaNoLeidas.add(aux);
							System.out.println("ID : " + aux.getId());
							Notificacion aux2 = em.find(Notificacion.class, aux.getId());
							aux2.setLeido(2);
							
							size++;
						}
						request.setAttribute("Notificaciones", Integer.toString(size));
						request.setAttribute("ListaNoLeidas", listaNoLeidas);
					}
					//Listamos las notificaciones leidas
					Iterator<Notificacion> it= notificaciones.iterator();
					int size = 0;
					while(it.hasNext()){
						Notificacion aux = it.next();
						
						listaLeidas.add(aux);
						size++;
					}
					request.setAttribute("ListaNotificaciones", listaLeidas);
					//En jsp hay que comprobar que lista leidas y lista no leidas no son null.
				}
			em.getTransaction().commit();
			em.close();
			ruta = "perfil.jsp";
			}
		}
		return ruta;
	}

}
