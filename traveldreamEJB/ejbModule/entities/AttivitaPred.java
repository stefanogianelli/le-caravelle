package entities;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the attivita_pred database table.
 * 
 */
@Entity
@Table(name="attivita_pred")
@NamedQueries ({
	@NamedQuery(name="AttivitaPred.elenco", query="SELECT a FROM AttivitaPred a"),
	@NamedQuery(name="AttivitaPred.getAttivita", query="SELECT a FROM AttivitaPred a WHERE a.pacchetto = :pacchetto AND a.escursione = :escursione")
})
public class AttivitaPred implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AttivitaPredPK id;
	
	@ManyToOne
	@JoinColumn(name="idPacchettoPredefinito")
	private PacchettiPredefiniti pacchetto;
	
	@ManyToOne
	@JoinColumn(name="idEscursione")
	private Escursioni escursione;

	public AttivitaPred() {
	}

	public AttivitaPredPK getId() {
		return this.id;
	}

	public void setId(AttivitaPredPK id) {
		this.id = id;
	}

	public PacchettiPredefiniti getPacchetto() {
		return pacchetto;
	}

	public void setPacchetto(PacchettiPredefiniti pacchetto) {
		this.pacchetto = pacchetto;
	}

	public Escursioni getEscursione() {
		return escursione;
	}

	public void setEscursione(Escursioni escursione) {
		this.escursione = escursione;
	}

}