package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the date_partenza database table.
 * 
 */
@Embeddable
public class DatePartenzaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	private java.util.Date data;

	@Column(insertable=false, updatable=false)
	private int idPacchettoPredefinito;

	public DatePartenzaPK() {
	}
	
	public java.util.Date getData() {
		return this.data;
	}
	
	public void setData(java.util.Date data) {
		this.data = data;
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
		if (!(other instanceof DatePartenzaPK)) {
			return false;
		}
		DatePartenzaPK castOther = (DatePartenzaPK)other;
		return 
			this.data.equals(castOther.data)
			&& (this.idPacchettoPredefinito == castOther.idPacchettoPredefinito);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.data.hashCode();
		hash = hash * prime + this.idPacchettoPredefinito;
		
		return hash;
	}
}