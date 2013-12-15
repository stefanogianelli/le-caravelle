package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the durate database table.
 * 
 */
@Embeddable
public class DuratePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int durata;

	@Column(insertable=false, updatable=false)
	private int idPacchettoPredefinito;

	public DuratePK() {
	}
	
	public int getDurata() {
		return this.durata;
	}
	
	public void setDurata(int durata) {
		this.durata = durata;
	}
	
	public int getIdPacchettoPredefinito() {
		return this.idPacchettoPredefinito;
	}
	
	public void setIdPacchettoPredefinito(int idPacchettoPredefinito) {
		this.idPacchettoPredefinito = idPacchettoPredefinito;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DuratePK)) {
			return false;
		}
		DuratePK castOther = (DuratePK)other;
		return 
			(this.durata == castOther.durata)
			&& (this.idPacchettoPredefinito == castOther.idPacchettoPredefinito);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.durata;
		hash = hash * prime + this.idPacchettoPredefinito;
		
		return hash;
	}
}