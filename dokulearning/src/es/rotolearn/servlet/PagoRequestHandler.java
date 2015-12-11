package es.rotolearn.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import codped.ValesPedidoOperacionBeanRemote;
import es.rotolearn.javaBean.*;

import org.glassfish.jersey.client.*;

public class PagoRequestHandler implements RequestHandler {
	private ValesPedidoOperacionBeanRemote _vales;

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1 . Pedir codigo de pedido
			//String codigoPedido
		try {
			_vales = (ValesPedidoOperacionBeanRemote) InitialContext.doLookup("ejb/ValesPedidoOperacionBean");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			System.out.println("Traza: "+ e.getMessage());
			System.out.println("Traza: "+ e.getRootCause().toString());
			System.out.println("Traza: "+ e.getCause().toString());
		}
		String codPed = _vales.generacionCodigoPedido();
		System.out.println("Remote EJB: "+ codPed);
		
		//2 . Pedir codigo de operacion
		//System.out.println("ENTRO A PEDIR EL CODIGO");
		//AuthorizationClient cliente = new AuthorizationClient();
		//cliente.generar();
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		
		WebTarget wt = client.target("http://localhost:8080/Banco/ws");
		
		
		String result = wt.path("codigo").path("operacion").path("300").path("A57575757").path(codPed).request().accept(MediaType.TEXT_PLAIN).get(String.class);
		System.out.println(result);
		//descomponer result OPERACION201512110409590902PM;300;ORDER777;201509110459902+0100
		String mensajes[] = result.split("-");
		
		//String codigoOp
		
		//3 . Escribir en la cola
		//String mensaje = codigpPedido + "-" + codigoOp + "-" + request.getAtributte("precio") + "-" + request.getAtributte("numbreCurso");
		String mensaje = codPed+mensajes[1]+"400-Java noobs";
		InteraccionJMS mq=new InteraccionJMS();
		/* CUANDO ESTE INTEGRADO EL CHAT HAY QUE PASARLE A ESCRITURA POR PARAMETRO EL ID DEL CURSO EN VEZ DE AAAAAAAA*/
		System.out.println("Me escriben esto:"+mensaje);
		mq.escrituraPago(mensaje);
		//4 . Redirigir
		

		
		return "index.jsp";
	}

}
