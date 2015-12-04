package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the MATERIAL database table.
 * 
 */
@Entity
@Table(name="MATERIAL")
@NamedQuery(name="Material.findAll", query="SELECT m FROM Material m")
public class Material implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;

	@Lob
	@Column(name="Contenido")
	private byte[] contenido;

	@Column(name="Nombre")
	private String nombre;

	@Column(name="Tipo")
	private String tipo;

	//bi-directional many-to-one association to Leccion
	@ManyToOne
	@JoinColumn(name="LECCION_ID")
	private Leccion leccion;

	public Material() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getContenido() {
		return this.contenido;
	}

	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Leccion getLeccion() {
		return this.leccion;
	}

	public void setLeccion(Leccion leccion) {
		this.leccion = leccion;
	}

}