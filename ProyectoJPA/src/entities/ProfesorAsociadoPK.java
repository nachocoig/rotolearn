package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the PROFESOR_ASOCIADO database table.
 * 
 */
@Embeddable
public class ProfesorAsociadoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int ID_c;

	@Column(insertable=false, updatable=false)
	private int ID_p;

	public ProfesorAsociadoPK() {
	}
	public int getID_c() {
		return this.ID_c;
	}
	public void setID_c(int ID_c) {
		this.ID_c = ID_c;
	}
	public int getID_p() {
		return this.ID_p;
	}
	public void setID_p(int ID_p) {
		this.ID_p = ID_p;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProfesorAsociadoPK)) {
			return false;
		}
		ProfesorAsociadoPK castOther = (ProfesorAsociadoPK)other;
		return 
			(this.ID_c == castOther.ID_c)
			&& (this.ID_p == castOther.ID_p);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ID_c;
		hash = hash * prime + this.ID_p;
		
		return hash;
	}
}