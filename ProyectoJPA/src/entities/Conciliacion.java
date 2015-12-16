package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CONCILIACION database table.
 * 
 */
@Entity
@Table(name="CONCILIACION")
@NamedQuery(name="Conciliacion.findAll", query="SELECT c FROM Conciliacion c")
public class Conciliacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;

	@Column(name="Anio")
	private String anio;

	@Column(name="CodOp")
	private String codOp;

	@Column(name="CodPed")
	private String codPed;

	@Column(name="Descuento")
	private String descuento;

	@Column(name="Importe")
	private int importe;

	@Column(name="Mes")
	private String mes;

	@Column(name="Pagado")
	private String pagado;

	@Column(name="Promocion")
	private int promocion;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="Cobrador")
	private Usuario usuario;

	public Conciliacion() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAnio() {
		return this.anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getCodOp() {
		return this.codOp;
	}

	public void setCodOp(String codOp) {
		this.codOp = codOp;
	}

	public String getCodPed() {
		return this.codPed;
	}

	public void setCodPed(String codPed) {
		this.codPed = codPed;
	}

	public String getDescuento() {
		return this.descuento;
	}

	public void setDescuento(String descuento) {
		this.descuento = descuento;
	}

	public int getImporte() {
		return this.importe;
	}

	public void setImporte(int importe) {
		this.importe = importe;
	}

	public String getMes() {
		return this.mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getPagado() {
		return this.pagado;
	}

	public void setPagado(String pagado) {
		this.pagado = pagado;
	}

	public int getPromocion() {
		return this.promocion;
	}

	public void setPromocion(int promocion) {
		this.promocion = promocion;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}