package es.rotolearn.tablas;

import javax.persistence.Column;

public class Curso {

	private String titulo;
	private String usuario;
	private String categoria;
	private String descripcion;
	private String destacado;
	private String dificultad;
	private String email_paypal;
	private String horas;
	private String imagen;
	private String precio;
	private String validado;
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDestacado() {
		return destacado;
	}
	public void setDestacado(String destacado) {
		this.destacado = destacado;
	}
	public String getDificultad() {
		return dificultad;
	}
	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}
	public String getEmail_paypal() {
		return email_paypal;
	}
	public void setEmail_paypal(String email_paypal) {
		this.email_paypal = email_paypal;
	}
	public String getHoras() {
		return horas;
	}
	public void setHoras(String horas) {
		this.horas = horas;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public String getValidado() {
		return validado;
	}
	public void setValidado(String validado) {
		this.validado = validado;
	}
	
	
}
