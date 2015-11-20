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

	@Column(insertable=false, updatable=false)
	private int ID_u;

	@Column(insertable=false, updatable=false)
	private int ID_c;

	public CursoAlumnoPK() {
	}
	public int getID_u() {
		return this.ID_u;
	}
	public void setID_u(int ID_u) {
		this.ID_u = ID_u;
	}
	public int getID_c() {
		return this.ID_c;
	}
	public void setID_c(int ID_c) {
		this.ID_c = ID_c;
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
			(this.ID_u == castOther.ID_u)
			&& (this.ID_c == castOther.ID_c);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ID_u;
		hash = hash * prime + this.ID_c;
		
		return hash;
	}
}