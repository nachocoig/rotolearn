package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the USUARIO database table.
 * 
 */
@Entity
@Table(name="USUARIO")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;

	@Column(name="Apellido1")
	private String apellido1;

	@Column(name="Apellido2")
	private String apellido2;

	@Column(name="Descripcion")
	private String descripcion;

	@Column(name="Direccion")
	private String direccion;

	@Column(name="Email")
	private String email;

	@Column(name="Fecha_nac")
	private String fecha_nac;

	@Column(name="Imagen")
	private String imagen;

	@Column(name="Intereses")
	private String intereses;

	@Column(name="Nickname")
	private String nickname;

	@Column(name="Nombre")
	private String nombre;

	@Column(name="Pass")
	private String pass;

	@Column(name="Telefono")
	private int telefono;

	@Column(name="Tipo")
	private String tipo;

	//bi-directional many-to-one association to Curso
	@OneToMany(mappedBy="usuario",cascade=CascadeType.ALL)
	private List<Curso> cursos;

	//bi-directional many-to-one association to CursoAlumno
	@OneToMany(mappedBy="usuario",cascade=CascadeType.ALL)
	private List<CursoAlumno> cursoAlumnos;

	public Usuario() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApellido1() {
		return this.apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return this.apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFecha_nac() {
		return this.fecha_nac;
	}

	public void setFecha_nac(String fecha_nac) {
		this.fecha_nac = fecha_nac;
	}

	public String getImagen() {
		return this.imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getIntereses() {
		return this.intereses;
	}

	public void setIntereses(String intereses) {
		this.intereses = intereses;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPass() {
		return this.pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getTelefono() {
		return this.telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Curso> getCursos() {
		return this.cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public Curso addCurso(Curso curso) {
		getCursos().add(curso);
		curso.setUsuario(this);

		return curso;
	}

	public Curso removeCurso(Curso curso) {
		getCursos().remove(curso);
		curso.setUsuario(null);

		return curso;
	}

	public List<CursoAlumno> getCursoAlumnos() {
		return this.cursoAlumnos;
	}

	public void setCursoAlumnos(List<CursoAlumno> cursoAlumnos) {
		this.cursoAlumnos = cursoAlumnos;
	}

	public CursoAlumno addCursoAlumno(CursoAlumno cursoAlumno) {
		getCursoAlumnos().add(cursoAlumno);
		cursoAlumno.setUsuario(this);

		return cursoAlumno;
	}

	public CursoAlumno removeCursoAlumno(CursoAlumno cursoAlumno) {
		getCursoAlumnos().remove(cursoAlumno);
		cursoAlumno.setUsuario(null);

		return cursoAlumno;
	}

}