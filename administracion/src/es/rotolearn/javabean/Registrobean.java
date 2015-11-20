package es.rotolearn.javabean;

public class Registrobean {
	
	private String nickName;
	private String pass;
	private int prioridad;
	
	public Registrobean(){}
	
	public Registrobean(String nickName, String pass, int prioridad) {
		super();
		this.pass = pass;
		this.nickName = nickName;
		this.prioridad = prioridad;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public int getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(int prioridad) {
		this.prioridad = prioridad;
	}


}
