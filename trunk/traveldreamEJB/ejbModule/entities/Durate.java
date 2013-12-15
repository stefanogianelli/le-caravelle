package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the durate database table.
 * 
 */
@Entity
@NamedQuery(name="Durate.findAll", query="SELECT d FROM Durate d")
public class Durate implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DuratePK id;

	//bi-directional many-to-one association to PacchettiPredefiniti
	@ManyToOne
	@JoinColumn(name="idPacchettoPredefinito")
	private PacchettiPredefiniti pacchettoPredefinito;

	public Durate() {
	}

	public DuratePK getId() {
		return this.id;
	}

	public void setId(DuratePK id) {
		this.id = id;
	}

	public PacchettiPredefiniti getPacchettoPredefinito() {
		return this.pacchettoPredefinito;
	}

	public void setPacchettoPredefinito(PacchettiPredefiniti pacchettoPredefinito) {
		this.pacchettoPredefinito = pacchettoPredefinito;
	}

}