package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CURSO_ALUMNO database table.
 * 
 */
@Embeddable
public class CursoAlumnoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="Titulo", insertable=false, updatable=false)
	private String titulo;

	@Column(name="Nickname", insertable=false, updatable=false)
	private String nickname;

	public CursoAlumnoPK() {
	}
	public String getTitulo() {
		return this.titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getNickname() {
		return this.nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CursoAlumnoPK)) {
			return false;
		}
		CursoAlumnoPK castOther = (CursoAlumnoPK)other;
		return 
			this.titulo.equals(castOther.titulo)
			&& this.nickname.equals(castOther.nickname);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.titulo.hashCode();
		hash = hash * prime + this.nickname.hashCode();
		
		return hash;
	}
}