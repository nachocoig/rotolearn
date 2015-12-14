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

	@Lob
	@Column(name="Imagen")
	private byte[] imagen;

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
	@OneToMany(mappedBy="usuario")
	private List<Curso> cursos;

	//bi-directional many-to-one association to CursoAlumno
	@OneToMany(mappedBy="usuario")
	private List<CursoAlumno> cursoAlumnos;

	//bi-directional many-to-one association to Notificacion
	@OneToMany(mappedBy="usuario")
	private List<Notificacion> notificacions;

	//bi-directional many-to-one association to ProfesorAsociado
	@OneToMany(mappedBy="usuario")
	private List<ProfesorAsociado> profesorAsociados;

	//bi-directional many-to-one association to Conciliacion
	@OneToMany(mappedBy="usuario")
	private List<Conciliacion> conciliacions;

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

	public byte[] getImagen() {
		return this.imagen;
	}

	public void setImagen(byte[] imagen) {
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

	public List<Notificacion> getNotificacions() {
		return this.notificacions;
	}

	public void setNotificacions(List<Notificacion> notificacions) {
		this.notificacions = notificacions;
	}

	public Notificacion addNotificacion(Notificacion notificacion) {
		getNotificacions().add(notificacion);
		notificacion.setUsuario(this);

		return notificacion;
	}

	public Notificacion removeNotificacion(Notificacion notificacion) {
		getNotificacions().remove(notificacion);
		notificacion.setUsuario(null);

		return notificacion;
	}

	public List<ProfesorAsociado> getProfesorAsociados() {
		return this.profesorAsociados;
	}

	public void setProfesorAsociados(List<ProfesorAsociado> profesorAsociados) {
		this.profesorAsociados = profesorAsociados;
	}

	public ProfesorAsociado addProfesorAsociado(ProfesorAsociado profesorAsociado) {
		getProfesorAsociados().add(profesorAsociado);
		profesorAsociado.setUsuario(this);

		return profesorAsociado;
	}

	public ProfesorAsociado removeProfesorAsociado(ProfesorAsociado profesorAsociado) {
		getProfesorAsociados().remove(profesorAsociado);
		profesorAsociado.setUsuario(null);

		return profesorAsociado;
	}

	public List<Conciliacion> getConciliacions() {
		return this.conciliacions;
	}

	public void setConciliacions(List<Conciliacion> conciliacions) {
		this.conciliacions = conciliacions;
	}

	public Conciliacion addConciliacion(Conciliacion conciliacion) {
		getConciliacions().add(conciliacion);
		conciliacion.setUsuario(this);

		return conciliacion;
	}

	public Conciliacion removeConciliacion(Conciliacion conciliacion) {
		getConciliacions().remove(conciliacion);
		conciliacion.setUsuario(null);

		return conciliacion;
	}

}