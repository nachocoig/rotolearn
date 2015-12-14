package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the PROMOCION database table.
 * 
 */
@Entity
@Table(name="PROMOCION")
@NamedQuery(name="Promocion.findAll", query="SELECT p FROM Promocion p")
public class Promocion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;

	@Column(name="Descuento")
	private int descuento;

	//bi-directional many-to-one association to Curso
	@ManyToOne
	@JoinColumn(name="ID_c")
	private Curso curso;

	public Promocion() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDescuento() {
		return this.descuento;
	}

	public void setDescuento(int descuento) {
		this.descuento = descuento;
	}

	public Curso getCurso() {
		return this.curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

}