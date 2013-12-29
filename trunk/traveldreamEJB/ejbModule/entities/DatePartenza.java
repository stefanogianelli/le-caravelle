package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the date_partenza database table.
 * 
 */
@Entity
@Table(name="date_partenza")
@NamedQueries ({
	@NamedQuery(name="DatePartenza.elenco", query="SELECT d FROM DatePartenza d"),
	@NamedQuery(name="DatePartenza.getDataPartenza", query="SELECT d FROM DatePartenza d WHERE d.pacchettoPredefinito = :pacchetto AND d.id.data = :data")
})
public class DatePartenza implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DatePartenzaPK id;

	//relazione bidirezionale many-to-one con l'entità PacchettiPredefiniti
	@ManyToOne
	@JoinColumn(name="idPacchettoPredefinito")
	private PacchettiPredefiniti pacchettoPredefinito;

	public DatePartenza() {
		id = new DatePartenzaPK();
	}

	public DatePartenzaPK getId() {
		return this.id;
	}

	public void setId(DatePartenzaPK id) {
		this.id = id;
	}

	public PacchettiPredefiniti getPacchettoPredefinito() {
		return this.pacchettoPredefinito;
	}

	public void setPacchettoPredefinito(PacchettiPredefiniti pacchettoPredefinito) {
		this.pacchettoPredefinito = pacchettoPredefinito;
		this.getId().setIdPacchettoPredefinito(pacchettoPredefinito.getId());
	}
	
	public Date getData () {
		return this.getId().getData();
	}
	
	public void setData (Date data) {
		this.getId().setData(data);
	}

}