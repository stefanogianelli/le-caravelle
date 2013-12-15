package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the date_partenza database table.
 * 
 */
@Entity
@Table(name="date_partenza")
@NamedQuery(name="DatePartenza.findAll", query="SELECT d FROM DatePartenza d")
public class DatePartenza implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DatePartenzaPK id;

	//bi-directional many-to-one association to PacchettiPredefiniti
	@ManyToOne
	@JoinColumn(name="idPacchettoPredefinito")
	private PacchettiPredefiniti pacchettoPredefinito;

	public DatePartenza() {
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
	}

}