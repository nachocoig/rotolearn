package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the SECCION database table.
 * 
 */
@Entity
@Table(name="SECCION")
@NamedQuery(name="Seccion.findAll", query="SELECT s FROM Seccion s")
public class Seccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;

	@Column(name="Nombre")
	private String nombre;

	//bi-directional many-to-one association to Leccion
	@OneToMany(mappedBy="seccion")
	private List<Leccion> leccions;

	//bi-directional many-to-one association to Curso
	@ManyToOne
	@JoinColumn(name="CURSO_ID")
	private Curso curso;

	public Seccion() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Leccion> getLeccions() {
		return this.leccions;
	}

	public void setLeccions(List<Leccion> leccions) {
		this.leccions = leccions;
	}

	public Leccion addLeccion(Leccion leccion) {
		getLeccions().add(leccion);
		leccion.setSeccion(this);

		return leccion;
	}

	public Leccion removeLeccion(Leccion leccion) {
		getLeccions().remove(leccion);
		leccion.setSeccion(null);

		return leccion;
	}

	public Curso getCurso() {
		return this.curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

}