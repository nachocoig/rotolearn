package wspackage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ValesPedidoOperacion{

    public ValesPedidoOperacion() {}

    private String generacionCodigo(){

    	Date dNow = new Date( );
        SimpleDateFormat ft =
        new SimpleDateFormat ("yyyymmddhhssSSSa");
        return ft.format(dNow);

    }
    
    public String generacionCodigoOperacion() {

        return "BANCO" + generacionCodigo();

     }

}
