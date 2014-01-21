package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the attivita database table.
 * 
 */
@Embeddable
public class AttivitaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int idDestinazione;

	@Column(insertable=false, updatable=false)
	private int idEscursione;

	public int getIdDestinazione() {
		return this.idDestinazione;
	}
	
	public void setIdDestinazione(int idDestinazione) {
		this.idDestinazione = idDestinazione;
	}
	
	public int getIdEscursione() {
		return this.idEscursione;
	}
	
	public void setIdEscursione(int idEscursione) {
		this.idEscursione = idEscursione;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AttivitaPK)) {
			return false;
		}
		AttivitaPK castOther = (AttivitaPK)other;
		return 
			(this.idDestinazione == castOther.idDestinazione)
			&& (this.idEscursione == castOther.idEscursione);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idDestinazione;
		hash = hash * prime + this.idEscursione;
		
		return hash;
	}
}