package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DESCUENTO database table.
 * 
 */
@Entity
@Table(name="DESCUENTO")
@NamedQuery(name="Descuento.findAll", query="SELECT d FROM Descuento d")
public class Descuento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;

	@Column(name="Cupon")
	private String cupon;

	@Column(name="Tipo")
	private String tipo;

	//bi-directional many-to-one association to Curso
	@ManyToOne
	@JoinColumn(name="ID_c")
	private Curso curso;

	public Descuento() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCupon() {
		return this.cupon;
	}

	public void setCupon(String cupon) {
		this.cupon = cupon;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Curso getCurso() {
		return this.curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

}