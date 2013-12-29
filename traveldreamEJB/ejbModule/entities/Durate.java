package entities;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the durate database table.
 * 
 */
@Entity
@NamedQueries ({
	@NamedQuery(name="Durate.findAll", query="SELECT d FROM Durate d"),
	@NamedQuery(name="Durate.getDurata", query="SELECT d FROM Durate d WHERE d.pacchettoPredefinito = :pacchetto AND d.id.durata = :durata")
})
public class Durate implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DuratePK id;

	//relazione bidirezionale many-to-one con l'entità PacchettiPredefiniti
	@ManyToOne
	@JoinColumn(name="idPacchettoPredefinito")
	private PacchettiPredefiniti pacchettoPredefinito;

	public Durate() {
		id = new DuratePK();
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
		this.getId().setIdPacchettoPredefinito(pacchettoPredefinito.getId());
	}
	
	public int getDurata () {
		return this.getId().getDurata();
	}
	
	public void setDurata (int durata) {
		this.getId().setDurata(durata);
	}
}