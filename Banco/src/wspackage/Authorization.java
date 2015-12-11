package wspackage;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/codigo")
public class Authorization {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String greeting(){
		return "Your WS says hello";
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/operacion/{importe}/{tarjeta}/{codpedido}")
	public String generarCodigoOperacion(@PathParam("importe") String importe,@PathParam("tarjeta") String tarjeta, @PathParam("codpedido") String codpedido){
		
		//COMPROBAR LA TARJETA EN LA WEB QUE EMPIEZA POR A o B Y LONGITUD 20
		ValesPedidoOperacion _valesPedidoOperacion = new ValesPedidoOperacion();
	   	String codigoOp = _valesPedidoOperacion.generacionCodigoOperacion();
	 	Date dNow = new Date( );
	    SimpleDateFormat ft =
	    new SimpleDateFormat ("yyyymmddhhssSSSa");
    
	    return codigoOp+"-"+importe+"-"+codpedido+"-"+ft.format(dNow);
	}
}
