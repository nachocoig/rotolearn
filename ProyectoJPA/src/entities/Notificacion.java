package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the NOTIFICACION database table.
 * 
 */
@Entity
@Table(name="NOTIFICACION")
@NamedQuery(name="Notificacion.findAll", query="SELECT n FROM Notificacion n")
public class Notificacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;

	@Column(name="Descripcion")
	private String descripcion;

	@Column(name="Leido")
	private int leido;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="Destinatario")
	private Usuario usuario;

	public Notificacion() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getLeido() {
		return this.leido;
	}

	public void setLeido(int leido) {
		this.leido = leido;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}