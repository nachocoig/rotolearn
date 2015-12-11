package codped;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class ValesPedidoOperacionBean
 */

@Stateless(name="ValesPedidoOperacionBean", mappedName="ejb/ValesPedidoOperacionBean")
@Remote(codped.ValesPedidoOperacionBeanRemote.class)
public class ValesPedidoOperacionBean implements ValesPedidoOperacionBeanRemote{

    public ValesPedidoOperacionBean() {}

    private String generacionCodigo(){

    	Date dNow = new Date( );
        SimpleDateFormat ft =
        new SimpleDateFormat ("yyyymmddhhssSSSa");
        return ft.format(dNow);

    }
    
    public String generacionCodigoPedido() {
	
    	return "ORDER" + generacionCodigo();
    }

}
