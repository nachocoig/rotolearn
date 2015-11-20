
package es.rotolearn.servlet;

import es.rotolearn.servlet.RequestHandler;
import es.rotolearn.tablas.Curso;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import es.rotolearn.tablas.*;
public class AddDeseoRequestHandler
implements RequestHandler {
    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Lifted jumps to return sites
     */
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ruta = "showCurso.form";
        System.out.println("Creamos el deseo");
        String titulo = request.getParameter("titulo");
        InitialContext miInitialContext;
    	ArrayList<Curso_Alumno> des = new ArrayList<Curso_Alumno>();
		DataSource miDS;
        System.out.println("Vamos a probar a hacer la insercion por DATASOURCE");
        try {
        	
            miInitialContext = new InitialContext();
            miDS = (DataSource)miInitialContext.lookup("RotolearnJNDI");
            Connection conexion = miDS.getConnection();
            Statement myStatement = conexion.createStatement();
            HttpSession miSession = request.getSession(false);
            System.out.println(String.valueOf(titulo) + miSession.getAttribute("usuario"));
            myStatement.executeUpdate("INSERT INTO CURSO_ALUMNO VALUES ('" + miSession.getAttribute("usuario") + "','" + titulo + "','lista deseos')");
			myStatement.close();
			conexion.close();
			request.setAttribute("deseo", "ok");
        }
    catch (NamingException e) {
		System.out.println("SALTA EXCEPCION NAMING");
		e.printStackTrace();
		request.setAttribute("deseo", "no");

	} catch (SQLWarning sqlWarning) {
		while (sqlWarning != null) {
			System.out.println("SALTA EXCEPCION SQLWARNING");
			System.out.println("Error: " + sqlWarning.getErrorCode());
			System.out.println("Descripcion: " + sqlWarning.getMessage());
			System.out.println("SQLstate: " + sqlWarning.getSQLState());
			sqlWarning = sqlWarning.getNextWarning();
			request.setAttribute("deseo", "no");
		}
	} catch (SQLException sqlException) {
		while (sqlException != null) {
			System.out.println("SALTA EXCEPCION SQLEXCEPTION");
			System.out.println("Error: " + sqlException.getErrorCode());
			System.out.println("Descripcion: " + sqlException.getMessage());
			System.out.println("SQLstate: " + sqlException.getSQLState());
			sqlException = sqlException.getNextException();
			request.setAttribute("deseo", "no");
		}
	}
       
     return ruta;
    }
}