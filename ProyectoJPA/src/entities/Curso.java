package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CURSO database table.
 * 
 */
@Entity
@Table(name="CURSO")
//@NamedQuery(name="Curso.findAll", query="SELECT c FROM Curso c")
public class Curso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Titulo")
	private String titulo;

	@Column(name="Categoria")
	private String categoria;

	@Column(name="Descripcion")
	private String descripcion;

	@Column(name="Destacado")
	private String destacado;

	@Column(name="Dificultad")
	private String dificultad;

	@Column(name="Email_paypal")
	private String email_paypal;

	@Column(name="Horas")
	private int horas;

	@Column(name="Imagen")
	private String imagen;

	@Column(name="Precio")
	private int precio;

	@Column(name="Validado")
	private String validado;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="Profesor")
	private Usuario usuario;

	//bi-directional many-to-one association to CursoAlumno
	@OneToMany(mappedBy="curso")
	private List<CursoAlumno> cursoAlumnos;

	public Curso() {
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDestacado() {
		return this.destacado;
	}

	public void setDestacado(String destacado) {
		this.destacado = destacado;
	}

	public String getDificultad() {
		return this.dificultad;
	}

	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}

	public String getEmail_paypal() {
		return this.email_paypal;
	}

	public void setEmail_paypal(String email_paypal) {
		this.email_paypal = email_paypal;
	}

	public int getHoras() {
		return this.horas;
	}

	public void setHoras(int horas) {
		this.horas = horas;
	}

	public String getImagen() {
		return this.imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public int getPrecio() {
		return this.precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public String getValidado() {
		return this.validado;
	}

	public void setValidado(String validado) {
		this.validado = validado;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<CursoAlumno> getCursoAlumnos() {
		return this.cursoAlumnos;
	}

	public void setCursoAlumnos(List<CursoAlumno> cursoAlumnos) {
		this.cursoAlumnos = cursoAlumnos;
	}

	public CursoAlumno addCursoAlumno(CursoAlumno cursoAlumno) {
		getCursoAlumnos().add(cursoAlumno);
		cursoAlumno.setCurso(this);

		return cursoAlumno;
	}

	public CursoAlumno removeCursoAlumno(CursoAlumno cursoAlumno) {
		getCursoAlumnos().remove(cursoAlumno);
		cursoAlumno.setCurso(null);

		return cursoAlumno;
	}

}