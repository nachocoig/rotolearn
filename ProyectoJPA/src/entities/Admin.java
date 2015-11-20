package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ADMIN database table.
 * 
 */
@Entity
@Table(name="ADMIN")
@NamedQuery(name="Admin.findAll", query="SELECT a FROM Admin a")
public class Admin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Nickname")
	private String nickname;

	@Column(name="Pass")
	private String pass;

	@Column(name="Prioridad")
	private int prioridad;

	public Admin() {
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPass() {
		return this.pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getPrioridad() {
		return this.prioridad;
	}

	public void setPrioridad(int prioridad) {
		this.prioridad = prioridad;
	}

}