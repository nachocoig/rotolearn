package es.rotolearn.javaBean;

public class RegistroBean{

	private String tipo;
	private String nickName;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String email;
	private String nacimiento;
	private String direccion;
	private String descripcion;
	private String intereses;
	private String telefono;
	
	public RegistroBean(){}
	
	public RegistroBean(String tipo, String nickName, String nombre,
			String apellido1, String apellido2, String email, 
			String nacimiento, String direccion, String descripcion,
			String intereses, String telefono) {
		super();
		this.tipo = tipo;
		this.nickName = nickName;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.email = email;
		this.nacimiento = nacimiento;
		this.direccion = direccion;
		this.descripcion = descripcion;
		this.intereses = intereses;
		this.telefono = telefono;
	}
	
	public String getNickName(){
		return nickName;
	}
	
	public void setNickName(String aux){
		nickName = aux;
	}
	
	public String getNombre(){
		return nombre;
	}
	 
	public void setNombre(String aux){
		nombre = aux;
	}
	 
	public String getApellido1(){
		return apellido1;
	}
	 
	public void setApellido1(String aux){
		apellido1 = aux;
	}
	 
	public String getApellido2(){
		return apellido2;
	}
	 
	public void setApellido2(String aux){
		apellido2 = aux;
	}
	 
	public String getEmail(){
		return email;
	}
	 
	public void setEmail(String aux){
		email = aux;
	}
	 

	public String getNacimiento(){
		return nacimiento;
	}
	 
	public void setNacimiento(String aux){
		nacimiento = aux;
	}
	 
	public String getDireccion(){
		return direccion;
	}
	 
	public void setDireccion(String aux){
		direccion = aux;
	}
	 
	public String getDescripcion(){
		return descripcion;
	}
	 
	public void setDescripcion(String aux){
		descripcion = aux;
	}
	 
	public String getIntereses(){
		return intereses;
	}
	 
	public void setIntereses(String aux){
		intereses = aux;
	}
	 
	public String getTelefono(){
		return telefono;
	}
	 
	public void setTelefono(String aux){
		telefono = aux;
	}
	  
	public String getTipo(){
	    return tipo;
	}
	      
	public void setTipo(String aux){
	    tipo = aux;
	}
}
