package codped;

import javax.ejb.Remote;

@Remote
public interface ValesPedidoOperacionBeanRemote {
	
	public String generacionCodigoPedido() ;

}
