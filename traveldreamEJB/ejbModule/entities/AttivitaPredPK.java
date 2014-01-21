package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the attivita_pred database table.
 * 
 */
@Embeddable
public class AttivitaPredPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int idEscursione;

	@Column(insertable=false, updatable=false)
	private int idPacchettoPredefinito;
	
	public int getIdEscursione() {
		return this.idEscursione;
	}
	
	public void setIdEscursione(int idEscursione) {
		this.idEscursione = idEscursione;
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
		if (!(other instanceof AttivitaPredPK)) {
			return false;
		}
		AttivitaPredPK castOther = (AttivitaPredPK)other;
		return 
			(this.idEscursione == castOther.idEscursione)
			&& (this.idPacchettoPredefinito == castOther.idPacchettoPredefinito);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idEscursione;
		hash = hash * prime + this.idPacchettoPredefinito;
		
		return hash;
	}
}