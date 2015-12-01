package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the PROFESOR_ASOCIADO database table.
 * 
 */
@Entity
@Table(name="PROFESOR_ASOCIADO")
@NamedQuery(name="ProfesorAsociado.findAll", query="SELECT p FROM ProfesorAsociado p")
public class ProfesorAsociado implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProfesorAsociadoPK id;

	@Column(name="Validado")
	private String validado;

	//bi-directional many-to-one association to Curso
	@ManyToOne
	@JoinColumn(name="ID_c")
	private Curso curso;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="ID_p")
	private Usuario usuario;

	public ProfesorAsociado() {
	}

	public ProfesorAsociadoPK getId() {
		return this.id;
	}

	public void setId(ProfesorAsociadoPK id) {
		this.id = id;
	}

	public String getValidado() {
		return this.validado;
	}

	public void setValidado(String validado) {
		this.validado = validado;
	}

	public Curso getCurso() {
		return this.curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}